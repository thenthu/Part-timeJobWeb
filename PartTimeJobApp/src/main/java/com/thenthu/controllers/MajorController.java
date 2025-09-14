/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.controllers;

import com.thenthu.dto.MajorDTO;
import com.thenthu.services.MajorService;
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
@RequestMapping("/admin/majors")
public class MajorController {
    @Autowired
    private MajorService majorService;
    
    @GetMapping
    public String listEmployers(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("majors", this.majorService.getMajors(params));
        return "majors";
    }
    
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("major", new MajorDTO());
        return "majorForm";
    }

    @PostMapping("/add")
    public String addJobs(@ModelAttribute("major") MajorDTO m) {
        this.majorService.addOrUpdateMajor(m);
        return "redirect:/admin/majors";
    }

    @GetMapping("/update/{iMajorId}")
    public String updateJob(Model model, @PathVariable("iMajorId") int id) {
        model.addAttribute("major", this.majorService.getMajorById(id));
        return "majorForm";
    }
}
