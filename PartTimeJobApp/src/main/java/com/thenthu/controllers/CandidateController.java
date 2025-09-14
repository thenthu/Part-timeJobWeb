/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.controllers;

import com.thenthu.dto.CandidateAdminDTO;
import com.thenthu.services.AccountService;
import com.thenthu.services.ApplicationService;
import com.thenthu.services.CandidateService;
import com.thenthu.services.FollowCompanyService;
import com.thenthu.services.ResumeService;
import com.thenthu.services.ReviewService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author this pc
 */
@Controller
@RequestMapping("/admin/candidates")
public class CandidateController {

    @Autowired
    private CandidateService canService;
    @Autowired
    private ResumeService resumeService;
    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private FollowCompanyService followCompanyService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private AccountService accService;

    @ModelAttribute
    public void response(Model model) {
        model.addAttribute("accounts", this.accService.getAccount(Map.of()));
    }

    @GetMapping
    public String listCandidates(Model model, @RequestParam Map<String, String> params) {
        int page = 1;
        if (params.containsKey("page")) {
            try {
                page = Integer.parseInt(params.get("page"));
            } catch (NumberFormatException ex) {
                page = 1;
            }
        }
        model.addAttribute("page", page);

        model.addAttribute("candidates", this.canService.getCandidate(params));
        return "candidates";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("candidate", new CandidateAdminDTO());
        return "candidateForm";
    }

    @PostMapping("/add")
    public String addJobs(@ModelAttribute("candidate") CandidateAdminDTO c) {
        this.canService.addOrUpdateCandidate(c);
        return "redirect:/admin/candidates";
    }

    @GetMapping("/update/{iCandidateId}")
    public String updateJob(Model model, @PathVariable("iCandidateId") int id) {
        model.addAttribute("candidate", this.canService.getCandidateByIdAdmin(id));
        return "candidateForm";
    }

    @GetMapping("/{iCandidateId}")
    public String candidateDetail(Model model, @PathVariable("iCandidateId") int id, @RequestParam Map<String, String> params) {
        CandidateAdminDTO candidate = canService.getCandidateByIdAdmin(id);
        model.addAttribute("candidate", candidate);

        model.addAttribute("resumes", resumeService.getResumesByCandidateId(id));
        model.addAttribute("applications", applicationService.getApplicationsByCandidateId(id));
        model.addAttribute("followCompanies", followCompanyService.getFollowCompaniesByCandidateId(id));
        if (reviewService != null) {
            int page = 1;
            if (params.containsKey("page")) {
                try {
                    page = Integer.parseInt(params.get("page"));
                } catch (NumberFormatException ex) {
                    page = 1;
                }
            }
            model.addAttribute("page", page);

            model.addAttribute("reviewsReceived", reviewService.getReviewsForUser(params, candidate.getAccount().getUsername()));
            model.addAttribute("reviewsWritten", reviewService.getReviewsByUser(candidate.getAccount().getUsername()));
        }
        return "candidateDetail";
    }
}
