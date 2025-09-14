/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.repositories.impl;

import com.thenthu.pojo.Application;
import com.thenthu.repositories.ApplicationRepository;
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
public class ApplicationRepositoryImpl implements ApplicationRepository{
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Application> getApplicationsByCandidateId(int candidateId) {
        Session session = this.factory.getObject().getCurrentSession();
        Query<Application> query = session.createNamedQuery("Application.findByICandidateId", Application.class);
        query.setParameter("iCandidateId", candidateId);
        return query.getResultList();
    }

    @Override
    public List<Application> getApplicationsByJobId(int jobId) {
        Session session = this.factory.getObject().getCurrentSession();
        Query<Application> query = session.createNamedQuery("Application.findByIJobPostId", Application.class);
        query.setParameter("iJobPostId", jobId);
        return query.getResultList();
    }

    @Override
    public Application applyToJob(Application a) {
        Session session = this.factory.getObject().getCurrentSession();
        
        session.persist(a);
        return a;
    }

    @Override
    public boolean applicationExits(int candidateId, int jobId) {
        Session session = this.factory.getObject().getCurrentSession();
        String hql = "SELECT COUNT(a) FROM Application a WHERE a.applicationPK.iCandidateId = :candidateId AND a.applicationPK.iJobPostId = :jobId";
        Query<Long> query = session.createQuery(hql, Long.class);
        query.setParameter("candidateId", candidateId);
        query.setParameter("jobId", jobId);

        Long count = query.uniqueResult();
        return count != null && count > 0;
    }

    @Override
    public Application getApplicationByCandidateAndJob(int candidateId, int jobId) {
        Session session = this.factory.getObject().getCurrentSession();
        String hql = "SELECT a FROM Application a WHERE a.applicationPK.iCandidateId = :iCandidateId AND a.applicationPK.iJobPostId = :iJobPostId";
        return session.createQuery(hql, Application.class)
                .setParameter("iCandidateId", candidateId)
                .setParameter("iJobPostId", jobId)
                .uniqueResult();
    }

    @Override
    public Application updateApplication(Application app) {
        Session session = this.factory.getObject().getCurrentSession();
        
        return session.merge(app);
    }
}
