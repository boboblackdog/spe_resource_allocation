
package com.example.spera_2.models.requests;

import org.bson.Document;

/**
 *
 * @author rakhadjo
 */
public class UserProfileRequest {
    
    private String nik;
    private String device_id;
    
    public String getNik() { return nik; }
    public void setNik(String nik) { this.nik = nik; }
    
    public String getDevice_id() { return device_id; }
    public void setDevice_id(String device_id) { this.device_id = device_id; }
    
    public UserProfileRequest() {}
    public UserProfileRequest(String nik, String device_id) {
        this.nik = nik;
        this.device_id = device_id;
    }
    
    public boolean nikVerifiable() {
        try {
            Integer.parseInt(nik);
            return nik.length() == 8;
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public String toString() {
        return    "nik: " + this.nik + ",%0D%0A" 
                + "device_id: " + this.device_id + "%0D%0A"
                ;
    }
    
    public Document toDocument() {
        return new Document()
                .append("nik", nik)
                .append("device_id", device_id)
                ;
    }
}
