/**
 * A simple plagiarism detection tool based on n-grams. On the command
 * line, the user can select between two implementations of WordMap,
 * LinkedWordMap and TreeWordMap. Likewise, the user can select
 * between two similarity measures, Jaccard and Cosine.
 *
 * "Pastiche" is the French word for a "literary or artistic work in
 * which the author imitates the way or the style of a master, by
 * stylistic exercise or with parodic intent" (translated from its
 * definition in "Le Petit Robert 2016").
 */

import java.io.*;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

import java.util.Iterator;

import java.util.Arrays;

public class Pastiche {

    // Constants

    private static final int WORD_SIZE = 4;
    private static final int MAX_NUMBER_OF_REPORTED_MATCHES = 2500;

    // Class variables
    
    private static Similarity similarity;
    private static WordMapFactory factory;

    // Creates either LinkedWordMap or TreeWordMap depending on the
    // argument passed on the command line.
    
    private static class WordMapFactory {
        private static final int WORDMAP_IS_LINKED = 0;
        private static final int WORDMAP_IS_TREE = 1;
        private int type;
        private WordMapFactory(int type) {
            if (type == WORDMAP_IS_LINKED || type == WORDMAP_IS_TREE) {
                this.type = type;
            } else {
                throw new IllegalArgumentException(Integer.toString(type));
            }
        }
        private WordMap newWordMap() {
            if (type == WORDMAP_IS_LINKED) {
                return new LinkedWordMap();
            } else if (type == WORDMAP_IS_TREE) {
                return new TreeWordMap();
            } else {
                throw new IllegalArgumentException(Integer.toString(type));
            }
        }
    }

    // Objects of the class Match are used internally to store the
    // result of a pairwise comparison.
    
    private static class Match implements Comparable<Match> {

        private String first;
        private String second;
        private double score;

        private Match(double score, String first, String second) {
            this.score = score;
            this.first = first;
            this.second = second;
        }

        public int compareTo(Match other) {
            if (score > other.score) { // reverse order!
                return -1;
            } else if (score == other.score) {
                return first.compareTo(second);
            } else {
                return 1;
            }
        }

        public String toString() {
            return String.format("%.2f", score * 100.0)+"%, "+first+", "+second;
        }
        
    }

    // Reads the content of a file and returns the corresponding
    // WordMap object.

    private static WordMap getWordMap(String fileName) throws FileNotFoundException, IOException {

        WordMap m;
        m = factory.newWordMap();

        WordReader w;
        w = new WordReader(fileName,false);

        Iterator<String> i;
        i = w.iterator(WORD_SIZE);

        while (i.hasNext()) {
            m.update(i.next());
        }

        return m;
    }

    // Compares two documents.

    private static Match compare(String fileA, String fileB) {

        WordMap a = null, b = null;

        try {

            a = getWordMap(fileA);
            b = getWordMap(fileB);

        } catch (FileNotFoundException e) {
            System.err.println(e);
        } catch (IOException e) {
            System.err.println(e);
        }

        return new Match(similarity.score(a,b), fileA, fileB);
    }

    // Compares all the pairs of files.

    private static void allPairs(LinkedList<String> files) {

        LinkedList<Match> matches;
        matches = new LinkedList<Match>();

        for (int i=0; i<files.size(); i++) {
            for (int j=i+1; j<files.size(); j++) {
                matches.addLast(compare(files.get(i), files.get(j)));
            }
        }

        Match[] ms = new Match[matches.size()];
        matches.toArray(ms);

        Arrays.parallelSort(ms);

        double ave = 0.0;
        
        for (int i=0; i < ms.length; i++) {
            ave += ms[i].score;
        }

        ave /= (double) ms.length;

        System.out.println("average score is " + String.format("%.2f", ave * 100.0) + "%");
        System.out.println();
        
        for (int i=0; i < ms.length && i < MAX_NUMBER_OF_REPORTED_MATCHES; i++) {
            System.out.println(ms[i]);
        }

    }

    // Displays information on how to use the application and quits.

    private static void displayUsageAndExit() {
        System.err.println("Usage: java Pastiche dir [LinkedWordMap|TreeWordMap] [Jaccard|Cosine]");
        System.exit(-1);
    }

    /**
     * The main method of this application.
     *
     * <pre>
     * &gt; java Pastiche dir [LinkedWordMap|TreeWordMap] [Jaccard|Cosine]
     * </pre>
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {

        // Parsing the command line arguments
        
        if (args.length != 3) {
            displayUsageAndExit();
        }

        if ("LinkedWordMap".startsWith(args[1])) {
            factory = new WordMapFactory(WordMapFactory.WORDMAP_IS_LINKED);
        } else if ("TreeWordMap".startsWith(args[1])) {
            factory = new WordMapFactory(WordMapFactory.WORDMAP_IS_TREE);
        } else {
            displayUsageAndExit();
        }

        if ("Jaccard".startsWith(args[2])) {
            similarity = new Jaccard();
            } else if ("Cosine".startsWith(args[2])) {
            similarity = new Cosine();
        } else {
            displayUsageAndExit();
        }

        // Collecting all the files from the specified directory

        LinkedList<String> files;
        files = new LinkedList<String>();

        Path dir = Paths.get(args[0]);

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.txt")) {
            for (Path file: stream) {
                files.addLast(file.toString());
            }
        } catch (IOException | DirectoryIteratorException e) {
            System.err.println(e);
            System.exit(-1);
        }

        // Comparing all the pairs

        long start = System.currentTimeMillis();

        allPairs(files);

        System.out.println((System.currentTimeMillis() - start) + " ms");

    }

}
