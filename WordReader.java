/**
 * An objet that reads and stores the content of file. It posses a
 * method that returns an iterator on the content (a String).
 *
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.BufferedReader;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class WordReader {

    private class WordIterator implements Iterator<String>{

        //counts how many times iterated
        private int counter;
        //size of the n-grams
        private int size;

        /**returns if there is a next n-gram
        *  @return boolean. True if there is a next n-gram, false if not.
        **/
        public boolean hasNext() {
            return !(counter + size > content.length());
        }

        /**returns the next n-gram
        *  @return String. Returns the next n-gram in strings.
        **/
        public String next() {
            String str = content.substring(counter, counter+size);
            counter++;
            return str;
        }

        /**
        *   Constructor
        *   @param size the size of the n-gram one wishes to produce
        */
        public WordIterator(int size) {
            this.size = size;
            counter = 0;
        }
    }

    // The content of the file that was read
    
    private String content;

    /**
     * When an object of the class WordReader is created, this
     * constructor reads the content the file specified by the
     * parameter fileName.
     *
     * @param fileName the specified file
     * @throws FileNotFoundException if the file could not be found
     * @throws IOException if there is an error reading the content of the file
     */
    
    public WordReader(String fileName) throws FileNotFoundException, IOException {
        this(fileName, true);
    }
    
    /**
     * When an object of the class WordReader is created, this
     * constructor reads the content the file specified by the
     * parameter fileName.
     *
     * @param fileName the specified file
     * @param caseSensitive if the value is false, the content is transformed to lower case letters
     * @throws FileNotFoundException if the file could not be found
     * @throws IOException if there is an error reading the content of the file
     */
    
    public WordReader(String fileName, boolean caseSensitive) throws FileNotFoundException, IOException {

        if (fileName == null) {
            throw new NullPointerException();
        }
        
        InputStreamReader input;
        input =  new InputStreamReader(new FileInputStream(fileName), "UTF-8");

        BufferedReader reader;
        reader = new BufferedReader(input);

        StringBuilder buffer;
        buffer = new StringBuilder();

        String line;

        while ((line = reader.readLine()) != null) {
            if (! caseSensitive) {
                line = line.toLowerCase();
            }
            buffer.append(line);
        }
        
        reader.close();

        content = buffer.toString();
    }

    /**
     * Removes all the blank spaces from the content of the text.
     */
    
    public void removeAllBlankCharacters() {
        content = content.replaceAll("\\p{Blank}","");
    }

    /**
     * Returns an iterator over the content in the text.
     *
     * @param size the size of the n-grams to be returned by the method of the iterator
     * @return an iterator over the content in the text
     */
    
    public Iterator<String> iterator(int size) {

        //Assignmenet sheet states the size of n-gram must be between 2 and length of the content
        if (size<2 || size>content.length()) {
            throw new IllegalArgumentException("Iterator size error, size should be between 2 and length of the content.");
        }

        //make an iterator
        WordIterator iter;
        //pass the size
        iter = new WordIterator(size);
        return iter;
    }

}