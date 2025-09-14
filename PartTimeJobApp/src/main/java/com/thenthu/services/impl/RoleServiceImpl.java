/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.services.impl;

import com.thenthu.dto.RoleDTO;
import com.thenthu.mapper.RoleMapper;
import com.thenthu.pojo.Role;
import com.thenthu.repositories.RoleRepository;
import com.thenthu.services.RoleService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author this pc
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepo;
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<RoleDTO> getRoles(Map<String, String> params) {
        List<Role> joblevels = this.roleRepo.getRoles(params);
        return joblevels.stream()
                .filter(role -> !role.getIRoleId().equals(1))
                .map(roleMapper::toDTO)
                .collect(Collectors.toList());
    }
}
