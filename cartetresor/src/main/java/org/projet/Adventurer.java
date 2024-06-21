package org.projet;

import java.util.Arrays;

public class Adventurer extends Element {
    public enum ORIENTATION {
        NORTH,
        SOUTH,
        EAST,
        WEST
    }
    public ORIENTATION[] direction = {ORIENTATION.NORTH, ORIENTATION.EAST, ORIENTATION.SOUTH, ORIENTATION.WEST};
    public final String name;
    private ORIENTATION orientation;
    private String movement;
    private int nbTresor;

    public Adventurer(int row, int column, String name, String movement, String orientation){
        super(row, column);
        this.name = name;
        this.movement = movement;
        this.nbTresor = 0;
        switch(orientation){
            case "N":
                this.orientation = ORIENTATION.NORTH;
                break;
            case "S":
                this.orientation = ORIENTATION.SOUTH;
                break;
            case "E":
                this.orientation = ORIENTATION.EAST;
                break;
            case "O":
                this.orientation = ORIENTATION.WEST;
                break;
            // par defaut on lui met une orientation NORD
            default:
                this.orientation = ORIENTATION.NORTH;
                break;
        }
        typeElem = ELEMENTS.ADVENTURER;
    }

    /**
     * Tourne l'aventurier, change son orientation
     * @param newOrient s'il doit tourner à droite ou à gauche
     */
    public void turn(char newOrient){
        // on recupere l'index de la direction dans laquelle on est
        int curOrient = Arrays.asList(direction).indexOf(orientation);
        if(newOrient == 'D'){
            // on tourne a droite, modulo le nombre d'orientation
            orientation = direction[((curOrient+1)%direction.length)];
        }else{
            // on tourne a gauche, modulo le nombre d'orientation
            orientation = direction[((curOrient - 1 + direction.length) % direction.length)];
        }
    }

    /**
     * On attribut de nouvelles coordonnées à l'aventurier
     * @param row la nouvelle ligne
     * @param col la nouvelle colonne
     */
    public void move(int row, int col){
        // l'aventurier a avancé, on lui attribut de nouvelles coordonnées
        this.row = row;
        this.column = col;
    }

    /**
     * Récupere un trésor
     */
    public void addTresor(){
        this.nbTresor ++;
    }

    /**
     * Recupere l'orientation de l'aventurier
     * @return l'orientation
     */
    public ORIENTATION getOrientation(){
        return orientation;
    }

    /**
     * Recupere les mouvements de l'aventurier
     * @return la suite de mouvements
     */
    public String getMovement() {
        return movement;
    }

    /**
     * Recupere le nombre de tresor que possede l'aventurier
     * @return le nb de tresors
     */
    public int getNbTresor() {
        return nbTresor;
    }

    /**
     * Ecrit la suite de mouvements de l'aventurier
     */
    public void setMovement(String movement) {
        this.movement = movement;
    }

    /**
     * L'affichage de l'element sur la carte
     * @return ce qu'il doit afficher sur la carte
     */
    public String toString(){
        return "A("+ name +")";
    }

    /**
     * La ligne a afficher dans le fichier de sortie pour un aventurier
     * @return la ligne a afficher
     */
    public String toWriteInFile(){
        return "A - " + name + " - " + column + " - " + row + " - " + orientation.toString().charAt(0) + " - " + nbTresor;
    }
}
