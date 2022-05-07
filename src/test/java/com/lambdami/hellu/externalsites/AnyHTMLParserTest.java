package com.lambdami.hellu.externalsites;

import com.lambdami.hellu.model.Author;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AnyHTMLParserTest {

    private final HTMLParser underTest = new AnyHTMLParser();

    @Test
    void test_Metadata_content_author() throws IOException {
        Document content = Jsoup.parse(HtmlUtils.getContent("/html/online_library.html"));
        Set<Author> authors = underTest.parseAuthors(content);
        assertEquals(6, authors.size());
    }

    @Test
    void test_body_author_title() throws IOException {
        Document content = Jsoup.parse(HtmlUtils.getContent("/html/digital_library.html"));
        Set<Author> authors = underTest.parseAuthors(content);
        assertEquals(5, authors.size());
    }

    @Test
    void test_meta_author_dc() throws IOException {
        Document content = Jsoup.parse(HtmlUtils.getContent("/html/nature.html"));
        Set<Author> authors = underTest.parseAuthors(content);
        assertEquals(4, authors.size());
    }
}