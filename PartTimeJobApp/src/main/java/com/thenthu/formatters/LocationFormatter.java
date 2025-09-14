/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.formatters;

import com.thenthu.dto.LocationDTO;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author this pc
 */
public class LocationFormatter implements Formatter<LocationDTO>{

    @Override
    public String print(LocationDTO loca, Locale locale) {
        return String.valueOf(loca.getLocationId());
    }

    @Override
    public LocationDTO parse(String locaId, Locale locale) throws ParseException {
        LocationDTO dto = new LocationDTO();
        dto.setLocationId(Integer.valueOf(locaId));
        return dto;
    }

}
