package Managers;
import Interfaces.HistoryManager;
import Tasks.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    private Node head;
    private Node tail;
    private  int size = 0;

   private final Map<Integer, Node> nodeData = new HashMap<>();

    public int getSize() {
        return size;
    }

    @Override
   public List<Task> getHistory() {
       return getTasks();
   }

    private List<Task> getTasks() {
        List<Task> tasks = new ArrayList<>();
        if(head != null) {
            Node oldHead = head;
            tasks.add(head.data);
            for (int i = 0; i <= nodeData.size(); i++) {
                if (oldHead.next != null) {
                    oldHead = oldHead.next;
                    if (oldHead.data != null) {
                        tasks.add(oldHead.data);
                    }
                }
            }
        }
        Collections.reverse(tasks);
        return tasks;
    }


   @Override
   public void addTask(Task task) {
       if (task != null) {
           int id = task.getId();
           if(nodeData.containsKey(id)) {
               remove(id);
               linkLast(task);
           } else {
               linkLast(task);
               nodeData.put(id, tail);
               size++;
           }
       }
   }

    private void linkLast(Task task) {
        final Node node = new Node(tail, task, null);
        if (head == null) {
            head = node;
        } else {
            tail.next = node;
        }
        tail = node;
    }


   @Override
   public  void remove(int id){
       Node node = nodeData.get(id);
       removeNode(node);
       nodeData.remove(id);
   }

    private void removeNode(Node node) {
        if (node != null) {
            if (node.next != null && node.prev == null) {  //если в начале
                head = node.next;
                size--;
            }
            if (node.next != null && node.prev != null) {  //если в середине
                node.prev.next = node.next;
                node.next.prev = node.prev;
                size--;
            }
            if (node.next == null && node.prev != null) {  //если в конце
                tail = node.prev;

                size--;
            }
            if (node.next == null && node.prev == null) {  //если единственный элемент
                head = null;
                tail = null;
                size--;
            }
        } else {
            System.out.println("Узел отсуттвует.");
        }
    }

    static class Node {

        public Task data;
        public Node next;
        public Node prev;

        public Node(Node prev, Task data, Node next) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "data=" + data +
                    ", next=" + next +
                    ", prev=" + prev +
                    '}';
        }
    }
}
