
package com.lambdami.hellu.searchsata.googleresults;

import com.lambdami.hellu.externalsites.PDFHTMLParser;
import com.lambdami.hellu.externalsites.AnyHTMLParser;
import com.lambdami.hellu.externalsites.InderScienceHTMLParser;
import com.lambdami.hellu.externalsites.IoPressHTMLParser;
import com.lambdami.hellu.externalsites.ScienceDirectHTMLParser;
import com.lambdami.hellu.model.Author;
import com.lambdami.hellu.model.Publication;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.lambdami.hellu.searchsata.googleresults.PublicationType.*;

/**
 * @author Loukas Mouzos
 * contact: loukas.mouzos@gmail.com
 * site: https://github.com/lmouzos
 * <p>
 * Abstract class to parse data from Google Scholar
 */

public abstract class ParseGoogleData {

    protected List<String> data;

    protected ParseGoogleData(List<String> data) {
        this.data = data;
    }

    public abstract <T extends Publication> List<T> parseData();

    protected String getCitationsPage(String main) {
        Element element = Jsoup.parse(main).selectFirst("div.gs_fl a:contains(Cited by)");
        if (element == null) {
            return "DOES_NOT_EXIST";
        }
        return element.attr("href");
    }

    protected String getHrefPage(String main) {
        Element href = Jsoup.parse(main).selectFirst("h3 a");
        if (href == null) {
            return "No page found";
        }
        return href.attr("href");
    }

    protected PublicationType getType(String main) {
        Element type = Jsoup.parse(main).selectFirst("span.gs_ctg2");
        if (type == null) {
            return PublicationType.UNKNOWN;
        }
        switch (type.text()) {
            case "[PDF]":
                return PDF;
            case "[HTML]":
                return HTML;
            case "[BOOK]":
                return BOOK;
            case "[CITATION]":
                return PublicationType.CITATION;
            default:
                return PublicationType.UNKNOWN;
        }
    }

    protected String getTitle(String main) {
        return Jsoup.parse(main).select("h3").text();
    }

    protected Set<Author> getAuthorsFromExternalSite(String href, PublicationType type, String title) {
        Document doc = null;
        try {
            doc = Jsoup.connect(href).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (doc == null) {
            return Collections.emptySet();
        }

        if (type == PDF) {
            return new PDFHTMLParser().parseAuthors(doc);
        } else if (href.contains("www.springerlink.com")) {
            return new AnyHTMLParser().parseAuthors(doc);
        } else if (href.contains("ieeexplore.ieee.org")) {
            return new AnyHTMLParser().parseAuthors(doc);
        } else if (href.contains("sciencedirect")) {
            return new ScienceDirectHTMLParser().parseAuthors(doc);
        } else if (href.contains("inderscience")) {
            return new InderScienceHTMLParser().parseAuthors(doc);
        } else if (href.contains("iopress")) {
            return new IoPressHTMLParser().parseAuthors(doc);
        } else if (type == HTML) {
            return new AnyHTMLParser().parseAuthors(doc);
        } else if (type == BOOK || href.contains("books.google.com")) {
            return new PDFHTMLParser().parseAuthors(doc);
        } else if (type == CITATION) {
            return Collections.emptySet();
        } else {
            return new AnyHTMLParser().parseAuthors(doc);
        }
    }

    protected Set<Author> getAuthorsFromGoogle(String each) {
        Element greenLine = Jsoup.parse(each).selectFirst("div.gs_a");

        if (greenLine == null) {
            return Collections.emptySet();
        }

        String text = greenLine.text();

        if (isNotCompletedText(text)) {
            return Collections.emptySet();
        }
        List<String> authorsString = Arrays.asList(text.split(" - ")[0].split(","));
        return Extract.getAuthors(new HashSet<>(authorsString));
    }

    private boolean isNotCompletedText(String text) {
        return text.contains("\u2026") || text.contains("&hellip;");
    }

    protected String getPublicationCode(String main) {
        Element link = Jsoup.parse(main).selectFirst("h3 a");
        if (link == null) {
            return "";
        }
        return link.id();
    }

    protected int getYear(String content) {
        Element greenElement = Jsoup.parse(content).selectFirst("div.gs_a");
        if (greenElement == null) {
            return 0;
        }
        return Integer.parseInt(greenElement.text().replaceAll("[^0-9]]", ""));
    }

    protected Set<Author> getAuthorsFromCITE(String content) {
        // TODO: parse the authors with Jsoup
        if (!content.contains("\"gs_cit2\">")) {
            return Collections.emptySet();
        }
        String[] chicago = content.split("\"gs_cit2\">")[1].split("div");
        String strAuth = "";
        if (chicago[0].contains("\"")) {
            strAuth = chicago[0].split("\"")[0];
        } else {
            strAuth = chicago[0].split("<i>")[0];
        }
        strAuth = strAuth.replaceFirst(",", "")
                .replaceAll("\\.", "")
                .replaceAll(" and ", "")
                .replaceAll("et al", "");
        Set<String> authors = Arrays.stream(strAuth.split(",")).collect(Collectors.toSet());
        return Extract.getAuthors(authors);
    }

    protected String getTitleFromCite(String content) {
        // TODO: parse Title with Jsoup
        if (!content.contains("\"gs_cit2\">")) {
            return null;
        }
        String chicago = content.split("\"gs_cit2\">")[1].split("div")[0];
        String title;
        if (chicago.contains("\"")) {
            title = chicago.split("\"")[1];
        } else {
            title = chicago.split("<i>")[1];
            title = title.split("</i>")[0];
        }
        return title;
    }
}
