import java.util.Scanner;

class TextFormatter
{
    private String text;

    // Constructor
    public TextFormatter(String text)
    {
        this.text = text;
    }

    // Method to capitalize first letter of each word
    public String capitalizeWords()
    {
        String[] words = text.split(" ");
        StringBuilder result = new StringBuilder();

        for (String word : words)
        {
            if (word.length() > 0)
            {
                String first = word.substring(0, 1).toUpperCase();
                String rest = word.substring(1);

                result.append(first).append(rest).append(" ");
            }
        }

        return result.toString().trim();
    }
}

public class Q4_CapitalizeWords
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter a line of text: ");
        String input = sc.nextLine();

        TextFormatter obj = new TextFormatter(input);

        String output = obj.capitalizeWords();

        System.out.println("Formatted String:");
        System.out.println(output);

        sc.close();
    }
}