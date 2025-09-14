/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.controllers;

import com.thenthu.services.LevelService;
import com.thenthu.services.LocationService;
import com.thenthu.services.MajorService;
import com.thenthu.services.StatsService;
import java.time.Year;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author this pc
 */
@Controller
@ControllerAdvice
public class HomeController {

    @Autowired
    private LevelService levelService;
    @Autowired
    private LocationService locaService;
    @Autowired
    private MajorService majorService;
    @Autowired
    private StatsService statsService;

    @ModelAttribute
    public void commonResponses(Model model) {
        model.addAttribute("levels", this.levelService.getJoblevels(Map.of()));
        model.addAttribute("locations", this.locaService.getLocations(Map.of()));
        model.addAttribute("majors", this.majorService.getMajors(Map.of()));
    }

    @GetMapping("/admin")
    public String index(Model model, @RequestParam Map<String, String> params) {
        
        int year = Year.now().getValue();
        model.addAttribute("jobsStats", statsService.countJobsByMonth("MONTH", year));
        model.addAttribute("candidatesStats", statsService.countCandidatesByMonth("MONTH", year));
        model.addAttribute("employersStats", statsService.countEmployersByMonth("MONTH", year));

        return "index";
    }
}
