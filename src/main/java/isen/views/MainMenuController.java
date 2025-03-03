package isen.views;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;

import isen.App;
import isen.db.daos.DataSourceFactory;
import isen.db.entities.Person;
import isen.services.PersonService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainMenuController {

    @FXML
    private TableView<Person> tableViewPerson;

    @FXML
    Button addPersonButton;

    @FXML
    private void initialize() {
        List<Person> listPerson = PersonService.get_list_person();
        
        
    }

    @FXML
    private void handleAddPersonButton() throws IOException {
        App.setRoot("/isen/views/AddPersonMenu");
    }

    @FXML
    private void handleEditSelectedListView() throws IOException {
        
    }

    @FXML
    private void handleEditPersonButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.class.getResource("/isen/views/EditPersonMenu.fxml"));
        AnchorPane rootLayout = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(rootLayout));
        stage.setTitle("Edit Person");
        stage.show();
    }
}
