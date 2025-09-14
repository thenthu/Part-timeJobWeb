/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.services.impl;

import com.thenthu.dto.ApplicationDTO;
import com.thenthu.mapper.ApplicationMapper;
import com.thenthu.pojo.Application;
import com.thenthu.repositories.ApplicationRepository;
import com.thenthu.services.ApplicationService;
import com.thenthu.services.MailService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author this pc
 */
@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private MailService mailService;
    @Autowired
    private ApplicationMapper applicationMapper;

    @Override
    public List<ApplicationDTO> getApplicationsByCandidateId(int candidateId) {
        List<Application> applications = applicationRepository.getApplicationsByCandidateId(candidateId);
        return applications.stream()
                .map(applicationMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ApplicationDTO> getApplicationsByJobId(int jobId) {
        List<Application> applications = applicationRepository.getApplicationsByJobId(jobId);
        return applications.stream()
                .map(applicationMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ApplicationDTO applyToJob(ApplicationDTO application) {
        if (applicationExits(application.getCandidate().getCandidateId(), application.getJobPost().getJobId())) {
            throw new RuntimeException();
        } else {
            Application app = applicationMapper.toEntity(application);

            Application save = applicationRepository.applyToJob(app);
            ApplicationDTO dto = applicationMapper.toDTO(save);
            return dto;
        }
    }

    @Override
    public boolean applicationExits(int candidateId, int jobId) {
        return this.applicationRepository.applicationExits(candidateId, jobId);
    }

    @Override
    public ApplicationDTO getApplicationByCandidateAndJob(int candidateId, int jobId) {
        Application app = this.applicationRepository.getApplicationByCandidateAndJob(candidateId, jobId);
        return applicationMapper.toDTO(app);
    }

    @Override
    public ApplicationDTO updateApplication(ApplicationDTO app) {
        sendMail(app);
        
        Application application = applicationMapper.toEntity(app);

        Application save = applicationRepository.updateApplication(application);
        ApplicationDTO dto = applicationMapper.toDTO(save);
        return dto;
    }

    public void sendMail(ApplicationDTO app) {
        String subject = "[VIỆC LÀM 24H] Đơn ứng tuyển của bạn cho công việc " + app.getJobPost().getJobTitle() + " " + app.getStatus().toLowerCase();
        String content = "Đơn ứng tuyển của bạn cho công việc " + app.getJobPost().getJobTitle() + " " + app.getStatus().toLowerCase() + ". Vui lòng chờ thông tin mới nhất từ nhà tuyển dụng.";
        String to = app.getCandidate().getEmail();
        
        mailService.sendMail(to, subject, content);
    }
}
