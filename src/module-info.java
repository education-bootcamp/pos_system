module pos.system {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires jbcrypt;
    requires core;
    requires java.desktop;
    requires javafx.base;

    opens com.devstack.pos to javafx.fxml;
    opens com.devstack.pos.controller to javafx.fxml;
    exports com.devstack.pos;
    exports com.devstack.pos.db;
    exports com.devstack.pos.view.tm;
    opens com.devstack.pos.db to javafx.fxml;
}