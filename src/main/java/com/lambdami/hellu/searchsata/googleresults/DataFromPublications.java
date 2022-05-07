
package com.lambdami.hellu.searchsata.googleresults;

import com.lambdami.hellu.model.Author;
import com.lambdami.hellu.model.CitedPublication;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Loukas Mouzos
 * contact : loukas.mouzos@gmail.com
 * site: https://github.com/lmouzos
 */

public class DataFromPublications extends ParseGoogleData {

    public DataFromPublications(List<String> data) {
        super(data);
    }

    @Override
    public List<CitedPublication> parseData() {

        List<CitedPublication> publications = new ArrayList<>();
        String each;
        CitedPublication publication;

        for (int i = 0; i < this.data.size(); i++) {
            each = this.data.get(i);

            String contentJavascript = null;
            String title = getTitle(each);
            PublicationType type = getType(each);
            //if it is there...! I have to do an examination on it.
            String hrefPage = getHrefPage(each);
            String citationsPage = getCitationsPage(each);

            int year = getYear(each);
            Set<Author> authors = getAuthorsFromGoogle(each);

            if (authors == null) {
                if (hrefPage.equals("DOES_NOT_EXIST") ||
                        (authors = getAuthorsFromExternalSite(hrefPage, type, title)) == null) {

                    String code = getPublicationCode(each);
                    GoogleRequestJS js = new GoogleRequestJS(code);
                    contentJavascript = js.getContent();
                    authors = getAuthorsFromCITE(contentJavascript);
                    if (authors == null) continue;
                }
            }

            if (title.contains("\u2026")) {
                if (contentJavascript == null) {
                    String code = getPublicationCode(each);
                    GoogleRequestJS js = new GoogleRequestJS(code);
                    contentJavascript = js.getContent();
                }
                title = getTitleFromCite(contentJavascript);
            }


            publication = new CitedPublication(title, authors, hrefPage, type, citationsPage, year);
            publications.add(publication);
        }
        return publications;
    }

}
