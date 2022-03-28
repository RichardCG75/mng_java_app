module app.unedl {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;

    opens app.unedl.controllers to javafx.fxml;
    exports app.unedl.controllers;
    exports app.unedl.views;
    opens app.unedl.views to javafx.fxml;
}