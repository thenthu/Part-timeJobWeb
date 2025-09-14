/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.controllers;

import com.thenthu.dto.AccountDTO;
import com.thenthu.dto.ApplicationDTO;
import com.thenthu.dto.CandidateDTO;
import com.thenthu.dto.EmployerDTO;
import com.thenthu.dto.FollowCompanyDTO;
import com.thenthu.dto.JobDTO;
import com.thenthu.services.ApplicationService;
import com.thenthu.services.CandidateService;
import com.thenthu.services.EmployerService;
import com.thenthu.services.FollowCompanyService;
import com.thenthu.services.JobService;
import com.thenthu.services.ProfileService;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author this pc
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiEmployerController {

    @Autowired
    private EmployerService employService;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private JobService jobService;
    @Autowired
    private ApplicationService appService;
    @Autowired
    private FollowCompanyService followService;
    @Autowired
    private CandidateService candidateService;

    @PreAuthorize("hasRole('EMPLOYER')")
    @PostMapping(path = "/secure/register/employer",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createEmployer(
            @RequestPart("employer") EmployerDTO employer,
            @RequestPart(value = "avatar", required = false) MultipartFile avatar,
            @RequestPart(value = "workEnvImg1", required = false) MultipartFile workEnvImg1,
            @RequestPart(value = "workEnvImg2", required = false) MultipartFile workEnvImg2,
            @RequestPart(value = "workEnvImg3", required = false) MultipartFile workEnvImg3,
            @RequestPart(value = "verifyDoc", required = false) MultipartFile verifyDoc,
            Principal principal) {

        String username = principal.getName();

        EmployerDTO created = employService.addEmployer(employer, username, avatar, workEnvImg1, workEnvImg2, workEnvImg3, verifyDoc);

        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('CANDIDATE')")
    @GetMapping("/secure/employers")
    public ResponseEntity<List<EmployerDTO>> listEmployer(@RequestParam Map<String, String> params) {
        if (params.containsKey("usernames")) {
            String usernamesStr = params.get("usernames");
            List<String> usernames = Arrays.asList(usernamesStr.split(","));

            return new ResponseEntity<>(this.employService.getEmployersByUsernames(usernames), HttpStatus.OK);
        }
        return new ResponseEntity<>(this.employService.getEmployers(params), HttpStatus.OK);
    }

    @GetMapping("/employer/{employerId}")
    public ResponseEntity<EmployerDTO> profile(@PathVariable("employerId") int id) {
        return new ResponseEntity<>(this.employService.getEmployerById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/employer/{employerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployer(@PathVariable("employerId") int id) {
        employService.deleteEmployer(id);
    }

    @PreAuthorize("hasRole('EMPLOYER')")
    @GetMapping("secure/employer/jobs")
    public ResponseEntity<List<JobDTO>> getJobs(@RequestParam Map<String, String> params, Principal principal) {
        String username = principal.getName();

        AccountDTO acc = profileService.getProfile(username);
        EmployerDTO currentEmployer = (EmployerDTO) acc;
        int employerId = currentEmployer.getEmployerId();
        params.put("employerId", String.valueOf(employerId));

        return new ResponseEntity<>(this.jobService.getJobsForEmployer(params), HttpStatus.OK);
    }

    @PreAuthorize("@jobSecurity.isOwner(#jobId, authentication.principal)")
    @DeleteMapping("/secure/employer/job/{jobId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable("jobId") int iJobpostId) {
        this.jobService.deleteJob(iJobpostId);
    }

    @PreAuthorize("@jobSecurity.isOwner(#jobId, authentication.principal)")
    @PatchMapping("/secure/employer/job/{jobId}")
    public ResponseEntity<JobDTO> update(@PathVariable("jobId") int jobId, @RequestBody JobDTO job, Principal principal) {
        String username = principal.getName();
        AccountDTO acc = profileService.getProfile(username);
        EmployerDTO currentEmployer = (EmployerDTO) acc;

        job.setEmployer(currentEmployer);
        job.setJobId(jobId);
        this.jobService.addOrUpdateJob(job);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("@jobSecurity.isOwner(#jobId, authentication.principal)")
    @GetMapping("/secure/employer/job/{jobId}/applications")
    public ResponseEntity<List<ApplicationDTO>> getApplicationsForJob(
            @PathVariable(value = "jobId") int jobId) {
        List<ApplicationDTO> applications = appService.getApplicationsByJobId(jobId);
        return new ResponseEntity<>(applications, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('EMPLOYER')")
    @GetMapping("/secure/employer/followers")
    public ResponseEntity<List<FollowCompanyDTO>> getFollowers(Principal principal) {
        String username = principal.getName();
        EmployerDTO employer = (EmployerDTO) profileService.getProfile(username);

        List<FollowCompanyDTO> followers = followService.getFollowCompaniesByEmployerId(employer.getEmployerId());

        return ResponseEntity.ok(followers);
    }

    @PreAuthorize("hasRole('EMPLOYER')")
    @PostMapping("/secure/employer/job")
    public ResponseEntity<JobDTO> create(@RequestBody JobDTO jobDTO, Principal principal) {
        String username = principal.getName();
        AccountDTO acc = profileService.getProfile(username);
        EmployerDTO currentEmployer = (EmployerDTO) acc;
        jobDTO.setEmployer(currentEmployer);

        this.jobService.addOrUpdateJob(jobDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("@jobSecurity.isOwner(#jobId, authentication.principal)")
    @PatchMapping("/secure/employer/job/application")
    public ResponseEntity<ApplicationDTO> approveOrReject(
            @RequestParam("candidateId") int candidateId,
            @RequestParam("jobId") int jobId,
            @RequestParam("status") String status
    ) {
        ApplicationDTO app = appService.getApplicationByCandidateAndJob(candidateId, jobId);
        if (app == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        CandidateDTO candidate = candidateService.getCandidateById(candidateId);
        JobDTO job = jobService.getJobById(jobId);

        app.setCandidate(candidate);
        app.setJobPost(job);
        app.setStatus(status);
        appService.updateApplication(app);
        return ResponseEntity.ok(app);
    }
}
