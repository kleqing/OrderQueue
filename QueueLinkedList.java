package CSD201.Order;

import java.util.Scanner;

public class QueueLinkedList {
    public static Scanner sc = new Scanner(System.in);
    
    public static class Order{
        String nameCustomer; 
        String nameItems;
        int quantity;
        String status;

        public Order(String nameCustomer, String nameItems, int quantity) {
            this.nameCustomer = nameCustomer;
            this.nameItems = nameItems;
            this.quantity = quantity;
            this.status = "wating";
        }
    }
    
    public static class Node {
        Order data;
        Node next;
        
        public Node(Order data) {
            this.data = data;
            this.next = null;
        }
    }
    
    public static class Queue{
        Node head;
        Node tail;
        
        public Queue(){
            head = tail = null;
        }
        
        public void displayList() {
            if(head == null) {          //ckeck Trg H: Empty list
                System.out.println("Empty list!");
            } else {                    //check TH: Not empty list
                Node temp = head;
                while(temp != null) {
                    System.out.printf("%-10s | %-10s | %-10d | %-10s\n", temp.data.nameCustomer, temp.data.nameItems,
                                                                                temp.data.quantity, temp.data.status);
                    //System.out.println(temp.data.nameCustomer +" | "+ temp.data.nameItems +" | "+ temp.data.quantity + " | " + temp.data.status);
                    temp = temp.next;       //Lấy Node tiếp theo
                }
            }
        }
        
        public void addLast(Order newData) {
            Node newNode = new Node(newData);
            if(head == null) {
                head = tail = newNode;
            }else {
                tail.next = newNode;
                tail = newNode;
            }
        }
        
        public void deleteBefore() {    //xóa node ở đầu list
            if(head == null) {
                return;
            } else if(head == tail) {
                head = tail = null;
            } else{
                head = head.next;
            }
        }
        
        public void run(){
            System.out.print(">Enter average order processing time (quantity/time): ");
            int quantum = Integer.parseInt(sc.nextLine());
            Node temp = head;
            int loop = 1;
            int tempQuantum = 0;   //quantum còn dư khi sử lý slot trc
            
            System.out.println("Loop 0:");      //hiển thị dữ liệu ban đầu
            displayList();
            do {
                System.out.printf(">>Loop %d:\n", loop++);
                if(temp.data.quantity > quantum + tempQuantum) { 
                    temp.data.quantity = temp.data.quantity - quantum - tempQuantum;
                    tempQuantum = 0;
                }else if (temp.data.quantity == quantum + tempQuantum){
                    temp.data.quantity = 0;
                    temp.data.status = "Done";
                    temp = temp.next;       //Di chuyển head đến slot tiếp theo
                } else if(temp.data.quantity < quantum + tempQuantum) {
                    tempQuantum = quantum - temp.data.quantity;
                    temp.data.quantity = 0;
                    temp.data.status = "Done";
                    temp = temp.next;       //Di chuyển head đến slot tiếp theo
                }
                displayList();
                System.out.println("==========================================");
            } while (temp != null);
        }
    }
    
    public static void main(String[] args) {
        Queue qu = new Queue();
        
        Order st = new Order("Nam", "cam", 15);
        Order st2 = new Order("Trung", "chanh", 20);
        Order st3 = new Order("Kien", "xoai", 13);
        Order st4 = new Order("Dung", "tv", 5);
        
        qu.addLast(st);
        qu.addLast(st2);
        qu.addLast(st3);
        qu.addLast(st4);
        
        qu.run();
    }
}
