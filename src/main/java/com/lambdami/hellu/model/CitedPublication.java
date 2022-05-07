package com.lambdami.hellu.model;

import com.lambdami.hellu.searchsata.googleresults.PublicationType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Loukas Mouzos
 * contact: loukas.mouzos@gmail.com
 * site: https://github.com/lmouzos
 * <p>
 * Data model to hold information for CitedPublication
 */

@Getter
@AllArgsConstructor
public class CitedPublication extends Publication {

    private String hrefCitations;
    private List<Publication> selfCitations;
    private List<Publication> heteroCitations;

    public CitedPublication(String title, Set<Author> authors, String hrefPage, PublicationType type, String hrefCitations, int year) {
        super(title, authors, hrefPage, type, year);
        this.hrefCitations = hrefCitations;
        this.selfCitations = new ArrayList<>();
        this.heteroCitations = new ArrayList<>();
    }

    public void addSelfCitation(Publication citation) {
        this.selfCitations.add(citation);
    }

    public void addSelfCitations(Set<Publication> citations) {
        this.selfCitations.addAll(citations);
    }

    public void addHeteroCitation(Publication citation) {
        this.heteroCitations.add(citation);
    }

    public void addHeteroCitations(Set<Publication> citations) {
        this.heteroCitations.addAll(citations);
    }

    public int getCitations() {
        return this.selfCitations.size() + this.heteroCitations.size();
    }
}
