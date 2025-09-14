/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.mapper;

import com.thenthu.dto.ApplicationDTO;
import com.thenthu.pojo.Application;
import com.thenthu.pojo.ApplicationPK;
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
    uses = { ResumeMapper.class, JobMapper.class, CandidateMapper.class }
)
public interface ApplicationMapper {

    @Mapping(source = "IResumeId", target = "resume")
    @Mapping(source = "candidate", target = "candidate")
    @Mapping(source = "job", target = "jobPost")
    @Mapping(source = "DApplyDate", target = "applyDate")
    @Mapping(source = "SStatus", target = "status")
    ApplicationDTO toDTO(Application application);
    
    @Mapping(source = "resume", target = "IResumeId")
    @Mapping(source = "candidate", target = "candidate")
    @Mapping(source = "jobPost", target = "job")
    @Mapping(source = "applyDate", target = "DApplyDate")
    @Mapping(source = "status", target = "SStatus")
    @Mapping(target = "applicationPK", ignore = true)
    Application toEntity(ApplicationDTO applicationDTO);
    
    @AfterMapping
    default void setPK(ApplicationDTO dto, @MappingTarget Application entity) {
        if (dto.getCandidate()!= null && dto.getJobPost()!= null) {
            Integer candidateId = dto.getCandidate().getCandidateId();
            Integer jobPostId = dto.getJobPost().getJobId();
            if (candidateId != null && jobPostId != null) {
                entity.setApplicationPK(new ApplicationPK(candidateId, jobPostId));
            }
        }
    }
}
