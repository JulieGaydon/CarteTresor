package org.projet;

public class Tresor extends Element {
    private int nbTresors;

    public Tresor(int row, int column, int nbTresors){
        super(row, column);
        this.nbTresors = nbTresors;
        typeElem = ELEMENTS.TRESOR;
    }

    /**
     * On retire un tresor jusqu'a ce qu'il n'y en ai plus
     */
    public void removeOneTresor(){
        if(nbTresors > 0){
            this.nbTresors --;
        }else{
            System.out.println("Plus de tresor");
        }
    }

    /**
     * Recupere le nombre de tresor restant
     * @return le nb de tresors
     */
    public int getNbTresor() {
        return nbTresors;
    }

    /**
     * Verifie qu'il reste encore des tresors
     * @return si il en reste ou non
     */
    public boolean isStill(){
        return nbTresors > 0;
    }

    /**
     * L'affichage de l'element sur la carte
     * @return ce qu'il doit afficher sur la carte
     */
    public String toString(){
        return "T("+nbTresors+")";
    }

    /**
     * La ligne a afficher dans le fichier de sortie pour un tresor
     * @return la ligne a afficher
     */
    public String toWriteInFile(){
        return "T - " + column + " - " + row + " - " + nbTresors;
    }
}
