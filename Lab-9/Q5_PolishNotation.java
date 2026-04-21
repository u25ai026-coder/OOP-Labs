import java.util.Stack;

class PolishConverter extends Thread
{
    String postfix;
    String result = "";

    PolishConverter(String postfix)
    {
        this.postfix = postfix;
    }

    public void run()
    {
        Stack<String> stack = new Stack<>();

        for (int i = 0; i < postfix.length(); i++)
        {
            char ch = postfix.charAt(i);

            if (Character.isLetter(ch))
            {
                stack.push(String.valueOf(ch));
            }
            else
            {
                String b = stack.pop();
                String a = stack.pop();
                stack.push(a + ch + b);
            }
        }

        result = stack.pop();
    }
}

class PrintResult extends Thread
{
    PolishConverter converter;

    PrintResult(PolishConverter converter)
    {
        this.converter = converter;
    }

    public void run()
    {
        try
        {
            converter.join();
        }
        catch (InterruptedException e) {}

        System.out.println("Original Expression: " + converter.result);
    }
}

public class Q5_PolishNotation
{
    public static void main(String[] args) throws InterruptedException
    {
        String postfix = "AB*CD/+";
        System.out.println("Postfix (Polish Notation): " + postfix);

        PolishConverter converter = new PolishConverter(postfix);
        PrintResult printer = new PrintResult(converter);

        converter.start();
        printer.start();

        printer.join();
    }
}
