/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.services;

import com.thenthu.dto.LocationDTO;
import java.util.List;
import java.util.Map;

/**
 *
 * @author this pc
 */
public interface LocationService {
    List<LocationDTO> getLocations(Map<String, String> params);
    LocationDTO getLocationById(int id);
    void addOrUpdateLocation(LocationDTO dto);
    void deleteLocation(int id);
}
