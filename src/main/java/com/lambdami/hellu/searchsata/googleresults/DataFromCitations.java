
package com.lambdami.hellu.searchsata.googleresults;

import com.lambdami.hellu.model.Author;
import com.lambdami.hellu.model.Publication;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Loukas Mouzos
 * contact: loukas.mouzos@gmail.com
 * site: https://github.com/lmouzos
 * <p>
 * Singleton class to parse data from Google Scholar Citations
 */

public class DataFromCitations extends ParseGoogleData {

    public DataFromCitations(List<String> data) {
        super(data);
    }

    @Override
    public List<Publication> parseData() {

        List<Publication> citations = new ArrayList<Publication>();
        Publication citation;
        String each;

        for (int i = 0; i < this.data.size(); i++) {

            String contentJavascript = null;

            each = this.data.get(i);
            String title = getTitle(each);
            String href = getHrefPage(each);
            PublicationType type = getType(each);

            String CODE = getPublicationCode(each);
            int year = getYear(each);
            Set<Author> authors = getAuthorsFromGoogle(each);

            if (authors == null) {
                if (href.equals("DOES_NOT_EXIST") || href.contains("google") ||
                        (authors = getAuthorsFromExternalSite(href, type, title)) == null) {
                    GoogleRequestJS js = new GoogleRequestJS(CODE);
                    contentJavascript = js.getContent();
                    authors = getAuthorsFromCITE(contentJavascript);
                    if (authors == null) continue;
                }
            }

            if (title.contains("\u2026")) {
                if (contentJavascript == null) {
                    GoogleRequestJS js = new GoogleRequestJS(CODE);
                    contentJavascript = js.getContent();
                }
                title = getTitleFromCite(contentJavascript);
            }

            citation = new Publication(title, authors, href, type, year);
            citations.add(citation);
        }
        return citations;
    }
}
