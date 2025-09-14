/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.repositories.impl;

import com.thenthu.pojo.Major;
import com.thenthu.repositories.MajorRepository;
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
public class MajorRepositoryImpl implements MajorRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Major> getMajors(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder cb = s.getCriteriaBuilder();
        CriteriaQuery<Major> cq = cb.createQuery(Major.class);
        Root<Major> root = cq.from(Major.class);

        List<Predicate> predicates = new ArrayList<>();

        String kw = params != null ? params.get("kw") : null;
        if (kw != null && !kw.isEmpty()) {
            predicates.add(cb.like(root.get("sMajorName"), "%" + kw + "%"));
        }

        cq.where(predicates.toArray(new Predicate[0]));
        cq.orderBy(cb.asc(root.get("iMajorId")));

        Query<Major> q = s.createQuery(cq);
        return q.getResultList();
    }

    @Override
    public Major getMajorById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(Major.class, id);
    }

    @Override
    public void addOrUpdateMajor(Major m) {
        Session s = this.factory.getObject().getCurrentSession();
        if (m.getIMajorId() == null) {
            s.persist(m);
        } else {
            s.merge(m);
        }
    }

    @Override
    public void deleteMajor(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Major m = getMajorById(id);
        if (m != null) {
            s.remove(m);
        }
    }
}
