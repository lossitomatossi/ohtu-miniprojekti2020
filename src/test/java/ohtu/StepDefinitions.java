package ohtu;

import ohtu.database.DbCommands;
import ohtu.userinterface.TextUI;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import java.sql.SQLException;

import java.util.HashMap;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class StepDefinitions {

    final PrintStream standardOut = System.out;
    final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    final BufferedReader br = mock(BufferedReader.class);
    final HashMap<String, String> inputs = new HashMap<>();
    final String testDatabase = "cucumber_test.db";
    DbCommands dbc;

    @Before
    public void setUp() throws ClassNotFoundException, SQLException {
        System.setOut(new PrintStream(outputStreamCaptor));
        dbc = new DbCommands("jdbc:sqlite:" + testDatabase);
        inputs.put("commandExit", "exit");
    }

    @After
    public void tearDown() throws SQLException {
        System.setOut(standardOut);
        dbc.closeDbConnection();
        String msg = new File(testDatabase).delete() ? "" + testDatabase + " deleted succesfully" : "Failed to delete " + testDatabase;
//        System.out.println("***\n" + msg + "\n***");
    }

    @Given("command book is selected")
    public void commandBookSelected() throws IOException {
        inputs.put("commandBook", "book");

    }

    @Given("command youtube is selected")
    public void commandYoutubeSelected() throws IOException {
        inputs.put("commandYoutube", "youtube");

    }
    
    @Given("command blog is selected")
    public void commandBlogSelected() throws IOException {
        inputs.put("commandBlog", "blog");
    }
    
    @Given("command movie is selected")
    public void commandMovieSelected() throws IOException {
        inputs.put("commandMovie", "movie");
    }

    @Given("command search is selected")
    public void commandSearchSelected() throws IOException {
        inputs.put("commandSearch", "search");
    }

    @Given("command list is selected")
    public void commandListSelected() throws IOException {
        inputs.put("commandList", "list");
    }
    
    @Given("command delete is selected")
    public void commandDeleteSelected() throws IOException {
        inputs.put("commandDelete", "delete");
    }
    
    @Given("command edit is selected")
    public void commandEditSelected() throws IOException {
        inputs.put("commandEdit", "edit");
    }

    @When("user lists books")
    public void userListsBooks() throws IOException, SQLException, ClassNotFoundException {
        when(br.readLine())
                .thenReturn(inputs.get("commandList"))
                .thenReturn("book")
                .thenReturn(inputs.get("commandExit"));
        runApp();
    }
    
    @When("user lists blogs")
    public void userListsBlogs() throws IOException, SQLException, ClassNotFoundException {
        when(br.readLine())
                .thenReturn(inputs.get("commandList"))
                .thenReturn("blog")
                .thenReturn(inputs.get("commandExit"));
        runApp();
    }
    
    @When("user lists movies")
    public void userListsMovies() throws IOException, SQLException, ClassNotFoundException {
        when(br.readLine())
                .thenReturn(inputs.get("commandList"))
                .thenReturn("movie")
                .thenReturn(inputs.get("commandExit"));
        runApp();
    }

    @When("user lists youtube")
    public void userListsYoutube() throws IOException, SQLException, ClassNotFoundException {
        when(br.readLine())
                .thenReturn(inputs.get("commandList"))
                .thenReturn("youtube")
                .thenReturn(inputs.get("commandExit"));
        runApp();
    }

    @When("user adds book title {string} and writer {string} and user enters category {string} and search term {string}")
    public void userAddsNewBookAndSearchesForIt(String title, String author, String category, String term) throws IOException, SQLException, ClassNotFoundException {
        when(br.readLine())
                .thenReturn(inputs.get("commandBook"))
                .thenReturn(title)
                .thenReturn(author)
                .thenReturn("")
                .thenReturn("")
                .thenReturn("")
                .thenReturn("search")
                .thenReturn(category)
                .thenReturn(term)
                .thenReturn(inputs.get("commandExit"));
        runApp();
    }

    @When("user adds book title {string} and writer {string} and user lists category {string}")
    public void userAddsNewBookAndListsBooks(String title, String author, String category) throws IOException, SQLException, ClassNotFoundException {
        when(br.readLine())
                .thenReturn(inputs.get("commandBook"))
                .thenReturn(title)
                .thenReturn(author)
                .thenReturn("")
                .thenReturn("")
                .thenReturn("")
                .thenReturn("list")
                .thenReturn(category)
                .thenReturn(inputs.get("commandExit"));
        runApp();
    }

    @When("user adds youtube url {string} and title {string} and user enters category {string} and search term {string}")
    public void userAddsNewYoutubeAndSearchesForIt(String url, String title, String category, String term) throws IOException, SQLException, ClassNotFoundException {
        when(br.readLine())
                .thenReturn(inputs.get("commandYoutube"))
                .thenReturn(url)
                .thenReturn(title)
                .thenReturn("")
                .thenReturn("search")
                .thenReturn(category)
                .thenReturn(term)
                .thenReturn(inputs.get("commandExit"));
        runApp();
    }

    @When("user adds youtube url {string} and title {string} and user lists category {string}")
    public void userAddsNewYoutubeAndListsYoutube(String url, String title, String category) throws IOException, SQLException, ClassNotFoundException {
        when(br.readLine())
                .thenReturn(inputs.get("commandYoutube"))
                .thenReturn(url)
                .thenReturn(title)
                .thenReturn("")
                .thenReturn("list")
                .thenReturn(category)
                .thenReturn(inputs.get("commandExit"));
        runApp();
    }

    @When("user searches for an non existent book")
    public void nonExistentBook() throws IOException, SQLException, ClassNotFoundException {
        when(br.readLine())
                .thenReturn(inputs.get("commandSearch"))
                .thenReturn("book")
                .thenReturn("Petteri")
                .thenReturn(inputs.get("commandExit"));
        runApp();
    }

    @When("user searches for an non existent youtube link")
    public void nonExistentYoutubeLink() throws IOException, SQLException, ClassNotFoundException {
        when(br.readLine())
                .thenReturn(inputs.get("commandSearch"))
                .thenReturn("youtube")
                .thenReturn("Petteri")
                .thenReturn(inputs.get("commandExit"));
        runApp();
    }

    

    @Given("book {string} by writer {string} is successfully added on the list")
    public void bookIsSuccessfullyAddedOnTheList(String title, String writer) throws IOException, SQLException, ClassNotFoundException {
        when(br.readLine())
                .thenReturn("book")
                .thenReturn(title)
                .thenReturn(writer)
                .thenReturn("")
                .thenReturn("")
                .thenReturn("")
                .thenReturn(inputs.get("commandExit"));

        runApp();

        assertTrue(outputStreamCaptor.toString().contains("Book added successfully!"));
    }
    
    @Given("blog {string} title {string} is successfully added on the list")
    public void blogIsSuccessfullyAddedOnTheList(String title, String writer) throws IOException, SQLException, ClassNotFoundException {
        when(br.readLine())
                .thenReturn("blog")
                .thenReturn(title)
                .thenReturn(writer)
                .thenReturn("")
                .thenReturn("")
                .thenReturn("")
                .thenReturn(inputs.get("commandExit"));

        runApp();

        assertTrue(outputStreamCaptor.toString().contains("Blog added successfully!"));
    }
    
    @Given("movie {string} is successfully added on the list")
    public void movieIsSuccessfullyAddedOnTheList(String title) throws IOException, SQLException, ClassNotFoundException {
        when(br.readLine())
                .thenReturn("movie")
                .thenReturn(title)
                .thenReturn("")
                .thenReturn("")
                .thenReturn("")
                .thenReturn(inputs.get("commandExit"));

        runApp();

        assertTrue(outputStreamCaptor.toString().contains("Movie added successfully!"));
    }

    @Given("youtube url {string} with title {string} is successfully added on the list")
    public void youtubeLinkIsSuccessfullyAddedOnTheList(String url, String title) throws IOException, SQLException, ClassNotFoundException {
        when(br.readLine())
                .thenReturn("youtube")
                .thenReturn(url)
                .thenReturn(title)
                .thenReturn("")
                .thenReturn(inputs.get("commandExit"));

        runApp();

        assertTrue(outputStreamCaptor.toString().contains("Youtube link added successfully!"));
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
    
    @When("user enters blog url {string} and title {string}")
    public void userEntersBlogTitleAndWriter(String url, String title) throws IOException, SQLException, ClassNotFoundException {
        when(br.readLine())
                .thenReturn(inputs.get("commandBlog"))
                .thenReturn(url)
                .thenReturn(title)
                .thenReturn("")
                .thenReturn("")
                .thenReturn("")
                .thenReturn(inputs.get("commandExit"));

        runApp();
    }
    
    @When("user enters movie title {string}")
    public void userEntersBlogTitleAndWriter(String title) throws IOException, SQLException, ClassNotFoundException {
        when(br.readLine())
                .thenReturn(inputs.get("commandMovie"))
                .thenReturn(title)
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
    
    @When("user leaves movie title blank")
    public void userLeavesMovieTitleBlank() throws IOException, SQLException, ClassNotFoundException {
        when(br.readLine())
                .thenReturn(inputs.get("commandMovie"))
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
    
    @When("user leaves blog url blank")
    public void userLBlogURLBlank() throws IOException, SQLException, ClassNotFoundException {
        when(br.readLine())
                .thenReturn(inputs.get("commandBlog"))
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

    @When("user enters category {string} which do not exists")
    public void userEntersCategoryWhichDoNotExists(String category) throws IOException, SQLException, ClassNotFoundException {
        when(br.readLine())
                .thenReturn(inputs.get("commandSearch"))
                .thenReturn(category)
                .thenReturn(inputs.get("commandExit"));
        runApp();
    }

    @When("user enters category {string} and search term {string}")
    public void userEntersCategoryAndSearchTerm(String category, String searchTerm) throws IOException, SQLException, ClassNotFoundException {
        when(br.readLine())
                .thenReturn(inputs.get("commandSearch"))
                .thenReturn(category)
                .thenReturn(searchTerm)
                .thenReturn(inputs.get("commandExit"));
        runApp();
    }
    
    
    @When("user enters category {string} and delete term {string}")
    public void userEntersCategoryAndDeleteTerm(String category, String deleteTerm) throws IOException, SQLException, ClassNotFoundException {
        when(br.readLine())
                .thenReturn(inputs.get("commandDelete"))
                .thenReturn(category)
                .thenReturn(deleteTerm)
                .thenReturn(inputs.get("commandExit"));
        runApp();
    }
    
    @When("user enters category {string} and edit term {string} and new title {string} and same writer {string}")
    public void userEntersCategoryAndEditTerm(String category, String editTerm, String newTitle, String writer) throws IOException, SQLException, ClassNotFoundException {
        when(br.readLine())
                .thenReturn(inputs.get("commandEdit"))
                .thenReturn(category)
                .thenReturn(editTerm)
                .thenReturn("yes")
                .thenReturn(newTitle)
                .thenReturn(writer)
                .thenReturn("2")
                .thenReturn("234")
                .thenReturn("234")
                .thenReturn(inputs.get("commandExit"));
        runApp();
    }

    @Then("system will respond with {string}")
    public void systemWillRespondWith(String expectedOutput) {
        //For debugging
        System.setOut(standardOut);
        System.out.println("****");
        System.out.println("****");
        System.out.println(outputStreamCaptor.toString());
        System.out.println("****");
        assertTrue(outputStreamCaptor.toString().contains(expectedOutput));

    }
    @Then("system will respond with {string} and {string}")
    public void systemWillRespondWithTwo(String expectedOutput, String expectedOutputTwo) {
        //For debugging
        System.setOut(standardOut);
        System.out.println("****");
        System.out.println("****");
        System.out.println(outputStreamCaptor.toString());
        System.out.println("****");
        assertTrue(outputStreamCaptor.toString().contains(expectedOutput));
        assertTrue(outputStreamCaptor.toString().contains(expectedOutputTwo));

    }
    // Helper methods
    private void runApp() throws IOException, SQLException, ClassNotFoundException {
        TextUI app = new TextUI(br, dbc) {
        };
        app.commandLine();
    }
}
