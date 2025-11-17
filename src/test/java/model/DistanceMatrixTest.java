package model;

import de.unijena.bioinf.dm2024.grp03.sequence.model.AminoacidSequence;
import de.unijena.bioinf.dm2024.grp03.sequence.model.AsciiSequence;
import de.unijena.bioinf.dm2024.grp03.sequence.model.NucleotideSequence;
import de.unijena.bioinf.dm2024.grp03.sequence.model.Sequence;
import de.unijena.bioinf.dm2024.grp4.alignment.algo.AlignerClass;
import de.unijena.bioinf.dm2024.grp4.alignment.algo.NeedlemanWunsch;
import de.unijena.bioinf.dm2024.grp4.alignment.algo.SmithWaterman;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class DistanceMatrixTest {

    static Stream<Arguments> dMatrix() {
        ArrayList<Sequence> s1 = new ArrayList<>();
        s1.add(new AsciiSequence("A", "abcdefghiop"));
        s1.add(new AsciiSequence("B", "aefh1z"));
        s1.add(new AsciiSequence("A", "abcd"));
        s1.add(new AsciiSequence("B", "abkl"));
        ArrayList<Sequence> s2 = new ArrayList<>();
        s2.add(new AsciiSequence("A", "sdkl"));
        s2.add(new AsciiSequence("B", "abcd"));
        ArrayList<Sequence> s3 = new ArrayList<>();
        s3.add(new AminoacidSequence("C", "ARSTHHPILEFG"));
        s3.add(new AminoacidSequence("D", "HPIEG"));
        return Stream.of(
                Arguments.of(s1),
                Arguments.of(s2),
                Arguments.of(s3)
                );
    }

    @ParameterizedTest
    @MethodSource
    void dMatrix(ArrayList<Sequence> sequences){
        try {
            DistanceMatrix dm = new DistanceMatrix(true, true, sequences);
            //dm.printMatrix();
        } catch (Exception e) {System.err.println(e.getMessage());}
    }


}

