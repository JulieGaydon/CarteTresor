package org.projet;

import java.util.ArrayList;

public class Case {
    private ArrayList<Element> elements = new ArrayList<>();
    private boolean isOccupied;

    public Case(){
        this.isOccupied = false;
    }

    /**
     * Ajoute l'element sur la case
     * @param element l'element a ajouter
     * @param isOccupied si la case est encore accessible ou non
     */
    public boolean addElt(Element element, boolean isOccupied){
        // occupé et on ajoute un autre element qu'un tresor
        if(this.isOccupied && element.typeElem != Element.ELEMENTS.TRESOR){
            // on n'ajoute pas l'elt
            return false;
        }else {
            if(this.isOccupied && elements.get(0).typeElem == Element.ELEMENTS.ADVENTURER){
                // occupé par un aventurier, mais on ajoute un tresor
                this.elements.add(element);
            }else {
                // c'est occupé mais c'est un tresor ou la case est libre
                this.elements.add(element);
                this.isOccupied = isOccupied;
            }
        }
        // on ajoute l'elt
        return true;
    }

    /**
     * Retire l'aventurier de la case
     * @return si l'aventurier a bien été enleve
     */
    public boolean removeAdventurer(){
        for(Element elem : elements){
            // si c'est un aventurier, on le retire
            if(elem.typeElem == Element.ELEMENTS.ADVENTURER){
                this.elements.remove(elem);
                this.isOccupied = false;
                return true;
            }
        }
        return false;
    }

    /**
     * Retire le tresor de la case
     * @return si le tresor a bien été enleve
     */
    public boolean removeTresor(){
        for(Element elem : elements){
            // c'est un tresor, on l'enleve
            if(elem.typeElem == Element.ELEMENTS.TRESOR){
                this.elements.remove(elem);
                this.isOccupied = false;
                return true;
            }
        }
        return false;
    }

    /**
     * Retourne les elements de la carte
     * @return les elements de la carte
     */
    public ArrayList<Element> getElements() {
        return elements;
    }

    /**
     * Retourne si la case est occupée
     * @return si la case est occupée ou non
     */
    public boolean isOccupied() {
        return isOccupied;
    }

    /**
     * Recupere l'element tresor de la case
     * @return le tresor
     */
    public Tresor getTresor(){
        for(Element elem : elements) {
            if (elem.typeElem == Element.ELEMENTS.TRESOR) {
                return (Tresor) elem;
            }
        }
        return null;
    }
}
