import java.util.*;

public class Mastermind {
    private static final String[] COLORS = {"R", "G", "B", "Y", "W", "O"};
    private static final int TRIES = 10;
    private static final int CODE_LENGTH = 4;

    public static void main(String[] args) {
        game();
    }

    private static List<String> generateCode() {
        List<String> code = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < CODE_LENGTH; i++) {
            String color = COLORS[random.nextInt(COLORS.length)];
            code.add(color);
        }

        return code;
    }

    private static List<String> guessCode() {
        Scanner scanner = new Scanner(System.in);
        List<String> guess;

        while (true) {
            System.out.print("Guess: ");
            String input = scanner.nextLine().toUpperCase();
            guess = Arrays.asList(input.split(" "));

            if (guess.size() != CODE_LENGTH) {
                System.out.printf("You must guess %d colors.%n", CODE_LENGTH);
                continue;
            }

            boolean valid = true;
            for (String color : guess) {
                if (!Arrays.asList(COLORS).contains(color)) {
                    System.out.printf("Invalid Color: %s. Try Again%n", color);
                    valid = false;
                    break;
                }
            }

            if (valid) {
                break;
            }
        }

        return guess;
    }

    private static int[] checkCode(List<String> guess, List<String> realCode) {
        Map<String, Integer> colorCounts = new HashMap<>();
        int correctPos = 0;
        int incorrectPos = 0;

        for (String color : realCode) {
            colorCounts.put(color, colorCounts.getOrDefault(color, 0) + 1);
        }

        for (int i = 0; i < guess.size(); i++) {
            String guessColor = guess.get(i);
            String realColor = realCode.get(i);
            if (guessColor.equals(realColor)) {
                correctPos++;
                colorCounts.put(guessColor, colorCounts.get(guessColor) - 1);
            }
        }

        for (String guessColor : guess) {
            if (colorCounts.containsKey(guessColor) && colorCounts.get(guessColor) > 0) {
                incorrectPos++;
                colorCounts.put(guessColor, colorCounts.get(guessColor) - 1);
            }
        }

        return new int[]{correctPos, incorrectPos};
    }

    private static void game() {
        System.out.printf("Welcome to Mastermind%nYou Have %d tries to guess the code...%n", TRIES);
        System.out.print("The valid colors are: ");
        System.out.println(String.join(", ", COLORS));
        List<String> code = generateCode();

        for (int attempts = 1; attempts <= TRIES; attempts++) {
            List<String> guess = guessCode();
            int[] result = checkCode(guess, code);
            int correctPos = result[0];
            int incorrectPos = result[1];

            if (correctPos == CODE_LENGTH) {
                System.out.printf("You guessed the code in %d tries!%n", attempts);
                return;
            }

            System.out.printf("Correct Positions: %d | Incorrect Positions: %d%n", correctPos, incorrectPos);
        }

        System.out.println("You ran out of tries, the code was: " + String.join(" ", code));
    }
}