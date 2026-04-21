import java.util.Scanner;

class TextProcessor 
{

    private String text;

    // Constructor
    public TextProcessor(String text) 
    {
        this.text = text.toLowerCase(); // for case-insensitive search
    }

    // Method to find first occurrence of "the"
    public int findFirstOccurrence() 
    {
        return text.indexOf("the");
    }

    // Method to find last occurrence of "the"
    public int findLastOccurrence() 
    {
        return text.lastIndexOf("the");
    }

    // Method to extract substring between first and last occurrence
    public String extractBetween(int first, int last) 
    {
        if (first == -1 || last == -1 || first == last) 
        {
            return null;
        }
        return text.substring(first + 3, last); // +3 to skip "the"
    }
}

public class Q1_FileHandlingTheSubstring 
{

    public static void main(String[] args) 
    {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter a line of text: ");
        String input = sc.nextLine();

        TextProcessor obj = new TextProcessor(input);

        int first = obj.findFirstOccurrence();
        int last = obj.findLastOccurrence();

        System.out.println("First occurrence of 'the': " + first);
        System.out.println("Last occurrence of 'the': " + last);

        String result = obj.extractBetween(first, last);

        if (result != null) 
        {
            System.out.println("Extracted string: " + result);
        } 
        else 
        {
            System.out.println("Cannot extract substring (less than two occurrences of 'the').");
        }

        sc.close();
    }
}
