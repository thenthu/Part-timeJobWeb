/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.repositories;

import com.thenthu.pojo.Location;
import java.util.List;
import java.util.Map;

/**
 *
 * @author this pc
 */
public interface LocationRepository {
    List<Location> getLocations(Map<String, String> params);
    Location getLocationById(int id);
    void addOrUpdateLocation(Location l);
    void deleteLocation(int id);
}
