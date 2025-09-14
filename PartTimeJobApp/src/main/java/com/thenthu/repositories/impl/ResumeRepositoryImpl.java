/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.repositories.impl;

import com.thenthu.pojo.Resume;
import com.thenthu.repositories.ResumeRepository;
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
public class ResumeRepositoryImpl implements ResumeRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Resume> getResumesByCandidateId(int candidateId) {
        Session session = this.factory.getObject().getCurrentSession();
        String hql = "FROM Resume r WHERE r.iCandidateId.iCandidateId = :candidateId";
        Query<Resume> query = session.createQuery(hql, Resume.class);
        query.setParameter("candidateId", candidateId);
        return query.getResultList();
    }

    @Override
    public Resume getResumeById(int id) {
        Session session = this.factory.getObject().getCurrentSession();
        return session.get(Resume.class, id);
    }

    @Override
    public void deleteResume(int id) {
        Session session = this.factory.getObject().getCurrentSession();
        Resume r = getResumeById(id);
        if (r != null) {
            session.remove(r);
        }
    }

    @Override
    public void addOrUpdateResume(Resume r) {
        Session s = this.factory.getObject().getCurrentSession();
        if (r.getICandidateId() == null) {
            s.persist(r);
        } else {
            s.merge(r);
        }
    }
}
