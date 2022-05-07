package com.lambdami.hellu.externalsites;

import com.lambdami.hellu.connection.HttpConnection;
import com.lambdami.hellu.model.Author;
import com.lambdami.hellu.searchsata.googleresults.Extract;
import org.jsoup.nodes.Document;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Loukas Mouzos
 * contact : loukas.mouzos@gmail.com
 */

public class PDFHTMLParser implements HTMLParser {

    private static String PAGE = "Academic Microsoft";

    /**
     * @deprecated Microsoft Academic has been retired as of 31st Dec 2021 <br>
     * Hence this method is deprecated
     */
    @Deprecated
    public static List<Author> getAuthors(String title) {

        String[] authors = requestToMicrosoft(title);
        if (authors == null) return null;
        return Extract.extractAuthors(authors);
    }

    private static boolean titleExists(String content, String title) {
        content = content.toLowerCase();
        title = title.toLowerCase();

        title = title.replaceAll("[^\\p{L}\\p{N}]", "-");

        String[] splittingContent = content.split("<h3>");
        String[] splittingContent2 = splittingContent[1].split("</h3>");
        String last = splittingContent2[0];


        if (title.contains("--")) {
            title = title.replaceAll("--", "-");
        }

        return last.contains(title);
    }

    private static String[] requestToMicrosoft(String title) {

        String title2 = exchangeTitle(title);

        String[] authors = null;
        String page = null;
        String url = "http://academic.research.microsoft.com/Search?query=" + title2;

        HttpConnection http = new HttpConnection(url, PAGE);
        http.connect();
        http.getContent();
        page = http.getContent();

        if (page.equals("")) return null;

        try {

            //page = clearFormatting.cleanPage(page);
            //spliting page
            if (page.contains("<li class=\"paper-item\">")) {
                String[] academicPublishes = page.split("<li class=\"paper-item\">");

                String annoying = academicPublishes[1];

                //if the begining title is not included in the results
                //we can't find the authors - returning null...
                if (!titleExists(annoying, title)) return null;

                String[] auth = annoying.split("<div class=\"content\">");
                String[] autho = auth[1].split("</div>");

                String[] author = autho[0].split("<a class=\"");//ekei briskontai oloi oi authors

                authors = extractAuthors(author);

                http.closeConnection();

                return authors;
            }
        } catch (Exception ex) {
            return null;
        }
        return null;

    }


    private static String[] extractAuthors(String[] authors) {
        String[] justNames = new String[authors.length - 1];

        for (int i = 1; i < authors.length; i++) {

            String[] A = authors[i].split("</a>");

            String[] B = A[0].split("\">");

            justNames[i - 1] = B[1];
        }

        return justNames;
    }

    private static String exchangeTitle(String title) {
        try {
            //we need to see and change more for this situation
            return URLEncoder.encode(title, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(PDFHTMLParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Set<Author> parseAuthors(Document content) {
        throw new RuntimeException("Not implemented yet");
    }
}
