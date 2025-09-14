/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.mapper;

import com.thenthu.dto.RoleDTO;
import com.thenthu.pojo.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 *
 * @author this pc
 */
@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(source = "IRoleId", target = "roleId")
    @Mapping(source = "SRoleName", target = "roleName")
    RoleDTO toDTO(Role role);
    
    @Mapping(source = "roleId", target = "IRoleId")
    @Mapping(source = "roleName", target = "SRoleName")
    Role toEntity(RoleDTO roleDTO);
}
