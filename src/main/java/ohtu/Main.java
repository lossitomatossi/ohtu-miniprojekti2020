package ohtu;

import ohtu.utilities.DataLoader;
import ohtu.userinterface.TextUI;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException, ParseException {
        String database = "production.db";
        handleArguments(args, database);
        TextUI app = new TextUI("jdbc:sqlite:" + database);
        app.commandLine();
    }

    private static void handleArguments(String[] args, String database) throws SQLException, ClassNotFoundException, ParseException {
        List<String> arguments = Arrays.asList(args);
        if (arguments.contains("demo")) {
            String dataFile = "data/demo/demo_lukuvinkit.txt";
            DataLoader.importDataFromFile(dataFile, database);
        }
    }
}
