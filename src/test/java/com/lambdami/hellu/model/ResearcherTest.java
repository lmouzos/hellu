package com.lambdami.hellu.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResearcherTest {

    @Test
    void from_SimpleName() {
        String name = "Loukas Mouzos";
        Researcher researcher = Researcher.from(name);
        assertEquals("Loukas", researcher.getFirstName());
        assertEquals("Mouzos", researcher.getLastName());
        assertNull(researcher.getMiddleName());
    }

    @Test
    void from_SimpleNameWithComma() {
        String name = "Mouzos, Loukas";
        Researcher researcher = Researcher.from(name);
        assertEquals("Loukas", researcher.getFirstName());
        assertEquals("Mouzos", researcher.getLastName());
        assertNull(researcher.getMiddleName());
    }

    @Test
    void from_NameWithMiddleName() {
        String name = "Loukas D. Mouzos";
        Researcher researcher = Researcher.from(name);
        assertEquals("Loukas", researcher.getFirstName());
        assertEquals("D.", researcher.getMiddleName());
        assertEquals("Mouzos", researcher.getLastName());
    }

    @Test
    void from_NameWithMiddleNameAndComma() {
        String name = "Mouzos, Loukas D.";
        Researcher researcher = Researcher.from(name);
        assertEquals("Loukas", researcher.getFirstName());
        assertEquals("D.", researcher.getMiddleName());
        assertEquals("Mouzos", researcher.getLastName());
    }

    @Test
    void from_NameWithAnd() {
        String name = "Loukas Mouzos and";
        Researcher researcher = Researcher.from(name);
        assertEquals("Loukas", researcher.getFirstName());
        assertEquals("Mouzos", researcher.getLastName());
        assertNull(researcher.getMiddleName());
    }

    @Test
    void from_NameWithTrailingComma() {
        String name = "Loukas Mouzos,";
        Researcher researcher = Researcher.from(name);
        assertEquals("Loukas", researcher.getFirstName());
        assertEquals("Mouzos", researcher.getLastName());
        assertNull(researcher.getMiddleName());
    }

    @Test
    void from_NameWithCommaAndTrailingComma() {
        String name = "Mouzos, Loukas,";
        Researcher researcher = Researcher.from(name);
        assertEquals("Loukas", researcher.getFirstName());
        assertEquals("Mouzos", researcher.getLastName());
        assertNull(researcher.getMiddleName());
    }
}