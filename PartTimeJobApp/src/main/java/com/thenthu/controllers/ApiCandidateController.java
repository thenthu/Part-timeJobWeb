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
import com.thenthu.dto.ResumeDTO;
import com.thenthu.services.ApplicationService;
import com.thenthu.services.CandidateService;
import com.thenthu.services.EmployerService;
import com.thenthu.services.FollowCompanyService;
import com.thenthu.services.JobService;
import com.thenthu.services.ProfileService;
import com.thenthu.services.ResumeService;
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
public class ApiCandidateController {

    @Autowired
    private CandidateService candidateService;
    @Autowired
    private ResumeService resumeService;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private ApplicationService appService;
    @Autowired
    private JobService jobService;
    @Autowired
    private FollowCompanyService followService;
    @Autowired
    private EmployerService employerService;

    @PreAuthorize("hasRole('CANDIDATE')")
    @PostMapping(path = "/secure/register/candidate",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createCandidate(
            @RequestPart CandidateDTO candidate,
            @RequestPart(value = "avatar", required = false) MultipartFile avatar,
            Principal principal) {

        String username = principal.getName();

        CandidateDTO created = candidateService.addCandidate(candidate, username, avatar);

        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
    
    @PreAuthorize("hasRole('EMPLOYER')")
    @GetMapping("/secure/candidates")
    public ResponseEntity<List<CandidateDTO>> listCandidate(@RequestParam Map<String, String> params) {
        if (params.containsKey("usernames")) {
            String usernamesStr = params.get("usernames");
            List<String> usernames = Arrays.asList(usernamesStr.split(","));

            return new ResponseEntity<>(this.candidateService.getCandidatesByUsernames(usernames), HttpStatus.OK);
        }
        return new ResponseEntity<>(this.candidateService.getCandidates(params), HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('EMPLOYER')")
    @GetMapping("/secure/candidate/{candidateId}")
    public ResponseEntity<CandidateDTO> profile(@PathVariable("candidateId") int id) {
        return new ResponseEntity<>(this.candidateService.getCandidateById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('CANDIDATE')")
    @PostMapping("/secure/job/{jobId}/apply")
    public ResponseEntity<?> applyToJob(
            @PathVariable("jobId") int jobId, @RequestBody ApplicationDTO applicationDTO, Principal principal) {
        try {
            CandidateDTO currentCandidate = currentCandidate(principal.getName());

            applicationDTO.setCandidate(currentCandidate);

            JobDTO job = jobService.getJobById(jobId);
            applicationDTO.setJobPost(job);

            ApplicationDTO result = appService.applyToJob(applicationDTO);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Bạn đã nộp hồ sơ cho công việc này rồi!");
        }
    }

    @PreAuthorize("hasRole('CANDIDATE')")
    @GetMapping("/secure/candidate/applications")
    public ResponseEntity<List<ApplicationDTO>> myApplication(Principal principal) {
        CandidateDTO currentCandidate = currentCandidate(principal.getName());

        return new ResponseEntity<>(this.appService.getApplicationsByCandidateId(currentCandidate.getCandidateId()), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('CANDIDATE')")
    @GetMapping("secure/candidate/resumes")
    public ResponseEntity<List<ResumeDTO>> list(Principal principal) {
        CandidateDTO currentCandidate = currentCandidate(principal.getName());

        return new ResponseEntity<>(this.resumeService.getResumesByCandidateId(currentCandidate.getCandidateId()), HttpStatus.OK);
    }

    @PreAuthorize("@resumeSecurity.isOwner(#resumeId, authentication.principal)")
    @GetMapping("/secure/candidate/resume/{resumeId}")
    public ResponseEntity<ResumeDTO> resumeDetail(@PathVariable("resumeId") int resumeId, Principal principal) {
        return new ResponseEntity<>(this.resumeService.getResumeById(resumeId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('CANDIDATE')")
    @PostMapping("secure/candidate/resume")
    public ResponseEntity<ResumeDTO> create(@RequestBody ResumeDTO resume, Principal principal) {
        resume.setCandidate(currentCandidate(principal.getName()));

        this.resumeService.addOrUpdateResume(resume);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("@resumeSecurity.isOwner(#resumeId, authentication.principal)")
    @PatchMapping("secure/candidate/resume/{resumeId}")
    public ResponseEntity<ResumeDTO> update(@PathVariable("resumeId") int resumeId, @RequestBody ResumeDTO resume, Principal principal) {
        resume.setCandidate(currentCandidate(principal.getName()));
        resume.setResumeId(resumeId);

        this.resumeService.addOrUpdateResume(resume);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("@resumeSecurity.isOwner(#resumeId, authentication.principal)")
    @DeleteMapping("/secure/candidate/resume/{resumeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable("resumeId") int resumeId, Principal principal) {
        this.resumeService.deleteResume(resumeId);
    }

    @PreAuthorize("hasRole('CANDIDATE')")
    @GetMapping("/secure/candidate/follow-employer")
    public ResponseEntity<List<FollowCompanyDTO>> follow(Principal principal) {
        CandidateDTO currentCandidate = currentCandidate(principal.getName());

        return new ResponseEntity<>(this.followService.getFollowCompaniesByCandidateId(currentCandidate.getCandidateId()), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('CANDIDATE')")
    @PostMapping("/secure/candidate/follow-employer/{employerId}")
    public ResponseEntity<?> followEmployer(@PathVariable("employerId") int employerId, Principal principal) {
        String username = principal.getName();
        CandidateDTO candidate = (CandidateDTO) profileService.getProfile(username);
        FollowCompanyDTO follow = new FollowCompanyDTO();
        EmployerDTO employer = employerService.getEmployerById(employerId);

        follow.setCandidate(candidate);
        follow.setEmployer(employer);

        followService.addFollow(follow);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('CANDIDATE')")
    @DeleteMapping("/secure/candidate/follow-employer/{employerId}")
    public ResponseEntity<?> unfollowEmployer(@PathVariable("employerId") int employerId, Principal principal) {
        String username = principal.getName();
        CandidateDTO candidate = (CandidateDTO) profileService.getProfile(username);

        followService.unfollow(candidate.getCandidateId(), employerId);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/candidate/{candidateId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCandidate(@PathVariable("candidateId") int id) {
        candidateService.deleteCandidate(id);
    }

    private CandidateDTO currentCandidate(String principal) {
        String username = principal;
        AccountDTO acc = profileService.getProfile(username);
        CandidateDTO currentCandidate = (CandidateDTO) acc;
        return currentCandidate;
    }
}
