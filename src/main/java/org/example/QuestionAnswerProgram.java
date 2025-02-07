package org.example;
import java.util.*;

public class QuestionAnswerProgram {

    private static final Map<String, List<String>> questionAnswerMap = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Choose an option:");
            System.out.println("1. Ask a question");
            System.out.println("2. Add a question with answers");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            String input = scanner.nextLine().trim();

            try {
                int choice = Integer.parseInt(input);

                switch (choice) {
                    case 1:
                        askQuestion(scanner);
                        break;
                    case 2:
                        addQuestion(scanner);
                        break;
                    case 3:
                        System.out.println("Exiting the program. Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number (1, 2, or 3).");
            }
        }
    }

    private static void askQuestion(Scanner scanner) {
        System.out.print("Enter your question: ");
        String question = scanner.nextLine().trim().toLowerCase();

        if (!question.endsWith("?")) {
            System.out.println("Invalid format. The question must end with a question mark (?).");
            return;
        }

        // check if question length > 255
        if (question.length() > 255) {
            System.out.println("Question exceeds 255 characters. Please try again.");
            return;
        }

        // debug message to see added question
        System.out.println("Debug: Searching for question -> '" + question + "'");

        // search for question in our map and after it true, search for all answers, if no question in map ,then just default answers
        if (questionAnswerMap.containsKey(question)) {
            List<String> answers = questionAnswerMap.get(question);
            System.out.println("Answers:");
            for (String answer : answers) {
                System.out.println(answer);
            }
        } else {
            System.out.println("the answer to life, universe and everything is 42");
        }
    }

    private static void addQuestion(Scanner scanner) {
        System.out.print("Enter the question and answers in the format <question>? \"<answer1>\" \"<answer2>\" ...: ");
        String input = scanner.nextLine();


        //split with ?
        String[] parts = input.split("\\?", 2);


        if (parts.length != 2) {
            System.out.println("Invalid format. Please use the format <question>? \"<answer1>\" \"<answer2>\" ...");
            return;
        }

        //adding ? and all to lowercase
        String question = (parts[0] + "?").trim().toLowerCase();

        // checking if question length > 255
        if (question.length() > 255) {
            System.out.println("Question exceeds 255 characters. Please try again.");
            return;
        }

        String answersPart = parts[1].trim();

        //checking if answers enclosed in "". It can be question? answer" or question? "answer
        if (!answersPart.startsWith("\"") || !answersPart.endsWith("\"")) {
            System.out.println("Invalid format. Answers must be enclosed in double quotes.");
            return;
        }

        //checking if answer is empty, like ""
        if (answersPart.equals("\"\"")) {
            System.out.println("Invalid format. The answer cannot be empty.");
            return;
        }

        //checking for question? "
        if (answersPart.length() < 2) {
            System.out.println("Invalid format. Answers must be enclosed in double quotes.");
            return;
        }

        String[] answers = answersPart.substring(1, answersPart.length() - 1).split("\"\\s*\"");


        List<String> answerList = new ArrayList<>();

        for (String answer : answers) {
            // checking if answer length > 255
            if (answer.length() > 255) {
                System.out.println("Answer exceeds 255 characters. Please try again.");
                return;
            }
            answerList.add(answer.trim());
        }

        //adding question and answerList to MAP
        questionAnswerMap.put(question, answerList);

        System.out.println("Question and answers added successfully!");

        // debug message to see all added questions to map
        System.out.println("Debug: Current questions in storage -> " + questionAnswerMap.keySet());
    }
}