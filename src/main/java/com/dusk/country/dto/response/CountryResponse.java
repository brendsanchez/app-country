package com.dusk.country.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;

public record CountryResponse<T>(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "200", description = "Código de respuesta del app-country")
        int code,

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "success", description = "Descripción del código de respuesta del app-country")
        String message,

        T data) {
}
