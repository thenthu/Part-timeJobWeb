/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.controllers;

import com.thenthu.dto.JoblevelDTO;
import com.thenthu.services.LevelService;
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
@RequestMapping("/admin/levels")
public class LevelController {
    @Autowired
    private LevelService levelService;
    
    @GetMapping
    public String listLevels(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("levels", this.levelService.getJoblevels(params));
        return "levels";
    }
    
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("level", new JoblevelDTO());
        return "levelForm";
    }

    @PostMapping("/add")
    public String addLevels(@ModelAttribute("level") JoblevelDTO j) {
        this.levelService.addOrUpdateLevel(j);
        return "redirect:/admin/levels";
    }

    @GetMapping("/update/{iLevelId}")
    public String updateJob(Model model, @PathVariable("iLevelId") int id) {
        model.addAttribute("level", this.levelService.getLevelById(id));
        return "levelForm";
    }
}
