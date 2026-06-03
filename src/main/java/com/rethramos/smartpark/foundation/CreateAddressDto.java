package com.rethramos.smartpark.foundation;

public record CreateAddressDto(String line1, String line2, String city, String province, String country,
        Double latitude, Double longitude) {

}
