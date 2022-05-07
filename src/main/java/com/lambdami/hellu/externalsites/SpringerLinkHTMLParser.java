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

public class SpringerLinkHTMLParser implements HTMLParser {

    private static String PAGE = "Springer Link";

    @Deprecated
    static public List<Author> getAuthors(String URL) {

        String[] tableAuthors = null;
        String Page = "";


        HttpConnection http = new HttpConnection(URL, PAGE);
        http.connect();
        http.getContent();
//        HttpExceptionHandler handler = new HttpExceptionHandler(http);
//        http = handler.checkStatement();
        Page = http.getContent();

        if (Page.equals("")) return null;

        String[] A = Page.split("</h1><p class=\"authors\">");

        String[] B = A[1].split("/p>");

        String[] C = B[0].split("</a>");

        tableAuthors = extractAuthors(C);

        http.closeConnection();

        return Extract.extractAuthors(tableAuthors);

    }

    static private String[] extractAuthors(String[] authors) {

        String finalAuthors[] = new String[authors.length - 1];

        for (int i = 0; i < authors.length - 1; i++) {

            String Dfinal[] = authors[i].split("\">");

            finalAuthors[i] = Dfinal[1];
        }
        return finalAuthors;

    }

    @Override
    public Set<Author> parseAuthors(Document content) {
        Set<String> authors = parseAuthorsAsStrings(content);
        return Extract.getAuthors(authors);
    }

    private Set<String> parseAuthorsAsStrings(Document document) {
        return document.head()
                .select("meta[name=\"citation_author\"]").stream()
                .map(element -> element.attr("content"))
                .collect(Collectors.toSet());
    }
}
