package com.lambdami.hellu.processdata.compare;

import com.lambdami.hellu.model.Author;
import com.lambdami.hellu.model.CitedPublication;
import com.lambdami.hellu.model.Publication;
import com.lambdami.hellu.util.Collections;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Loukas Mouzos
 * contact : loukas.mouzos@gmail.com
 */

public class CitationType {

    private CitedPublication publication;
    private List<Publication> citations;

//    Comparator<String>

    public CitationType(CitedPublication publication, List<Publication> citations) {
        this.publication = publication;
        this.citations = citations;
    }

    // TODO: Continue from here: comparison of two string whether they are same or not
    public void setCitationType() {
        Set<Author> publicationAuthors = this.publication.getAuthors();
        Set<Publication> selfCitations = this.citations.stream()
                .filter(citation -> Collections.any(citation.getAuthors(), publicationAuthors))
                .collect(Collectors.toSet());

        Set<Publication> heteroCitations = this.citations.stream()
                .filter(citation -> Collections.none(citation.getAuthors(), publicationAuthors))
                .collect(Collectors.toSet());

        this.publication.addSelfCitations(selfCitations);
        this.publication.addHeteroCitations(heteroCitations);
        if (publicationAuthors == null) {
            return;
        }
        for (int i = 0; i < citations.size(); i++) {
            Set<Author> citationSAuthors = citations.get(i).getAuthors();
            boolean isSelf = false;
            if (citationSAuthors != null) {
                for (int mainPubAuthor = 0; mainPubAuthor < publicationAuthors.size(); mainPubAuthor++) {
                    for (int citationAuthor = 0; citationAuthor < citationSAuthors.size(); citationAuthor++) {
                        Comparison c = new Comparison(publicationAuthors.get(mainPubAuthor), citationSAuthors.get(citationAuthor));
                        if (c.isTheSameAuthor()) {
                            isSelf = true;
                        }
                    }
                }
                if (isSelf) {
                    this.publication.addSelfCitation(citations.get(i));
                } else {
                    this.publication.addHeteroCitation(citations.get(i));
                }
            }
        }
    }

    public CitedPublication getCitedPublication() {
        return this.publication;
    }
}
