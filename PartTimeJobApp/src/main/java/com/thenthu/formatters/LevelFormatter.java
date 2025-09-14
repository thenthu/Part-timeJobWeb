/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.formatters;

import com.thenthu.dto.JoblevelDTO;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author this pc
 */
public class LevelFormatter implements Formatter<JoblevelDTO> {

    @Override
    public String print(JoblevelDTO level, Locale locale) {
        return String.valueOf(level.getLevelId());
    }

    @Override
    public JoblevelDTO parse(String levelId, Locale locale) throws ParseException {
        JoblevelDTO dto = new JoblevelDTO();
        dto.setLevelId(Integer.valueOf(levelId));
        return dto;
    }
}
