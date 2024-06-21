package org.projet;

public class Element {
    protected int column, row;

    enum ELEMENTS {
        MOUNTAIN,
        ADVENTURER,
        TRESOR,
    }

    ELEMENTS typeElem = ELEMENTS.MOUNTAIN;

    public Element(int row, int column){
        this.column = column;
        this.row = row;
    }

    /**
     * Renvoie la colonne ou se trouve l'element
     * @return l'indice de la colonne
     */
    public int getColumn() {
        return column;
    }

    /**
     * Renvoie la ligne ou se trouve l'element
     * @return l'indice de la ligne
     */
    public int getRow() {
        return row;
    }

    /**
     * L'affichage de l'element sur la carte
     * @return ce qu'il doit afficher sur la carte
     */
    @Override
    public String toString() {
        return "M";
    }

    /**
     * La ligne a afficher dans le fichier de sortie pour une montagne
     * @return la ligne a afficher
     */
    public String toWriteInFile() {
        return "M - " + column + " - " + row;
    }
}
