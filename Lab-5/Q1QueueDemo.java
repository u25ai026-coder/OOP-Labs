// Q1QueueDemo.java

import java.util.Scanner;

// Interface
interface QueueImpl {
    void insert(int value);
    void delete();
    void display();
}

// Class implementing interface
class QueueDemo implements QueueImpl {

    int[] queue = new int[10];
    int front = -1;
    int rear = -1;

    // Insert method
    public void insert(int value) {
        if (rear == 9) {
            System.out.println("Queue Overflow! Cannot insert.");
            return;
        }

        if (front == -1) {
            front = 0;
        }

        rear++;
        queue[rear] = value;
        System.out.println(value + " inserted into queue.");
    }

    // Delete method
    public void delete() {
        if (front == -1 || front > rear) {
            System.out.println("Queue Underflow! Cannot delete.");
            return;
        }

        System.out.println(queue[front] + " deleted from queue.");
        front++;
    }

    // Display method
    public void display() {
        if (front == -1 || front > rear) {
            System.out.println("Queue is empty.");
            return;
        }

        System.out.print("Queue elements: ");
        for (int i = front; i <= rear; i++) {
            System.out.print(queue[i] + " ");
        }
        System.out.println();
    }

    // Main method
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        QueueDemo q = new QueueDemo();
        int choice, value;

        do {
            System.out.println("\n--- Queue Menu ---");
            System.out.println("1. Insert");
            System.out.println("2. Delete");
            System.out.println("3. Display");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter value to insert: ");
                    value = sc.nextInt();
                    q.insert(value);
                    break;

                case 2:
                    q.delete();
                    break;

                case 3:
                    q.display();
                    break;

                case 4:
                    System.out.println("Exiting program...");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 4);

        sc.close();
    }
}
