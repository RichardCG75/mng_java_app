module app.mvc.unedl_app {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;

    opens app.mvc.unedl_app to javafx.fxml;
    exports app.mvc.unedl_app;
}