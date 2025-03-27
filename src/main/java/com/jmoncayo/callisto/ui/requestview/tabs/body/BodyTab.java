package com.jmoncayo.callisto.ui.requestview.tabs.body;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Scope(SCOPE_PROTOTYPE)
@Order(5)
public class BodyTab extends Tab {
	private final StackPane contentStackPane;
	private final Label noneTextArea;
	private final TextArea formDataTextArea;
	private final TextArea xwwwFormTextArea;
	private final TextArea rawTextArea;
	private final FileUpload binaryTextArea;
	private final TextArea graphqlTextArea;

	public BodyTab(FileUpload fileUpload) {
		this.setText("Body");
		this.setClosable(false);

		ToggleGroup group = new ToggleGroup();

		RadioButton noneButton = new RadioButton("None");
		noneButton.setToggleGroup(group);
		noneButton.setSelected(true);

		RadioButton formDataButton = new RadioButton("form-data");
		formDataButton.setToggleGroup(group);

		RadioButton xwwwFormButton = new RadioButton("x-www-form-urlencoded");
		xwwwFormButton.setToggleGroup(group);

		RadioButton rawButton = new RadioButton("raw");
		rawButton.setToggleGroup(group);

		RadioButton binaryButton = new RadioButton("binary");
		binaryButton.setToggleGroup(group);

		RadioButton graphqlButton = new RadioButton("graphql");
		graphqlButton.setToggleGroup(group);

		// Create the Nodes for each body type
		noneTextArea = new Label("No request body");

		formDataTextArea = new TextArea();
		formDataTextArea.setPromptText("Enter form data here...");

		xwwwFormTextArea = new TextArea();
		xwwwFormTextArea.setPromptText("Enter x-www-form-urlencoded data here...");

		rawTextArea = new TextArea();
		rawTextArea.setPromptText("Enter raw content here...");

		binaryTextArea = fileUpload;

		graphqlTextArea = new TextArea();
		graphqlTextArea.setPromptText("Enter GraphQL query here...");

		// StackPane to manage the switching of different TextAreas
		contentStackPane = new StackPane();
		contentStackPane.getChildren().add(noneTextArea); // Default content is the "None" text area

		// Create a VBox to arrange the radio buttons vertically
		HBox radioButtons =
				new HBox(10, noneButton, formDataButton, xwwwFormButton, rawButton, binaryButton, graphqlButton);

		// Create a main container to hold the radio buttons and the StackPane
		VBox mainContainer = new VBox(10, radioButtons, contentStackPane);
		mainContainer.setSpacing(10);

		// Set the content of the tab to the VBox
		this.setContent(mainContainer);

		// Add listeners to radio buttons to switch between different TextAreas
		noneButton.setOnAction(e -> showContent(noneTextArea));
		formDataButton.setOnAction(e -> showContent(formDataTextArea));
		xwwwFormButton.setOnAction(e -> showContent(xwwwFormTextArea));
		rawButton.setOnAction(e -> showContent(rawTextArea));
		binaryButton.setOnAction(e -> showContent(binaryTextArea));
		graphqlButton.setOnAction(e -> showContent(graphqlTextArea));
	}

	// Method to swap the displayed content in the StackPane
	private void showContent(Node newContent) {
		contentStackPane.getChildren().clear();
		contentStackPane.getChildren().add(newContent);
	}
}
