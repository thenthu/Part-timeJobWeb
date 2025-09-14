/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.repositories.impl;

import com.thenthu.pojo.Role;
import com.thenthu.repositories.RoleRepository;
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
public class RoleRepositoryImpl implements RoleRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public Role findByName(String name) {
        Session session = factory.getObject().getCurrentSession();
        return session.createNamedQuery("Role.findBySRoleName", Role.class)
                .setParameter("sRoleName", name)
                .uniqueResult();
    }

    @Override
    public Role findById(int id) {
        Session session = factory.getObject().getCurrentSession();
        return session.get(Role.class, id);
    }

    @Override
    public List<Role> getRoles(Map<String, String> params) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Role> cq = cb.createQuery(Role.class);
        Root<Role> root = cq.from(Role.class);

        List<Predicate> predicates = new ArrayList<>();

        cq.where(predicates.toArray(new Predicate[0]));
        cq.orderBy(cb.asc(root.get("iRoleId")));

        Query<Role> query = session.createQuery(cq);
        return query.getResultList();
    }
    
}
