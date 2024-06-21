package org.projet;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimulationTest {

    /**
     * Teste la lecture d'un fichier
     */
    @org.junit.jupiter.api.Test
    void readFileTest(){
        // lire un fichier qui n'existe pas
        assertFalse(Simulation.readFile("pas/un/chemin"));
        // lire un fichier qui existe
        assertTrue(Simulation.readFile("src/main/resources/carte"));
    }

    /**
     * Teste l'ecriture dans un fichier
     */
    @org.junit.jupiter.api.Test
    void writeFiletest() {
        // creation d'elts
        String line = "C - 3 - 4";
        Simulation.createElementsLine(line);
        // on peut alors créer l'element
        line = "T - 1 - 3 - 3";
        Simulation.createElementsLine(line);
        assertTrue(Simulation.writeFile("src/main/resources/resTest.txt"));
    }

    /**
     * Verifie qu'on recupere bien les elements du fichier correspondants a une montagne
     */
    @org.junit.jupiter.api.Test
    void getLineValuesMountainTest() {
        String line = "M - 1 - 0";
        List<String> val = Simulation.getLineValues(line);
        assertEquals(3, val.size());
        assertEquals("M", val.get(0));
        assertEquals("1", val.get(1));
        assertEquals("0", val.get(2));
    }

    /**
     * Verifie les elements avec des valeurs negatives dans la ligne
     */
    @org.junit.jupiter.api.Test
    void getLineValuesNegativeTest() {
        String line = "T - -1 - 0 - 2";
        List<String> val = Simulation.getLineValues(line);
        assertEquals(4, val.size());
        assertEquals("T", val.get(0));
        assertEquals("1", val.get(1));
        assertEquals("0", val.get(2));
        assertEquals("2", val.get(3));
    }

    /**
     * Verifie qu'on recupere bien les elements du fichier correspondants a un aventurier
     */
    @org.junit.jupiter.api.Test
    void getLineValuesAdventurerTest() {
        String line = "A - Lara - 1 - 1 - S - AADADAGGA";
        List<String> val = Simulation.getLineValues(line);
        assertEquals(6, val.size());
        assertEquals("A", val.get(0));
        assertEquals("Lara", val.get(1));
        assertEquals("1", val.get(2));
        assertEquals("1", val.get(3));
        assertEquals("S", val.get(4));
        assertEquals("AADADAGGA", val.get(5));
    }

    /**
     * Verifie la creation des elements depuis les lignes contenues dans le fichier
     */
    @org.junit.jupiter.api.Test
    void createElementsLineTest() {
        boolean isCreated;
        String line = "T - 1 - 3 - 3";
        isCreated = Simulation.createElementsLine(line);
        // pas créer car pas de carte créée
        assertFalse(isCreated);

        // on crée alors la carte
        line = "C - 3 - 4";
        isCreated = Simulation.createElementsLine(line);
        assertTrue(isCreated);
        // on peut alors créer l'element
        line = "T - 1 - 3 - 3";
        isCreated = Simulation.createElementsLine(line);
        assertTrue(isCreated);
    }

    /**
     * Vérifie que les aventuriers ont bien réalisés leurs mouvements
     */
    @org.junit.jupiter.api.Test
    void moveAdventurers() {
        // on crée une carte
        String line = "C - 3 - 4";
        Simulation.createElementsLine(line);
        // on crée un aventurier
        line = "A - Lara - 1 - 1 - S - AADA";
        Simulation.createElementsLine(line);
        Adventurer adventurer = Simulation.map.adventurers.get(0);
        int initRow = adventurer.getRow();
        int initCol = adventurer.getColumn();
        Simulation.moveAdventurers();
        // on voit que l'aventurier a fini ses deplacements
        assertTrue(adventurer.getMovement().isEmpty());
        // qu'il s'est bien deplace
        assertNotEquals(initRow, adventurer.getRow());
        assertNotEquals(initRow, adventurer.getColumn());
        // aux nouvelles coordonnées
        assertEquals(3, adventurer.getRow());
        assertEquals(0, adventurer.getColumn());
    }

    /**
     * Verifie qu'on crée bien des elements que si la ligne respecte le bon format
     */
    @org.junit.jupiter.api.Test
    void verifyLineValuesTest() {
        // on crée un element
        boolean isValuesOk;
        String line = "M - 1 - 0";
        List<String> val = Simulation.getLineValues(line);
        isValuesOk = Simulation.verifyLineValues(val);
        assertTrue(isValuesOk);

        // on crée un element qui ne respecte pas le nombre de valeurs pour l'elt
        line = "M - 1 - 0 - 2";
        val = Simulation.getLineValues(line);
        isValuesOk = Simulation.verifyLineValues(val);
        assertFalse(isValuesOk);

        // on crée un element qui ne respecte pas le format pour l'elt
        line = "M - 1 - A";
        val = Simulation.getLineValues(line);
        isValuesOk = Simulation.verifyLineValues(val);
        assertFalse(isValuesOk);
    }
}