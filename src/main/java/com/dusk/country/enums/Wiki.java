package com.dusk.country.enums;

public enum Wiki {
    URI("https://en.wikipedia.org"),
    ALL_COUNTRY("/wiki/List_of_countries_and_dependencies_by_population");

    public final String route;

    Wiki(String route) {
        this.route = route;
    }
}
