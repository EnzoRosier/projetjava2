package isen.views;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

import isen.App;
import isen.db.daos.DataSourceFactory;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class EditPersonController {

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
    private void handleEditPersonButton() {
        try (Connection connection = DataSourceFactory.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO person(lastname, firstname, nickname, phone_number, address, email_address, birth_date) VALUES (?, ?, ?, ?, ?, ?, ?)")) {
                statement.setString(1, lastNameTextField.getText());
                statement.setString(2, firstNameTextField.getText());
                statement.setString(3, nicknameTextField.getText());
                statement.setString(4, phoneNumberTextField.getText());
                statement.setString(5, addressTextField.getText());
                statement.setString(6, emailTextField.getText());
                statement.setDate(7, Date.valueOf(birthDateDatePicker.getValue()));
                statement.executeUpdate();
                App.setRoot("/isen/views/MainMenu");
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRemovePersonButton() {

    }
}
