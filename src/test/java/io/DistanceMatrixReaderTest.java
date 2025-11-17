package io;

import de.unijena.bioinf.dm2024.grp03.exactsearch.io.Fasta;
import org.junit.jupiter.api.Test;

public class DistanceMatrixReaderTest {


    @Test
    void readAndPrint() {
        DistanceMatrixReader r = new DistanceMatrixReader("./Testfiles/DistancematrixreaderTest");
        double[][] test = r.getDistMatrix();
        for (int i = 0; i<r.getSequences().size(); i++) {
            for (int j = 0; j<r.getSequences().size(); j++) {
                System.out.print(test[i][j]+ " ");
            }
            System.out.println();
        }

        System.out.println(r.getSequences());
    }
}

