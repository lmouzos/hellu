package com.lambdami.hellu.externalsites;

import com.lambdami.hellu.model.Author;
import org.jsoup.nodes.Document;

import java.util.Set;

public interface HTMLParser {

    Set<Author> parseAuthors(Document content);
}
