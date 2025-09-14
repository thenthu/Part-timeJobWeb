/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.mapper;

import com.thenthu.dto.JoblevelDTO;
import com.thenthu.pojo.Joblevel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 *
 * @author this pc
 */
@Mapper(componentModel = "spring")
public interface JoblevelMapper {
    @Mapping(source = "ILevelId", target = "levelId")
    @Mapping(source = "SLevelName", target = "levelName")
    JoblevelDTO toDTO(Joblevel joblevel);
    
    @Mapping(source = "levelId", target = "ILevelId")
    @Mapping(source = "levelName", target = "SLevelName")
    @Mapping(target = "jobSet", ignore = true)
    Joblevel toEntity(JoblevelDTO joblevelDTO);
}
