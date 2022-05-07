
package com.lambdami.hellu.searchsata.googleresults;

import com.lambdami.hellu.connection.HttpConnection;
import com.lambdami.hellu.connection.HttpExceptionHandler;
import org.apache.http.client.utils.URIBuilder;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Loukas Mouzos
 * contact : loukas.mouzos@gmail.com
 */

public class GoogleRequest {

    private static final String PAGE = "Google Scholar";
    public static final int AUTHOR = 100;
    public static final int CITATION = 200;

    private static final String GOOGLE_SCHOLAR = "scholar.google.com/scholar";

    private final List<String> listOfElements = new ArrayList<>();
    private final String query;

    public GoogleRequest(String mainQuery) {
        this.query = mainQuery;
    }

    //set query for Google 
    private String convertSimpleQueryToGSQuery(String mainQuery) {
        StringBuilder queryURLBuilder = new StringBuilder();
        queryURLBuilder
                .append("author:")
                .append(mainQuery.replaceAll("\\s+", "+"));
        return queryURLBuilder.toString();
    }

    public List<String> getPublications(boolean mainSearch) {
        int pages = 0;
        //In case there isn't citation page...
        if (this.query.equals("DOES_NOT_EXIST")) {
            return Collections.emptyList();
        }

        while (true) {

            URIBuilder uriBuilder = new URIBuilder();
            String encoded = "";
            try {
                encoded = URLEncoder.encode(convertSimpleQueryToGSQuery(this.query), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String url = "";
            if (mainSearch) {//it is the case of main publications
                url = "https://" + GOOGLE_SCHOLAR + "?start=" + pages + "&hl=en" + "&q=" + encoded + "&as_sdt=0.5";
                uriBuilder.setScheme("https")
                        .setHost(GOOGLE_SCHOLAR)
                        .addParameter("start", pages + "")
                        .addParameter("hl", "en")
                        .addParameter("q", convertSimpleQueryToGSQuery(this.query))
                        .addParameter("as_sdt", "0.5");
            } else {//citations for a publication
                uriBuilder.setScheme("https")
                        .setHost(GOOGLE_SCHOLAR)
                        .addParameter("start", pages + "")
                        .addParameter("hl", "en")
                        .addParameter("num", "20")
                        .addParameter("q", this.query)
                        .addParameter("as_sdt", "0.5");
            }
            try {
                Connection connection = Jsoup.connect(uriBuilder.build().toString());
                Document doc = connection.get();
                // parse HTML content

                Set<String> publications = doc.select(".gs_r.gs_or.gs_scl")
                        .stream().map(Node::toString).collect(Collectors.toSet());

                this.listOfElements.addAll(publications);
                //delay after every site
                delay();

                if (publications.isEmpty()) break;

                Elements navigation = doc.body().select("div#gs_nm button[aria-label=\"Next\"]");
                if (navigation.isEmpty()) break;
                if (navigation.get(0).is(":disabled")) break;

            } catch (IOException e) {
                e.printStackTrace();
                break;
            } catch (URISyntaxException e) {
                e.printStackTrace();
                break;
            }

            pages += 20;
        }

        return this.listOfElements;
    }

    //fill in the ArrayList with publications
    private static ArrayList TransportPublicationsToAList(String[] array, int start) {
        ArrayList<String> List = new ArrayList<String>();
        for (int i = start; i < array.length; i++) {
            List.add(array[i]);
        }
        return List;
    }


    //delay for each google request
    private void delay() {
        double count = 0.0;
        //-0.50
        while (count < 5000000000.0) {
            count++;
        }
    }


}
