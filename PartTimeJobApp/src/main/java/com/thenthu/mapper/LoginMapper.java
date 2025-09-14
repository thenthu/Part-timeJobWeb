/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.mapper;

import com.thenthu.dto.LoginDTO;
import com.thenthu.pojo.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 *
 * @author this pc
 */
@Mapper(componentModel = "spring")
public interface LoginMapper {
    @Mapping(source = "SUsername", target = "username")
    @Mapping(source = "SPassword", target = "password")
    LoginDTO toDTO(Account account);

    // DTO -> Entity (ngược lại)
    @Mapping(source = "username", target = "SUsername")
    @Mapping(source = "password", target = "SPassword")
    Account toEntity(LoginDTO dto);
}
