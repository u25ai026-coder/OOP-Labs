enum Result
{
    CORRECT, WRONG, NOT_ANSWERED
}

public class Q7_Quiz
{
    public static void main(String[] args)
    {
        char[] correctAns = {'C','A','B','D'};

        int correctCount = 0;
        int wrongCount = 0;

        System.out.println("QUESTION\tSUBMITTED\tCORRECT\tRESULT");

        for(int i = 0; i < 4; i++)
        {
            char submitted;

            if(i < args.length)
                submitted = args[i].charAt(0);
            else
                submitted = 'X';

            Result res;

            if(submitted == 'X')
            {
                res = Result.NOT_ANSWERED;
            }
            else if(submitted == correctAns[i])
            {
                res = Result.CORRECT;
                correctCount++;
            }
            else
            {
                res = Result.WRONG;
                wrongCount++;
            }

            System.out.println((i+1) + "\t\t" + submitted + "\t\t" + correctAns[i] + "\t" + res);
        }

        System.out.println("\nNo. of correct answers: " + correctCount);
        System.out.println("No. of wrong answers: " + wrongCount);

        if(correctCount >= 2)
            System.out.println("The candidate passed.");
        else
            System.out.println("The candidate failed.");
    }
}