package ohtu;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class DataLoader {

    public static void importDataFromFile(String dataFile, String database) throws ParseException, SQLException, ClassNotFoundException {
        DbCommands dbc = new DbCommands("jdbc:sqlite:" + database);

        ArrayList<Object> recommendations = recommendations(readFile(dataFile));

        for (Object lukuvinkki : recommendations) {
            if (!dbc.contains(lukuvinkki)) {
                dbc.add(lukuvinkki);
            }
        }
    }

    private static ArrayList<String[]> readFile(String fileName) {
        ArrayList<String[]> lines = new ArrayList<>();
        try {
            Scanner reader = new Scanner(new File(fileName));
            while (reader.hasNextLine()) {
                lines.add(reader.nextLine().split(";"));
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occured while reading file");
            e.printStackTrace();
        }

        return lines;
    }

    private static ArrayList<Object> recommendations(ArrayList<String[]> lines) throws ParseException {
        ArrayList<Object> lukuvinkit = new ArrayList<>();
        Object lukuvinkki;
        for (String[] inputs : lines) {
            lukuvinkki = parseRecommendation(inputs);
            if (lukuvinkki != null) {
                lukuvinkit.add(lukuvinkki);
            }
        }

        return lukuvinkit;
    }

    private static Object parseRecommendation(String[] inputs) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        switch (inputs[0]) {
            case "book":
                return new Book(inputs[1], inputs[2], Integer.parseInt(inputs[3]), Integer.parseInt(inputs[4]), inputs[5]);
            case "youtube":
                return new Youtube(inputs[1], inputs[2], inputs[3]);
            case "blog":
                return new Blog(inputs[1], inputs[2], inputs[3], new Date(formatter.parse(inputs[4]).getTime()));
            case "movie":
                return new Movie(inputs[1], inputs[2], Integer.parseInt(inputs[3]), Integer.parseInt(inputs[4]));
            default:
                return null;
        }
    }

    private static void deleteDatabase(String database) {
        try {
            File db = new File(database);
            String msg = db.delete() ? "Database " + database + " deleted" : "Failed to delete database " + database;
            System.out.println(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
