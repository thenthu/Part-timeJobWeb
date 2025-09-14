/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.repositories.impl;

import com.thenthu.pojo.Location;
import com.thenthu.repositories.LocationRepository;
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
public class LocationRepositoryImpl implements LocationRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Location> getLocations(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder cb = s.getCriteriaBuilder();
        CriteriaQuery<Location> cq = cb.createQuery(Location.class);
        Root<Location> root = cq.from(Location.class);

        List<Predicate> predicates = new ArrayList<>();

        // Tìm kiếm theo tên địa điểm
        String kw = params != null ? params.get("kw") : null;
        if (kw != null && !kw.isEmpty()) {
            predicates.add(cb.like(root.get("sLocationName"), "%" + kw + "%"));
        }

        cq.where(predicates.toArray(new Predicate[0]));
        cq.orderBy(cb.asc(root.get("iLocationId")));

        Query<Location> q = s.createQuery(cq);
        return q.getResultList();
    }

    @Override
    public Location getLocationById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(Location.class, id);
    }

    @Override
    public void addOrUpdateLocation(Location l) {
        Session s = this.factory.getObject().getCurrentSession();
        if (l.getILocationId() == null) {
            s.persist(l);
        } else {
            s.merge(l);
        }
    }

    @Override
    public void deleteLocation(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Location l = getLocationById(id);
        if (l != null) {
            s.remove(l);
        }
    }

}
