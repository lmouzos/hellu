package com.lambdami.hellu.externalsites;

import com.lambdami.hellu.model.Author;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SpringerLinkHTMLParserTest {

    HTMLParser underTest = new SpringerLinkHTMLParser();

    @Test
    void parseAuthors() throws IOException {
        Document content = Jsoup.parse(HtmlUtils.getContent("/html/springerlink.html"));
        Set<Author> result = underTest.parseAuthors(content);
        assertEquals(2, result.size());
    }
}