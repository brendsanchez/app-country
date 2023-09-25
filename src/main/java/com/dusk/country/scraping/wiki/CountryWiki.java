package com.dusk.country.scraping.wiki;

import com.dusk.country.dto.Country;
import com.dusk.country.utils.UtilDocument;
import com.dusk.country.enums.Wiki;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

public final class CountryWiki {
    private CountryWiki() {
        throw new UnsupportedOperationException("This is a CountryWiki class and cannot be instantiated");
    }

    private static List<Country> countries = new ArrayList<>();

    public static void start() {
        countries = getCountriesFromWiki();
    }

    public static List<Country> getCountries() {
        return countries;
    }

    private static List<Country> getCountriesFromWiki() {
        Document document = UtilDocument.getFromUri(Wiki.URI.route + Wiki.ALL_COUNTRY.route);
        Element tables = document.getElementsByTag("tbody").getLast();
        Assert.notNull(tables, "don't found body");

        Element info = tables.select("div").last();
        Assert.notNull(info, "don't found info");

        List<Country> countries = new ArrayList<>();
        Elements links = info.select("a");
        for (Element link : links) {
            String name = link.attr("title");
            String path = link.attr("href");

            var country = new Country(name, path);
            countries.add(country);
        }

        return countries;
    }
}
