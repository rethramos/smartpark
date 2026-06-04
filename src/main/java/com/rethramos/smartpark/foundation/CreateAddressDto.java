package com.rethramos.smartpark.foundation;

import jakarta.validation.constraints.NotEmpty;

public record CreateAddressDto(@NotEmpty String line1, String line2, @NotEmpty String city, @NotEmpty String province,
                @NotEmpty String country,
                Double latitude, Double longitude) {

}
