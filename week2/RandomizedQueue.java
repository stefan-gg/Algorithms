import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/*
    Iterator.  Each iterator must return the items in uniformly random order. The order of two or
     more iterators to the same randomized queue must be mutually independent; each iterator must
      maintain its own random order.

    Corner cases.  Throw the specified exception for the following corner cases:

    Throw an IllegalArgumentException if the client calls enqueue() with a null argument.
    Throw a java.util.NoSuchElementException if the client calls either sample() or dequeue()
    when the randomized queue is empty.
    Throw a java.util.NoSuchElementException if the client calls the next() method in the
    iterator when there are no more items to return.
    Throw an UnsupportedOperationException if the client calls the remove() method in the iterator.
*/

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int size;
    private int numOfElements;
    private Item[] items;

    private class ArrayIterator implements Iterator<Item> {
        //        private Deque.Node currentNode = first;
        private final Item[] randomItems = shuffleArray(items);
        private int arrIndex = 0;

        /**
         * @return boolean value
         */
        @Override
        public boolean hasNext() {
            return this.arrIndex < numOfElements && numOfElements > 0;
        }

        /**
         * @return value of the current random item
         */
        @Override
        public Item next() {
            if (hasNext()) {
                return randomItems[this.arrIndex++];
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

        private Item[] shuffleArray(Item[] items) {
            int[] randomIndexes = StdRandom.permutation(numOfElements);
            Item[] shuffledItems = (Item[]) new Object[numOfElements];

            for (int i = 0; i < randomIndexes.length; i++) {
                shuffledItems[i] = items[randomIndexes[i]];
            }

            return shuffledItems;
        }
    }

    // construct an empty randomized queue
    public RandomizedQueue() {
        this.size = 1;
        this.numOfElements = 0;
        items = (Item[]) new Object[this.size];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return numOfElements == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return numOfElements;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null.");
        } else {
            increaseArraySize();
            this.items[this.numOfElements++] = item;
        }
    }

    // remove and return a random item
    public Item dequeue() {
        if (!isEmpty()) {
            int randomIndex = StdRandom.uniformInt(0, this.numOfElements);
            Item randomItem = items[randomIndex];

            decreaseArraySize();

            removeElement(randomIndex);
            numOfElements--;

            return randomItem;
        } else {
            throw new NoSuchElementException("Queue is empty.");
        }
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (!isEmpty()) {
            int randomIndex = StdRandom.uniformInt(0, this.numOfElements);
            return items[randomIndex];
        } else {
            throw new NoSuchElementException("Queue is empty.");
        }
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    /*
     * If the array is full of elements, it will double its size
     */
    private void increaseArraySize() {
        if (this.numOfElements == this.size) {
            Item[] itemsCopy = this.items;

            this.size *= 2;
            this.items = (Item[]) new Object[this.size];

            for (int i = 0; i < itemsCopy.length; i++) this.items[i] = itemsCopy[i];
        }
    }

    /*
     * If the array is 25% full, it will shrink in size by half
     */
    private void decreaseArraySize() {
        if (this.numOfElements <= this.size / 4) {
            Item[] itemsCopy = this.items;

            this.size /= 2;
            this.items = (Item[]) new Object[this.size];

            for (int i = 0; i < items.length; i++) this.items[i] = itemsCopy[i];
        }

    }

    private void removeElement(int elementIndex) {
        Item temp = this.items[elementIndex];
        this.items[elementIndex] = this.items[this.numOfElements - 1];
        this.items[this.numOfElements - 1] = temp;
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<String>();

        randomizedQueue.enqueue("a");
        randomizedQueue.enqueue("aa");
        randomizedQueue.enqueue("aaa");
        randomizedQueue.enqueue("aaaa");

        System.out.println(randomizedQueue.sample());
        System.out.println(randomizedQueue.dequeue());

        System.out.println(randomizedQueue.size());
        System.out.println(randomizedQueue.isEmpty());

        System.out.println(randomizedQueue.dequeue());
        System.out.println(randomizedQueue.dequeue());
        System.out.println(randomizedQueue.dequeue());

        System.out.println(randomizedQueue.numOfElements);

        for (String s :
                randomizedQueue) {
            System.out.println(s);
        }
    }
}