package org.projet;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdventurerTest {
    Adventurer adventurer = new Adventurer(0, 0, "Test", "A", "O");

    /**
     * Vérifie que quand l'aventurier se tourne, il arrive dans la bonne direction
     */
    @Test
    void turnTest() {
        // l'aventurier est orienté vers l'Ouest
        assertEquals(Adventurer.ORIENTATION.WEST, adventurer.getOrientation());
        // il se troune a droite
        adventurer.turn('D');
        // il a donc une orientation egale à Nord
        assertEquals(Adventurer.ORIENTATION.NORTH, adventurer.getOrientation());
        // il tourne à gauche deux fois
        adventurer.turn('D');
        adventurer.turn('D');
        // sa nouvelle orientation est le Sud
        assertEquals(Adventurer.ORIENTATION.SOUTH, adventurer.getOrientation());
    }

    /**
     * Vérifie que l'aventurier s'est bien deplacé
     */
    @Test
    void moveTest() {
        int initRow = adventurer.getRow();
        int initCol = adventurer.getColumn();
        adventurer.move(1,1);
        // n'est plus au même coordonnées
        assertNotEquals(initRow, adventurer.getRow());
        assertNotEquals(initCol, adventurer.getColumn());
        // en possede de nouveaux
        assertEquals(1, adventurer.getRow());
        assertEquals(1, adventurer.getColumn());
    }

    /**
     * Vérifie que l'ajout d'un tresor fonctionne
     */
    @Test
    void addTresorTest() {
        // initialement, il n'a pas de tresor
        assertEquals(0, adventurer.getNbTresor());
        adventurer.addTresor();
        // il en recupere un
        assertEquals(1, adventurer.getNbTresor());
    }

    /**
     * Vérifie le bon affichage lorsque l'on affiche la carte
     */
    @Test
    void toStringTest() {
        assertEquals("A("+ adventurer.name +")", adventurer.toString());
    }

    /**
     * Verifie que l'affichage est correcte quand on ecrit dans le fichier un aventurier
     */
    @Test
    void toWriteInFileTest() {
        assertEquals( "A - " + adventurer.name + " - " + adventurer.getColumn() + " - " + adventurer.getRow() +
                        " - " + adventurer.getOrientation().toString().charAt(0) + " - " + adventurer.getNbTresor(),
                adventurer.toWriteInFile());
    }
}