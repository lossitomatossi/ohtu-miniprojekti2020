package ohtu;

import ohtu.userinterface.UserInterface;

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

import org.junit.After;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class StepDefinitions {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final BufferedReader br = mock(BufferedReader.class);
    private final HashMap<String, String> inputs = new HashMap<>();

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

    @Given("command youtube is selected")
    public void commandYoutubeSelected() throws IOException {
        inputs.put("commandYoutube", "youtube");

    }
    
    @Given("command search is selected")
    public void commandSearchSelected() throws IOException {
        inputs.put("commandSearch", "search");

    }

    @When("user enters book title {string} and writer {string}")
    public void userEntersBookTitleAndWriter(String title, String writer) throws IOException, SQLException, ClassNotFoundException {
        when(br.readLine())
                .thenReturn(inputs.get("commandBook"))
                .thenReturn(title)
                .thenReturn(writer)
                .thenReturn("")
                .thenReturn("")
                .thenReturn("")
                .thenReturn(inputs.get("commandExit"));

        runApp();
    }

    @When("user enters youtube url {string} and title {string}")
    public void userEntersYoutubeURLAndTitle(String url, String title) throws IOException, SQLException, ClassNotFoundException {
        when(br.readLine())
                .thenReturn(inputs.get("commandYoutube"))
                .thenReturn(url)
                .thenReturn(title)
                .thenReturn("")
                .thenReturn(inputs.get("commandExit"));

        runApp();
    }

    @When("user leaves book title blank")
    public void userLeavesTitleBlank() throws IOException, SQLException, ClassNotFoundException {
        when(br.readLine())
                .thenReturn(inputs.get("commandBook"))
                .thenReturn("")
                .thenReturn(inputs.get("commandExit"));

        runApp();
    }

    @When("user leaves youtube url blank")
    public void userLeavesURLBlank() throws IOException, SQLException, ClassNotFoundException {
        when(br.readLine())
                .thenReturn(inputs.get("commandYoutube"))
                .thenReturn("")
                .thenReturn(inputs.get("commandExit"));

        runApp();
    }

    @When("user enters book title {string} but leaves writer blank")
    public void userEntersBookTitleButLeavesWriterBlank(String title) throws IOException, SQLException, ClassNotFoundException {
        when(br.readLine())
                .thenReturn(inputs.get("commandBook"))
                .thenReturn(title)
                .thenReturn("")
                .thenReturn(inputs.get("commandExit"));

        runApp();
    }
    
    @When ("user enters category {string} which do not exists")
    public void userEntersCategoryWhichDoNotExists(String category) throws IOException, SQLException, ClassNotFoundException {
        when(br.readLine())
                .thenReturn(inputs.get("commandSearch"))
                .thenReturn(category)
                .thenReturn(inputs.get("commandExit"));
        runApp();
    }
    
    @When ("user enters category {string} and searchTerm {string}")
    public void userEntersCategoryAndSearchTerm (String category, String searchTerm) throws IOException, SQLException, ClassNotFoundException {
        when(br.readLine())
                .thenReturn(inputs.get("commandSearch"))
                .thenReturn(category)
                .thenReturn(searchTerm)
                .thenReturn(inputs.get("commandExit"));
        runApp();
    }    

    @Then("system will respond with {string}")
    public void systemWillRespondWith(String expectedOutput) {
        //For debugging
        System.setOut(standardOut);
        System.out.println(outputStreamCaptor);
        //*****************************************
        assertTrue(outputStreamCaptor.toString().contains(expectedOutput));
    }

    // Helper methods
    private void runApp() throws IOException, SQLException, ClassNotFoundException {
        UserInterface app = new UserInterface(br) {};
        app.commandLine();
    }

}
