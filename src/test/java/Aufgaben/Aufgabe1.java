package Aufgaben;

import algo.NeighborhoodJoining;
import algo.PGMA;
import de.unijena.bioinf.dm2024.grp03.sequence.model.NucleotideSequence;
import de.unijena.bioinf.dm2024.grp03.sequence.model.Sequence;


import java.util.ArrayList;

public class Aufgabe1 {
    public static void main(String[] args) throws Exception {

        double[][] matrix1 ={{0, 4, 4, 6, 6},
                             {4, 0, 4, 6, 6},
                             {4, 4, 0, 6, 6},
                             {6, 6, 6, 0, 5},
                             {6, 6, 6, 5, 0}};
        PGMA upgma = new PGMA(true, matrix1, true);
        System.out.println("upgma= "+upgma.getTree().getNewickWithoutRoot());

        System.out.println();

        PGMA pgma = new PGMA(true, matrix1, false);
        System.out.println("pgma= "+pgma.getTree().getNewickWithoutRoot());

        System.out.println();

        ArrayList<Sequence> sequences = new ArrayList<>();
        sequences.add(new NucleotideSequence("A", ""));
        sequences.add(new NucleotideSequence("B", ""));
        sequences.add(new NucleotideSequence("C", ""));
        sequences.add(new NucleotideSequence("D", ""));
        sequences.add(new NucleotideSequence("E", ""));

        ArrayList<String> queue = new ArrayList<>();
        for (Sequence s: sequences){
            queue.add(s.getName());
        }
        double[][] baummetrik ={{0, 3, 3, 5, 7},
                                {3, 0, 3, 5, 7},
                                {3, 3, 0, 5, 7},
                                {5, 5, 5, 0, 4},
                                {7, 7, 7, 4, 0}};
        NeighborhoodJoining nj = new NeighborhoodJoining(true, queue, baummetrik);
        System.out.println("neighborjoining= "+nj.getTree().getNewickWithoutRoot());

    }


}
