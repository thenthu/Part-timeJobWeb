/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.repositories.impl;

import com.thenthu.pojo.Candidate;
import com.thenthu.repositories.CandidateRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
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
public class CandidateRepositoryImpl implements CandidateRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    private static final int PAGE_SIZE = 6;

    @Override
    public List<Candidate> getCandidate(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder cb = s.getCriteriaBuilder();
        CriteriaQuery<Candidate> cq = cb.createQuery(Candidate.class);
        Root<Candidate> root = cq.from(Candidate.class);

        root.fetch("resumeSet", JoinType.LEFT);
        root.fetch("applicationSet", JoinType.LEFT);
        root.fetch("followcompanySet", JoinType.LEFT);

        List<Predicate> predicates = new ArrayList<>();

        String kw = params != null ? params.get("kw") : null;
        if (kw != null && !kw.isEmpty()) {
            predicates.add(cb.like(root.get("sFullName"), "%" + kw + "%"));
        }

        cq.where(predicates.toArray(new Predicate[0]));
        cq.orderBy(cb.asc(root.get("iCandidateId")));

        Query<Candidate> query = s.createQuery(cq);

        String pageStr = params != null ? params.get("page") : null;
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
    public Candidate getCandidateId(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder cb = s.getCriteriaBuilder();
        CriteriaQuery<Candidate> cq = cb.createQuery(Candidate.class);
        Root<Candidate> root = cq.from(Candidate.class);

        root.fetch("resumeSet", JoinType.LEFT);
        root.fetch("applicationSet", JoinType.LEFT);
        root.fetch("followcompanySet", JoinType.LEFT);

        cq.where(cb.equal(root.get("iCandidateId"), id));
        cq.distinct(true);

        Query<Candidate> query = s.createQuery(cq);
        return query.getSingleResult();
    }

    @Override
    public List<Candidate> getCandidatesByAccounts(List<String> usernames) {
        Session session = this.factory.getObject().getCurrentSession();
        String hql = "FROM Candidate c WHERE c.account.sUsername IN (:usernames)";
        Query<Candidate> query = session.createQuery(hql, Candidate.class);
        query.setParameterList("usernames", usernames);
        return query.getResultList();
    }
    
    @Override
    public void addOrUpdateCandidate(Candidate c) {
        Session s = this.factory.getObject().getCurrentSession();
        if (c.getICandidateId() == null) {
            s.persist(c);
        } else {
            s.merge(c);
        }
    }

    @Override
    public void deleteCandidate(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Candidate c = getCandidateId(id);
        if (c != null) {
            s.remove(c);
        }
    }

    @Override
    public Candidate getCandidateByAccount(String username) {
        Session s = this.factory.getObject().getCurrentSession();

        String hql = "FROM Candidate c WHERE c.account.sUsername = :sUsername";
        Query<Candidate> query = s.createQuery(hql, Candidate.class);
        query.setParameter("sUsername", username);
        return query.uniqueResult();
    }

}
