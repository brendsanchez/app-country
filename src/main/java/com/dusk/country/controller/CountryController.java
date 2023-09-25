package com.dusk.country.controller;

import com.dusk.country.dto.response.CountryDto;
import com.dusk.country.dto.response.CountryInfoDto;
import com.dusk.country.dto.response.CountryResponse;
import com.dusk.country.service.CountryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/v1/country")
public class CountryController {

    @Autowired
    private CountryService countryService;

    @Operation(summary = "get all countries.", tags = {"country"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "404", useReturnTypeSchema = true, content = @Content(schema = @Schema(
                    example = "{\"code\": 404, \"message\": \"not found country o city\"}"))),
            @ApiResponse(responseCode = "500", useReturnTypeSchema = true, content = @Content(schema = @Schema(
                    example = "{\"code\": 500, \"message\": \"error intern\"}"))),
    })
    @GetMapping
    public CountryResponse<CountryDto> getCountries() {
        return this.countryService.getCountries();
    }


    @Operation(summary = "get country info", tags = {"country"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "404", useReturnTypeSchema = true, content = @Content(schema = @Schema(
                    example = "{\"code\": 404, \"message\": \"not found country o city\"}"))),
            @ApiResponse(responseCode = "500", useReturnTypeSchema = true, content = @Content(schema = @Schema(
                    example = "{\"code\": 500, \"message\": \"error intern\"}"))),
    })
    @GetMapping("/{country}")
    public CountryResponse<CountryInfoDto> getCountryInfo(@Parameter(name = "country", description = "country to get info", example = "bosnia and herzegovina")
                                                          @PathVariable("country") final String country) {
        return this.countryService.getCountryInfo(country);
    }
}
