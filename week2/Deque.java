import java.util.Iterator;
import java.util.NoSuchElementException;

/*
    Dequeue. A double-ended queue or deque (pronounced “deck”) is a generalization of a stack and a
    queue that supports adding and removing items from either the front or the back of the data
    structure. Create a generic data type Deque.

    Corner cases.  Throw the specified exception for the following corner cases:

    Throw an IllegalArgumentException if the client calls either addFirst() or addLast() with a null
    argument.
    Throw a java.util.NoSuchElementException if the client calls either removeFirst() or removeLast
    when the deque is empty.
    Throw a java.util.NoSuchElementException if the client calls the next() method in the iterator
    when there are no more items to return.
    Throw an UnsupportedOperationException if the client calls the remove() method in the iterator.
 */

public class Deque<Item> implements Iterable<Item> {

    private int size;
    private Node first;
    private Node last;

    private class Node {
        private Item item;
        private Node next = null;
        private Node prev = null;

        private Node() {

        }

        private Node(Item item) {
            this.item = item;
//            this.next = node;
        }
    }

    private class LinkedListIterator implements Iterator<Item> {
        private Node currentNode = first;

        /**
         * @return boolean value based on a check if currentNode has value null or not
         */
        @Override
        public boolean hasNext() {
            return currentNode != null;
        }

        /**
         * @return value of the current item
         */
        @Override
        public Item next() {
            if (hasNext()) {
                Item item = currentNode.item;
                currentNode = currentNode.next;

                return item;
            } else {
                throw new NoSuchElementException("There are no more elements.");
            }
        }

        /**
         * @throw new UnsupportedOperationException
         */
        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove method is not implemented.");
        }
    }

    // construct an empty deque
    public Deque() {
        this.size = 0;
        this.first = null;
        this.last = null;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return this.size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return this.size;
    }

    // add the item to the front
    public void addFirst(Item item) {

        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null.");
        } else if (isEmpty()) {
            Node node = new Node(item);
            this.first = node;
            this.last = node;
            this.size++;
        } else if (this.size == 1) {
            Node node = new Node(item);
            Node oldFirstNode = this.first;
            node.next = oldFirstNode;
            this.first = node;
            this.last = oldFirstNode;
            this.last.prev = node;
            this.size++;
        } else {
            Node node = new Node(item);
            Node oldFirstNode = this.first;
            node.next = oldFirstNode;
            this.first = node;
// link node to the previous node
            node.next.prev = node;
            this.size++;
        }
    }

    // add the item to the back
    public void addLast(Item item) {

        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null.");
        } else if (isEmpty()) {
            Node node = new Node(item);
            this.first = node;
            this.last = node;
            this.size++;
        } else {
            Node node = new Node(item);
            Node oldLastNode = this.last;
            oldLastNode.next = node;
            this.last = node;
            this.last.prev = oldLastNode;
            this.size++;
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty.");
        } else {
            Node firstNode = this.first;
            this.first = firstNode.next;
            this.size--;
            if (this.size == 0) this.first = this.last = null;
            return firstNode.item;
        }
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty.");
        } else {
            Node lastNode = this.last;
            Item lastNodeItem = lastNode.item;

            this.last = lastNode.prev;
            lastNode = null;

            if (last != null) this.last.next = null;

            this.size--;
            if (this.size == 0) this.first = this.last = null;
            return lastNodeItem;
        }
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new LinkedListIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<String>();

        deque.addFirst("first");
        deque.addFirst("i am first");
        deque.addFirst("1st");
        deque.addLast("i am last");
        deque.addLast("no this is last");
//        deque.removeLast();
//        deque.removeLast();
//        deque.removeLast();
//        deque.forEach(System.out::println);
        deque.removeFirst();

//        deque.forEach(System.out::println);
        deque.removeFirst();

        deque.forEach(System.out::println);

        deque.removeLast();
//        deque.forEach(System.out::println);
        System.out.println(deque.size());
        System.out.println(deque.isEmpty());


//        deque.iterator().remove();
//        deque.addFirst(null);
//        deque.removeFirst();
//        deque.removeFirst();
//        deque.removeFirst();
//        deque.removeLast();
//
//        deque.forEach(System.out::println);
//        deque.iterator().remove();

    }
}