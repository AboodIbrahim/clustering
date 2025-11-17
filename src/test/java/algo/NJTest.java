package algo;

import de.unijena.bioinf.dm2024.grp03.sequence.model.NucleotideSequence;
import de.unijena.bioinf.dm2024.grp03.sequence.model.Sequence;
import io.DistanceMatrixReader;
import model.DistanceMatrix;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class NJTest {

    @Test
    public void NJ() {
        try {
            DistanceMatrixReader matrix = new DistanceMatrixReader("./Testfiles/DistancematrixreaderTest");
            NeighborhoodJoining cluster = new NeighborhoodJoining(false,matrix.getSequences(), matrix.getDistMatrix());
            System.out.println(cluster.getTree().getNewickWithoutRoot());
        } catch(Exception e) {System.out.println(e.getMessage());}
    }


}
