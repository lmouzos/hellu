
package com.lambdami.hellu.externalsites;


import com.lambdami.hellu.connection.HttpConnection;
import com.lambdami.hellu.model.Author;
import com.lambdami.hellu.searchsata.googleresults.Extract;
import org.jsoup.nodes.Document;

import java.util.List;
import java.util.Set;

/**
 * @author Loukas Mouzos
 * contact : loukas.mouzos@gmail.com
 */

public class IoPressHTMLParser implements HTMLParser {

    private static String PAGE = "IO Press";

    @Deprecated
    public static List<Author> getAuthors(String URL) {

        String[] authors = null;

        HttpConnection http = new HttpConnection(URL, PAGE);
        http.connect();
        http.getContent();
//        HttpExceptionHandler handler = new HttpExceptionHandler(http);
//        http = handler.checkStatement();
        String Page = http.getContent();

        //do stuff and find authors!!!!

        http.closeConnection();

        return Extract.extractAuthors(authors);

    }

    @Override
    public Set<Author> parseAuthors(Document content) {
        throw new RuntimeException("Not implemented yet");
    }
}
