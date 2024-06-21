package org.projet;

public class Main {
    public static void main(String[] args) {
        Simulation.readFile("src/main/resources/carte");
        // on affiche la carte a l'etat initial
        System.out.println("-- Etat initial de la carte --");
        Simulation.map.printMap();
        // on va gerer tous les deplacements des aventuriers
        Simulation.moveAdventurers();
        // on affiche la carte une fois tous les deplacements terminés
        System.out.println("\n-- Etat final de la carte--");
        Simulation.map.printMap();
        // on ecrit le resultat dans un fichier
        Simulation.writeFile("src/main/resources/resultats.txt");
    }
}