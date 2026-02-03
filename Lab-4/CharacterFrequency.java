import java.util.Scanner;

public class CharacterFrequency {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter string: ");
        String str = sc.nextLine().toLowerCase();

        System.out.print("Enter n: ");
        int n = sc.nextInt();

        int[] freq = new int[26]; // for a-z

        // count characters
        for (char ch : str.toCharArray()) {
            if (Character.isLetter(ch)) {
                freq[ch - 'a']++;
            }
        }

        System.out.println("Characters exceeding " + n + " times:");

        // print result
        for (int i = 0; i < 26; i++) {
            if (freq[i] > n) {
                char ch = (char) (i + 'a');
                System.out.println(ch + " -> " + freq[i]);
            }
        }

        sc.close();
    }
}