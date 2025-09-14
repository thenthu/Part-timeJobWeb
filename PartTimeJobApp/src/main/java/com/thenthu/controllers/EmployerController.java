/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.controllers;

import com.thenthu.dto.EmployerAdminDTO;
import com.thenthu.services.AccountService;
import com.thenthu.services.EmployerService;
import com.thenthu.services.FollowCompanyService;
import com.thenthu.services.JobService;
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
@RequestMapping("/admin/employers")
public class EmployerController {

    @Autowired
    private EmployerService employService;
    @Autowired
    private FollowCompanyService followCompanyService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private JobService jobService;
    @Autowired
    private AccountService accService;

    @ModelAttribute
    public void response(Model model) {
        model.addAttribute("accounts", this.accService.getAccount(Map.of()));
    }

    @GetMapping
    public String listEmployers(Model model, @RequestParam Map<String, String> params) {
        int page = 1;
        if (params.containsKey("page")) {
            try {
                page = Integer.parseInt(params.get("page"));
            } catch (NumberFormatException ex) {
                page = 1;
            }
        }
        model.addAttribute("page", page);
        
        model.addAttribute("employers", this.employService.getEmployer(params));
        return "employers";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("employer", new EmployerAdminDTO());
        return "employerForm";
    }

    @PostMapping("/add")
    public String addJobs(@ModelAttribute("employer") EmployerAdminDTO e) {
        this.employService.addOrUpdateEmployer(e);
        return "redirect:/admin/employers";
    }

    @GetMapping("/update/{iEmployerId}")
    public String updateJob(Model model, @PathVariable("iEmployerId") int id) {
        model.addAttribute("employer", this.employService.getEmployerByIdAdmin(id));
        return "employerForm";
    }

    @GetMapping("/{iEmployerId}")
    public String employerDetail(Model model, @PathVariable("iEmployerId") int id, @RequestParam Map<String, String> params) {
        EmployerAdminDTO employer = employService.getEmployerByIdAdmin(id);
        model.addAttribute("employer", employer);

        model.addAttribute("followCompanies", followCompanyService.getFollowCompaniesByEmployerId(id));
        model.addAttribute("followerCount", followCompanyService.getFollowCompaniesByEmployerId(id) != null ? followCompanyService.getFollowCompaniesByEmployerId(id).size() : 0);
        model.addAttribute("jobs", jobService.getJobsByEmployerId(id));
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

            model.addAttribute("reviewsReceived", reviewService.getReviewsForUser(params, employer.getAccount().getUsername()));
            model.addAttribute("reviewsWritten", reviewService.getReviewsByUser(employer.getAccount().getUsername()));
        }
        return "employerDetail";
    }
}
