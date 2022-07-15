module app.unedl {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.controlsfx.controls;

    opens app.unedl.controllers to javafx.fxml;
    opens  app.unedl.models to javafx.base;
    exports app.unedl.controllers;
    exports app.unedl.views;
    opens app.unedl.views to javafx.fxml;
}