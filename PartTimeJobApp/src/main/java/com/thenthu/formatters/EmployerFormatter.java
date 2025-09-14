/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.formatters;

import com.thenthu.dto.EmployerAdminDTO;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author this pc
 */
public class EmployerFormatter implements Formatter<EmployerAdminDTO>{

    @Override
    public String print(EmployerAdminDTO employ, Locale locale) {
        return String.valueOf(employ.getEmployerId());
    }

    @Override
    public EmployerAdminDTO parse(String employerId, Locale locale) throws ParseException {
        EmployerAdminDTO dto = new EmployerAdminDTO();
        dto.setEmployerId(Integer.valueOf(employerId));
        return dto;
    }
    
}
