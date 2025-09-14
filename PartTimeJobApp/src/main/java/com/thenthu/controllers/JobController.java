/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.controllers;

import com.thenthu.dto.JobAdminDTO;
import com.thenthu.services.ApplicationService;
import com.thenthu.services.EmployerService;
import com.thenthu.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/admin/jobs")
public class JobController {

    @Autowired
    private JobService jobService;
    @Autowired
    private EmployerService employService;
    @Autowired
    private ApplicationService appService;
    
    @ModelAttribute
    public void response(Model model) {
        model.addAttribute("employers", this.employService.getEmployer(Map.of()));
    }

    @GetMapping
    public String listJobs(Model model, @RequestParam Map<String, String> params) {
        int page = 1;
        if (params.containsKey("page")) {
            try {
                page = Integer.parseInt(params.get("page"));
            } catch (NumberFormatException ex) {
                page = 1;
            }
        }
        model.addAttribute("page", page);

        model.addAttribute("jobs", this.jobService.getJob(params));
        return "jobs";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("job", new JobAdminDTO());
        return "jobForm";
    }

    @PostMapping("/add")
    public String addJobs(@ModelAttribute("job") JobAdminDTO j) {
        this.jobService.addOrUpdateJob(j);
        return "redirect:/admin/jobs";
    }

    @GetMapping("/update/{iJobPostId}")
    public String updateJob(Model model, @PathVariable("iJobPostId") int id) {
        model.addAttribute("job", this.jobService.getJobByIdAdmin(id));
        return "jobForm";
    }
    
    @GetMapping("/{iJobPostId}")
    public String jobDetail(Model model, @PathVariable("iJobPostId") int id) {
        JobAdminDTO job = jobService.getJobByIdAdmin(id);
        model.addAttribute("job", job);
        
        model.addAttribute("applications", appService.getApplicationsByJobId(id));
        return "jobDetail";
    }
}
