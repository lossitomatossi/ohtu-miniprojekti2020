package ohtu;

import ohtu.userinterface.UserInterface;

import java.io.IOException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        UserInterface app = new UserInterface("jdbc:sqlite:production.db");
        app.commandLine();
    }
}
