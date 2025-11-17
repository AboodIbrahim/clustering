package algo;

import de.unijena.bioinf.dm2024.grp03.sequence.model.*;
import model.DistanceMatrix;

import java.util.ArrayList;

/**
 *
 */
public abstract class ClusteringClass implements Clustering{
    double[][] dist;
    DistanceMatrix dmatrix;


    /**
     * Konstruktor der aus gegebenen Parametern eine Distanzmatrix erstellt
     * @param metric true, wenn die 3 und 4 Punkte Bedingung geprüft werden sollen
     *            false, wenn nicht
     * @param global true globales alignment
     *               false lokales alignment
     * @param sequences Liste der Sequencen
     */
    public ClusteringClass(boolean global, boolean metric, ArrayList<Sequence> sequences) {
        dmatrix = new DistanceMatrix(metric,global,sequences);
        dist = dmatrix.getMatrix();
    }
/*
    //könnte fehler machen hab nicht soviel ahnung von vararg
    public ClusteringClass(boolean global,boolean metric, Sequence ... sequences) {
        dmatrix = new DistanceMatrix(metric,global,sequences);
        dist = dmatrix.getMatrix();
    }

 */
    /**
     * Konstruktor der aus gegebenen Parametern eine Distanzmatrix erstellt
     * @param metric true, wenn die 3 und 4 Punkte Bedingung geprüft werden sollen
     *            false, wenn nicht
     * @param matrix matrix mit distanzen
     */
    public ClusteringClass(boolean metric, double[][] matrix){
        dmatrix = new DistanceMatrix(metric,matrix);
        dist = dmatrix.getMatrix();
    }


    /**
     * Methode um die Distanzmatrix zu updaten ohne Zeile/spalte a und b
     * aber mit cluster ab und dessen distanzen
     * @param distmatrix zu updatende distanzmatrix
     * @param a Zeile und Spalte die in distmatrix gelöscht werden soll
     * @param b Zeile und Spalte die in distmatrix gelöscht werden soll
     * @return newdist geupdatete distanzmatrix ohne zeile/spalte a/b und mit cluster ab als letzte Spalte/Zeile
     */

    public double[][] updateDistanceMatrix(double[][] distmatrix, int a, int b){
        double[][] newdist = new double[distmatrix.length - 1][distmatrix.length - 1];
        int row = 0;
        int column;

        if(b<a){
            int temp =b;
            b=a;
            a=temp;
        }

        for (int i = 0; i < distmatrix.length; i++) {
            column = 0;
            if (i != a && i != b) {
                for (int j = 0; j < distmatrix.length; j++) {
                    if (j != a && j != b) {
                        newdist[row][column] = dist[i][j];
                        column++;
                    }
                    /*
                    if (j == b) {
                        newdist[row][j] = 0.5 * (distmatrix[a][i] + distmatrix[b][i] - distmatrix[a][b]);
                    }
                     */
                }
                row++;
            }
        }
//last column & row , inefficient solution, however faster solution doesnt really take more time
        row=0;
        for (int i = 0; i < distmatrix.length; i++) {
            if (i != a && i != b) {
                newdist[row][newdist.length-1] = 0.5 * (distmatrix[a][i] + distmatrix[b][i] - distmatrix[a][b]);
                newdist[newdist.length-1][row]=newdist[row][newdist.length-1];
                row++;
            }
        }


        return newdist;
    }

    public  DistanceMatrix  getDM(){
        return this.dmatrix;
    }

}
