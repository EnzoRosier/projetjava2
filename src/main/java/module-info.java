module isen {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens isen to javafx.fxml;
    opens isen.views to javafx.fxml;
    exports isen;
}