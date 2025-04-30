package edu.westga.comp4420.javafx_sample.view.codebehind;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class MainWindow {

    @FXML
    private TextField itemNameField;

    @FXML
    private TextField quantityField;

    @FXML
    private ListView<String> itemListView;

    @FXML
    private void handleAdd(ActionEvent event) {
        String itemName = this.itemNameField.getText().trim();
        String quantityText = this.quantityField.getText().trim();

        if (itemName.isEmpty()) {
            this.showError("A valid item name must be provided.");
            return;
        }

        try {
            int quantity = Integer.parseInt(quantityText);
            if (quantity <= 0) {
                this.showError("Quantity must be a positive number!");
                return;
            }

            this.itemListView.getItems().add(itemName + " - " + quantity);
            this.itemNameField.clear();
            this.quantityField.clear();
        } catch (NumberFormatException e) {
            this.showError("Quantity must be a valid number!");
        }
    }

    @FXML
    private void handleRemove(ActionEvent event) {
        int selectedIndex = this.itemListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            this.itemListView.getItems().remove(selectedIndex);
        } else {
            this.showError("Please select an item to remove!");
        }
    }

    @FXML
    private void handleUpdate(ActionEvent event) {
        int selectedIndex = this.itemListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            String quantityText = this.quantityField.getText().trim();

            if (quantityText.isEmpty()) {
                this.showError("A quantity must be provided.");
                return;
            }

            try {
                int quantity = Integer.parseInt(quantityText);
                if (quantity <= 0) {
                    this.showError("Quantity must be a positive number!");
                    return;
                }

                String existingItem = this.itemListView.getItems().get(selectedIndex);
                String itemName = existingItem.split(" - ")[0];

                this.itemListView.getItems().set(selectedIndex, itemName + " - " + quantity);
                this.quantityField.clear();
            } catch (NumberFormatException e) {
                this.showError("Quantity must be a valid number!");
            }
        } else {
            this.showError("Please select an item to update!");
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}