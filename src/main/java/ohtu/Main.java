package ohtu;

import ohtu.userinterface.UserInterface;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException, ParseException {
        String database = "production.db";
        String dataFile = "data/demo/demo_lukuvinkit.txt";
        List<String> arguments = Arrays.asList(args);
        if (arguments.contains("demo")) {
            DataLoader.importDataFromFile(dataFile, database);
        }
        UserInterface app = new UserInterface("jdbc:sqlite:" + database);
        app.commandLine();
    }
}
