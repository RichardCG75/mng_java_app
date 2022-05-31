module app.unedl {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens app.unedl.controllers to javafx.fxml;
    exports app.unedl.controllers;
    exports app.unedl.views;
    opens app.unedl.views to javafx.fxml;
}