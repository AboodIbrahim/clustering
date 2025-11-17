package algo;

import de.unijena.bioinf.dm2024.grp03.sequence.model.AsciiSequence;
import de.unijena.bioinf.dm2024.grp03.sequence.model.Sequence;
import de.unijena.bioinf.dm2024.grp03.sequence.model.SequenceClass;
import model.Node;
import model.TreeClass;

import java.util.ArrayList;
import java.util.Random;

/**
 * Führt auf eine Distanzmatrix entweder UPGMA oder WPGMA aus und erstellt
 * parallel einen Baum, der ausgegeben werden kann.
 */
public class PGMA extends ClusteringClass {
    private int row, col = 0;
    private double min = Double.MAX_VALUE;
    private ArrayList<Sequence> sequences = new ArrayList<>();
    private TreeClass tree;
    private ArrayList<Node> nodes;
    private double distance;
    private boolean weighed;
    private ArrayList<DuoInt> rowCol;

    /**
     * Konstruktor der aus gegebenen Parametern eine Distanzmatrix erstellen
     * lässt, und UPGMA oder WPGMA auf diese anwendet.
     * @param global true, wenn die Distanzmatrix mit globalen Alignments gebildet werden soll
     *               false, wenn sie stattdessen mit lokalen Alignmentes gebildet werden soll
     * @param metric true, wenn die 3 und 4 Punkte Bedingung geprüft werden sollen
     *            false, wenn nicht
     * @param sequences die Liste an Sequenzen, die Aligniert werden
     * @param weighed true, führt WPGMA aus
     *                false, führt UPGMA aus
     */
    public PGMA(boolean global, boolean metric, ArrayList<Sequence> sequences, boolean weighed) {
        super(global, metric, sequences);
        construct(sequences, weighed);
    }

    /**
     * Konstruktor der auf gegebene Distanzmatrix UPGMA oder WPGMA anwendet.
     * @param metric true, wenn die 3 und 4 Punkte Bedingung geprüft werden sollen
     *               false, wenn nicht
     * @param matrix bereits gefüllte Matrix, Sequenznamen werden automatisch generiert,
     *               die erste Sequenz in der Liste, ist auch die zuerst benannte Sequenz
     * @param weighed true, führt WPGMA aus
     *                false, führt UPGMA aus
     */
    public PGMA( boolean metric, double[][] matrix, boolean weighed) {
        super(metric, matrix);
        ArrayList<Sequence> sequences = new ArrayList<>();
        for(int i = 0; i < matrix.length; i++)
            sequences.add(new SequenceClass(i + "", ""));
        construct(sequences, weighed);
    }


    /**
     * Konstruktor der auf gegebene Distanzmatrix UPGMA oder WPGMA anwendet.
     * @param metric true, wenn die 3 und 4 Punkte Bedingung geprüft werden sollen
     *               false, wenn nicht
     * @param sequencestrings namen der Sequencen für die Arraylist, werden nicht automatisch generiert
     * @param matrix bereits gefüllte Matrix, Sequenznamen werden automatisch generiert,
     *               die erste Sequenz in der Liste, ist auch die zuerst benannte Sequenz
     * @param weighed true, führt WPGMA aus
     *                false, führt UPGMA aus
     */
    public PGMA(boolean metric, ArrayList<String> sequencestrings, double[][] matrix, boolean weighed) {
        super( metric, matrix);
        ArrayList<Sequence> tmp = new ArrayList<>();
        for (int i =0; i<sequencestrings.size(); i++){
            tmp.add(new AsciiSequence(sequencestrings.get(i), ""));
        }

        construct(sequences, weighed);
    }

    private void construct(ArrayList<Sequence> sequences, boolean weighed)
    {
        this.sequences = new ArrayList<>(sequences);
        this.weighed = weighed;
        tree = new TreeClass("", "", getSequenceArrayListString());
        nodes = new ArrayList<>(tree.getRoot().getChildren());
        // Testing
        //printDist();
        rowCol = new ArrayList<DuoInt>();
        while (dist.length > 1) updateMatrix();
    }

