package io;

import java.io.*;
import java.util.ArrayList;

public class DistanceMatrixReader {

    private final ArrayList<String> queue = new ArrayList<>();
    private double[][] matrix;

    /**
     * Konstruktor, um aus einer csv eine distanzmatrix und Namen zu lesen
     * @param path pfad der csv datei
     */


    public DistanceMatrixReader(String path) {
        StringBuilder helper = new StringBuilder();
        try {
            File file = new File(path);
            if (file.exists() && file.canRead()) {
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                String line = br.readLine();
                String[] tmp = line.split(";");
                int j=0;
                for(int i=1; i<tmp.length; i++){
                    queue.add(tmp[i]);
                }

                //;; asd;sada;sad beheben
                //fix int i=1 anstelle i=0
                line = br.readLine();
                tmp = line.split(";");
                matrix = new double[tmp.length-1][tmp.length-1];
                while(line != null) {
                    tmp = line.split(";");
                    for(int i=1; i<tmp.length; i++){
                        matrix[j][i-1] = Double.parseDouble(tmp[i]);
                    }
                    j++;
                    line = br.readLine();
                }
            }
        } catch(IOException e) {System.err.println(e.getMessage());}

    }

    public ArrayList<String> getSequences() { return queue; }

    public double[][] getDistMatrix() { return matrix; }

}
