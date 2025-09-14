/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.mapper;

import com.thenthu.dto.EmployerAdminDTO;
import com.thenthu.dto.EmployerDTO;
import com.thenthu.pojo.Employer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 *
 * @author this pc
 */
@Mapper(
        componentModel = "spring",
        uses = { AccountMapper.class, RoleMapper.class }
)
public interface EmployerMapper {

    // Mapping cho client
    @Mapping(target = "email", source = "account.SEmail")
    @Mapping(target = "username", source = "account.SUsername")
    @Mapping(target = "createdAt", source = "account.DCreatedAt")
    @Mapping(target = "role", source = "account.IRoleId")
    @Mapping(source = "IEmployerId", target = "employerId")
    @Mapping(source = "SCompanyName", target = "companyName")
    @Mapping(source = "SRepresentativeName", target = "representativeName")
    @Mapping(source = "SRepresentativeTitle", target = "representativeTitle")
    @Mapping(source = "SAvatar", target = "avatar")
    @Mapping(source = "STaxCode", target = "taxCode")
    @Mapping(source = "SWorkEnvImg1", target = "workEnvImg1")
    @Mapping(source = "SWorkEnvImg2", target = "workEnvImg2")
    @Mapping(source = "SWorkEnvImg3", target = "workEnvImg3")
    EmployerDTO toEmployerDTO(Employer employer);
    // Mapping DTO (Client) → Entity
    
    @Mapping(target = "account.SEmail", source = "email")
    @Mapping(target = "account.SUsername", source = "username")
    @Mapping(target = "account.DCreatedAt", source = "createdAt")
    @Mapping(target = "account.IRoleId", source = "role")
    @Mapping(source = "employerId", target = "IEmployerId")
    @Mapping(source = "companyName", target = "SCompanyName")
    @Mapping(source = "representativeName", target = "SRepresentativeName")
    @Mapping(source = "representativeTitle", target = "SRepresentativeTitle")
    @Mapping(source = "avatar", target = "SAvatar")
    @Mapping(source = "taxCode", target = "STaxCode")
    @Mapping(source = "workEnvImg1", target = "SWorkEnvImg1")
    @Mapping(source = "workEnvImg2", target = "SWorkEnvImg2")
    @Mapping(source = "workEnvImg3", target = "SWorkEnvImg3")
    Employer toEmployerEntity(EmployerDTO dto);

    // Mapping cho admin
    @Mapping(source = "IEmployerId", target = "employerId")
    @Mapping(source = "SCompanyName", target = "companyName")
    @Mapping(source = "SRepresentativeName", target = "representativeName")
    @Mapping(source = "SRepresentativeTitle", target = "representativeTitle")
    @Mapping(source = "SAvatar", target = "avatar")
    @Mapping(source = "STaxCode", target = "taxCode")
    @Mapping(source = "SWorkEnvImg1", target = "workEnvImg1")
    @Mapping(source = "SWorkEnvImg2", target = "workEnvImg2")
    @Mapping(source = "SWorkEnvImg3", target = "workEnvImg3")
    @Mapping(source = "BVerified", target = "verified")
    @Mapping(source = "SVerifyDoc", target = "verifyDoc")
    @Mapping(source = "account", target = "account")
    EmployerAdminDTO toEmployerAdminDTO(Employer employer);
    // Mapping AdminDTO → Entity

    @Mapping(source = "employerId", target = "IEmployerId")
    @Mapping(source = "companyName", target = "SCompanyName")
    @Mapping(source = "representativeName", target = "SRepresentativeName")
    @Mapping(source = "representativeTitle", target = "SRepresentativeTitle")
    @Mapping(source = "avatar", target = "SAvatar")
    @Mapping(source = "taxCode", target = "STaxCode")
    @Mapping(source = "workEnvImg1", target = "SWorkEnvImg1")
    @Mapping(source = "workEnvImg2", target = "SWorkEnvImg2")
    @Mapping(source = "workEnvImg3", target = "SWorkEnvImg3")
    @Mapping(source = "verified", target = "BVerified")
    @Mapping(source = "verifyDoc", target = "SVerifyDoc")
    @Mapping(source = "account", target = "account")
    Employer toEmployerEntity(EmployerAdminDTO dto);
}
