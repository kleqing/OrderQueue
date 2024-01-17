/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package orderqueue;

import java.util.LinkedList;
import java.util.Queue;

class OrderQueue {
    int orderId;
    //String customerName;
    int productId;
    int quantity;
    //String status;

    public OrderQueue(int orderId, int productId, int quantity) {
        this.orderId = orderId;
        //this.customerName = customerName;
        this.productId = productId;
        this.quantity = quantity;
        //this.status = "Pending"; 
    }
}

class Inventory {
    
    static int[] productQuantities = new int[100];

    public static void initializeInventory() {
        productQuantities[1] = 50; 
    }

    public static boolean checkInventory(int productId, int quantity) {
        return productQuantities[productId] >= quantity;
    }

    public static void updateInventory(int productId, int quantity) {
        productQuantities[productId] -= quantity;
    }
}

class OrderQueueProcessingSystem {
    Queue<OrderQueue> orderQueue;

    public OrderQueueProcessingSystem() {
        this.orderQueue = new LinkedList<>();
        Inventory.initializeInventory();
    }
    // Enqueue
    public void placeOrderQueue(OrderQueue order) {
        if (Inventory.checkInventory(order.productId, order.quantity)) {
            //check xem co san phan + so luong con du
            orderQueue.add(order); // -> add
            System.out.println("Added.");
        } else {
            System.out.println("Insufficient inventory for order " + order.orderId);
        }
    }
    // Dequeue
    public void processOrderQueues() {
        while (!orderQueue.isEmpty()) { //Don hang empty
            OrderQueue currentOrderQueue = orderQueue.poll(); //poll() = xoa head (front)
            System.out.println("Processing order " + currentOrderQueue.orderId);
            //currentOrderQueue.status = "Processed";
            Inventory.updateInventory(currentOrderQueue.productId, currentOrderQueue.quantity);
            System.out.println("OrderQueue " + currentOrderQueue.orderId + " processed successfully.");
        }
    }

    public static void main(String[] args) {
        OrderQueueProcessingSystem orderSystem = new OrderQueueProcessingSystem();

        OrderQueue order1 = new OrderQueue(1, 1, 3);
        OrderQueue order2 = new OrderQueue(2, 2, 2);

        orderSystem.placeOrderQueue(order1);
        orderSystem.placeOrderQueue(order2);

        orderSystem.processOrderQueues();
    }
}

