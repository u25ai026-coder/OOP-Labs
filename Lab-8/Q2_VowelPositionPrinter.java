import java.util.Scanner;

class VowelProcessor 
{

    private String text;

    // Constructor
    public VowelProcessor(String text) 
    {
        this.text = text.toLowerCase(); // for uniform checking
    }

    // Method to print vowels and their positions
    public void printVowelsWithPositions() 
    {
        System.out.println("Vowels and their positions:");

        for (int i = 0; i < text.length(); i++) 
        {
            char ch = text.charAt(i);

            if (isVowel(ch)) 
            {
                System.out.println(ch + " found at position " + i);
            }
        }
    }

    // Helper method to check vowel
    private boolean isVowel(char ch) 
    {
        return ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u';
    }
}

public class Q2_VowelPositionPrinter 
{

    public static void main(String[] args) 
    {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter a line of text: ");
        String input = sc.nextLine();

        VowelProcessor obj = new VowelProcessor(input);

        obj.printVowelsWithPositions();

        sc.close();
    }
}