package io;


import model.Tree;

import java.io.*;

/**
 * Der Newickwriter schreibt das Newickformat als String und speichert es in einer Text-Datei am angegebenen Pfad
 */

public class NewickWriter {

    /**
     * Konstruktor der zum schreiben des Newick-format dient
     * @param path  Pfad der zu speichernden Datei
     * @param newick  Newick format als String
     */
    public NewickWriter(String path, String newick){
        writeNewick(path, newick);
    }


    /**
     * Konstruktor der zum schreiben des Newick-format dient
     * @param path Pfad der zu speichernden Datei
     * @param tree der Baum der den Newick-Format liefert
     */
    public NewickWriter(String path, Tree tree){
        writeNewick(path, tree.getNewick());
    }

    private void writeNewick(String path, String newick) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path));
            bufferedWriter.write(newick);
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

}
