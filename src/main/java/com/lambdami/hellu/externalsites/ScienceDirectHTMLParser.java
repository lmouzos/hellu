package com.lambdami.hellu.externalsites;


import com.lambdami.hellu.connection.HttpConnection;
import com.lambdami.hellu.model.Author;
import com.lambdami.hellu.searchsata.googleresults.Extract;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Loukas Mouzos
 * contact : loukas.mouzos@gmail.com
 */

public class ScienceDirectHTMLParser implements HTMLParser {

    private static String PAGE = "Science Direct";

    @Deprecated
    static public List<Author> getAuthors(String URL) {

        String[] authors = null;
        String Page = null;

        HttpConnection http = new HttpConnection(URL, PAGE);
        http.connect();
        http.getContent();
//        HttpExceptionHandler handler = new HttpExceptionHandler(http);
//        http = handler.checkStatement();
        Page = http.getContent();

        if (Page.equals("")) return null;
        try {
            String A[] = Page.split("<ul class=\"authorGr");

            String B[] = A[1].split("<li>");

            authors = extractAuthors(B);
        } catch (Exception ex) {
            return null;
        }
        http.closeConnection();

        if (authors == null) return null;

        return Extract.extractAuthors(authors);
    }

    static private String[] extractAuthors(String[] authors) {

        int length = 0;
        for (int i = 1; i < authors.length; i++) {
            if (authors[i].contains("class=\"authorName\"")) {
                length++;
            }
        }

        String[] finalAuthors = new String[length];

        for (int i = 1; i < authors.length; i++) {
            if (authors[i].contains("class=\"authorName\"")) {
                String A[] = null;
                if (authors[i].contains("</a><a"))
                    A = authors[i].split("</a><a");
                else if (authors[i].contains("</a> <a"))
                    A = authors[i].split("</a> <a");
                else
                    A = authors[i].split("</a>");
                String B[] = A[0].split(">");
                //
                finalAuthors[i - 1] = B[1];
            }
        }

        return finalAuthors;

    }

    @Override
    public Set<Author> parseAuthors(Document content) {
        Set<String> authString = parseAuthorsAsStrings(content);
        return Extract.getAuthors(authString);
    }

    private Set<String> parseAuthorsAsStrings(Document document) {
        return document.body()
                .select("div.author-group a.author").stream()
                .map(element ->
                        element.select("span.given-name").text() + " " + element.select("span.surname").text())
                .collect(Collectors.toSet());
    }
}