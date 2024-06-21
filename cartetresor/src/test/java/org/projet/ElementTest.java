package org.projet;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ElementTest {
    Element montagne = new Element(1, 1);

    /**
     * Vérifie qu'on a le bon affichage quand on affiche la carte
     */
    @Test
    void toStringTest() {
        assertEquals("M", montagne.toString());
    }

    /**
     * Verifie qu'on affiche correctement lorsque l'on va ecrire dans le fichier
     */
    @Test
    void toWriteInFileTest() {
        assertEquals("M - " + montagne.column + " - " + montagne.row, montagne.toWriteInFile());
    }
}