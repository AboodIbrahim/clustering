package Aufgaben;

import algo.NeighborhoodJoining;
import algo.PGMA;
import de.unijena.bioinf.dm2024.grp03.sequence.model.NucleotideSequence;
import de.unijena.bioinf.dm2024.grp03.sequence.model.Sequence;
import de.unijena.bioinf.dm2024.grp03.sequence.model.SequenceClass;

import java.util.ArrayList;

public class Aufgabe2 {
    public static void main(String[] args) throws Exception {

        ArrayList<Sequence> sequences = new ArrayList<>();
        sequences.add(new NucleotideSequence("A", "GGGGCGGTAGCTCAGCTGGGAGAGCGCCGATATGGCATGTCGGAGGTCGGGGGTTCGAGTCCCCTCCGCTCCACCA"));
        sequences.add(new NucleotideSequence("B", "GGGGGTGTAGCTCAGCTGGGAGAGCGCCTGCTTTGCAAGCAGGAGGTCGTGGGTTCGAGTCCCACCACCTCCA"));
        sequences.add(new NucleotideSequence("C", "GGGGGTGTAGCTCAGCTGGGAGAGCGCCTGCTTTGCAAGCAGGAGGTCGTGGGTTCGAGTCCCACCACCTCCA"));
        sequences.add(new NucleotideSequence("D", "CGGGCTGTAGCTCAACTGGATAGAGCGCGGGACTACGGATCCCGAGGTTGCGGGTTCGACTCCCGCCAGCCCGGCCA"));
        sequences.add(new NucleotideSequence("E", "GTGCCCGTAGCTCAGGTGGACAGAGCACGGGGCTCCGGACCCCGGGGTCGCGGGTTCAAGTCCCGCCGGGCACA"));
        sequences.add(new NucleotideSequence("F", "GTGCCCGTAGCTCAGGCGGATAGAGCGCGAGATTCCTAATCTCGAGGTCGCGGGTTCGAGTCCCGCCGGGCACACCA"));

        PGMA pgma= new PGMA(true, true, sequences, false);
        System.out.println("pgma= "+pgma.getTree().getNewickWithoutRoot());
        System.out.println();

        PGMA upgma= new PGMA(true, true, sequences, true);
        System.out.println("upgma= "+upgma.getTree().getNewickWithoutRoot());
        System.out.println();

        NeighborhoodJoining nj = new NeighborhoodJoining(false, sequences, true);
        System.out.println("neighborjoining= "+nj.getTree().getNewickWithoutRoot());

    }
}
