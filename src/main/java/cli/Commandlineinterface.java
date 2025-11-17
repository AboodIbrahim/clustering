package cli;

import algo.ClusteringClass;
import algo.NeighborhoodJoining;
import algo.PGMA;
import de.unijena.bioinf.dm2024.grp03.exactsearch.io.Fasta;
import de.unijena.bioinf.dm2024.grp03.sequence.model.Sequence;

import io.DistanceMatrixReader;
import io.MultiFastaReader;
import io.NewickWriter;
import picocli.CommandLine;

import picocli.CommandLine.Command;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Option;

import java.util.ArrayList;
import java.util.concurrent.Callable;

@Command(name = "clustering", mixinStandardHelpOptions = true, version = "1.0",
        description = "alignments")
public class Commandlineinterface implements Callable<String> {


    @ArgGroup(exclusive = true, multiplicity = "1")
    private ArgU u;

    static class ArgU {
        @Option(names = "-inmult", required = true, description = "multifasta path")
        String multfasta;
        @Option(names = "-insing", required = true, description = "multiple fasta file paths")
        ArrayList<String> files = new ArrayList<>();
        @Option(names = "-dist", required = true, description = "distancematrix path")
        String distpath;
    }


    @ArgGroup(exclusive = true, multiplicity = "0..1")
    private Sequencetype seq;


    //not needed if pam and blossum arent needed?? but needed for Sequence
    static class Sequencetype {
        @Option(names = "-dna", required = true, description = "dna")
        boolean dna;
        @Option(names = "-rna", required = true, description = "rna")
        boolean rna;
        @Option(names = "-ascii", required = true, description = "ascii")
        boolean ascii;
        @Option(names = "-amino", required = true, description = "amino")
        boolean amino;
    }

    @ArgGroup(exclusive = true, multiplicity = "0..1")
    private ArgAl al;

    static class ArgAl {
        @Option(names = "-upgma", required = true, description = "unweighted pair group method")
        boolean upgma;
        @Option(names = "-wpgma", required = true, description = "weighted pair group method")
        boolean wpgma;
        @Option(names = "-nj", required = true, description = "neighborhood joining")
        boolean nj;
    }

    @Option(names = "-global", description = "global or not global")
    private boolean global;

    @Option(names = "-metric", description = "checks the metric")
    private boolean metric;

    @Option(names = "-ot", description = "terminaloutput tree")
    private boolean ot;

    @Option(names = "-otd", description = "terminaloutput distancematrix")
    private boolean otd;

    @Option(names = "-op", description = "tree filepathoutput")
    private String op;




    public String call() throws Exception {
        String path = "";
        ArrayList<Sequence> fastas =null;
        long time = 0;
        ClusteringClass cluster =null;

//Problem ist header ist != name, name wird für die Sequence deklariert
        //folgeproblem für die Alignments
        try {
            if (u == null) {
                throw new Exception("Missing u");
            }
            if(u.multfasta != null) {
                MultiFastaReader multfasta = null;
                //fasta löst das problem nicht
                    if (seq.dna) {
                        multfasta = new MultiFastaReader(u.multfasta, Fasta.SequenceType.Nucleotide);
                    } else if (seq.rna) {
                        multfasta = new MultiFastaReader(u.multfasta, Fasta.SequenceType.Nucleotide);
                    } else if (seq.amino) {
                        multfasta = new MultiFastaReader(u.multfasta, Fasta.SequenceType.Protein);
                    } else {
                        multfasta = new MultiFastaReader(u.multfasta, Fasta.SequenceType.Ascii);
                    }

                    if (global) {
                        if (al.upgma) {
                            cluster = new PGMA(true, metric,  multfasta.getSequences(), false);
                        } else if (al.wpgma) {
                            cluster = new PGMA(true, metric,  multfasta.getSequences(), true);
                        } else if (al.nj) {
                            cluster = new NeighborhoodJoining(metric, multfasta.getSequences(), true);
                        }
                    } else {
                        if (al.upgma) {
                            cluster = new PGMA(false, metric,  multfasta.getSequences(), false);
                        } else if (al.wpgma) {
                            cluster = new PGMA(false, metric,  multfasta.getSequences(), true);
                        } else if (al.nj) {
                            cluster = new NeighborhoodJoining(metric,multfasta.getSequences(), false);
                        }
                    }
            } else if (u.files != null){
                    for (int i = 0; i < u.files.size(); i++) {
                        if (seq.dna) {
                            Fasta fasta = new Fasta(u.files.get(i), Fasta.SequenceType.Nucleotide);
                            fastas.add(fasta.getSequence());
                        } else if (seq.rna) {
                            Fasta fasta = new Fasta(u.files.get(i), Fasta.SequenceType.Nucleotide);
                            fastas.add(fasta.getSequence());
                        } else if (seq.amino) {
                            Fasta fasta = new Fasta(u.files.get(i), Fasta.SequenceType.Protein);
                            fastas.add(fasta.getSequence());
                        } else {
                            Fasta fasta = new Fasta(u.files.get(i), Fasta.SequenceType.Ascii);
                            fastas.add(fasta.getSequence());
                        }
                    }

                    if (global) {
                        if (al.upgma) {
                            cluster = new PGMA(true, metric, fastas, false);
                        } else if (al.wpgma) {
                            cluster = new PGMA(true, metric, fastas, true);
                        } else if (al.nj) {
                            cluster = new NeighborhoodJoining(metric,fastas, true);
                        }
                    } else {
                        if (al.upgma) {
                            cluster = new PGMA(false, metric, fastas, false);
                        } else if (al.wpgma) {
                            cluster = new PGMA(false, metric, fastas, true);
                        } else if (al.nj) {
                           cluster = new NeighborhoodJoining(metric,fastas, false);
                        }
                    }
            }else{
                DistanceMatrixReader matrix =new DistanceMatrixReader(u.distpath);
                if (al.upgma) {
                    cluster = new PGMA(metric, matrix.getSequences(), matrix.getDistMatrix(),false);
                } else if (al.wpgma) {
                    cluster = new PGMA(metric, matrix.getSequences(), matrix.getDistMatrix(),true);
                } else if (al.nj) {

                    cluster = new NeighborhoodJoining(metric, matrix.getSequences(), matrix.getDistMatrix());
                }else{
                    throw new Exception("input error");
                }
            }

            try{
if(ot){
    System.out.println(cluster.getTree().getNewick());
}
if(otd){

    cluster.getDM().printMatrix();
}

if(op != null){
    NewickWriter nw = new NewickWriter(op, cluster.getTree());
}
} catch (Exception e) {
    throw new Exception("Output Error");
}




        }catch(Exception e){
            System.err.println(e.getMessage());
            return "";
        }
        return"";




    }


    public static void main(String[] args) {
        int exitCode = new CommandLine(new Commandlineinterface()).execute(args);
        System.exit(exitCode);

    }}



