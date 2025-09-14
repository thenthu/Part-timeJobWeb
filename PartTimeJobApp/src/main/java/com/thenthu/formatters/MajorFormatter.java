/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.formatters;

import com.thenthu.dto.MajorDTO;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author this pc
 */
public class MajorFormatter implements Formatter<MajorDTO>{

    @Override
    public String print(MajorDTO major, Locale locale) {
        return String.valueOf(major.getMajorId());
    }

    @Override
    public MajorDTO parse(String majorId, Locale locale) throws ParseException {
        MajorDTO dto = new MajorDTO();
        dto.setMajorId(Integer.valueOf(majorId));
        return dto;
    }
    
}
