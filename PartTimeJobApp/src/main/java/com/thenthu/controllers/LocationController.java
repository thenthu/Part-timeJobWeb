/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.controllers;

import com.thenthu.dto.LocationDTO;
import com.thenthu.services.LocationService;
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
@RequestMapping("/admin/locations")
public class LocationController {
    @Autowired
    private LocationService locaService;
    
    @GetMapping
    public String listEmployers(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("locations", this.locaService.getLocations(params));
        return "locations";
    }
    
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("location", new LocationDTO());
        return "locationForm";
    }

    @PostMapping("/add")
    public String addJobs(@ModelAttribute("location") LocationDTO l) {
        this.locaService.addOrUpdateLocation(l);
        return "redirect:/admin/locations";
    }

    @GetMapping("/update/{iLocationId}")
    public String updateJob(Model model, @PathVariable("iLocationId") int id) {
        model.addAttribute("location", this.locaService.getLocationById(id));
        return "locationForm";
    }
}
