package model;

import de.unijena.bioinf.dm2024.grp03.sequence.model.AsciiSequence;
import de.unijena.bioinf.dm2024.grp03.sequence.model.NucleotideSequence;
import de.unijena.bioinf.dm2024.grp03.sequence.model.Sequence;
import de.unijena.bioinf.dm2024.grp4.alignment.algo.AlignerClass;
import de.unijena.bioinf.dm2024.grp4.alignment.algo.NeedlemanWunsch;
import de.unijena.bioinf.dm2024.grp4.alignment.algo.SmithWaterman;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Die Distanzmatrix Klasse aligniert multiple Paarweise Alignments entweder global oder lokal
 * und erstellt mit den Scores die Distanzmatrix und überprüft ob die jeweiligen Metriken erfüllt sind
 */

public class DistanceMatrix {

    private ArrayList<Sequence> sequenceList = new ArrayList<>();
    private double[][] matrix;

    /**
     * Konstrutor zum erstellen einer Distanzmatrix aus einer Arraylist aus Sequenzen
     * @param metric true für Metriken-prüfung
     *               false für keine Prüfung
     * @param global für globales alinieren
     *               false für lokales alinieren
     * @param sequences die Arraylist mit den Sequenzen zum alinieren
     */
    public DistanceMatrix(boolean metric,boolean global,ArrayList<Sequence> sequences) {
        sequenceList.addAll(sequences);
        buildMatrix(global);
        isMetric(metric);
    }

    /**
     * Konstrutor zum erstellen einer Distanzmatrix aus einer folge von Sequenzen
     * @param metric true für Metriken-prüfung
     *               false für keine Prüfung
     * @param global für globales alinieren
     *               false für lokales alinieren
     * @param sequences die Arraylist mit den Sequenzen zum alinieren
     */
    public DistanceMatrix(boolean metric,boolean global,Sequence... sequences) {
        sequenceList.addAll(Arrays.asList(sequences));
        buildMatrix(global);
        isMetric(metric);
    }

    /**
     * Konstrutor zum erstellen einer Distanzmatrix aus gegebenen gefüllten Matrix
     * @param metric true für Metriken-prüfung
     *               false für keine Prüfung
     * @param dist die Matrix die als Distanzmatrix dienen soll
     */
    public DistanceMatrix(boolean metric,double[][] dist){
        matrix = dist;
        isMetric(metric);
    }

    private double[][] buildMatrix(boolean global){
        matrix = new double[sequenceList.size()][sequenceList.size()];
        AlignerClass align;

        for(int i=0; i<sequenceList.size(); i++){
            for(int j=i; j<sequenceList.size();j++){
                if(i==j){
                    matrix[i][j]=0;
                }else{
                    if(global){
                        align = new NeedlemanWunsch(sequenceList.get(i),sequenceList.get(j), "./dataAlignments/Matrices/GAPCOST1", false);
                        //align = new NaiveGlobal(sequenceList.get(i),sequenceList.get(j));
                        matrix[i][j]= align.getScore();
                        matrix[j][i]=matrix[i][j];
                    }else{
                        align = new SmithWaterman(sequenceList.get(i),sequenceList.get(j));
                        matrix[i][j]= align.getScore();
                        matrix[j][i]=matrix[i][j];
                    }
                }
            }
        }
        return matrix;
    }

    private void isMetric(boolean alg) {
        boolean isMetric= true;
        boolean isBaum= true;
        boolean isUltra= true;
        if(alg) {
            for (int i = 0; i < matrix.length; i++) {
                if (matrix[i][i] != 0) {
                    System.out.println("Matrix ist keine Metrik: d(i, i) != 0 bei i = " + i);
                    isMetric = false;
                }
                for (int j = 0; j < matrix.length; j++) {
                    if (matrix[i][j] < 0) {
                        System.out.println("Matrix ist keine Metrik: d(i, j) < 0 bei i = " + i + ", j = " + j);
                        isMetric = false;
                    }
                }
            }

            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix.length; j++) {
                    if (matrix[i][j] != matrix[j][i]) {
                        System.out.println("Matrix ist keine Metrik: d(i, j) != d(j, i) bei i = " + i + ", j = " + j);
                        isMetric = false;
                    }
                }
            }

            //true parameter für BaumMetrik und UltraMetrik, Nj baummetrik, upgma ultrametrik, ultrametrik
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix.length; j++) {
                    for (int k = 0; k < matrix.length; k++) {
                        if (matrix[i][k] > matrix[i][j] + matrix[j][k]) {
                            System.out.println("Matrix ist keine Metrik: Dreiecksungleichung verletzt bei i = " + i + ", j = " + j + ", k = " + k);
                            isMetric = false;
                        }
                    }
                }
            }

            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix.length; j++) {
                    for (int k = 0; k < matrix.length; k++) {
                        for (int l = 0; l < matrix.length; l++) {
                            double leftSide = matrix[i][j] + matrix[k][l];
                            double rightSide = Math.max(
                                    Math.max(matrix[i][k] + matrix[j][l], matrix[j][k] + matrix[i][l]),
                                    matrix[i][j] + matrix[k][j]
                            );
                            if (leftSide > rightSide) {
                                System.out.println("Matrix verletzt die 4-Punkte-Bedingung (Additive Baum-Metrik) bei i=" + i + ", j=" + j + ", k=" + k + ", l=" + l);
                                isBaum = false;
                            }
                        }
                    }
                }
            }

            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix.length; j++) {
                    for (int k = 0; k < matrix.length; k++) {
                        if (matrix[i][j] > Math.max(matrix[i][k], matrix[j][k])) {
                            System.out.println("Matrix verletzt die 3-Punkte-Bedingung (Ultrametrik) bei i=" + i + ", j=" + j + ", k=" + k);
                            isUltra = false;
                        }
                    }
                }
            }

            if (isMetric) System.out.println("Die gegebene Matrix erfuellt die Metrik.");
            if (isBaum) System.out.println("Zusaetzlich erfuellt sie die Baum-Metrik.");
            if (isUltra)System.out.println("Zusaetzlich erfuellt sie die Ultrametrik.");
            }
        }


    public double[][] getMatrix(){
        return matrix;
    }


    /**
     *Methode um Distanzmatritzen im Terminal zu visualisieren
     */
    public void printMatrix(){
        for (int i = 0; i < sequenceList.size(); i++) {
            for (int j = 0; j < sequenceList.size(); j++) {
                System.out.print("    "+matrix[i][j]);
            }
            System.out.println();
        }
    }

    //Test
    public static void main(String[] args) {
        DistanceMatrix dm = new DistanceMatrix(true,true,new NucleotideSequence("A", "AAAT"), new NucleotideSequence("B", "frjo"),
                new NucleotideSequence("A", "ATATG"), new NucleotideSequence("B", "AAAT"));
        for(Sequence i: dm.sequenceList){
            System.out.println(i.getText());
        }
        dm.printMatrix();
        dm.isMetric(true);
    }



}
