package ohtu;

import java.io.File;
import java.io.FileNotFoundException;
import ohtu.userinterface.UserInterface;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException, ParseException {
//        UserInterface app = new UserInterface("jdbc:sqlite:production.db");
//        app.commandLine();
        initializeDemoDatabase();
    }

    private static void initializeDemoDatabase() throws SQLException, ClassNotFoundException, ParseException {

        String demodatabase = "demo_database.db";

        try {
            File oldDemoDatabase = new File(demodatabase);
            String msg = oldDemoDatabase.delete() ? "Old demo database deleted" : "Failed to delete old demo database";
            System.out.println(msg);
        } catch (Exception ignored) {
            System.out.println("Error when deleting old demo database");
        }

        DbCommands dbc = new DbCommands("jdbc:sqlite:" + demodatabase);

        try {
            Scanner reader = new Scanner(new File("data/demo_lukuvinkit.txt"));
            int lineNumber = 1;
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            while (reader.hasNextLine()) {
                String[] inputs = reader.nextLine().split(";");
                System.out.println(Arrays.toString(inputs));
                switch (inputs[0]) {
                    case "book":
                        dbc.add(new Book(inputs[1], inputs[2], Integer.parseInt(inputs[3]), Integer.parseInt(inputs[4]), inputs[5]));
                        break;
                    case "youtube":
                        dbc.add(new Youtube(inputs[1], inputs[2], inputs[3]));
                        break;
                    case "blog":
                        dbc.add(new Blog(inputs[1], inputs[2], inputs[3], new Date(formatter.parse(inputs[4]).getTime())));
                        break;
                    case "movie":
                        dbc.add(new Movie(inputs[1], inputs[2], Integer.parseInt(inputs[3]), Integer.parseInt(inputs[4])));
                        break;
                    default:
                        System.out.println("Line " + lineNumber + ": Unknown format");
                }
                lineNumber++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occured while reading file");
            e.printStackTrace();
        }
    }

}
