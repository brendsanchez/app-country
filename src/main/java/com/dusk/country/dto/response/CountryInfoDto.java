package com.dusk.country.dto.response;


import com.dusk.country.scraping.wiki.model.Adr;
import com.dusk.country.scraping.wiki.model.ImageRoute;
import com.dusk.country.scraping.wiki.model.Label;

import java.util.List;

public record CountryInfoDto(
        Adr adr,
        ImageRoute imageRoute,
        String motto,
        List<String> anthems,
        Label label,
        Label largestCity) {
}
