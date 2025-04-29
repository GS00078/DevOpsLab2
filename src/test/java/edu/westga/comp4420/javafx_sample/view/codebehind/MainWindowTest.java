package edu.westga.comp4420.javafx_sample.view.codebehind;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testfx.assertions.api.Assertions.assertThat;
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

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/edu/westga/comp4420/javafx_sample/view/codebehind/MainWindow.fxml")));
        stage.setScene(scene);
        stage.show();

        itemNameField = lookup("#itemNameField").query();
        quantityField = lookup("#quantityField").query();
        itemListView = lookup("#itemListView").query();
        addButton = lookup("#addButton").query();
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
}
