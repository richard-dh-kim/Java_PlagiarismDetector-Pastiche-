/** 
*
*An implementation of the interface WordMap using linked elements.
*This class contains 1 inner class (with one method, the constructor), 
*and 7 public methods: the constructor, size, contains, get, update, keys, and counts.
*and 1 private method: addAfter.
*/

public class LinkedWordMap implements WordMap {

    /**
    * Inner class that creates the doubly linked list's nodes.
    **/
    private class DoublyLinkedList {

        //count of the n-gram
        private int count;
        //n-gram
        private String key;
        //references to previous/next nodes
        private DoublyLinkedList previous, next;

        /**
        * Constructor.
        * Creats a new node in the doubly linked list.
        * @param key the n-gram
        * @param count count of the n-gram
        * @param previous reference to the previous node
        * @param next reference to the next node
        **/
        private DoublyLinkedList (String key, int count, DoublyLinkedList previous, DoublyLinkedList next) {
            this.key = key;
            this.count = count;
            this.previous = previous;
            this.next = next;
        }
    }

    //the dummy node that we are starting with
    private final DoublyLinkedList head;

    //size of the doubly linked list. In other words, how many nodes we have.
    private int size;

    /**
    * Constructor
    * Creates the head. The head has key of 'null', count of '0', and its next/previous
    * should point to itself.
    **/
    public LinkedWordMap() {
        head = new DoublyLinkedList (null, 0, null, null);
        head.previous = head.next = head;
        size = 0;
    }

    /**
     * Returns the logical size of this WordMap. That is the number of
     * associations currently stored in it.
     *
     * @return the logical size of this WordMap
     */
    
    public int size() {
        //returns the instance variable 'size'
        return size;
    }

    /**
     * Returns true if and only if this WordMap contains the specified
     * word.
     *
     * @param key the specified word
     * @return true if and only if this WordMap contains the specified word
     * @throws NullPointerException if the value of the parameter is null
     */

    public boolean contains(String key) {

        //throw the error if param is null
        if (key == null) {
            throw new NullPointerException("Key is null."); 
        }

        //make a tail as a pointer, start at the head
        DoublyLinkedList tail = head;

        //run the while loop if tail's key does not equal to param
        while (!key.equals(tail.key)) {

            //return false if we get back to the head (when we finished travelling in a full cirlce)
            if (tail.next == head) {
                return false;
            }

            //move the tail forward
            tail = tail.next;
        }

        return true;
    }

//previous version of contains, did not remove just in case i need to look at it again,
//or in case i get into plagiarism issues.
/**
        if (key == null) {
            throw new NullPointerException("Key is null.");
        }

        DoublyLinkedList tail = head;
        boolean val = false;

        while (!key.equals(tail.key)) {

            if (key.equals(tail.key)) {
                val = true;
                break;
            }


            if (tail.next == head) {
                break;
            }

            tail = tail.next;
        }

        return val;
**/    
    
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
            throw new NullPointerException("Key is null."); 
        }

        //only run the rest of the code if the doubly linked list actually contains the node
        if (contains(key) == false) {
            return 0;
        }

        //make a tail as a pointer, start at the head
        DoublyLinkedList tail = head;

        //run the while loop if tail's key does not equal to param
        while (!key.equals(tail.key)) {

            //move the tail forward
            tail = tail.next;
        }

        return tail.count;
    }

//previous version of get, did not remove just in case i need to look at it again,
//or in case i get into plagiarism issues.
/**
        if (key == null) {
            throw new NullPointerException("Key is null.");
        }

        else {
            DoublyLinkedList tail = head;

            while (!key.equals(tail.key)) {

                if (key.equals(tail.key)) {
                    return tail.count;
                }

                if (tail.next == head) {
                    return 0;
                }

                tail = tail.next;
            }
        }   
        return 0;
**/
    
    /**
     * Increments by 1 the counter associated with the specified
     * word. If the specified word is absent from the data structure,
     * a new association is created.
     *
     * @param key the specified word
     * @throws NullPointerException if the value of the parameter is null
     */

    public void update(String key) {
        //null pointer exception when parameter is null
        if (key == null) {
            throw new NullPointerException("Key is null.");
        }

        //make a tail (pointer) at head.
        DoublyLinkedList tail = head;

        //run the while loop if tail's key does not equal to param
        while (!key.equals(tail.key)) {

            //if next node's key equals to this key, increment counter by 1
            if (key.equals(tail.next.key)) {
                tail.next.count++;
                break;
            }

            //if the key does not exist as a node in the doubly linked list
            if (tail.next == head) {
                //if this doubly linked list is empty, add the node right after head
                if (size == 0) {
                    addAfter(head, key);
                    break;
                }

                tail = tail.next; //locate tail to head
                //find the new node's location regarding alphabetical order
                while(size>0 && tail.next != head && tail.next.key.compareTo(key) < 0) {
                    tail = tail.next;
                }
                //add the new node in the right location by calling the private method addAfter
                addAfter(tail,key);
                break;
            }

            tail = tail.next;
        }

//previous version, did not remove just in case i need to look at it again,
//or in case i get into plagiarism issues.
/**
        if (key == null) {
            throw new NullPointerException("Key is null.");
        }

        DoublyLinkedList tail = head;

        while (!key.equals(tail.key)) {

            if (key.equals(tail.next.key)) {
                tail.next.count++;
                break;
            }

            if (tail.next == head) {
                DoublyLinkedList tail2 = head;

                if (tail2.next == head) {
                    addAfter(tail2, key);
                    break;
                }

                else {
                    boolean flag = true;

                    while (flag == true) {
                        int compareValue = key.compareTo(tail2.next.key);

                        if (compareValue < 0) {
                            addAfter(tail2, key);
                            flag = false;
                            break;
                        }

                        if (compareValue > 0) {
                            tail2 = tail2.next;
                            flag = true;
                        }
                    }

                    if (flag == false) {
                        break;
                    }
                }
            }

            tail = tail.next;
        }
**/
    }

    /**
    * Private helper method that I made.
    * Adds a new node after 'before', using n-gram 'key' provided as params.
    * @param before the node before the location of our insertion
    * @param key the n-gram for the new node
    **/
    private void addAfter(DoublyLinkedList before, String key) {
        //create another pointer after before, which will also be after our new location
        DoublyLinkedList after = before.next;
        //make a new node after before. Give it count 1 because we are making it
        //which means there is one at least
        before.next = new DoublyLinkedList(key, 1, before, after);
        //connect after's previous to the new node
        after.previous = before.next;
        size++;
    }


    /**
     * Returns all the keys (words) of this WordMap using their
     * natural order.
     *
     * @return all the keys (words)
     */
    
    public String[] keys() {

        //pointer starting from the first node
        DoublyLinkedList current = head.next;
        //make a string list
        String[] keyArray = new String[size];
        int i = 0;
        //keep adding key of the node to the list until we went around the full circle
        while(current != head){
          keyArray[i] = current.key;
          i++;
          current = current.next;
        }
        return keyArray;
    }

    /**
     * Returns all the counts associated with keys in this
     * WordMap. The counts are in the same order as that of the keys
     * returned by the method keys().
     *
     * @return all the counts
     */
    
    public Integer[] counts() {

      //pointer starting from the first node
      DoublyLinkedList current = head.next;
      //make a integer list
      Integer[] countsArray = new Integer[size];
      int i = 0;
      //keep adding count of the node to the list until we went around the full circle
      while(current != head){
        countsArray[i] = current.count;
        i++;
        current = current.next;
      }
      return countsArray;
    }
  
}
