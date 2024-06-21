package org.projet;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MapTest {

    Map map = new Map(3,5);
    int nbTresors = 2;
    Tresor tresor = new Tresor(0, 1, nbTresors);
    Tresor erreurTresor = new Tresor(-1, 1, nbTresors);
    Element montagne = new Element(1, 1);
    Adventurer adventurer = new Adventurer(0, 0, "Test", "A", "E");
    Adventurer adventurer2 = new Adventurer(1, 2, "Test2", "AD", "O");

    /**
     * Teste l'ajout d'un element sur la carte
     */
    @Test
    void addElemOnMapTest() {
        boolean addElt;
        Case case_null = map.map[tresor.getRow()][tresor.getColumn()];
        // null car on n'a pas encore ajouté d'elt sur la carte
        assertTrue(case_null.getElements().isEmpty());
        addElt = map.addElemOnMap(tresor);
        Case case_tresor = map.map[tresor.getRow()][tresor.getColumn()];
        // on verifie qu'on a bien l'elt et que c'est le bon type
        assertFalse(case_tresor.getElements().isEmpty());
        assertEquals(Element.ELEMENTS.TRESOR, case_tresor.getElements().get(0).typeElem);
        // l'elt a ete ajoute
        assertTrue(addElt);

        // mauvais coordonnées, element n'est pas ajouté
        addElt = map.addElemOnMap(erreurTresor);
        assertFalse(addElt);
    }

    /**
     * Verifie que l'ensemble des aventuriers ont réalisé l'ensemble de leurs mouvements
     */
    @Test
    void movementsOfAllAdventurersTest() {
        // on ajoute les elts
        map.addElemOnMap(adventurer2);
        map.addElemOnMap(adventurer);
        // ils leur reste des mouvements
        assertTrue(map.differentMovements(adventurer2));
        assertTrue(map.differentMovements(adventurer));
        // on effectue les mouvements des aventuriers
        map.movementsOfAllAdventurers();
        // ils ont effectué tous leurs mouvements
        assertFalse(map.differentMovements(adventurer2));
        assertFalse(map.differentMovements(adventurer));
    }

    /**
     * Teste que les aventuriers peuvent effectuer plusieurs mouvments, avancer et tourner
     */
    @Test
    void differentMovementsTest() {
        boolean stillMovements;
        map.addElemOnMap(adventurer2);
        // au depart l'aventurier a deux mouvements
        assertEquals(2, adventurer2.getMovement().length());
        stillMovements = map.differentMovements(adventurer2);
        // apres avoir effectué un mouvement, il lui en reste un
        assertEquals(1, adventurer2.getMovement().length());
        assertTrue(stillMovements);
        // il effectue son dernier mouvement
        stillMovements = map.differentMovements(adventurer2);
        assertEquals(0, adventurer2.getMovement().length());
        assertTrue(stillMovements);
        // il n'a plus de mouvement
        stillMovements = map.differentMovements(adventurer2);
        assertEquals(0, adventurer2.getMovement().length());
        assertFalse(stillMovements);
    }

    /**
     * Verifie que lorsque l'on veut ajouter un element sur la carte, les coordonnées sont correctes
     */
    @Test
    void verifyReplacementMapTest() {
        // Hors du cadre ligne
        assertFalse(map.verifyReplacement(-1, 0, adventurer));
        assertFalse(map.verifyReplacement(map.nbRows+1, 0, adventurer));
        // Hors du cadre colonne
        assertFalse(map.verifyReplacement(0, -2, adventurer));
        assertFalse(map.verifyReplacement(0, map.nbColumns+2, adventurer));

        // sur les extremités
        assertFalse(map.verifyReplacement(map.nbRows, map.nbColumns, adventurer));

        // Dans le cadre sur une case vide
        assertTrue(map.verifyReplacement(map.nbRows-1, map.nbColumns-1, adventurer));
    }

    /**
     * Verifie que lorsque l'on veut ajouter un aventurier sur la carte,
     * et qu'un tresor occupe la case
     */
    @Test
    void verifyReplacementTresorTest() {
        map.addElemOnMap(tresor);
        // il existe un tresor
        assertEquals(Element.ELEMENTS.TRESOR, map.map[tresor.getRow()][tresor.getColumn()].getElements().get(0).typeElem);
        // On peut y aller quand meme
        assertTrue(map.verifyReplacement(tresor.getRow(), tresor.getColumn(), adventurer));
        // il recupere alors le tresor
        assertEquals(adventurer.getNbTresor(), 1);
        assertEquals(tresor.getNbTresor(), nbTresors-1);
    }

    /**
     * Verifie que lorsque l'on veut ajouter un aventurier sur la carte,
     * et qu'une montagne occupe la case
     */
    @Test
    void verifyReplacementMontagneTest() {
        map.addElemOnMap(montagne);
        // il existe une montagne
        assertEquals(Element.ELEMENTS.MOUNTAIN, map.map[montagne.getRow()][montagne.getColumn()].getElements().get(0).typeElem);
        // On ne peut pas y aller
        assertFalse(map.verifyReplacement(montagne.getRow(), montagne.getColumn(), adventurer));
    }

    /**
     * Verifie que lorsque l'on veut ajouter un aventurier sur la carte,
     * et qu'un autre aventurier occupe la case
     */
    @Test
    void verifyReplacementAdevnturerTest() {
        map.addElemOnMap(adventurer2);
        // il existe un aventurier
        assertEquals(Element.ELEMENTS.ADVENTURER, map.map[adventurer2.getRow()][adventurer2.getColumn()].getElements().get(0).typeElem);
        // On ne peut pas y aller
        assertFalse(map.verifyReplacement(adventurer2.getRow(), adventurer2.getColumn(), adventurer));
    }

    /**
     * Verifie que l'aventurier s'est bien deplacé
     */
    @Test
    void moveAdventurerOKTest() {
        // on ajoute aventurier et tresor
        map.addElemOnMap(adventurer);
        map.addElemOnMap(tresor);
        // l'aventurier est tourné vers l'est
        assertEquals(adventurer.getOrientation(), Adventurer.ORIENTATION.EAST);
        // son mouvement est d'avancer
        assertEquals(adventurer.getMovement().charAt(0), 'A');
        // on le deplace si c'est possible
        map.moveAdventurer(adventurer);
        // ici, il se deplace sur une case tresor
        assertEquals(adventurer.getRow(), tresor.getRow());
        assertEquals(adventurer.getColumn(), tresor.getColumn());
    }

    /**
     * Verifie que l'aventurier ne peut pas se deplacer sur la case
     */
    @Test
    void moveAdventurerKOTest() {
        // on ajoute aventurier et montagne
        map.addElemOnMap(adventurer2);
        map.addElemOnMap(montagne);
        // l'aventurier est tourné vers l'est
        assertEquals(Adventurer.ORIENTATION.WEST, adventurer2.getOrientation());
        // son mouvement est d'avancer
        assertEquals(adventurer2.getMovement().charAt(0), 'A');
        int initRow = adventurer2.getRow();
        int initCol = adventurer2.getColumn();
        // on le deplace si c'est possible
        map.moveAdventurer(adventurer2);
        // ici, il ne se deplace pas
        assertEquals(initRow, adventurer2.getRow());
        assertEquals(initCol, adventurer2.getColumn());
    }

    /**
     * Verifie qu'on recupere bien tous les elements contenu sur la carte
     */
    @Test
    void getElemToWriteTest() {
        ArrayList<String> elements = new ArrayList<>();
        elements = map.getElemToWrite();
        // la carte
        assertEquals(1, elements.size());
        // on ajoute des elts
        map.addElemOnMap(tresor);
        map.addElemOnMap(montagne);
        elements = map.getElemToWrite();
        assertEquals(3, elements.size());
    }

    /**
     * Vérifie que ce qu'on ecrit dans le fichier est correct
     */
    @Test
    void toWriteInFileTest() {
        assertEquals( "C - " + map.nbColumns + " - " + map.nbRows, map.toWriteInFile());
    }
}