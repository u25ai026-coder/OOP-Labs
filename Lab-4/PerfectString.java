import java.util.Scanner;

public class PerfectString {

    public static boolean isPerfect(String str) {

        boolean[] visited = new boolean[26];

        str = str.toLowerCase();

        for (char ch : str.toCharArray()) {

            if (!Character.isLetter(ch))
                continue;

            int index = ch - 'a';

            if (visited[index]) {
                return false; // duplicate found
            }

            visited[index] = true;
        }

        return true;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter string: ");
        String input = sc.nextLine();

        if (isPerfect(input))
            System.out.println("Perfect String ");
        else
            System.out.println("Not Perfect ");

        sc.close();
    }
}