package com.dusk.country.scraping.wiki.model;

import com.dusk.country.enums.CountryBox;
import com.dusk.country.enums.Wiki;
import org.apache.logging.log4j.util.Strings;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;

import static java.util.Objects.isNull;

public class CountryInfoBox {

    private final Element tBodyCard;
    private final EnumMap<CountryBox, Element> rowsBoxes;

    public CountryInfoBox(Element tBodyCard) {
        this.tBodyCard = this.tBodyVCard(tBodyCard);
        this.rowsBoxes = this.rowsBoxElements();
    }

    public Adr getAdr() {
        Element adr = this.rowsBoxes.get(CountryBox.ADR);
        if (isNull(adr)) {
            return null;
        }

        String countryName = this.tBodyCard.getElementsByClass("fn org country-name").text();
        String languageText = this.tBodyCard.getElementsByClass("ib-country-names").getFirst().text();
        return new Adr(countryName, languageText);
    }

    public ImageRoute getImageRoute() {
        Element imageRoute = this.rowsBoxes.get(CountryBox.IMAGE_ROUTE);
        if (isNull(imageRoute)) {
            return null;
        }

        String flagHref = imageRoute.getElementsByTag("a").get(0).attr("href");
        String coatOfArmsHref = imageRoute.getElementsByTag("a").get(2).attr("href");

        String flag = this.wikiRouteConcat(flagHref);
        String coatOfArms = this.wikiRouteConcat(coatOfArmsHref);
        return new ImageRoute(flag, coatOfArms);
    }

    public String getMotto() {
        Element motto = this.rowsBoxes.get(CountryBox.MOTTO);
        if (isNull(motto)) {
            return Strings.EMPTY;
        }

        Element mottoSpan = motto.selectFirst("span");
        if (isNull(mottoSpan)) {
            return Strings.EMPTY;
        }

        return this.replaceQuotationMarks(mottoSpan.text());
    }

    public List<String> getAnthems() {
        Element anthem = this.rowsBoxes.get(CountryBox.ANTHEM);
        if (isNull(anthem)) {
            return Collections.emptyList();
        }

        Elements anthemElements = anthem.select("span");
        if (anthemElements.isEmpty()) {
            return Collections.emptyList();
        }

        List<String> anthems = new ArrayList<>();
        for (Element a : anthemElements) {
            String anthemText = this.replaceQuotationMarks(a.text());
            if (this.hasValidAnthem(anthemText)) {
                anthems.add(anthemText);
            }
        }
        return anthems;
    }

    public Label getLabel() {
        Element label = this.rowsBoxes.get(CountryBox.LABEL);
        if (isNull(label)) {
            return null;
        }

        return this.getLabel(label);
    }

    public Label getLargestCity() {
        Element largestCity = this.rowsBoxes.get(CountryBox.LARGEST_CITY);
        if (isNull(largestCity)) {
            return null;
        }

        Element thLargestCity = largestCity.getElementsMatchingText("Largest city").first();
        if (isNull(thLargestCity)) {
            return null;
        }

        Element aLargestCity = largestCity.getElementsByTag("a").first();
        if (isNull(aLargestCity)) {
            return null;
        }

        return this.getLabel(aLargestCity);
    }

    private EnumMap<CountryBox, Element> rowsBoxElements() {
        EnumMap<CountryBox, Element> rowsBoxElements = new EnumMap<>(CountryBox.class);

        Element adr = this.tBodyCard.getElementsByClass("infobox-above adr").first();
        Element imageRoute = this.tBodyCard.getElementsByClass("infobox-image").first();
        Element motto = this.tBodyCard.selectFirst("td.infobox-full-data");
        Element anthem = this.tBodyCard.getElementsByClass("infobox-full-data anthem").first();
        Element label = this.tBodyCard.selectFirst("td.infobox-data a");
        Element largestCity = this.tBodyCard.selectFirst("tr.mergedbottomrow");

        rowsBoxElements.put(CountryBox.ADR, adr);
        rowsBoxElements.put(CountryBox.IMAGE_ROUTE, imageRoute);
        rowsBoxElements.put(CountryBox.MOTTO, motto);
        rowsBoxElements.put(CountryBox.ANTHEM, anthem);
        rowsBoxElements.put(CountryBox.LABEL, label);
        rowsBoxElements.put(CountryBox.LARGEST_CITY, largestCity);
        return rowsBoxElements;
    }

    private Element tBodyVCard(Element tBody) {
        Element countryCardElement = tBody.getElementsByClass("infobox ib-country vcard").first();
        Assert.notNull(countryCardElement, "don't found ib-country vcard element");

        Element tBodyVCard = countryCardElement.getElementsByTag("tbody").first();
        Assert.notNull(tBodyVCard, "don't found country card tbody element");

        return tBodyVCard;
    }

    private Label getLabel(Element element) {
        String capitalName = element.attr("title");
        String capitalRoute = this.wikiRouteConcat(element.attr("href"));
        return new Label(capitalName, capitalRoute);
    }

    private boolean hasValidAnthem(String anthem) {
        if (anthem.isEmpty()) {
            return false;
        }
        String anthemResult = this.replaceQuotationMarks(anthem);
        return !anthemResult.contains("(");
    }

    private String wikiRouteConcat(String result) {
        return result.isEmpty() ? Strings.EMPTY : Wiki.URI.route.concat(result);
    }

    private String replaceQuotationMarks(String field) {
        return field.replace("\"", Strings.EMPTY).trim();
    }
}
