import java.util.Random;
import java.util.Scanner;

class Player extends Thread
{
    String playerName;
    int inputNumber;
    int randomNumber;
    static int totalPoints1 = 0;
    static int totalPoints2 = 0;

    Player(String name, int input)
    {
        this.playerName = name;
        this.inputNumber = input;
    }

    public void run()
    {
        Random rand = new Random();
        randomNumber = rand.nextInt(100) + 1;
        System.out.println(playerName + " entered: " + inputNumber + " | Random number assigned: " + randomNumber);
    }
}

public class Q4_NumberGame
{
    public static void main(String[] args) throws InterruptedException
    {
        Scanner sc = new Scanner(System.in);

        System.out.print("Player 1, enter any integer: ");
        int input1 = sc.nextInt();

        System.out.print("Player 2, enter any integer: ");
        int input2 = sc.nextInt();

        Player p1 = new Player("Player 1", input1);
        Player p2 = new Player("Player 2", input2);

        p1.start();
        p2.start();

        p1.join();
        p2.join();

        System.out.println();

        if (p1.randomNumber > p2.randomNumber)
        {
            int points = p1.randomNumber - p2.randomNumber;
            System.out.println("Player 1 wins! Points earned: " + points);
        }
        else if (p2.randomNumber > p1.randomNumber)
        {
            int points = p2.randomNumber - p1.randomNumber;
            System.out.println("Player 2 wins! Points earned: " + points);
        }
        else
        {
            System.out.println("It's a tie! No points awarded.");
        }

        sc.close();
    }
}
