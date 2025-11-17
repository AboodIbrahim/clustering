package Aufgaben;

import algo.NeighborhoodJoining;
import algo.PGMA;
import de.unijena.bioinf.dm2024.grp03.exactsearch.io.Fasta;
import io.MultiFastaReader;

public class Aufgabe3 {

    public static void main(String[] args) throws Exception {
        MultiFastaReader mfr = new MultiFastaReader("./dataClustering/aquifex-tRNA-clean.fasta", Fasta.SequenceType.Nucleotide);
        PGMA wpgma = new PGMA(true, false, mfr.getSequences(), true);
        PGMA upgma = new PGMA(true, false, mfr.getSequences(), false);
        NeighborhoodJoining nj = new NeighborhoodJoining(false, mfr.getSequences(), true);

        System.out.println(wpgma.getTree().getNewickWithoutRoot());
        System.out.println(upgma.getTree().getNewickWithoutRoot());
        System.out.println(nj.getTree().getNewickWithoutRoot());
    }
}