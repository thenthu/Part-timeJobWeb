/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.mapper;

import com.thenthu.dto.FollowCompanyDTO;
import com.thenthu.pojo.Followcompany;
import com.thenthu.pojo.FollowcompanyPK;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 *
 * @author this pc
 */
@Mapper(
    componentModel = "spring",
    uses = { CandidateMapper.class, EmployerMapper.class }
)
public interface FollowCompanyMapper {

    @Mapping(source = "candidate", target = "candidate")
    @Mapping(source = "employer", target = "employer")
    @Mapping(source = "DFollowedAt", target = "followedAt")
    FollowCompanyDTO toDTO(Followcompany followcompany);

    @Mapping(target = "candidate", source = "candidate")
    @Mapping(target = "employer", source = "employer")
    @Mapping(source = "followedAt", target = "DFollowedAt")
    @Mapping(target = "followcompanyPK", ignore = true)
    Followcompany toEntity(FollowCompanyDTO dto);
    
    @AfterMapping
    default void setPK(FollowCompanyDTO dto, @MappingTarget Followcompany entity) {
        if (dto.getCandidate() != null && dto.getEmployer() != null) {
            Integer candidateId = dto.getCandidate().getCandidateId();
            Integer employerId = dto.getEmployer().getEmployerId();
            if (candidateId != null && employerId != null) {
                entity.setFollowcompanyPK(new FollowcompanyPK(candidateId, employerId));
            }
        }
    }
}
