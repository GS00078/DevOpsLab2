package edu.westga.comp4420.javafx_sample.view.codebehind;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testfx.util.WaitForAsyncUtils.waitForFxEvents;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class MainWindowTest extends ApplicationTest {

    private TextField itemNameField;
    private TextField quantityField;
    private ListView<String> itemListView;
    private Button addButton;
    private Button removeButton;
    private Button updateButton;

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/edu/westga/comp4420/javafx_sample/view/codebehind/MainWindow.fxml")));
        stage.setScene(scene);
        stage.show();

        itemNameField = lookup("#itemNameField").query();
        quantityField = lookup("#quantityField").query();
        itemListView = lookup("#itemListView").query();
        addButton = lookup("#addButton").query();
        removeButton = lookup("#removeButton").query();
        updateButton = lookup("#updateButton").query();
    }

    @Test
    public void testAddValidItem() {
        clickOn(itemNameField).write("Milk");
        clickOn(quantityField).write("2");
        clickOn(addButton);

        String expectedItem = "Milk - 2";
        assertEquals(expectedItem, itemListView.getItems().get(0));
    }

    @Test
    public void testAddInvalidItem() {
        clickOn(itemNameField).write("");
        clickOn(quantityField).write("1");
        clickOn(addButton);

        waitForFxEvents();

        boolean alertExists = lookup(".alert").tryQuery().isPresent();
        assertEquals(true, alertExists, "Expected an error dialog to appear");

        assertEquals(0, itemListView.getItems().size());
    }

    @Test
    public void testAddItemWithZeroQuantity() {
        clickOn(itemNameField).write("Juice");
        clickOn(quantityField).write("0");
        clickOn(addButton);

        waitForFxEvents();

        boolean alertExists = lookup(".alert").tryQuery().isPresent();
        assertEquals(true, alertExists, "Expected an error dialog for zero quantity.");
    }

    @Test
    public void testAddItemWithNegativeQuantity() {
        clickOn(itemNameField).write("Chips");
        clickOn(quantityField).write("-5");
        clickOn(addButton);

        waitForFxEvents();

        boolean alertExists = lookup(".alert").tryQuery().isPresent();
        assertEquals(true, alertExists, "Expected an error dialog for negative quantity.");
    }

    @Test
    public void testAddItemWithNonNumericQuantity() {
        clickOn(itemNameField).write("Candy");
        clickOn(quantityField).write("abc");
        clickOn(addButton);

        waitForFxEvents();

        boolean alertExists = lookup(".alert").tryQuery().isPresent();
        assertEquals(true, alertExists, "Expected an error dialog for non-numeric quantity.");
    }

    @Test
    public void testRemoveItemWithSelection() {
        clickOn(itemNameField).write("Apples");
        clickOn(quantityField).write("5");
        clickOn(addButton);

        clickOn(itemListView).clickOn("Apples - 5");
        clickOn(removeButton);

        assertEquals(0, itemListView.getItems().size());
    }

    @Test
    public void testRemoveItemWithoutSelection() {
        clickOn(removeButton);
        waitForFxEvents();

        boolean alertExists = lookup(".alert").tryQuery().isPresent();
        assertEquals(true, alertExists, "Expected an error dialog to appear");
    }

    @Test
    public void testUpdateQuantityWithSelectionAndValidQuantity() {
        clickOn(itemNameField).write("Bananas");
        clickOn(quantityField).write("3");
        clickOn(addButton);

        clickOn(itemListView).clickOn("Bananas - 3");
        clickOn(quantityField).clickOn(quantityField).eraseText(1).write("5");
        clickOn(updateButton);

        assertEquals("Bananas - 5", itemListView.getItems().get(0));
    }

    @Test
    public void testUpdateQuantityWithoutSelection() {
        clickOn(quantityField).write("4");
        clickOn(updateButton);
        waitForFxEvents();

        boolean alertExists = lookup(".alert").tryQuery().isPresent();
        assertEquals(true, alertExists, "Expected an error dialog to appear");
    }

    @Test
    public void testUpdateQuantityWithSelectionAndZeroQuantity() {
        clickOn(itemNameField).write("Oranges");
        clickOn(quantityField).write("6");
        clickOn(addButton);

        clickOn(itemListView).clickOn("Oranges - 6");
        clickOn(quantityField).clickOn(quantityField).eraseText(1).write("0");
        clickOn(updateButton);
        waitForFxEvents();

        boolean alertExists = lookup(".alert").tryQuery().isPresent();
        assertEquals(true, alertExists, "Expected an error dialog for zero quantity.");
    }

    @Test
    public void testUpdateQuantityWithSelectionAndNegativeQuantity() {
        clickOn(itemNameField).write("Mangoes");
        clickOn(quantityField).write("4");
        clickOn(addButton);

        clickOn(itemListView).clickOn("Mangoes - 4");
        clickOn(quantityField).clickOn(quantityField).eraseText(1).write("-3");
        clickOn(updateButton);
        waitForFxEvents();

        boolean alertExists = lookup(".alert").tryQuery().isPresent();
        assertEquals(true, alertExists, "Expected an error dialog for negative quantity.");
    }

    @Test
    public void testUpdateQuantityWithEmptyField() {
        clickOn(itemNameField).write("Grapes");
        clickOn(quantityField).write("4");
        clickOn(addButton);

        clickOn(itemListView).clickOn("Grapes - 4");
        clickOn(quantityField).eraseText(1);
        clickOn(updateButton);
        waitForFxEvents();

        boolean alertExists = lookup(".alert").tryQuery().isPresent();
        assertEquals(true, alertExists, "Expected an error dialog for empty quantity field.");
    }

    @Test
    public void testUpdateQuantityWithNonNumericValue() {
        clickOn(itemNameField).write("Tomatoes");
        clickOn(quantityField).write("3");
        clickOn(addButton);

        clickOn(itemListView).clickOn("Tomatoes - 3");
        clickOn(quantityField).eraseText(1).write("xyz");
        clickOn(updateButton);
        waitForFxEvents();

        boolean alertExists = lookup(".alert").tryQuery().isPresent();
        assertEquals(true, alertExists, "Expected an error dialog for non-numeric quantity.");
    }
}