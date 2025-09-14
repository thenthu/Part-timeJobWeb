/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.repositories.impl;

import com.thenthu.pojo.Account;
import com.thenthu.pojo.Candidate;
import com.thenthu.pojo.Employer;
import com.thenthu.pojo.Job;
import com.thenthu.repositories.StatsRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import java.util.List;
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
public class StatsRepositoryImpl implements StatsRepository{
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Object[]> countJobsByTime(String time, int year) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = b.createQuery(Object[].class);
        Root<Job> root = q.from(Job.class);

        q.multiselect(
                b.function("MONTH", Integer.class, root.get("dPostedDate")),
                b.count(root.get("id"))
        );
        q.where(
                b.equal(
                        b.function("YEAR", Integer.class, root.get("dPostedDate")),
                        year
                )
        );
        q.groupBy(
                b.function("MONTH", Integer.class, root.get("dPostedDate"))
        );

        Query query = s.createQuery(q);
        return query.getResultList();
    }

    @Override
    public List<Object[]> countCandidatesByTime(String time, int year) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = b.createQuery(Object[].class);
        Root<Candidate> root = q.from(Candidate.class);
        Join<Candidate, Account> accountJoin = root.join("account", JoinType.INNER);

        q.multiselect(
                b.function(time, Integer.class, accountJoin.get("dCreatedAt")),
                b.count(root.get("iCandidateId"))
        );
        q.where(
                b.equal(
                        b.function("YEAR", Integer.class, accountJoin.get("dCreatedAt")),
                        year
                )
        );
        q.groupBy(
                b.function(time, Integer.class, accountJoin.get("dCreatedAt"))
        );

        Query query = s.createQuery(q);
        return query.getResultList();
    }

    @Override
    public List<Object[]> countEmployersByTime(String time, int year) {
                Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = b.createQuery(Object[].class);
        Root<Employer> root = q.from(Employer.class);
        Join<Employer, Account> accountJoin = root.join("account", JoinType.INNER);

        q.multiselect(
                b.function(time, Integer.class, accountJoin.get("dCreatedAt")),
                b.count(root.get("iEmployerId"))
        );
        q.where(
            b.equal(
                b.function("YEAR", Integer.class, accountJoin.get("dCreatedAt")),
                year
            )
        );
        q.groupBy(
            b.function(time, Integer.class, accountJoin.get("dCreatedAt"))
        );

        Query query = s.createQuery(q);
        return query.getResultList();
    }
    
}
