// Q7QuizGrader.java

// Enum to represent result
enum Result {
    CORRECT,
    WRONG,
    UNANSWERED
}

public class Q7QuizGrader {

    public static void main(String[] args) {

        // Correct answers array
        char[] correctAnswers = {'C','A','B','D','B','C','C','A'};

        int correctCount = 0;
        int wrongCount = 0;
        int unansweredCount = 0;

        System.out.println("QUESTION  SUBMITTED  CORRECT  RESULT");

        for (int i = 0; i < correctAnswers.length; i++) {

            char submitted;

            // If user gives fewer than 8 answers
            if (i < args.length) {
                submitted = args[i].toUpperCase().charAt(0);
            } else {
                submitted = 'X';
            }

            Result result;

            if (submitted == 'X') {
                result = Result.UNANSWERED;
                unansweredCount++;
            }
            else if (submitted == correctAnswers[i]) {
                result = Result.CORRECT;
                correctCount++;
            }
            else {
                result = Result.WRONG;
                wrongCount++;
            }

            System.out.println((i+1) + "         " + submitted + 
                               "          " + correctAnswers[i] + 
                               "       " + result);
        }

        System.out.println("\nNo. of correct answers: " + correctCount);
        System.out.println("No. of wrong answers: " + wrongCount);
        System.out.println("No. of questions unanswered: " + unansweredCount);

        // Pass condition
        if (correctCount >= 5) {
            System.out.println("Result: PASS");
        } else {
            System.out.println("Result: FAIL");
        }
    }
}