    private void updateMatrix() {
        // Finde Minimum in Distanzmatrix
        rowCol.clear();
        min = Double.MAX_VALUE;
        for (int i = 0; i < dist.length; i++) {
            for (int j = i + 1; j < dist.length; j++)
                if (min >= dist[i][j]){
                    min = dist[i][j];
                    row = i;
                    col = j;
                    if (!rowCol.isEmpty() && rowCol.getLast().getValue() > min)
                        rowCol.clear();
                    rowCol.add(new DuoInt(i, j, min));
                }

        }
        /*
        System.out.println("mins: ");
        for (var r: rowCol) System.out.print(r.getValue() + " ");
        System.out.println();

         */

        Random rand = new Random();
        if (rowCol.size() > 1) {
            int r = rand.nextInt(rowCol.size()-1);
            //System.out.println("rowCol: " + rowCol.size());
            //System.out.println("random: " + r);
            row = rowCol.get(r).getRow();
            col = rowCol.get(r).getCol();
        }

        //System.out.println("min: " + min + " row: " + row + " col: " + col);

        // Distanz berechnen
        distance = min/2.0d;

        // Neue, kleinere Distanzmatrix
        dist = newDistanceMatrix(dist, row, col);

        tree.MergeNodes(nodes, row, col, Double.toString(distance), Double.toString(distance), sequences.getFirst().getName());

        // Testing
        //printDist();
    }

    private double[][] newDistanceMatrix(double[][] dist, int row, int col) {
        double[][] newDist = new double[dist.length-1][dist.length-1];
        // Speichern welche Einträge unberührt blieben
        ArrayList<Integer> indexes = new ArrayList<>();
        for (var i: sequences) {
            if (sequences.indexOf(i) != row && sequences.indexOf(i) != col) indexes.add(sequences.indexOf(i));
        }

        // Neue kleinere Matrix befüllen
        for (int i = 1; i < newDist.length; i++) {
            /*
            for (int j = i; j < dist.length; j++) {

                if (i < row && j < col) newDist[i][j] = dist[i][j];
                else if (i > row && j < col) newDist[i][j] = dist[i][j];
                else if (i < row && j > col) newDist[i][j] = dist[i][j];
                else if (i > row && j > col) newDist[i-1][j-1] = dist[i][j];
            }

             */
            /*
            // Testing
            System.out.print("index: ");
            for (var x: indexes) {
                System.out.print(x + " ");
            }
            System.out.println();

             */

            int k = 1;
            for (var x : indexes) {
                newDist[k][i] = dist[x][i];
                newDist[i][k] = newDist[k][i];
                k++;

            }
        }

        // Berechnen der neuen Scores
        int k = 1;
        for (var i: indexes) {
            if (weighed) {
                newDist[0][k] = (dist[row][i] + dist[col][i])/2.0d;
            } else {
                double n = sequences.get(row).getName().split("/").length;
                double m = sequences.get(col).getName().split("/").length;
                newDist[0][k] = (n * dist[row][i] + m * dist[col][i])/(n+m);
                newDist[k][0] = newDist[0][k];
            }
            k++;
        }
        /*
        // Matrix diagonal spiegeln
        for (int i = 0; i < newDist.length; i++){
            for (int j = 0; j < newDist.length; j++) {
                newDist[j][i] = newDist[i][j];
            }
        }

         */
        updateSequences();
        return newDist;
    }

    private ArrayList<String> getSequenceArrayListString() {
        ArrayList<String> ar = new ArrayList<>();
        for (var i: sequences) ar.add(i.getName());
        return ar;
    }

    private void updateSequences() {
        Sequence sc = new SequenceClass(sequences.get(row).getName() + "/" + sequences.get(col).getName(),"");
        // Testing
        //System.out.println("Distance: " + distance);
        if (row < col) {
            sequences.remove(col);
            sequences.remove(row);
        } else if (row > col) {
            sequences.remove(row);
            sequences.remove(col);
        }
        sequences.addFirst(sc);
        // Testing
        //printSequences();
    }
    /*
    // Testing

    public void printDist() {
        for (int i = 0; i < dist.length; i++) {
            for (int j = 0; j < dist.length; j++) {
                System.out.print(dist[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Testing
    public void printSequences() {
        System.out.print("Sequence: ");
        for (var i: sequences)
            System.out.print(i.getName() + " ");
        System.out.println();
    }

     */



    public TreeClass getTree() {
        return tree;
    }
}
