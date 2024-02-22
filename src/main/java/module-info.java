module com.example.phpgui {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;
    requires jbcrypt;

    opens com.example.phpgui to javafx.fxml;
    exports com.example.phpgui;
    exports com.example.phpgui.Controller;
    opens com.example.phpgui.Controller to javafx.fxml;
}