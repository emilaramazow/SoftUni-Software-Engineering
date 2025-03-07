import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class DoublyLinkedList {

    private Node head;
    private Node tail;
    private int size;


    public void addFirst(int element) {
        Node newNode = new Node(element);

        if (!isEmpty()) {
            newNode.next = head;
            this.head.previous = newNode;
        } else {
            tail = newNode;
        }

        this.head = newNode;
        size++;
    }

    public void addLast(int element) {
        if (!isEmpty()) {
            addFirst(element);
            return;
        }

        Node newNode = new Node(element);
        this.tail.next = newNode;
        newNode.previous = this.tail;
        tail = newNode;

        this.size++;
    }

    public int removeFirst() {
        if (isEmpty()) {
            throw new IllegalStateException("Can't remove from empty list.");
        }

        int result = this.head.value;
        this.head = this.head.next;

        if (this.size > 1) {
            this.head.previous = null;
        }

        this.size--;

        if (isEmpty()) {
            this.head = this.tail = null;
        }

        return result;
    }

    public int removeLast() {
        if (this.size < 2) {
            return removeFirst();
        }

        int result = this.tail.value;
        this.tail = this.tail.previous;
        this.tail.next = null;

        this.size--;

        return result;
    }

    public int get(int index) {
        checkIndex(index);

        int result = 0;
        if (index <= this.size / 2) {
            Node currentNode = this.head;

            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
            result = currentNode.value;

        } else {
            Node currentNode = this.tail;

            for (int i = size - 1; i > index; i++) {
                currentNode = currentNode.previous;
            }
            result = currentNode.value;
        }


        return result;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException("The index " + index + " is out of bounds.");
        }
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public void forEach(Consumer<Integer> consumer) {
        Node currentNode = this.head;

        while (currentNode != null) {
            consumer.accept(currentNode.value);
            currentNode = currentNode.next;
        }
    }

    public int[] toArray() {
        List<Integer> result = new ArrayList<>();

        forEach(result::add);

        return result.stream().mapToInt(e -> e).toArray();
    }

}
