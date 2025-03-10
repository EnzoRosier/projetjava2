package isen.views;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import isen.App;
import isen.db.entities.Person;
import isen.services.PersonService;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainMenuController {

    @FXML
    private TableView<Person> tableViewPerson;

    @FXML
    private TableColumn<Person, String> lastNameColumn;

    @FXML
    private TableColumn<Person, String> firstNameColumn;

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
    private Button editPersonButton;

    @FXML
    private Button removePersonButton;

    @FXML
    private AnchorPane infoPane;

    @FXML
    private void initialize() {
        refreshListPerson();

        tableViewPerson.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Person>() {
            @Override
            public void changed(ObservableValue<? extends Person> observable,
                    Person oldValue, Person newValue) {
                showPersonDetail(newValue);
            }
        });
    }

    @FXML
    private void handleAddPersonButton() throws IOException {
        App.setRoot("/isen/views/AddPersonMenu");
    }
    
    public void exitApplication() {
		Platform.exit();
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
    
    @FXML
    private void handleEditPersonButton() throws IOException {
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
            Person person_to_edit = new Person(lastNameTextField.getText(), firstNameTextField.getText(), nicknameTextField.getText(),
            phoneNumberTextField.getText(), addressTextField.getText(), emailTextField.getText(),
            Date.valueOf(birthDateDatePicker.getValue()).toLocalDate(), tableViewPerson.getSelectionModel().getSelectedItem().getId());
            PersonService.editPerson(person_to_edit);
            refreshListPerson();
        }
    }

    @FXML
    private void handleRemovePersonButton() {
        Person person_to_remove = tableViewPerson.getSelectionModel().getSelectedItem();
        PersonService.deletePerson(person_to_remove);
        refreshListPerson();
    }

    private void refreshListPerson() {
        List<Person> listPerson = PersonService.getListPerson();
        System.out.println(listPerson);
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("lastName"));
        tableViewPerson.setItems(FXCollections.observableArrayList(listPerson));
        tableViewPerson.getSelectionModel().clearSelection();
        showPersonDetail(null);
    }

    private void showPersonDetail(Person person) {
        if (person == null) {
            infoPane.setVisible(false);
        } else {
            infoPane.setVisible(true);
            firstNameTextField.setText(person.getFirstName());
            lastNameTextField.setText(person.getLastName());
            nicknameTextField.setText(person.getNickname());
            phoneNumberTextField.setText(person.getPhoneNumber());
            addressTextField.setText(person.getAddress());
            emailTextField.setText(person.getEmailAddress());
            birthDateDatePicker.setValue(person.getBirthDate());
            editPersonButton.setText("Edit " + person.getFirstName() );
            removePersonButton.setText("Remove " + person.getFirstName());
        }
    }
}
