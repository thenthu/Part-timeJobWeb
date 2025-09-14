/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.mapper;

import com.thenthu.dto.LocationDTO;
import com.thenthu.pojo.Location;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 *
 * @author this pc
 */
@Mapper(componentModel = "spring")
public interface LocationMapper {

    @Mapping(source = "ILocationId", target = "locationId")
    @Mapping(source = "SLocationName", target = "locationName")
    LocationDTO toDTO(Location location);
    
    @Mapping(source = "locationId", target = "ILocationId")
    @Mapping(source = "locationName", target = "SLocationName")
    @Mapping(target = "jobSet", ignore = true)
    Location toEntity(LocationDTO locationDTO);
}
