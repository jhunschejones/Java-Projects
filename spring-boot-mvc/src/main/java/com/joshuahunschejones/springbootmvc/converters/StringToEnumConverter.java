package com.joshuahunschejones.springbootmvc.converters;

import com.joshuahunschejones.springbootmvc.beans.Gender;
import org.springframework.core.convert.converter.Converter;

public class StringToEnumConverter implements Converter<String, Gender> {

    @Override
    public Gender convert(String s) {
        if(s.toUpperCase().equals("MALE")) {
            return Gender.MALE;
        }
        if(s.toUpperCase().equals("FEMALE")) {
            return Gender.FEMALE;
        }
        if(s.toUpperCase().equals("NON-BINARY")) {
            return Gender.NON_BINARY;
        }
        return Gender.PREFER_NOT_TO_SAY;
    }
}
