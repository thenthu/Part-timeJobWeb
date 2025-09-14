/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.repositories.impl;

import com.thenthu.pojo.Employer;
import com.thenthu.repositories.EmployerRepository;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;

/**
 *
 * @author this pc
 */
@Repository
@Transactional
public class EmployerRepositoryImpl implements EmployerRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    private static final int PAGE_SIZE = 6;

    @Override
    public List<Employer> getEmployer(Map<String, String> params) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Employer> cq = cb.createQuery(Employer.class);
        Root<Employer> root = cq.from(Employer.class);

        List<Predicate> predicates = new ArrayList<>();

        String kw = params.get("kw");
        if (kw != null && !kw.isEmpty()) {
            predicates.add(cb.like(root.get("sCompanyName"), "%" + kw + "%"));
        }
        
        String verified = params.get("verified");
        if (verified != null && !verified.isEmpty()) {
            Boolean isVerified = Boolean.parseBoolean(verified);
            predicates.add(cb.equal(root.get("bVerified"), isVerified));
        }

        cq.where(predicates.toArray(new Predicate[0]));
        cq.orderBy(cb.asc(root.get("iEmployerId")));

        Query<Employer> query = session.createQuery(cq);

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
    public Employer getEmployerById(int id) {
        Session session = this.factory.getObject().getCurrentSession();
        return session.get(Employer.class, id);
    }

    @Override
    public void addOrUpdateEmployer(Employer e) {
        Session session = this.factory.getObject().getCurrentSession();
        if (e.getIEmployerId() == null) {
            session.persist(e);
        } else {
            session.merge(e);
        }
    }

    @Override
    public void deleteEmployer(int id) {
        Session session = this.factory.getObject().getCurrentSession();
        Employer e = getEmployerById(id);
        if (e != null) {
            session.remove(e);
        }
    }

    @Override
    public Employer getEmployerByAccount(String username) {
        Session session = this.factory.getObject().getCurrentSession();
        
        String hql = "FROM Employer e WHERE e.account.sUsername = :username";
        Query<Employer> query = session.createQuery(hql, Employer.class);
        query.setParameter("username", username);
        return query.uniqueResult();
    }

    @Override
    public List<Employer> getEmployersByAccounts(List<String> usernames) {
        Session session = this.factory.getObject().getCurrentSession();
        String hql = "FROM Employer e WHERE e.account.sUsername IN (:usernames)";
        Query<Employer> query = session.createQuery(hql, Employer.class);
        query.setParameterList("usernames", usernames);
        return query.getResultList();
    }
}
