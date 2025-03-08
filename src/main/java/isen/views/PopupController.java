package isen.views;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class PopupController {
    @FXML private TextArea messageLabel;

    public void setMessage(String message) {
        messageLabel.setText(message);
    }

    @FXML
    private void closePopup() {
        Stage stage = (Stage) messageLabel.getScene().getWindow();
        stage.close();
    }
}
