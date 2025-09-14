/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.repositories.impl;

import com.thenthu.pojo.Followcompany;
import com.thenthu.repositories.FollowCompanyRepository;
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
public class FollowCompanyRepositoryImpl implements FollowCompanyRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Followcompany> getFollowCompaniesByCandidateId(int candidateId) {
        Session session = this.factory.getObject().getCurrentSession();
        Query<Followcompany> query = session.createNamedQuery("Followcompany.findByICandidateId", Followcompany.class);
        query.setParameter("iCandidateId", candidateId);
        return query.getResultList();
    }

    @Override
    public List<Followcompany> getFollowCompaniesByEmployerId(int employerId) {
        Session session = this.factory.getObject().getCurrentSession();
        Query<Followcompany> query = session.createNamedQuery("Followcompany.findByIEmployerId", Followcompany.class);
        query.setParameter("iEmployerId", employerId);
        return query.getResultList();
    }

    @Override
    public Followcompany getFollowCompanyById(int candidateId, int employerId) {
        Session session = this.factory.getObject().getCurrentSession();
        String hql = "FROM Followcompany f WHERE f.followcompanyPK.iCandidateId = :candidateId AND f.followcompanyPK.iEmployerId = :employerId";
        Query<Followcompany> query = session.createQuery(hql, Followcompany.class);
        query.setParameter("candidateId", candidateId);
        query.setParameter("employerId", employerId);

        return query.uniqueResult();
    }

    @Override
    public void addFollow(Followcompany follow) {
        Session session = this.factory.getObject().getCurrentSession();
        session.persist(follow);
    }

    @Override
    public void unfollow(int candidateId, int employerId) {
        Session session = this.factory.getObject().getCurrentSession();

        Followcompany follow = this.getFollowCompanyById(candidateId, employerId);
        session.remove(follow);
    }
}
