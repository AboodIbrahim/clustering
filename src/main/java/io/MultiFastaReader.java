package io;

import de.unijena.bioinf.dm2024.grp03.exactsearch.io.Fasta;
import de.unijena.bioinf.dm2024.grp03.exactsearch.io.Fasta.SequenceType;
import de.unijena.bioinf.dm2024.grp03.sequence.model.*;

import java.io.*;
import java.util.ArrayList;

/**
 * Der MultiFastaReader liest Dateien im MultiFasta-Format aus und speichert alle darin
 * enthaltenen Sequenzen in einer Array-Liste. Alle Sequenzen müssen den gleichen Typen haben!
 * Sequenzen bestehen aus Header (Name) und Inhalt (Text) und können voneinander getrennt
 * ausgegeben werden.
 *
 * @author Jonas Eichelkraut
 */
public class MultiFastaReader {

    private final ArrayList<Sequence> sequences = new ArrayList<>();

    /**
     * Der Konstruktor liest eine Datei im MultiFasta-Format am angegeben Pfad aus.
     *
     * @param path       Pfad der MultiFasta-Datei.
     * @param t          Typ (Nucleotide, Protein, Ascii) der MultiFasta-Datei
     */
    public MultiFastaReader(String path, SequenceType t) {
        StringBuilder helper = new StringBuilder();
        try {
            File file = new File(path);
            if (file.exists() && file.canRead()) {
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                String line = br.readLine();
                while(line != null) {
                    if (line.charAt(0) == '>'){
                        do helper.append(line).append("\n");
                        while ((line = br.readLine()) != null && line.charAt(0) != '>');
                    }
                    File tmp = new File("./tmp.fasta");
                    FileWriter fw = new FileWriter(tmp);
                    fw.write(helper.toString());
                    helper.delete(0, helper.length());
                    fw.close();
                    Fasta fasta = new Fasta("./tmp.fasta", t);
                    sequences.add(fasta.getNewSequence());
                    tmp.delete();
                }
            }
        } catch(IOException e) {System.err.println(e.getMessage());}
    }

    public ArrayList<Sequence> getSequences() { return sequences; }
}
