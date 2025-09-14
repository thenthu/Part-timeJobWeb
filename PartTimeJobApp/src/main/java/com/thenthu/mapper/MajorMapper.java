/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.mapper;

import com.thenthu.dto.MajorDTO;
import com.thenthu.pojo.Major;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 *
 * @author this pc
 */
@Mapper(componentModel = "spring")
public interface MajorMapper {

    @Mapping(source = "IMajorId", target = "majorId")
    @Mapping(source = "SMajorName", target = "majorName")
    MajorDTO toDTO(Major major);
    
    @Mapping(source = "majorId", target = "IMajorId")
    @Mapping(source = "majorName", target = "SMajorName")
    @Mapping(target = "jobSet", ignore = true)
    Major toEntity(MajorDTO majorDTO);
}
