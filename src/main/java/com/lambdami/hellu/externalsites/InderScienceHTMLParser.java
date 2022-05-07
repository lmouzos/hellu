
package com.lambdami.hellu.externalsites;

import com.lambdami.hellu.model.Author;
import com.lambdami.hellu.searchsata.googleresults.Extract;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author Loukas Mouzos
 * contact : loukas.mouzos@gmail.com
 */

public class InderScienceHTMLParser implements HTMLParser {

    private final String page = "Inder Science";

    @Override
    public Set<Author> parseAuthors(Document content) {
        Set<String> authors = parseAuthorsAsStrings(content);
        return Extract.getAuthors(authors);
    }

    private Set<String> parseAuthorsAsStrings(Document document) {
        return document.select("ul[title=\"list of authors\"] > li")
                .stream()
                .map(Element::text)
                .collect(Collectors.toSet());
    }
}
