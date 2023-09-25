package com.dusk.country.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record CountryDto(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "cant countries")
        int cant,

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "all the names of the countries")
        List<String> countries) {
}
