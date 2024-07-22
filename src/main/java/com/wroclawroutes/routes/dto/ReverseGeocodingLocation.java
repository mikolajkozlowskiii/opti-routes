package com.wroclawroutes.routes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReverseGeocodingLocation {
    private Point point;
    private String name;
    private String country;
    @JsonProperty("countrycode")
    private String countryCode;
    private String city;
    private String state;
    private String street;
    private String postcode;
    @JsonProperty("housenumber")
    private String houseNumber;
    @JsonProperty("osmvalue")
    private String osmValue;

    public String getAddress() {
        StringBuilder address = new StringBuilder();

        if (street != null && !street.isEmpty()) {
            address.append(street);
        }
        if (country != null && !country.isEmpty()) {
            if (!address.isEmpty()) {
                address.append(", ");
            }
            address.append(country);
        }
        if (city != null && !city.isEmpty()) {
            if (!address.isEmpty()) {
                address.append(", ");
            }
            address.append(city);
        }
        if (postcode != null && !postcode.isEmpty()) {
            if (!address.isEmpty() && city != null && !city.isEmpty()) {
                address.append(" ");
            } else if (!address.isEmpty()) {
                address.append(", ");
            }
            address.append(postcode);
        }

        return address.toString();
    }

}

