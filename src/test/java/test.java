import org.example.QuestionAnswerProgram;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

public class test {

    // Вспомогательный метод для симуляции пользовательского ввода
    private void provideInput(String data) {
        InputStream inputStream = new ByteArrayInputStream(data.getBytes());
        System.setIn(inputStream);
    }

    // Вспомогательный метод для захвата вывода программы
    private String captureOutput(Runnable code) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        code.run();

        System.setOut(originalOut);
        return outputStream.toString().trim();
    }

    // Тесты для начального выбора (1, 2, 3)
    @Test
    void testInitialChoiceAskQuestion() {
        provideInput("1\nWhat is Peters favorite food?\n3\n");
        String output = captureOutput(() -> QuestionAnswerProgram.main(new String[]{}));

        assertTrue(output.contains("Enter your question:"),
                "Expected prompt for asking a question not found.");
    }

    @Test
    void testInitialChoiceAddQuestion() {
        provideInput("2\nWhat is Peters favorite food? \"Pizza\" \"Spaghetti\"\n3\n");
        String output = captureOutput(() -> QuestionAnswerProgram.main(new String[]{}));

        assertTrue(output.contains("Enter the question and answers"),
                "Expected prompt for adding a question not found.");
    }

    @Test
    void testInitialChoiceExit() {
        provideInput("3\n");
        String output = captureOutput(() -> QuestionAnswerProgram.main(new String[]{}));

        assertTrue(output.contains("Exiting the program. Goodbye!"),
                "Expected exit message not found.");
    }

    // Тесты для функции askQuestion
    @Test
    void testAskQuestionValid() {
        provideInput("2\nWhat is Peters favorite food? \"Pizza\" \"Spaghetti\"\n1\nWhat is Peters favorite food?\n3\n");
        String output = captureOutput(() -> QuestionAnswerProgram.main(new String[]{}));

        assertTrue(output.contains("Answers:"),
                "Expected answers not found.");
        assertTrue(output.contains("Pizza"),
                "Expected answer 'Pizza' not found.");
        assertTrue(output.contains("Spaghetti"),
                "Expected answer 'Spaghetti' not found.");
    }

    @Test
    void testAskQuestionTooLong() {
        String longQuestion = "a".repeat(256);
        provideInput("1\n" + longQuestion + "\n3\n");
        String output = captureOutput(() -> QuestionAnswerProgram.main(new String[]{}));

        assertTrue(output.contains("Question exceeds 255 characters"),
                "Expected error message for long question not found.");
    }

    @Test
    void testAskQuestionDefaultAnswer() {
        provideInput("1\nWhen is Peters birthday?\n3\n");
        String output = captureOutput(() -> QuestionAnswerProgram.main(new String[]{}));

        assertTrue(output.contains("the answer to life, universe and everything is 42"),
                "Expected default answer not found.");
    }

    // Тесты для функции addQuestion
    @Test
    void testAddQuestionInvalidFormat() {
        provideInput("2\nWhat is Peters favorite food Pizza Spaghetti\n3\n");
        String output = captureOutput(() -> QuestionAnswerProgram.main(new String[]{}));

        assertTrue(output.contains("Invalid format"),
                "Expected error message for invalid format not found.");
    }

    @Test
    void testAddQuestionTooLong() {
        String longQuestion = "a".repeat(256) + "? \"Pizza\"";
        provideInput("2\n" + longQuestion + "\n3\n");
        String output = captureOutput(() -> QuestionAnswerProgram.main(new String[]{}));

        assertTrue(output.contains("Question exceeds 255 characters"),
                "Expected error message for long question not found.");
    }

    @Test
    void testAddQuestionUnclosedQuotes() {
        provideInput("2\nWhat is Peters favorite food? \"Pizza Spaghetti\n3\n");
        String output = captureOutput(() -> QuestionAnswerProgram.main(new String[]{}));

        assertTrue(output.contains("Answers must be enclosed in double quotes"),
                "Expected error message for unclosed quotes not found.");
    }

    @Test
    void testAddQuestionEmptyQuotes() {
        provideInput("2\nWhat is Peters favorite food? \"\"\n3\n");
        String output = captureOutput(() -> QuestionAnswerProgram.main(new String[]{}));

        assertTrue(output.contains("The answer cannot be empty"),
                "Expected error message for empty quotes not found.");
    }

    @Test
    void testAddQuestionAnswerTooLong() {
        String longAnswer = "a".repeat(256);
        provideInput("2\nWhat is Peters favorite food? \"" + longAnswer + "\"\n3\n");
        String output = captureOutput(() -> QuestionAnswerProgram.main(new String[]{}));

        assertTrue(output.contains("Answer exceeds 255 characters"),
                "Expected error message for long answer not found.");
    }

    @Test
    void testAddQuestionSuccess() {
        provideInput("2\nWhat is Peters favorite food? \"Pizza\" \"Spaghetti\"\n3\n");
        String output = captureOutput(() -> QuestionAnswerProgram.main(new String[]{}));

        assertTrue(output.contains("Question and answers added successfully"),
                "Expected success message not found.");
        assertTrue(output.contains("Debug: Current questions in storage -> [what is peters favorite food?]"),
                "Expected question not found in storage.");
    }
}