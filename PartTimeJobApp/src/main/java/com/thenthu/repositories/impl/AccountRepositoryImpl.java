/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.repositories.impl;

import com.thenthu.pojo.Account;
import com.thenthu.repositories.AccountRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author this pc
 */
@Repository
@Transactional
public class AccountRepositoryImpl implements AccountRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    private static final int PAGE_SIZE = 6;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Account> getAccount(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder cb = s.getCriteriaBuilder();
        CriteriaQuery<Account> cq = cb.createQuery(Account.class);
        Root<Account> root = cq.from(Account.class);

        List<Predicate> predicates = new ArrayList<>();

        String kw = params != null ? params.get("kw") : null;
        if (kw != null && !kw.isEmpty()) {
            predicates.add(cb.like(root.get("sUsername"), "%" + kw + "%"));
        }
        
        String status = params.get("status");
        if (status != null && !status.isEmpty()) {
            Boolean isActive = Boolean.parseBoolean(status);
            predicates.add(cb.equal(root.get("bStatus"), isActive));
        }

        cq.where(predicates.toArray(new Predicate[0]));
        cq.orderBy(cb.asc(root.get("id")));

        Query<Account> query = s.createQuery(cq);

        String pageStr = params != null ? params.get("page") : null;
        int page = 1;
        if (pageStr != null) {
            try {
                page = Integer.parseInt(pageStr);
                if (page < 1) page = 1;
            } catch (NumberFormatException ex) {
                page = 1;
            }
        }
        query.setFirstResult((page - 1) * PAGE_SIZE);
        query.setMaxResults(PAGE_SIZE);

        return query.getResultList();
    }

    @Override
    public Account getAccountById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(Account.class, id);
    }

    @Override
    public void addOrUpdateAccount(Account a) {
        Session s = this.factory.getObject().getCurrentSession();
        if (a.getIAccountId() == null) {
            s.persist(a);
        } else {
            s.merge(a);
        }
    }

//    @Override
//    public void deleteAccount(int id) {
//        Session s = this.factory.getObject().getCurrentSession();
//        Account a = getAccountById(id);
//        if (a != null) {
//            s.remove(a);
//        }
//    }

    @Override
    public Account getAccountByUsername(String username) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createNamedQuery("Account.findBySUsername", Account.class);
        q.setParameter("sUsername", username);
        
        return (Account) q.getSingleResult();
    }

    @Override
    public Account addAccount(Account a) {
        Session s = this.factory.getObject().getCurrentSession();
        
        if("ROLE_EMPLOYER".equals(a.getIRoleId().getSRoleName())) {
            a.setBStatus(Boolean.FALSE);
        }
        
        s.persist(a);
        
        return a;
    }

    @Override
    public boolean authenticate(String username, String password) {
        Account a = this.getAccountByUsername(username);
        if (a.getBStatus()) {
            return this.passwordEncoder.matches(password, a.getSPassword());
        }
        else return false;
        
    }

    @Override
    public boolean existsByUsername(String username) {
        String jpql = "SELECT COUNT(a) > 0 FROM Account a WHERE a.sUsername = :sUsername";
        return (Boolean) entityManager.createQuery(jpql)
                .setParameter("sUsername", username)
                .getSingleResult();
    }

    @Override
    public boolean existsByEmail(String email) {
        String jpql = "SELECT COUNT(a) > 0 FROM Account a WHERE a.sEmail = :sEmail";
        return (Boolean) entityManager.createQuery(jpql)
                .setParameter("sEmail", email)
                .getSingleResult();
    }

    @Override
    public List<Account> getAccountByRole(String role) {
        Session s = this.factory.getObject().getCurrentSession();
        String hql = "SELECT a FROM Account a WHERE a.iRoleId.sRoleName = :sRoleName";
        Query q = s.createQuery(hql, Account.class);
        q.setParameter("sRoleName", role);
        
        return q.getResultList();
    }
}
