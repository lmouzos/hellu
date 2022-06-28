package com.lambdami.hellu.processdata.compare;

import com.lambdami.hellu.model.Author;
import com.lambdami.hellu.model.CitedPublication;
import com.lambdami.hellu.model.Publication;
import com.lambdami.hellu.util.Collections;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Loukas Mouzos
 * contact : loukas.mouzos@gmail.com
 */

public class CitationType {

    private final CitedPublication publication;
    private final List<Publication> citations;

    public CitationType(CitedPublication publication, List<Publication> citations) {
        this.publication = publication;
        this.citations = citations;
    }

    public void setCitationType() {
        Set<Author> publicationAuthors = this.publication.getAuthors();
        if (publicationAuthors == null) {
            return;
        }
        for (Publication citation : citations) {
            Set<Author> citationAuthors = citation.getAuthors();
            if (citationAuthors == null) {
                continue;
            }
            if (compare(publicationAuthors, citationAuthors)) {
                this.publication.addSelfCitation(citation);
            } else {
                this.publication.addHeteroCitation(citation);
            }

        }
    }

    private boolean compare(Set<Author> publicationAuthors, Set<Author> citationSAuthors) {
        Iterator<Author> iteratorMainPub = publicationAuthors.iterator();
        Iterator<Author> iteratorCitation = citationSAuthors.iterator();
        while (iteratorMainPub.hasNext()) {
            Author authorMain = iteratorMainPub.next();
            while (iteratorCitation.hasNext()) {
                Author authorCite = iteratorCitation.next();
                if (authorMain.isSameWith(authorCite)) {
                    return true;
                }
            }
        }
        return false;
    }

    public CitedPublication getCitedPublication() {
        return this.publication;
    }
}
