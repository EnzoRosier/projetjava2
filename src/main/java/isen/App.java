package isen;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;

import isen.db.daos.DataSourceFactory;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException, Exception {
        DataSourceFactory.setConnectionUrl("jdbc:sqlite:sqlite.db");
        Connection connection = DataSourceFactory.getConnection();
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS person (\r\n" + //
                        "    idperson INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, \r\n" + //
                        "    lastname VARCHAR(45) NOT NULL,  \r\n" + //
                        "    firstname VARCHAR(45) NOT NULL,\r\n" + //
                        "    nickname VARCHAR(45) NOT NULL,\r\n" + //
                        "    phone_number VARCHAR(15) NULL,\r\n" + //
                        "    address VARCHAR(200) NULL,\r\n" + //
                        "    email_address VARCHAR(150) NULL,\r\n" + //
                        "    birth_date DATE NULL);");

        stmt.executeUpdate("DELETE FROM person");
        stmt.executeUpdate(
                "INSERT INTO person(idperson,lastname, firstname, nickname, phone_number, address, email_address, birth_date) "
                        + "VALUES (1, 'Bib', 'Bob', 'bobibob', '0612121212', 'Rue yahoo', 'EZEZ@2EZE.com', '2015-12-12 12:00:00.000')");
        stmt.executeUpdate(
                "INSERT INTO person(idperson,lastname, firstname, nickname, phone_number, address, email_address, birth_date) "
                        + "VALUES (2, 'Bab', 'Bub', 'bobibob', '0612121212', 'Rue yahoo', 'EZEZ@2EZE.com', '2015-12-12 12:00:00.000')");

        stmt.close();
        connection.close();

        scene = new Scene(loadFXML("/isen/views/MainMenu"));
        stage.setScene(scene);
        stage.setTitle("Projet JAVA2 2025");
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}