module isen {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens isen to javafx.fxml;
    opens isen.views to javafx.fxml;
    opens isen.db.entities to javafx.base;
    exports isen;
}