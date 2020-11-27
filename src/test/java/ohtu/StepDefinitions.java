package ohtu;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.HashMap;
import ohtu.userinterface.UserInterface;
import org.junit.After;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class StepDefinitions {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private BufferedReader br = mock(BufferedReader.class);
    private HashMap<String, String> inputs = new HashMap<>();

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
        inputs.put("commandExit", "exit");
    }

    @After
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Given("command book is selected")
    public void commandBookSelected() throws IOException {
        inputs.put("commandBook", "book");

    }

    @When("user enters book title {string} and writer {string}")
    public void userEntersBookTitleAndWriter(String title, String writer) throws IOException, SQLException {
        when(br.readLine())
                .thenReturn(inputs.get("commandBook"))
                .thenReturn(title)
                .thenReturn(writer)
                .thenReturn(inputs.get("commandExit"));

        runApp();
    }

    @Then("system will respond with {string}")
    public void systemWillRespondWith(String expectedOutput) {
        //For debugging
//        System.setOut(standardOut);
//        System.out.println(outputStreamCaptor);
        assertTrue(outputStreamCaptor.toString().contains(expectedOutput));
    }

    @When("user leaves book title blank")
    public void userLeavesTitleBlank() throws IOException, SQLException {
        when(br.readLine())
                .thenReturn(inputs.get("commandBook"))
                .thenReturn("")
                .thenReturn(inputs.get("commandExit"));

        runApp();

    }

    @When("user enters book title {string} but leaves writer blank")
    public void userEntersBookTitleButLeavesWriterBlank(String title) throws IOException, SQLException {
        when(br.readLine())
                .thenReturn(inputs.get("commandBook"))
                .thenReturn(title)
                .thenReturn("")
                .thenReturn(inputs.get("commandExit"));

        runApp();
    }

    // Helper methods
    private void runApp() throws IOException, SQLException {
        UserInterface app = new UserInterface(br) {
        };
        app.commandLine();
    }

}
