/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.repositories.impl;

import com.thenthu.pojo.Review;
import com.thenthu.repositories.ReviewRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.hibernate.query.Query;
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
public class ReviewRepositoryImpl implements ReviewRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    private static final int PAGE_SIZE = 3;

    @Override
    public List<Review> getReviewsWrittenByUser(String username) {
        Session session = this.factory.getObject().getCurrentSession();
        String hql = "FROM Review r WHERE r.iFromAccountId.sUsername = :sUsername";
        Query<Review> query = session.createQuery(hql, Review.class);
        query.setParameter("sUsername", username);
        return query.getResultList();
    }

    @Override
    public List<Review> getReviewsForUser(Map<String, String> params, String username) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder cb = s.getCriteriaBuilder();
        CriteriaQuery<Review> cq = cb.createQuery(Review.class);
        Root<Review> root = cq.from(Review.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(root.get("iToAccountId").get("sUsername"), username));

        cq.where(predicates.toArray(new Predicate[0]));
        cq.orderBy(cb.asc(root.get("iReviewId")));

        Query<Review> query = s.createQuery(cq);
        
        String pageStr = params.get("page");
        int page = 1;
        if (pageStr != null) {
            try {
                page = Integer.parseInt(pageStr);
                if (page < 1) {
                    page = 1;
                }
            } catch (NumberFormatException ex) {
                page = 1;
            }
        }

        query.setFirstResult((page - 1) * PAGE_SIZE);
        query.setMaxResults(PAGE_SIZE);
        return query.getResultList();
    }

    @Override
    public Review getReviewById(int id) {
        Session session = this.factory.getObject().getCurrentSession();
        return session.get(Review.class, id);
    }

    @Override
    public Review addOrUpdateReview(Review r) {
        Session session = this.factory.getObject().getCurrentSession();
        if (r.getIReviewId() == null) {
            session.persist(r);
        } else {
            session.merge(r);
        }
        return r;
    }

    @Override
    public void deleteReview(int id) {
        Session session = this.factory.getObject().getCurrentSession();
        Review r = getReviewById(id);
        if (r != null) {
            session.remove(r);
        }
    }

    @Override
    public Review findByFromAccountAndToAccount(String fromAcc, String toAcc) {
        Session session = this.factory.getObject().getCurrentSession();
        String hql = "FROM Review r WHERE r.iFromAccountId.sUsername = :fromAcc AND r.iToAccountId.sUsername = :toAcc";
        return session.createQuery(hql, Review.class)
                .setParameter("fromAcc", fromAcc)
                .setParameter("toAcc", toAcc)
                .uniqueResult();
    }
}
