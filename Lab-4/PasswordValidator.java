public class PasswordValidator {

    // function to validate password
    public static boolean isValid(String pass) {

        // Rule 1: length check
        if (pass.length() < 5 || pass.length() > 12)
            return false;

        boolean hasLower = false;
        boolean hasDigit = false;

        // Rule 2: character checks
        for (char ch : pass.toCharArray()) {

            if (Character.isUpperCase(ch))
                return false;   // uppercase not allowed

            if (!Character.isLetterOrDigit(ch))
                return false;   // special char not allowed

            if (Character.isLowerCase(ch))
                hasLower = true;

            if (Character.isDigit(ch))
                hasDigit = true;
        }

        if (!hasLower || !hasDigit)
            return false;

        // Rule 3: immediate repeated pattern check
        int n = pass.length();

        for (int size = 1; size <= n / 2; size++) {
            for (int i = 0; i + 2 * size <= n; i++) {

                String part1 = pass.substring(i, i + size);
                String part2 = pass.substring(i + size, i + 2 * size);

                if (part1.equals(part2))
                    return false;
            }
        }

        return true;
    }

    // main method
    public static void main(String[] args) {

        String[] tests = {
                "abcanan1",
                "abc11se",
                "123sd123",
                "adfasdsdf",
                "Aasdfasd12"
        };

        for (String p : tests) {
            System.out.println(p + " -> " + isValid(p));
        }
    }
}
