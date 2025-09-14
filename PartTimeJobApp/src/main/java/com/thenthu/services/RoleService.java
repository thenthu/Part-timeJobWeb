/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.services;

import com.thenthu.dto.RoleDTO;
import java.util.List;
import java.util.Map;

/**
 *
 * @author this pc
 */
public interface RoleService {
    List<RoleDTO> getRoles(Map<String, String> params);
}
