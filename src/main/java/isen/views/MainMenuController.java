package isen.views;

import java.io.IOException;
import java.sql.Date;
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
    private void handleEditPersonButton() throws IOException {
        Person person_to_edit = new Person(lastNameTextField.getText(), firstNameTextField.getText(), nicknameTextField.getText(),
                phoneNumberTextField.getText(), addressTextField.getText(), emailTextField.getText(),
                Date.valueOf(birthDateDatePicker.getValue()).toLocalDate(), tableViewPerson.getSelectionModel().getSelectedItem().getId());
        PersonService.editPerson(person_to_edit);
        refreshListPerson();
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
        }
    }
}
