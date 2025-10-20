package com.devstack.pos.controller;

import com.devstack.pos.util.BackupUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

public class BackupFormController {
    public AnchorPane context;
    public TextArea txtArea;

    public void initialize() {
        loadBackupData();
    }

    private void loadBackupData()  {

        try {
            String dump = BackupUtil.createBackup("localhost", "3306", "pos_system_dsmp6", "root", "1234");
            txtArea.setText(dump);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void backToScreenOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashboardForm");
    }

    private void setUi(String location) throws IOException {
        URL resource = getClass().getResource("/com/devstack/pos/view/" + location + ".fxml");
        Parent load = FXMLLoader.load(resource);
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(
                new Scene(load)
        );
    }

    public void downloadOnAction(ActionEvent actionEvent) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Backup File");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("SQL Files", "*.sql")
            );
            fileChooser.setInitialFileName("backup_" + System.currentTimeMillis() + ".sql");

            Stage stage = (Stage) txtArea.getScene().getWindow();
            File file = fileChooser.showSaveDialog(stage);

            if (file != null) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    writer.write(txtArea.getText());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
