/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.mapper;

import com.thenthu.dto.JobDTO;
import com.thenthu.dto.JobAdminDTO;
import com.thenthu.pojo.Job;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 *
 * @author this pc
 */
@Mapper(
    componentModel = "spring",
    uses = { LocationMapper.class, MajorMapper.class, JoblevelMapper.class, EmployerMapper.class }
)
public interface JobMapper {

    @Mapping(source = "IJobPostId", target = "jobId")
    @Mapping(source = "SJobTitle", target = "jobTitle")
    @Mapping(source = "SWorkAddress", target = "workAddress")
    @Mapping(source = "IMinSalary", target = "minSalary")
    @Mapping(source = "IMaxSalary", target = "maxSalary")
    @Mapping(source = "IQuantity", target = "quantity")
    @Mapping(source = "SDescription", target = "description")
    @Mapping(source = "SCandidateRequirement", target = "candidateRequirement")
    @Mapping(source = "SRelatedSkills", target = "relatedSkills")
    @Mapping(source = "SBenefits", target = "benefits")
    @Mapping(source = "SWorkTime", target = "workTime")
    @Mapping(source = "DDeadline", target = "deadline")

    @Mapping(source = "IEmployerId", target = "employer")
    @Mapping(source = "ILocationId", target = "location")
    @Mapping(source = "IMajorId", target = "major")
    @Mapping(source = "ILevelId", target = "jobLevel")
    JobDTO toDTO(Job job);
    
    @Mapping(source = "jobId", target = "IJobPostId")
    @Mapping(source = "jobTitle", target = "SJobTitle")
    @Mapping(source = "workAddress", target = "SWorkAddress")
    @Mapping(source = "minSalary", target = "IMinSalary")
    @Mapping(source = "maxSalary", target = "IMaxSalary")
    @Mapping(source = "quantity", target = "IQuantity")
    @Mapping(source = "description", target = "SDescription")
    @Mapping(source = "candidateRequirement", target = "SCandidateRequirement")
    @Mapping(source = "relatedSkills", target = "SRelatedSkills")
    @Mapping(source = "benefits", target = "SBenefits")
    @Mapping(source = "workTime", target = "SWorkTime")
    @Mapping(source = "deadline", target = "DDeadline")
    @Mapping(source = "employer", target = "IEmployerId")
    @Mapping(source = "location", target = "ILocationId")
    @Mapping(source = "major", target = "IMajorId")
    @Mapping(source = "jobLevel", target = "ILevelId")
    Job toEntity(JobDTO dto);
    
    @Mapping(source = "IJobPostId", target = "jobId")
    @Mapping(source = "SJobTitle", target = "jobTitle")
    @Mapping(source = "SWorkAddress", target = "workAddress")
    @Mapping(source = "IMinSalary", target = "minSalary")
    @Mapping(source = "IMaxSalary", target = "maxSalary")
    @Mapping(source = "IQuantity", target = "quantity")
    @Mapping(source = "SDescription", target = "description")
    @Mapping(source = "SCandidateRequirement", target = "candidateRequirement")
    @Mapping(source = "SRelatedSkills", target = "relatedSkills")
    @Mapping(source = "SBenefits", target = "benefits")
    @Mapping(source = "SWorkTime", target = "workTime")
    @Mapping(source = "DPostedDate", target = "postedDate")
    @Mapping(source = "DDeadline", target = "deadline")
    @Mapping(source = "BStatus", target = "status")
    @Mapping(source = "IEmployerId", target = "employer")
    @Mapping(source = "ILocationId", target = "location")
    @Mapping(source = "IMajorId", target = "major")
    @Mapping(source = "ILevelId", target = "jobLevel")
    JobAdminDTO toAdminDTO(Job job);

    @Mapping(source = "jobId", target = "IJobPostId")
    @Mapping(source = "jobTitle", target = "SJobTitle")
    @Mapping(source = "workAddress", target = "SWorkAddress")
    @Mapping(source = "minSalary", target = "IMinSalary")
    @Mapping(source = "maxSalary", target = "IMaxSalary")
    @Mapping(source = "quantity", target = "IQuantity")
    @Mapping(source = "description", target = "SDescription")
    @Mapping(source = "candidateRequirement", target = "SCandidateRequirement")
    @Mapping(source = "relatedSkills", target = "SRelatedSkills")
    @Mapping(source = "benefits", target = "SBenefits")
    @Mapping(source = "workTime", target = "SWorkTime")
    @Mapping(source = "postedDate", target = "DPostedDate")
    @Mapping(source = "deadline", target = "DDeadline")
    @Mapping(source = "status", target = "BStatus")
    @Mapping(source = "employer", target = "IEmployerId")
    @Mapping(source = "location", target = "ILocationId")
    @Mapping(source = "major", target = "IMajorId")
    @Mapping(source = "jobLevel", target = "ILevelId")
    Job toEntity(JobAdminDTO dto);
}
