package isen.views;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

import isen.App;
import isen.db.daos.DataSourceFactory;
import isen.db.entities.Person;
import isen.services.PersonService;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class AddPersonController {

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField nicknameTextField;

    @FXML
    private TextField phoneNumberTextField;

    @FXML
    private TextField addressTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private DatePicker birthDateDatePicker;

    @FXML
    private void handleAddPersonButton() throws IOException {
        Person person_to_add = new Person(lastNameTextField.getText(), firstNameTextField.getText(), nicknameTextField.getText(),
                phoneNumberTextField.getText(), addressTextField.getText(), emailTextField.getText(),
                Date.valueOf(birthDateDatePicker.getValue()).toLocalDate());
        PersonService.add_person(person_to_add);
        App.setRoot("/isen/views/MainMenu");
    }
}
