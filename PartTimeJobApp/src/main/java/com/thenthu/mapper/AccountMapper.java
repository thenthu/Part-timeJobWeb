/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.mapper;

import com.thenthu.dto.AccountAdminDTO;
import com.thenthu.dto.AccountDTO;
import com.thenthu.pojo.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 *
 * @author this pc
 */
@Mapper(
    componentModel = "spring",
    uses = { RoleMapper.class, CandidateMapper.class, EmployerMapper.class }
)
public interface AccountMapper {
    
    // Entity -> DTO (đã có ở trên)
    @Mapping(source = "SEmail", target = "email")
    @Mapping(source = "SUsername", target = "username")
    @Mapping(source = "DCreatedAt", target = "createdAt")
    @Mapping(source = "IRoleId", target = "role")
    AccountDTO toDTO(Account account);

    @Mapping(source = "IAccountId", target = "accountId")
    @Mapping(source = "SEmail", target = "email")
    @Mapping(source = "SUsername", target = "username")
    @Mapping(source = "DCreatedAt", target = "createdAt")
    @Mapping(source = "BStatus", target = "status")
    @Mapping(source = "IRoleId", target = "role")
    AccountAdminDTO toAdminDTO(Account account);

    // DTO -> Entity (ngược lại)
    @Mapping(source = "email", target = "SEmail")
    @Mapping(source = "username", target = "SUsername")
    @Mapping(source = "createdAt", target = "DCreatedAt")
    @Mapping(source = "role", target = "IRoleId")
    Account toEntity(AccountDTO dto);

    @Mapping(source = "accountId", target = "IAccountId")
    @Mapping(source = "email", target = "SEmail")
    @Mapping(source = "username", target = "SUsername")
    @Mapping(source = "password", target = "SPassword")
    @Mapping(source = "createdAt", target = "DCreatedAt")
    @Mapping(source = "status", target = "BStatus")
    @Mapping(source = "role", target = "IRoleId")
    Account toEntity(AccountAdminDTO adminDTO);
}
