package org.projet;

import java.util.ArrayList;

public class Map {
    Case[][] map;
    int nbColumns, nbRows;
    ArrayList<Adventurer> adventurers = new ArrayList<>();
    // longueur fixee pour formatter l'affichage
    int NB_LEN_MAX = 20;

    public Map(int rows, int columns){
        nbRows = rows;
        nbColumns = columns;
        map = new Case[rows][columns];
        for(int row=0; row < rows; row++){
            for(int col=0; col<columns; col++){
                // initialisation : aucune case occupée
                map[row][col] = new Case();
            }
        }
    }

    /**
     * On ajoute l'element sur la carte
     * @param elem element à ajouter
     * @return si l'elt a pu etre ajouté ou non
     */
    public boolean addElemOnMap(Element elem){
        if(elem.getRow() >= nbRows || elem.getRow() < 0 || elem.getColumn() >= nbColumns || elem.getColumn() < 0){
            return false;
        }
        // si l'elt est un tresor, on peut encore aller sur la case
        if(elem.typeElem == Element.ELEMENTS.TRESOR){
            map[elem.getRow()][elem.getColumn()].addElt(elem, false);
        }else{
            // si c'est un aventurier, on l'ajoute a la liste des aventuriers
            if(elem.typeElem == Element.ELEMENTS.ADVENTURER) {
                adventurers.add((Adventurer) elem);
            }
            // la case est alors occupée, on ne peut pas y aller dessus
            map[elem.getRow()][elem.getColumn()].addElt(elem, true);
        }
        return true;
    }

    /**
     * Realisation des mouvements des aventuriers un a un
     */
    public void movementsOfAllAdventurers() {
        // chaque aventurier effectue un mouvement
        boolean stillMovments;
        do {
            stillMovments = false;
            for (Adventurer adventurers : adventurers) {
                // tant que l'aventurier a des mouvements
                if (differentMovements(adventurers)) {
                    stillMovments = true;
                }
            }
        } while (stillMovments);
    }

    /**
     * Recupere le mouvement suivant de la liste de mouvement de l'aventurier
     * @param adventurer l'aventurier auquel on veut recuprer les mouvements
     * @return si on a recuperer les mouvements ou s'il n'y en a plus
     */
    public boolean differentMovements(Adventurer adventurer) {
        if(!adventurer.getMovement().isEmpty()) {
            // on recupere le premier mouvement restant
            char action = adventurer.getMovement().charAt(0);
            // on retire le mouvement effectué
            adventurer.setMovement(adventurer.getMovement().substring(1));
            if (action == 'A') {
                // on teste voir si l'aventurier peut avancer
                moveAdventurer(adventurer);
            } else {
                // on fait tourner l'aventurier
                adventurer.turn(action);
            }
            return true;
        }else{
            // plus de mouvements
            return false;
        }
    }

    /**
     * Vérifie si le deplacement de l'aventurier est possible
     * @param row ligne sur laquelle se deplace l'aventurier
     * @param column colonne sur laquelle se deplace l'aventurier
     * @param adventurer l'aventurier a deplacer
     * @return si l'aventurier peut se deplacer ou non
     */
    public boolean verifyReplacement(int row, int column, Adventurer adventurer){
        // sort du cadre, ne peut pas se deplacer
        if(row >= nbRows || row < 0 || column >= nbColumns || column < 0){
            return false;
        }
        Case my_case = map[row][column];
        // c'est occupé, il y a une montagne ou un aventurier, ne peut pas se deplacer
        if(my_case.isOccupied()){
            return false;
        }
        // c'est libre
        else{
            Tresor tresor = my_case.getTresor();
            // il y a un tresor
            if(tresor != null){
                // l'aventurier recupere un tresor
                tresor.removeOneTresor();
                adventurer.addTresor();
                // il n'y a plus de tresor
                if(!tresor.isStill()){
                    if(!my_case.removeTresor()){
                        System.out.println("Tresor n'a pas ete retire !");
                    }
                }
            }
            return true;
        }
    }

    /**
     * Deplace l'aventurier si c'est possible
     * @param adventurer l'aventurier a deplacer
     */
    public void moveAdventurer(Adventurer adventurer) {
        int row = adventurer.getRow();
        int column = adventurer.getColumn();
        switch (adventurer.getOrientation()){
            case NORTH:
                row --;
                break;
            case SOUTH:
                row ++;
                break;
            case EAST:
                column ++;
                break;
            case WEST:
                column --;
                break;
        }
        // il peut se deplacer, pas d'obstacle
        if(verifyReplacement(row,column, adventurer)){
            // l'aventurier sort de la case, donc elle devient libre
            if(!map[adventurer.getRow()][adventurer.getColumn()].removeAdventurer()){
                System.out.println("Aventurier n'a pas ete enlevé !");
            }
            // on assigne les nouvelles coordonnées a l'aventurier
            adventurer.move(row,column);
            // la nouvelle case ou se situe l'aventurier est occupee
            map[row][column].addElt(adventurer,true);
        }
    }

    /**
     * Affichage de la carte et de ses elements
     */
    public void printMap(){
        // on parcourt la carte
        for(int r=0; r<nbRows; r++){
            for(int c=0; c<nbColumns; c++){
                Case my_case = map[r][c];
                // on affiche les elts
                if(!my_case.getElements().isEmpty()){
                    StringBuilder printElem = new StringBuilder();
                    for(Element elem : my_case.getElements()){
                        printElem.append(elem.toString());
                    }
                    // on formate les elts pour qu'ils prennent la longueur max fixee
                    System.out.printf("%-" + NB_LEN_MAX + "s", printElem);
                }else{
                    System.out.printf("%-" + NB_LEN_MAX + "s", ".");
                }
            }
            System.out.println();
        }
    }

    /**
     * Recupere l'ensemble des elements sur la carte pour pouvoir ecrire le fichier de sortie
     * @return la liste contenant tous les elements de la carte
     */
    public ArrayList<String> getElemToWrite(){
        ArrayList<String> elem = new ArrayList<>();
        // on ajoute la carte
        elem.add(toWriteInFile());
        // on parcourt la carte
        for(int r=0; r<nbRows; r++){
            for(int c=0; c<nbColumns; c++){
                Case my_case = map[r][c];
                // on ajoute les elts
                if(!my_case.getElements().isEmpty()){
                    StringBuilder printElem = new StringBuilder();
                    for(Element elt : my_case.getElements()){
                        printElem.append(elt.toWriteInFile());
                    }
                    elem.add(printElem.toString());
                }
            }
        }
        return elem;
    }

    /**
     * La ligne a afficher dans le fichier de sortie pour la carte
     * @return la ligne a afficher
     */
    public String toWriteInFile(){
        return "C - " + nbColumns + " - " + nbRows;
    }
}
