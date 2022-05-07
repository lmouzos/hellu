
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
 * @deprecated The Project has been terminated, hence no support needed.
 */
@Deprecated
public class ScientificCommonsHTMLParser implements HTMLParser {

    private static String PAGE = "Scientific Commons";

    @Deprecated
    public static List<Author> getAuthors(String URL) {

        String[] authors = null;

        HttpConnection http = new HttpConnection(URL, PAGE);
        http.connect();
        http.getContent();
//        HttpExceptionHandler handler = new HttpExceptionHandler(http);
//        http = handler.checkStatement();
        String Page = http.getContent();

        try {
            String[] first = Page.split("class=\"dc_creator\">");

            String[] second = first[1].split("</ul>");


            String[] subListAuthor = second[0].split("/li>");


            authors = exportAuthors(subListAuthor);
        } catch (Exception ex) {
            return null;
        }

        http.closeConnection();

        return Extract.extractAuthors(authors);

    }

    private static String[] exportAuthors(String[] subListAuthor) {

        String[] authors = null;

        for (int i = 0; i < subListAuthor.length; i++) {

            String[] A = subListAuthor[i].split("</a>");
            String[] B = A[0].split("\"<");

            authors[i] = B[1];

        }
        return authors;

    }

    @Override
    public Set<Author> parseAuthors(Document content) {
        return null;
    }
}
