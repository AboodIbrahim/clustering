package algo;

import de.unijena.bioinf.dm2024.grp03.sequence.model.Sequence;
import de.unijena.bioinf.dm2024.grp4.alignment.io.Matrix;
import model.*;

import java.util.ArrayList;
import java.util.Random;

public class NeighborhoodJoining extends ClusteringClass {
private ArrayList<Node> nodes;
TreeClass tree;
private ArrayList<DuoInt> rowCol = new ArrayList<DuoInt>();

    /**
     * Konstruktor der aus gegebenen Parametern eine Distanzmatrix, Queue &Baum erstellen
     * lässt, und NJ auf diese anwendet.
     * @param queue namen der Blätter
     * @param metric true, wenn die 3 und 4 Punkte Bedingung geprüft werden sollen
     *            false, wenn nicht
     * @param matrix Distanzmatrix
     */
    public NeighborhoodJoining(boolean metric,ArrayList<String> queue, double[][] matrix) throws Exception {
        super(metric, matrix);
        tree = new TreeClass(queue);

        nodes = new ArrayList<>(tree.getRoot().getChildren());
        nj(dist);
    }


    /**
     * Konstruktor der aus gegebenen Parametern eine Distanzmatrix, Queue &Baum erstellen
     * lässt, und NJ auf diese anwendet.
     * @param sequences Liste der Sequencen fürs Clustern
     * @param metric true, wenn die 3 und 4 Punkte Bedingung geprüft werden sollen
     *            false, wenn nicht
     * @param global global or lokal Alignment
     */
    public  NeighborhoodJoining(boolean metric,ArrayList<Sequence> sequences, boolean global) throws Exception {
        super(global, metric, sequences);
        //false bezüglich nj und kein upgma/nj
        String[] leaves = new String[sequences.size()];
        ArrayList<String> queue = new ArrayList<>();

        for (Sequence sequence : sequences) {
            queue.add(sequence.getName());
        }
        tree = new TreeClass(queue);

        nodes = new ArrayList<>(tree.getRoot().getChildren());


        nj(dist);
    }

    private void nj(double[][] dist){
        rowCol.clear();
        if(nodes.size()>=3) {
           double[] r = new double[dist.length];
           double[][] M = new double[dist.length][dist.length];
           for (int i = 0; i < dist.length; i++) {
               for (int j = 0; j < dist.length; j++) {
                   r[i] += dist[i][j];
               }
           }

        for(int i= 0; i<M.length-1; i++){
            for (int j =i+1; j<M.length;j++){
                M[i][j]=dist[i][j]- (r[i] + r[j]) /(M.length-2);
                //nicht notwendig aber sichherheitshalber
                M[j][i]=M[i][j];
            }
        }

       int mina =0;
       int minb =1;
      for(int i= 0; i<M.length-1; i++){
            for (int j =i+1; j<M.length;j++){
                if(M[i][j]<M[mina][minb]){
                    mina =i;
                    minb =j;
                    if(mina>minb){
                        //kann eigentlich nicht eintreten
                        int tmp = minb;
                        minb=mina;
                        mina=tmp;
                    }
                    if (!rowCol.isEmpty() && rowCol.getLast().getValue() > M[mina][minb])
                        rowCol.clear();
                    rowCol.add(new DuoInt(i, j,  M[mina][minb]));
                }
            }
        }


            Random rand = new Random();
            if (rowCol.size() > 1) {
                int ra = rand.nextInt(rowCol.size()-1);
                mina= rowCol.get(ra).getRow();
                minb = rowCol.get(ra).getCol();
            }

      double diu = 0.5 * dist[mina][minb] + (r[mina] - r[minb]) / (2 * (dist.length - 2));
      nodes.add(tree.MergeNodes(nodes.get(mina), nodes.get(minb), Double.toString(diu),Double.toString(dist[mina][minb] - diu)));

            nodes.remove(minb);
        nodes.remove(mina);
        double[][] newdist = updateDistanceMatrix(dist, mina, minb);
        nj(newdist);
       }else{
            System.out.println("");
            tree.MergeNodes(nodes.get(1), nodes.get(0), Double.toString(dist[0][1]),"0");
            nodes.remove(1);
            nodes.remove(0);
       }
    }

    public TreeClass getTree() { return this.tree;}

}
