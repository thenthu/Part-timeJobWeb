/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.mapper;

import com.thenthu.dto.ResumeDTO;
import com.thenthu.pojo.Resume;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 *
 * @author this pc
 */
@Mapper(
    componentModel = "spring",
    uses = { CandidateMapper.class, MajorMapper.class, JoblevelMapper.class }
)
public interface ResumeMapper {
    @Mapping(source = "IResumeId", target = "resumeId")
    @Mapping(source = "SResumeName", target = "resumeName")
    @Mapping(source = "IMajorId", target = "major")
    @Mapping(source = "ILevelId", target = "level")
    @Mapping(source = "SCareerObjective", target = "careerObjective")
    @Mapping(source = "SExperience", target = "experience")
    @Mapping(source = "SSkills", target = "skills")
    @Mapping(source = "SEducation", target = "education")
    @Mapping(source = "SSoftSkills", target = "softSkills")
    @Mapping(source = "SAwards", target = "awards")
    @Mapping(source = "ICandidateId", target = "candidate")
    ResumeDTO toDTO(Resume resume);

    @Mapping(source = "resumeId", target = "IResumeId")
    @Mapping(source = "resumeName", target = "SResumeName")
    @Mapping(source = "major", target = "IMajorId")
    @Mapping(source = "level", target = "ILevelId")
    @Mapping(source = "careerObjective", target = "SCareerObjective")
    @Mapping(source = "experience", target = "SExperience")
    @Mapping(source = "skills", target = "SSkills")
    @Mapping(source = "education", target = "SEducation")
    @Mapping(source = "softSkills", target = "SSoftSkills")
    @Mapping(source = "awards", target = "SAwards")
    @Mapping(source = "candidate", target = "ICandidateId")
    Resume toEntity(ResumeDTO dto);
}
