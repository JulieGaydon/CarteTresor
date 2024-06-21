package org.projet;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CaseTest {
    Case my_case = new Case();
    Element montagne = new Element(1, 1);
    Tresor tresor = new Tresor(0, 1, 2);
    Adventurer adventurer = new Adventurer(0, 0, "Test", "A", "E");
    Adventurer adventurer2 = new Adventurer(1, 2, "Test2", "AD", "O");

    /**
     * Verifie que quand on ajoute un element, il existe bien sur la carte
     */
    @Test
    void addEltOKTest() {
        boolean isEltAdd;
        assertTrue(my_case.getElements().isEmpty());
        isEltAdd = my_case.addElt(adventurer, true);
        // on a bien ajoute l'aventurier a la case
        assertFalse(my_case.getElements().isEmpty());
        assertTrue(isEltAdd);
        // on ajoute un autre tresor
        isEltAdd = my_case.addElt(tresor, false);
        // on a bien ajoute le tresor en plus de l'aventurier a la case
        assertEquals(2, my_case.getElements().size());
        assertTrue(isEltAdd);
    }

    /**
     * Verifie qu'on ne puisse bien pas ajouter un element sur la carte
     */
    @Test
    void addEltKOTest() {
        boolean isEltAdd;
        assertTrue(my_case.getElements().isEmpty());
        isEltAdd = my_case.addElt(adventurer, true);
        // on a bien ajoute l'aventurier a la case
        assertFalse(my_case.getElements().isEmpty());
        assertTrue(isEltAdd);
        // on ajoute un autre aventurier
        isEltAdd = my_case.addElt(adventurer2, true);
        // pas d'ajout en plus de l'aventurier a la case
        assertEquals(1, my_case.getElements().size());
        assertFalse(isEltAdd);
        // on ajoute une montagne
        isEltAdd = my_case.addElt(montagne, true);
        // pas d'ajout en plus de l'aventurier a la case
        assertEquals(1, my_case.getElements().size());
        assertFalse(isEltAdd);
    }

    /**
     * Retire correctement l'aventurier s'il existe d'une case
     */
    @Test
    void removeAdventurerTest() {
        // pas d'elt, donc rien a retirer
        assertFalse(my_case.removeAdventurer());
        // il n'y a pas d'aventurier, on ne peut donc pas le retirer
        my_case.addElt(tresor, false);
        assertFalse(my_case.removeAdventurer());
        // on ajoute un aventurier, on va donc pouvoir le retirer
        my_case.addElt(adventurer, true);
        assertTrue(my_case.removeAdventurer());
    }

    /**
     * Retire correctement le tresor s'il existe d'une case
     */
    @Test
    void removeTresorTest() {
        // pas d'elt, donc rien a retirer
        assertFalse(my_case.removeTresor());
        // il n'y a pas de tresor, on ne peut donc pas le retirer
        my_case.addElt(adventurer, false);
        assertFalse(my_case.removeTresor());
        // on ajoute un tresor, on va donc pouvoir le retirer
        my_case.addElt(tresor, true);
        assertTrue(my_case.removeTresor());

        //on a deja enlever le tresor, rien a retirer
        assertFalse(my_case.removeTresor());
    }
}