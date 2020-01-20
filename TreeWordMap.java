/** 
*
*A Binary Search Tree implementation of the interface WordMap.
*This class contains 1 inner class (with one method, the constructor), 
*and 7 public methods: the constructor, size, contains, get, update, keys, and counts.
*and 3 private methods: add, preorderTraversal, and preorderTraversalInt.
*/

public class TreeWordMap implements WordMap {

    //Inner class that creates the binary tree's nodes.
    private static class Elem {

        //n-gram
        private String key;
        //count of the n-gram
        private int count;
        //references to left/right nodes
        private Elem left, right;

        /**
        * Constructor.
        * Creats a new node in the tree.
        * @param key the n-gram
        **/
        private Elem(String key) {
            this.key = key;
            count = 1;
        }

    }

    //root of the tree
    private Elem root;
    private int size = 0;
    private String[] keyArray;
    private Integer[] countsArray;
    private int counter = 0;

    /**
     * Returns true if and only if this WordMap contains the specified
     * word.
     *
     * @param key the specified word
     * @return true if and only if this WordMap contains the specified key
     * @throws NullPointerException if the value of the parameter is null
     */

    public boolean contains(String key) {

        //throw the error if param is null
        if (key == null) {
            throw new NullPointerException();
        }

        boolean found = false;
        Elem current = root;
        while (! found && current != null) {
            int test = key.compareTo(current.key);
            if (test == 0) {
                found = true;
            } else if (test < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return found;
    }

    /**
     * Increments by 1 the counter associated with the specified
     * word. If the specified word is absent from the data structure,
     * a new association is created.
     *
     * @param key the specified word
     * @throws NullPointerException if the value of the parameter is null
     */

    public void update(String key) {

        //throw the error if param is null
        if(key == null){
          throw new IllegalArgumentException("Key is Null");
        }
        else{
          if(size == 0){
            root = new Elem(key);
            root.left = root.right = null;
            size++;
          }
          else if(contains(key)){
            Elem current = root;
            while (current != null) {
                int test = key.compareTo(current.key);
                if (key.equals(current.key)) {
                    current.count++;
                    break;
                } else if (test < 0) {
                    current = current.left;
                } else {
                    current = current.right;
                }
            }
          }
          else{
            Elem current = root;
            add(key, current);
          }
        }
    }

    /**
    * Private helper method.
    * Adds a new node in the right location, using current location 'current', 
    * and n-gram 'key' provided as params.
    * @param key the n-gram for the new node
    * @param current the current node position
    **/
    private void add(String key, Elem current){

      int test = key.compareTo(current.key);
      if(test < 0){
        if(current.left == null){
          current.left = new Elem(key);
          size++;
        }
        else{
          add(key, current.left);
        }
      }
      else{
        if(current.right == null){
          current.right = new Elem(key);
          size++;
        }
        else{
          add(key, current.right);

        }
      }
    }

    /**
     * Returns the count associated with the specified word or 0 if
     * the word is absent.
     *
     * @param key the specified word
     * @return the count associated with the specified word or 0 if absent
     * @throws NullPointerException if the value of the parameter is null
     */

    public int get(String key) {

        //throw the error if param is null
        if (key == null) {
            throw new NullPointerException();
        }

        Elem current = root;
        while (current != null) {
            int test = key.compareTo(current.key);
            if (test == 0) {
                return current.count;
            } else if (test < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        return 0;
    }

    /**
     * Returns the logical size of this WordMap. That is the number of
     * associations currently stored in it.
     *
     * @return the logical size of this WordMap
     */

    public int size() {
        return size;
    }

    /**
     * Returns all the keys (words) of this WordMap using their
     * natural order.
     *
     * @return all the keys (words)
     */

    public String[] keys() {
      //System.out.println(size);
      keyArray = new String[size];
      Elem current = root;
      counter = 0;
      preorderTraversal(current);
      return keyArray;
    }

    /**
    * Private helper recursive method.
    * Traverse the tree in preorder, using current location 'current'.
    * Used for keys in the node.
    * @param current the current node position
    **/
    private void preorderTraversal(Elem current){

      if(current == null){
        return;
      }

      preorderTraversal(current.left);
      //System.out.println(current.key);
      keyArray[counter] = current.key;
      //System.out.println(keyArray[counter]);
      counter++;
      //System.out.println(current.key);
      preorderTraversal(current.right);
    }
    /**
     * Returns all the counts associated with keys in this
     * WordMap. The counts are in the same order as that of the keys
     * returned by the method keys().
     *
     * @return all the counts
     */

    public Integer[] counts() {
      countsArray = new Integer[size];
      Elem current = root;
      counter = 0;
      preorderTraversalInt(current);
      return countsArray;
    }

    /**
    * Private helper recursive method.
    * Traverse the tree in preorder, using current location 'current'.
    * Used for counts in the node.
    * @param current the current node position
    **/
    private void preorderTraversalInt(Elem current){
      if(current == null){
        return;
      }
      preorderTraversalInt(current.left);
      countsArray[counter] = current.count;
      counter++;
      preorderTraversalInt(current.right);
    }
}
