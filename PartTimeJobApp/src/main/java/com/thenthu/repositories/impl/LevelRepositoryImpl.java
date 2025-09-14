/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.repositories.impl;

import com.thenthu.pojo.Joblevel;
import com.thenthu.repositories.LevelRepository;
import org.hibernate.query.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author this pc
 */
@Repository
@Transactional
public class LevelRepositoryImpl implements LevelRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Joblevel> getJoblevels(Map<String, String> params) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Joblevel> cq = cb.createQuery(Joblevel.class);
        Root<Joblevel> root = cq.from(Joblevel.class);

        List<Predicate> predicates = new ArrayList<>();

        // Tìm kiếm theo tên level
        String kw = params.get("kw");
        if (kw != null && !kw.isEmpty()) {
            predicates.add(cb.like(root.get("sLevelName"), "%" + kw + "%"));
        }

        cq.where(predicates.toArray(new Predicate[0]));
        cq.orderBy(cb.asc(root.get("iLevelId")));

        Query<Joblevel> query = session.createQuery(cq);
        return query.getResultList();
    }

    @Override
    public Joblevel getLevelById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(Joblevel.class, id);
    }

    @Override
    public void addOrUpdateLevel(Joblevel l) {
        Session s = this.factory.getObject().getCurrentSession();
        if (l.getILevelId() == null) {
            s.persist(l);
        } else {
            s.merge(l);
        }
    }

    @Override
    public void deleteLevel(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Joblevel l = getLevelById(id);
        if (l != null) {
            s.remove(l);
        }
    }
}
