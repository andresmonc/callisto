package com.jmoncayo.callisto.ui.toolbar;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;
import org.springframework.stereotype.Component;

@Component
public class Toolbar extends HBox {
	public Toolbar() {
		setSpacing(10);
		setPadding(new Insets(2));
		setStyle("-fx-background-color: #f0f0f0;");

		// Create the ComboBox
		ComboBox<String> stringComboBox = new ComboBox<>();
		stringComboBox.setPromptText("No Environment");

		// Create the gear icon using FontAwesome5
		FontIcon gearIcon = new FontIcon(FontAwesomeSolid.COG);
		gearIcon.setIconSize(20); // Adjust icon size if needed

		Button gearButton = new Button();
		gearButton.setGraphic(gearIcon);
		gearButton.setFocusTraversable(false);

		// Use a Region to push everything to the right
		Region spacer = new Region();
		HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

		// Add ComboBox, spacer, and button to the HBox
		this.getChildren().addAll(spacer, stringComboBox, gearButton);
	}
}
