package io;

import org.junit.jupiter.api.Test;

import de.unijena.bioinf.dm2024.grp03.exactsearch.io.Fasta;
import de.unijena.bioinf.dm2024.grp03.exactsearch.io.Fasta.SequenceType;
import static org.junit.jupiter.api.Assertions.*;

class MultiFastaReaderTest {

    @Test
    void getSequences() {
        MultiFastaReader mfr = new MultiFastaReader("./dataClustering/aquifex-tRNA-clean.fasta", SequenceType.Nucleotide);
        for (var s: mfr.getSequences()) {
            System.out.println(s.getName() + "\n" + s.getText());
        }
    }
}