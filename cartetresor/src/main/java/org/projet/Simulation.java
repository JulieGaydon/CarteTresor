package org.projet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.List;

public abstract class Simulation {
    // les indices ou recuperer les coordonnees des elts
    public final static int INDEX_NB_COLUMN = 1;
    public final static int INDEX_NB_ROW = 2;
    public final static int INDEX_NB_COLUMN_ADVENT = 2;
    public final static int INDEX_NB_ROW_ADVENT = 3;
    // nombre de variable selon objet
    public final static int NB_VAL_ELEM = 3;
    public final static int NB_VAL_TRES = 4;
    public final static int NB_VAL_ADVENT = 6;
    public static Map map = null;

    /**
     * Lit le fichier ligne par ligne et va créer les éléments correspondants
     * @param pathFile le chemin pour trouver le fichier à lire
     */
    public static boolean readFile(String pathFile){
        try {
            // Création d'un fileReader pour lire le fichier
            FileReader fileReader = new FileReader(pathFile);
            // Création d'un bufferedReader qui utilise le fileReader
            BufferedReader reader = new BufferedReader(fileReader);
            // on va lire les lignes une à une
            String line = reader.readLine();
            // tant que le fichier n'est pas terminé
            while (line != null) {
                if(!line.contains("#")) {
                    // on va créer les elements des lignes qui ne sont pas des commentaires
                    if(!createElementsLine(line)){
                        System.out.println("Element non créé");
                    }
                }
                // lecture de la prochaine ligne
                line = reader.readLine();
            }
            reader.close();
        // gestion erreur
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Ecrit le fichier résultat des elements restant sur la carte
     * @param pathFile le chemin pour trouver le fichier dans lequelle ecrire
     */
    public static boolean writeFile(String pathFile){
        try {
            // Création d'un fileWriter pour écrire dans un fichier
            FileWriter fileWriter = new FileWriter(pathFile, false);
            // Création d'un bufferedWriter qui utilise le fileWriter
            BufferedWriter writer = new BufferedWriter(fileWriter);
            if(map == null){
                return false;
            }
            ArrayList<String> elemToWrite = map.getElemToWrite();
            for(String str : elemToWrite) {
                // ajout d'une ligne à notre fichier
                writer.write(str);
                // Retour à la ligne
                writer.newLine();
            }
            writer.close();
        // gestion erreur
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Va récuperer les éléments d'une ligne du fichier
     * @param line la ligne à "decouper"
     */
    public static List<String> getLineValues(String line){
        // expression reguliere pour recuperer  ce qu'il y a entre "-"
        String regex = "([^\\-]+)";
        // creation expression reguliere
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);

        // Une liste contenant les valeurs trouvées
        List<String> values = new ArrayList<>();
        String val = "";
        while (matcher.find()) {
            val = matcher.group(1).trim();
            // recupere et ajoutes les valeurs sans espace et non vide
            if(!val.isEmpty()) {
                values.add(matcher.group(1).trim());
            }
        }
        return values;
    }

    public static boolean isInteger(String str){
        try {
            // on peut parser, donc c'est un entier
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Verifie si les lignes du fichier fourni respecte le bon format
     * @param valueLine les elements qu'on a recuperer de la ligne
     * @return si la ligne respecte les formats pour chaque element
     */
    public static boolean verifyLineValues(List<String> valueLine){
        return switch (valueLine.get(0)) {
            case "C", "M" ->
                    valueLine.size() == NB_VAL_ELEM && isInteger(valueLine.get(INDEX_NB_ROW)) && isInteger(valueLine.get(INDEX_NB_COLUMN));
            case "T" ->
                    valueLine.size() == NB_VAL_TRES && isInteger(valueLine.get(INDEX_NB_ROW)) && isInteger(valueLine.get(INDEX_NB_COLUMN));
            case "A" ->
                    valueLine.size() == NB_VAL_ADVENT && isInteger(valueLine.get(INDEX_NB_COLUMN_ADVENT)) && isInteger(valueLine.get(INDEX_NB_ROW_ADVENT));
            default -> false;
        };
    }

    /**
     * Creation des éléments de la ligne
     * @param line contenant les valeurs pour créer l'élément correspondant
     */
    public static boolean createElementsLine(String line){
        List<String> valueLine = getLineValues(line);
        // on verifie que les lignes passées respectent le format pour chaque elt
        if(!verifyLineValues(valueLine)){
            return false;
        }
        int columns = -1;
        int rows = -1;
        String elementToCreate = valueLine.get(0);
        // pour tous les elts sauf aventurier
        if(!elementToCreate.equals("A")){
            // on recupere les coordonnées
            columns = Integer.parseInt(valueLine.get(INDEX_NB_COLUMN));
            rows = Integer.parseInt(valueLine.get(INDEX_NB_ROW));
        // coordonnees pour aventurier
        }else{
            columns = Integer.parseInt(valueLine.get(INDEX_NB_COLUMN_ADVENT));
            rows = Integer.parseInt(valueLine.get(INDEX_NB_ROW_ADVENT));
        }
        // on crée la carte
        if( elementToCreate.equals("C")){
            map = new Map(rows, columns);
        }else{
            // on crée l'elt de la ligne
            Element elem = switch (elementToCreate) {
                case "T" -> new Tresor(rows, columns, Integer.parseInt(valueLine.get(3)));
                case "A" -> new Adventurer(rows, columns, valueLine.get(1), valueLine.get(5), valueLine.get(4));
                default -> new Element(rows, columns);
            };
            // si on ne commence pas par creer la carte, aucun elt ne se crée
            if(map == null){
                return false;
            }
            // on l'ajoute a la carte si coordonnées sont bons
            if(! map.addElemOnMap(elem)){
                System.out.println("Mauvais coordonnées, l'element n'a pas ete ajouté");
                return false;
            }
        }
        return  true;
    }

    /**
     * Appelle la fonction de carte pour gerer le déplacement des aventuriers
     */
    public static void moveAdventurers(){
        map.movementsOfAllAdventurers();
    }
}
