/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package orderqueue;
import java.util.LinkedList;
//import java.util.Queue;

class Order {
    int orderId;
    String customerName;
    int productId;
    int quantity;
    String status;

    public Order(int orderId, String customerName, int productId, int quantity) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.productId = productId;
        this.quantity = quantity;
        this.status = "Pending"; // Initial status when the order is placed
    }
}

class Inventory {
    static int[] productQuantities = new int[100];

    public static void initializeInventory() {
        // Initialize product quantities in the inventory
        // You can load this information from a database or another source
        productQuantities[1] = 50; // Example: Product with ID 1 has 50 available units
        // Initialize quantities for other products
    }

    public static boolean checkInventory(int productId, int quantity) {
        return productQuantities[productId] >= quantity;
    }

    public static void updateInventory(int productId, int quantity) {
        productQuantities[productId] -= quantity;
    }
}

class Queue {
    int front, rear, size;
    int capacity;
    Order[] array;

    public Queue(int capacity) {
        this.capacity = capacity;
        this.front = this.size = 0;
        this.rear = capacity - 1;
        this.array = new Order[this.capacity];
    }

    private void grow() {
        int newCapacity = this.capacity * 2;
        Order[] newArray = new Order[newCapacity];

        // Copy elements to the new array
        for (int i = 0; i < this.capacity; i++) {
            newArray[i] = this.array[(this.front + i) % this.capacity];
        }

        this.array = newArray;
        this.front = 0;
        this.rear = this.capacity - 1;
        this.capacity = newCapacity;
    }

    boolean isFull() {
        return (this.size == this.capacity);
    }

    boolean isEmpty() {
        return (this.size == 0);
    }

    void enqueue(Order order) {
        if (isFull()) {
            grow();
        }

        this.rear = (this.rear + 1) % this.capacity;
        this.array[this.rear] = order;
        this.size = this.size + 1;
        System.out.println("Order " + order.orderId + " enqueued to queue");
    }

    Order dequeue() {
        if (isEmpty()) {
            return null;
        }

        Order order = this.array[this.front];
        this.front = (this.front + 1) % this.capacity;
        this.size = this.size - 1;
        return order;
    }

    Order front() {
        if (isEmpty()) {
            return null;
        }

        return this.array[this.front];
    }

    Order rear() {
        if (isEmpty()) {
            return null;
        }

        return this.array[this.rear];
    }
}

class OrderProcessingSystem {
    Queue orderQueue;

    public OrderProcessingSystem(int capacity) {
        this.orderQueue = new Queue(capacity);
        Inventory.initializeInventory();
    }

    public void placeOrder(Order order) {
        if (Inventory.checkInventory(order.productId, order.quantity)) {
            orderQueue.enqueue(order);
            System.out.println("Order " + order.orderId + " placed successfully.");
        } else {
            System.out.println("Insufficient inventory for order " + order.orderId);
        }
    }

    public void processOrders() {
        while (!orderQueue.isEmpty()) {
            Order currentOrder = orderQueue.dequeue();
            System.out.println("Processing order " + currentOrder.orderId);
            currentOrder.status = "Processed";
            Inventory.updateInventory(currentOrder.productId, currentOrder.quantity);
            System.out.println("Order " + currentOrder.orderId + " processed successfully.");
        }
    }

    public static void main(String[] args) {
        OrderProcessingSystem orderSystem = new OrderProcessingSystem(1000);

        Order order1 = new Order(1, "John Doe", 1, 3);
        Order order2 = new Order(2, "Jane Smith", 2, 2);

        orderSystem.placeOrder(order1);
        orderSystem.placeOrder(order2);

        orderSystem.processOrders();
    }
}

