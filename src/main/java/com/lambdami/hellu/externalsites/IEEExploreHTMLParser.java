
package com.lambdami.hellu.externalsites;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdami.hellu.connection.HttpConnection;
import com.lambdami.hellu.model.Author;
import com.lambdami.hellu.searchsata.googleresults.Extract;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Loukas Mouzos
 * contact: loukas.mouzos@gmail.com
 * site: https://github.com/lmouzos
 * <p>
 * Singleton class to parse {@link Author} from IEEE explore specific HTML content using JSoup
 */
public class IEEExploreHTMLParser implements HTMLParser {

    private final ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Deprecated
    private static String PAGE = "IEEExplore";

    @Deprecated
    static public List<Author> getAuthors(String URL) {

        String authors[] = null;
        String Page = "";

        HttpConnection http = new HttpConnection(URL, PAGE);
        http.connect();
        http.getContent();
//        HttpExceptionHandler handler = new HttpExceptionHandler(http);
//        http = handler.checkStatement();
        Page = http.getContent();

        if (Page.equals("")) return null;

        try {
            String A[] = Page.split("r\\(s\\):");

            String B[] = A[1].split("<strong>");

            String C[] = B[0].split("</script>");

            authors = extractAuthors(C);
        } catch (Exception ex) {
            return null;
        }

        http.closeConnection();

        return Extract.extractAuthors(authors);
    }

    static private String[] extractAuthors(String[] authors) {

        String finalAuthors[] = new String[authors.length - 1];

        for (int i = 1; i < authors.length; i++) {
            String Dfinal[] = authors[i].split("</a>");

            finalAuthors[i - 1] = Dfinal[0];
        }

        for (int i = 0; i < finalAuthors.length; i++) {
            finalAuthors[i] = finalAuthors[i].replaceAll("[^\\p{L}\\p{N}\\.\\,]", "");
            finalAuthors[i] = finalAuthors[i].replaceAll(",", " ");
        }

        return finalAuthors;
    }

    private final String page = "IEEExplore";

    @Override
    public Set<Author> parseAuthors(Document content) {
        Set<String> authors = parseAuthorsAsStrings(content);
        return Extract.getAuthors(authors);
    }

    private Set<String> parseAuthorsAsStrings(Document document) {
        // Raw parsing of script data
        // xplGlobal.document.metadata
        // https://ieeexplore.ieee.org/document/7976317
        return document
                .select("script")
                .dataNodes().stream()
                .map(DataNode::getWholeData)
                .filter(nd -> nd.contains("xplGlobal.document.metadata="))
                .findAny()
                .map(this::getFromJson)
                .orElseGet(Collections::emptySet);
    }

    private Set<String> getFromJson(String jsonStr) {
        String sty = jsonStr.split("xplGlobal.document.metadata=")[1].split(";")[0];
        try {
            return mapper.readValue(sty, IEEEObject.class)
                    .getAuthors().stream()
                    .map(IEEEAuthor::getName)
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptySet();
    }

    @JsonIgnoreProperties
    @Getter
    @NoArgsConstructor
    private static class IEEEAuthor {
        private String name;
    }

    @JsonIgnoreProperties
    @Getter
    @NoArgsConstructor
    private static class IEEEObject {
        private List<IEEEAuthor> authors;
    }

}
