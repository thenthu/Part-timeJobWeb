/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.mapper;

import com.thenthu.dto.RegisterDTO;
import com.thenthu.pojo.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 *
 * @author this pc
 */
@Mapper(
    componentModel = "spring",
    uses = { RoleMapper.class }
)
public interface RegisterMapper {
    
    @Mapping(source = "SEmail", target = "email")
    @Mapping(source = "SUsername", target = "username")
    @Mapping(source = "DCreatedAt", target = "createdAt")
    @Mapping(source = "IRoleId", target = "role")
    RegisterDTO toDTO(Account account);

    // DTO -> Entity (ngược lại)
    @Mapping(source = "email", target = "SEmail")
    @Mapping(source = "username", target = "SUsername")
    @Mapping(source = "password", target = "SPassword")
    @Mapping(source = "createdAt", target = "DCreatedAt")
    @Mapping(source = "role", target = "IRoleId")
    Account toEntity(RegisterDTO dto);
}
