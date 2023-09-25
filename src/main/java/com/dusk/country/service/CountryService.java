package com.dusk.country.service;

import com.dusk.country.scraping.wiki.CountryWiki;
import com.dusk.country.dto.Country;
import com.dusk.country.dto.response.CountryDto;
import com.dusk.country.dto.response.CountryInfoDto;
import com.dusk.country.dto.response.CountryResponse;
import com.dusk.country.exception.GenericCountryException;
import com.dusk.country.scraping.wiki.model.Adr;
import com.dusk.country.scraping.wiki.model.CountryInfoBox;
import com.dusk.country.scraping.wiki.model.ImageRoute;
import com.dusk.country.scraping.wiki.model.Label;
import com.dusk.country.utils.UtilDocument;
import com.dusk.country.enums.Wiki;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.util.List;
import java.util.Optional;


@Service
public class CountryService {
    private final Logger logger = LoggerFactory.getLogger(CountryService.class);

    public CountryResponse<CountryDto> getCountries() {
        List<Country> countries = CountryWiki.getCountries();

        if (countries.isEmpty()) {
            throw new GenericCountryException("countries wiki empty");
        }

        List<String> nameCountries = countries.stream()
                .map(Country::name)
                .toList();

        CountryDto countryDto = new CountryDto(nameCountries.size(), nameCountries);
        logger.debug("found countries");
        return new CountryResponse<>(HttpURLConnection.HTTP_OK,
                "success",
                countryDto);
    }

    public CountryResponse<CountryInfoDto> getCountryInfo(String country) {
        List<Country> countries = CountryWiki.getCountries();

        Optional<Country> countryFound = countries.stream()
                .filter(c -> c.name().equalsIgnoreCase(country))
                .findFirst();

        if (countryFound.isEmpty()) {
            throw new GenericCountryException("not found country with: " + country);
        }

        String uri = Wiki.URI.route + countryFound.get().path();
        logger.debug("founding info: {}", uri);
        Document document = UtilDocument.getFromUri(uri);
        var cityDto = getCountryInfoDto(document);
        return new CountryResponse<>(HttpURLConnection.HTTP_OK,
                "success",
                cityDto);
    }

    private CountryInfoDto getCountryInfoDto(Document document) {
        CountryInfoBox countryInfoBox = new CountryInfoBox(document);

        Adr adr = countryInfoBox.getAdr();
        ImageRoute imageRoute = countryInfoBox.getImageRoute();
        String motto = countryInfoBox.getMotto();
        List<String> anthems = countryInfoBox.getAnthems();
        Label label = countryInfoBox.getLabel();
        Label largestCity = countryInfoBox.getLargestCity();

        return new CountryInfoDto(adr,
                imageRoute,
                motto,
                anthems,
                label,
                largestCity);
    }
}
