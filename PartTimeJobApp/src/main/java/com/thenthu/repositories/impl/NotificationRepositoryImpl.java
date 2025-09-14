/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.repositories.impl;

import com.thenthu.pojo.Account;
import com.thenthu.pojo.Notification;
import com.thenthu.repositories.NotificationRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author this pc
 */
@Repository
@Transactional
public class NotificationRepositoryImpl implements NotificationRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Notification> getNotifications(Map<String, String> params) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Notification> cq = cb.createQuery(Notification.class);
        Root<Notification> root = cq.from(Notification.class);
        
        List<Predicate> predicates = new ArrayList<>();
        
        String kw = params.get("kw");
        if (kw != null && !kw.isEmpty()) {
            predicates.add(cb.like(root.get("sContent"), "%" + kw + "%"));
        }
        
        String employerId = params.get("iAccountId");
        if (employerId != null && !employerId.isEmpty()) {
            predicates.add(cb.equal(root.get("iAccountId").get("iAccountId"), Integer.valueOf(employerId)));
        }
        
        cq.where(predicates.toArray(new Predicate[0]));
        cq.orderBy(cb.asc(root.get("iNotifId")));

        Query<Notification> query = session.createQuery(cq);
        return query.getResultList();
    }

    @Override
    public Notification getNotiById(int id) {
        Session session = this.factory.getObject().getCurrentSession();
        return session.get(Notification.class, id);
    }

    @Override
    public void sendNotification(Notification noti) {
        Session session = this.factory.getObject().getCurrentSession();
        session.persist(noti);
    }

    @Override
    public void sendNotifications(List<Notification> notifications) {
        Session session = this.factory.getObject().getCurrentSession();
        for (int i = 0; i < notifications.size(); i++) {
            session.persist(notifications.get(i));
            // Flush/clear sau mỗi 20 records
            if ((i + 1) % 20 == 0) {
                session.flush();
                session.clear();
            }
        }
        
        session.flush();
        session.clear();
    }
    
    @Override
    public List<Notification> getNotiToUser(Account acc) {
        Session session = this.factory.getObject().getCurrentSession();
        String hql = "FROM Notification n WHERE n.iAccountId.iAccountId = :iAccountId";
        Query<Notification> query = session.createQuery(hql, Notification.class);
        query.setParameter("iAccountId", acc.getIAccountId());
        return query.getResultList();
    }

    @Override
    public void saveNoti(Notification n) {
        Session session = this.factory.getObject().getCurrentSession();
        session.merge(n);
    }
}
