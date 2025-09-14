/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.mapper;

import com.thenthu.dto.CandidateAdminDTO;
import com.thenthu.dto.CandidateDTO;
import com.thenthu.pojo.Candidate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 *
 * @author this pc
 */
@Mapper(
        componentModel = "spring",
        uses = {ResumeMapper.class, ApplicationMapper.class, FollowCompanyMapper.class, AccountMapper.class, RoleMapper.class }
)
public interface CandidateMapper {

    // Entity -> DTO cho client
    @Mapping(target = "email", source = "account.SEmail")
    @Mapping(target = "username", source = "account.SUsername")
    @Mapping(target = "createdAt", source = "account.DCreatedAt")
    @Mapping(target = "role", source = "account.IRoleId")
    @Mapping(source = "ICandidateId", target = "candidateId")
    @Mapping(source = "SFullName", target = "fullName")
    @Mapping(source = "SPhone", target = "phone")
    @Mapping(source = "SGender", target = "gender")
    @Mapping(source = "DBirthDate", target = "birthDate")
    @Mapping(source = "SAvatar", target = "avatar")
    @Mapping(source = "SAddress", target = "address")
    CandidateDTO toDTO(Candidate candidate);

    // Entity -> DTO cho admin
    @Mapping(source = "ICandidateId", target = "candidateId")
    @Mapping(source = "SFullName", target = "fullName")
    @Mapping(source = "SPhone", target = "phone")
    @Mapping(source = "SGender", target = "gender")
    @Mapping(source = "DBirthDate", target = "birthDate")
    @Mapping(source = "SAvatar", target = "avatar")
    @Mapping(source = "SAddress", target = "address")
    @Mapping(source = "account", target = "account")
    CandidateAdminDTO toAdminDTO(Candidate candidate);

    // DTO -> Entity (nếu cần)
    @Mapping(target = "account.SEmail", source = "email")
    @Mapping(target = "account.SUsername", source = "username")
    @Mapping(target = "account.DCreatedAt", source = "createdAt")
    @Mapping(target = "account.IRoleId", source = "role")
    @Mapping(source = "candidateId", target = "ICandidateId")
    @Mapping(source = "fullName", target = "SFullName")
    @Mapping(source = "phone", target = "SPhone")
    @Mapping(source = "gender", target = "SGender")
    @Mapping(source = "birthDate", target = "DBirthDate")
    @Mapping(source = "avatar", target = "SAvatar")
    @Mapping(source = "address", target = "SAddress")
    Candidate toEntity(CandidateDTO dto);

    @Mapping(source = "candidateId", target = "ICandidateId")
    @Mapping(source = "fullName", target = "SFullName")
    @Mapping(source = "phone", target = "SPhone")
    @Mapping(source = "gender", target = "SGender")
    @Mapping(source = "birthDate", target = "DBirthDate")
    @Mapping(source = "avatar", target = "SAvatar")
    @Mapping(source = "address", target = "SAddress")
    @Mapping(source = "account", target = "account")
    Candidate toAdminEntity(CandidateAdminDTO adminDTO);
}
