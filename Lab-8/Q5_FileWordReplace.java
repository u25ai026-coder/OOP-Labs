import java.io.*;

class FileProcessor
{
    private String fileName;

    // Constructor
    public FileProcessor(String fileName)
    {
        this.fileName = fileName;
    }

    // Method to read file and replace words
    public void processFile()
    {
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;

            System.out.println("Modified Content:\n");

            while ((line = br.readLine()) != null)
            {
                // Replace "his" with "her"
                line = line.replaceAll("\\bhis\\b", "her");

                System.out.println(line);
            }

            br.close();
        }
        catch (IOException e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

public class Q5_FileWordReplace
{
    public static void main(String[] args)
    {
        FileProcessor obj = new FileProcessor("sdj.txt");

        obj.processFile();
    }
}