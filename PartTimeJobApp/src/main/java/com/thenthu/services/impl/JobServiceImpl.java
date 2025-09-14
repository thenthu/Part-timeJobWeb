/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.services.impl;

import com.thenthu.dto.CandidateDTO;
import com.thenthu.dto.FollowCompanyDTO;
import com.thenthu.dto.JobAdminDTO;
import com.thenthu.dto.JobDTO;
import com.thenthu.mapper.JobMapper;
import com.thenthu.pojo.Job;
import com.thenthu.repositories.JobRepository;
import com.thenthu.services.FollowCompanyService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import com.thenthu.services.JobService;
import com.thenthu.services.MailService;
import java.time.LocalDate;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 *
 * @author this pc
 */
@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepository jobRepo;
    @Autowired
    private JobMapper jobMapper;
    @Autowired
    private MailService mailService;
    @Autowired
    private FollowCompanyService followService; 

    @Override
    public List<JobAdminDTO> getJob(Map<String, String> params) {
        List<Job> jobs = this.jobRepo.getJob(params);

        return jobs.stream()
                .map(jobMapper::toAdminDTO)
                .toList();
    }

    @Override
    public JobAdminDTO getJobByIdAdmin(int id) {
        Job job = this.jobRepo.getJobById(id);
        return jobMapper.toAdminDTO(job);
    }

    @Override
    public void deleteJob(int id) {
        this.jobRepo.deleteJob(id);
    }

    @Override
    public void addOrUpdateJob(JobAdminDTO dto) {
        Job job = jobMapper.toEntity(dto);
        this.jobRepo.addOrUpdateJob(job);
    }

    @Override
    public List<JobAdminDTO> getJobsByEmployerId(int employerId) {
        List<Job> list = jobRepo.getJobsByEmployerId(employerId);
        return list.stream()
                .map(jobMapper::toAdminDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<JobDTO> getJobs(Map<String, String> params) {
        List<Job> jobs = this.jobRepo.getJob(params);

        LocalDate now = LocalDate.now();

        return jobs.stream()
                .filter(job -> Boolean.TRUE.equals(job.getBStatus()))
                .filter(job -> job.getDDeadline() != null && !job.getDDeadline().isBefore(now))
                .map(jobMapper::toDTO)
                .toList();
    }

    @Override
    public JobDTO getJobById(int id) {
        Job job = this.jobRepo.getJobById(id);
        return jobMapper.toDTO(job);
    }

    @Override
    public void addOrUpdateJob(JobDTO dto) {
        if(dto.getJobId() == null) {
            sendMail(dto);
        }
        Job job = jobMapper.toEntity(dto);
        this.jobRepo.addOrUpdateJob(job);
    }

    @Override
    public List<JobDTO> getJobsForEmployer(Map<String, String> params) {
        List<Job> jobs = this.jobRepo.getJob(params);

        return jobs.stream()
                .map(jobMapper::toDTO)
                .toList();
    }

    @Override
    public int getTotalPages(Map<String, String> params) {
        return this.jobRepo.getTotalPages(params);
    }
    
    public void sendMail(JobDTO job) {
        int employerId = job.getEmployer().getEmployerId();
        List<FollowCompanyDTO> followers = followService.getFollowCompaniesByEmployerId(employerId);
        
        String subject = "[VIỆC LÀM 24H] Có cập nhật mới từ nhà tuyển dụng " + job.getEmployer().getCompanyName();
        String content = "Nhà tuyển dụng " + job.getEmployer().getCompanyName() + " vừa đăng tin " + job.getJobTitle();
        
        for (FollowCompanyDTO follower : followers) {
            mailService.sendMail(follower.getCandidate().getEmail(), subject, content);
        }
    }
}
