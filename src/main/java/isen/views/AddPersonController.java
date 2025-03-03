package isen.views;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

import isen.App;
import isen.db.daos.DataSourceFactory;
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
    private void handleAddPersonButton() {
        
    }
}
