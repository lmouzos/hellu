package com.lambdami.hellu.externalsites;

import com.lambdami.hellu.model.Author;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScienceDirectHTMLParserTest {

    private final HTMLParser underTest = new ScienceDirectHTMLParser();

    @Test
    void parseAuthors() throws IOException {
        Document content = Jsoup.parse(HtmlUtils.getContent("/html/sciencedirect.html"));
        Set<Author> authors = underTest.parseAuthors(content);
        assertEquals(3, authors.size());
    }
}