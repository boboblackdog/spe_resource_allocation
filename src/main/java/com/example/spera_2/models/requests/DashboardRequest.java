/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.spera_2.models.requests;

import java.util.regex.Pattern;
import org.bson.Document;

/**
 *
 * @author rakhadjo
 */
public class DashboardRequest {

    private String year;
    private String device_id;

    private static final String VALID_DEVICE_ID_REGEX = "[A-Za-z0-9]{17}";

    public DashboardRequest() {
    }

    public DashboardRequest(String device_id, String year) {
        if (isValid(device_id, year)) {
            this.year = year;
            this.device_id = device_id;
        } else {
            this.year = "";
            this.device_id = "";
        }

    }

    private static boolean validDeviceId(String device_id) {
        return Pattern.matches(VALID_DEVICE_ID_REGEX, device_id);
    }

    private static boolean validYear(String year) {
        try {

            return Integer.parseInt(year) > 1970
                    && Integer.parseInt(year) < 2100
                    && year.length() == 4;

        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isValid(String device_id, String year) {
        return validYear(year) && validDeviceId(device_id);
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public Document toJSON() {
        return new Document()
                .append("year", year)
                .append("device_id", device_id);
    }
}
