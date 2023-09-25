package com.dusk.country;

import com.dusk.country.scraping.wiki.CountryWiki;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CountryApplication {
    public static void main(String[] args) {
        CountryWiki.start();
        SpringApplication.run(CountryApplication.class, args);
    }
}