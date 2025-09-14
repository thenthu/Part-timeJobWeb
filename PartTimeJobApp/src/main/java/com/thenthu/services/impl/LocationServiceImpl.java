/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.services.impl;

import com.thenthu.dto.LocationDTO;
import com.thenthu.mapper.LocationMapper;
import com.thenthu.pojo.Location;
import com.thenthu.repositories.LocationRepository;
import com.thenthu.services.LocationService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author this pc
 */
@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationRepository locaRepo;
    @Autowired
    private LocationMapper locationMapper;

    @Override
    public List<LocationDTO> getLocations(Map<String, String> params) {
        List<Location> locations = this.locaRepo.getLocations(params);
        return locations.stream()
                .map(locationMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public LocationDTO getLocationById(int id) {
        Location location = locaRepo.getLocationById(id);
        if (location == null) {
            return null;
        }
        return locationMapper.toDTO(location);
    }

    @Override
    public void addOrUpdateLocation(LocationDTO dto) {
        Location entity = locationMapper.toEntity(dto);
        locaRepo.addOrUpdateLocation(entity);
    }

    @Override
    public void deleteLocation(int id) {
        locaRepo.deleteLocation(id);
    }
}
