package isen.views;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import isen.App;
import isen.db.entities.Person;
import isen.services.PersonService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
        List<String> errors = new ArrayList<>();

        // Vérification des champs
        if (firstNameTextField.getText().trim().isEmpty()) errors.add("First name field is empty");
        if (lastNameTextField.getText().trim().isEmpty()) errors.add("Last name field is empty");
        if (nicknameTextField.getText().trim().isEmpty()) errors.add("Nickname field is empty");
        if (phoneNumberTextField.getText().trim().isEmpty()) errors.add("Number field is empty");
        if (addressTextField.getText().trim().isEmpty()) errors.add("Address field is empty");
        if (emailTextField.getText().trim().isEmpty()) errors.add("Email field is empty");
        if (birthDateDatePicker.getValue() == null) errors.add("Date field is empty");

        // Vérification du numéro de tel
        if (!phoneNumberTextField.getText().trim().isEmpty() && !phoneNumberTextField.getText().matches("\\d+")) {
            errors.add("Number field must contain only digits");
        }

        // Vérification du mail
        if (!emailTextField.getText().trim().isEmpty() && !emailTextField.getText().contains("@")) {
            errors.add("Email field must contain '@'");
        }

        // Show popup if there are errors
        if (!errors.isEmpty()) {
            showPopup(errors);
        }
        else{        
            Person person_to_add = new Person(lastNameTextField.getText(), firstNameTextField.getText(), nicknameTextField.getText(),
            phoneNumberTextField.getText(), addressTextField.getText(), emailTextField.getText(),
            Date.valueOf(birthDateDatePicker.getValue()).toLocalDate());
        PersonService.addPerson(person_to_add);
        App.setRoot("/isen/views/MainMenu");
        }

    }

    @FXML
    private void showPopup(List<String> errors) throws IOException {
            try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddPersonPopup.fxml"));
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setScene(new Scene(loader.load()));
            popupStage.setTitle("Validation Errors");

            // Get the controller and pass the error messages
            PopupController popupController = loader.getController();
            popupController.setMessage(String.join("\n", errors));

            popupStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}