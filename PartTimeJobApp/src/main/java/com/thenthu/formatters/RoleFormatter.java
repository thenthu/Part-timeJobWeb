/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.formatters;

import com.thenthu.dto.RoleDTO;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author this pc
 */
public class RoleFormatter implements Formatter<RoleDTO>{
    
    @Override
    public String print(RoleDTO role, Locale locale) {
        return String.valueOf(role.getRoleId());
    }

    @Override
    public RoleDTO parse(String roleId, Locale locale) throws ParseException {
        RoleDTO dto = new RoleDTO();
        dto.setRoleId(Integer.valueOf(roleId));
        return dto;
    }
}
