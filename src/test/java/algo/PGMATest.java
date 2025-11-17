package algo;

import de.unijena.bioinf.dm2024.grp03.sequence.model.NucleotideSequence;
import de.unijena.bioinf.dm2024.grp03.sequence.model.Sequence;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class PGMATest {
    double[][] matrix = {{0, 17, 21, 31, 23},
            {17, 0, 30, 34, 21},
            {21, 30, 0, 28, 39},
            {31, 34, 28, 0, 43},
            {23, 21, 39, 43, 0}};

    double[][] matrix2 = {{0, 17, 21, 31, 23},
            {17, 0, 30, 34, 21},
            {21, 30, 0, 28, 39},
            {31, 34, 28, 0, 17},
            {23, 21, 39, 17, 0}};

    @Test
    public void testRandomTree() {
        try {
            PGMA wpgma = new PGMA(true, matrix2, true);
            System.out.println(wpgma.getTree().getNewick());
        } catch(Exception e) {System.out.println(e.getMessage());}
    }
    @Test
    public void lookAtUpdateDistanceMatrix() {
        ArrayList<Sequence> sequences = new ArrayList<>();
        sequences.add(new NucleotideSequence("A", ""));
        sequences.add(new NucleotideSequence("B", "AGTC"));
        sequences.add(new NucleotideSequence("C", "AGTC"));
        sequences.add(new NucleotideSequence("D", "CTGA"));
        PGMA wpgma = new PGMA(true, true, sequences, true);
        System.out.println(wpgma.getTree().getNewick());
    }

    @Test
    public void WPGMA() {
        PGMA wpgma = new PGMA(false, matrix, true);
        System.out.println(wpgma.getTree().getNewick());
    }

    @Test
    public void UPGMA() {
        PGMA upgma = new PGMA(false, matrix, false);
        System.out.println(upgma.getTree().getNewick());
    }

}
