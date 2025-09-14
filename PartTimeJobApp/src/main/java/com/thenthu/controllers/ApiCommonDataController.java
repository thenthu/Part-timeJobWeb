/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.controllers;

import com.thenthu.dto.JoblevelDTO;
import com.thenthu.dto.LocationDTO;
import com.thenthu.dto.MajorDTO;
import com.thenthu.dto.RoleDTO;
import com.thenthu.services.LevelService;
import com.thenthu.services.LocationService;
import com.thenthu.services.MajorService;
import com.thenthu.services.RoleService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author this pc
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiCommonDataController {
    @Autowired
    private LevelService levelService;
    @Autowired
    private MajorService majorService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private RoleService roleService;
    
    @GetMapping("/job-levels")
    public ResponseEntity<List<JoblevelDTO>> getJobLevels(@RequestParam Map<String, String> params) {
        return new ResponseEntity<>(this.levelService.getJoblevels(params), HttpStatus.OK);
    }

    @GetMapping("/majors")
    public ResponseEntity<List<MajorDTO>> getMajors(@RequestParam Map<String, String> params) {
        return new ResponseEntity<>(this.majorService.getMajors(params), HttpStatus.OK);
    }

    @GetMapping("/locations")
    public ResponseEntity<List<LocationDTO>> getLocations(@RequestParam Map<String, String> params) {
        return new ResponseEntity<>(this.locationService.getLocations(params), HttpStatus.OK);
    }
    
    @GetMapping("/roles")
    public ResponseEntity<List<RoleDTO>> getRoles(@RequestParam Map<String, String> params) {
        return new ResponseEntity<>(this.roleService.getRoles(params), HttpStatus.OK);
    }
}
