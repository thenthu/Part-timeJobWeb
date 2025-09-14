/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.repositories.impl;

import com.thenthu.pojo.Job;
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
import com.thenthu.repositories.JobRepository;
import java.time.LocalDate;

/**
 *
 * @author this pc
 */
@Repository
@Transactional
public class JobRepositoryImpl implements JobRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    private static final int PAGE_SIZE = 3;

    @Override
    public List<Job> getJob(Map<String, String> params) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Job> cq = cb.createQuery(Job.class);
        Root<Job> root = cq.from(Job.class);

        List<Predicate> predicates = buildPredicates(params, cb, root);

        cq.where(predicates.toArray(new Predicate[0]));
        cq.orderBy(cb.asc(root.get("iJobPostId")));

        Query<Job> query = session.createQuery(cq);

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
    public Job getJobById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(Job.class, id);
    }

    @Override
    public void addOrUpdateJob(Job j) {
        Session s = this.factory.getObject().getCurrentSession();
        if (j.getIJobPostId() == null) {
            s.persist(j);
        } else {
            j.setBStatus(Boolean.TRUE);
            s.merge(j);
        }
    }

    @Override
    public void deleteJob(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Job j = this.getJobById(id);
        s.remove(j);
    }

    @Override
    public List<Job> getJobsByEmployerId(int employerId) {
        Session session = this.factory.getObject().getCurrentSession();
        String hql = "FROM Job j WHERE j.iEmployerId.iEmployerId = :iEmployerId";
        Query<Job> query = session.createQuery(hql, Job.class);
        query.setParameter("iEmployerId", employerId);
        return query.getResultList();
    }

    @Override
    public int getTotalPages(Map<String, String> params) {
        long totalJobs = countJobs(params);
        return (int) Math.ceil((double) totalJobs / PAGE_SIZE);
    }

    private long countJobs(Map<String, String> params) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Job> root = cq.from(Job.class);

        List<Predicate> predicates = buildPredicates(params, cb, root);

        predicates.add(cb.isTrue(root.get("bStatus")));
        predicates.add(cb.greaterThanOrEqualTo(root.get("dDeadline"), LocalDate.now()));

        cq.select(cb.count(root));
        cq.where(predicates.toArray(new Predicate[0]));
        Query<Long> query = session.createQuery(cq);
        return query.getSingleResult();
    }

    private List<Predicate> buildPredicates(Map<String, String> params, CriteriaBuilder cb, Root<Job> root) {
        List<Predicate> predicates = new ArrayList<>();

        String kw = params.get("kw");
        if (kw != null && !kw.isEmpty()) {
            predicates.add(cb.like(root.get("sJobTitle"), "%" + kw + "%"));
        }

        String employerId = params.get("employerId");
        if (employerId != null && !employerId.isEmpty()) {
            predicates.add(cb.equal(root.get("iEmployerId").get("iEmployerId"), Integer.valueOf(employerId)));
        }

        String levelId = params.get("levelId");
        if (levelId != null && !levelId.isEmpty()) {
            predicates.add(cb.equal(root.get("iLevelId").get("iLevelId"), Integer.valueOf(levelId)));
        }

        String majorId = params.get("majorId");
        if (majorId != null && !majorId.isEmpty()) {
            predicates.add(cb.equal(root.get("iMajorId").get("iMajorId"), Integer.valueOf(majorId)));
        }

        String locationId = params.get("locationId");
        if (locationId != null && !locationId.isEmpty()) {
            predicates.add(cb.equal(root.get("iLocationId").get("iLocationId"), Integer.valueOf(locationId)));
        }

        String status = params.get("status");
        if (status != null && !status.isEmpty()) {
            predicates.add(cb.equal(root.get("bStatus").get("bStatus"), Integer.valueOf(status)));
        }

        String salaryRange = params.get("salaryRange");
        if (salaryRange != null && !salaryRange.isEmpty()) {
            switch (salaryRange) {
                case "1":
                    predicates.add(cb.lessThan(root.get("iMinSalary"), 5000000));
                    break;
                case "2":
                    predicates.add(
                            cb.and(
                                    cb.greaterThanOrEqualTo(root.get("iMinSalary"), 5000000),
                                    cb.lessThan(root.get("iMaxSalary"), 10000000)
                            )
                    );
                    break;
                case "3":
                    predicates.add(
                            cb.and(
                                    cb.greaterThanOrEqualTo(root.get("iMinSalary"), 10000000),
                                    cb.lessThan(root.get("iMaxSalary"), 20000000)
                            )
                    );
                    break;
                case "4":
                    predicates.add(
                            cb.and(
                                    cb.greaterThanOrEqualTo(root.get("iMinSalary"), 20000000),
                                    cb.lessThan(root.get("iMaxSalary"), 30000000)
                            )
                    );
                    break;
                case "5":
                    predicates.add(cb.greaterThanOrEqualTo(root.get("iMinSalary"), 30000000));
                    break;
                default:
                    break;
            }
        }

        return predicates;
    }
}
