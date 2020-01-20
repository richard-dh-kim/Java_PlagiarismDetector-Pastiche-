/**
 * An abstract data type associating a string and a counter.
 *
 */

public interface WordMap {

    /**
     * Returns true if and only if this WordMap contains the specified
     * word.
     *
     * @param word the specified word
     * @return true if and only if this WordMap contains the specified word
     * @throws NullPointerException if the value of the parameter is null
     */
    
    boolean contains(String word);
        
    
    /**
     * Increments by 1 the counter associated with the specified
     * word. If the specified word is absent from the data structure,
     * a new association is created.
     *
     * @param word the specified word
     * @throws NullPointerException if the value of the parameter is null
     */
    
    void update(String word);
    
    /**
     * Returns the count associated with the specified word or 0 if
     * the word is absent.
     *
     * @param word the specified word
     * @return the count associated with the specified word or 0 if absent
     * @throws NullPointerException if the value of the parameter is null
     */
    
    int get(String word);
    
    /**
     * Returns the logical size of this WordMap. That is the number of
     * associations currently stored in it.
     *
     * @return the logical size of this WordMap
     */
    
    int size();
    
    /**
     * Returns all the keys (words) of this WordMap using their
     * natural order.
     *
     * @return all the keys (words)
     */
    
    String[] keys();
    
    /**
     * Returns all the counts associated with keys in this
     * WordMap. The counts are in the same order as that of the keys
     * returned by the method keys().
     *
     * @return all the counts
     */
    
    Integer[] counts();

}
