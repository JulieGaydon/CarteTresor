package org.projet;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TresorTest {

    Tresor tresor = new Tresor(1, 1, 1);

    /**
     * Verifie qu'on arrive a enlever un tresor jusqu'a qu'il n'y en ai plus
     */
    @Test
    void removeOneTresorTest() {
        assertEquals(1, tresor.getNbTresor());
        // on enleve un tresor
        tresor.removeOneTresor();
        assertEquals(0, tresor.getNbTresor());
        // si on enleve encore un tresor alors qu'il ny en a plus
        tresor.removeOneTresor();
        assertEquals(0, tresor.getNbTresor());
    }

    /**
     * Vérifie s'il reste des tresors
     */
    @Test
    void isStillTest() {
        // on a un tresor initialement
        assertEquals(1, tresor.getNbTresor());
        assertTrue(tresor.isStill());
        // on enleve un tresor
        tresor.removeOneTresor();
        assertFalse(tresor.isStill());
    }

    /**
     * Verifie l'affichage d'un tresor lorsque l'on affiche la carte
     */
    @Test
    void toStringTest() {
        assertEquals("T("+tresor.getNbTresor()+")", tresor.toString());
    }

    /**
     * Verifie l'affichage lorsque l'on ecrit un tresor dans le fichier final
     */
    @Test
    void toWriteInFileTest() {
        assertEquals("T - " + tresor.getColumn() + " - " + tresor.getRow() + " - " + tresor.getNbTresor(), tresor.toWriteInFile());
    }
}