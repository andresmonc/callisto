package com.jmoncayo.callisto.ui.environmentview;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(SCOPE_PROTOTYPE)
public class EnvironmentTab extends Tab {

	private final TextField editableEnvironmentName;

	public EnvironmentTab() {
		StackPane stackPane = new StackPane();
		editableEnvironmentName = new TextField();
		editableEnvironmentName.setText("New Environment");

		// Initially not editable and styled like a label
		setTextFieldStyle(false);

		editableEnvironmentName.setEditable(false);
		editableEnvironmentName.setFocusTraversable(false);

		// when name changes update tab name
		editableEnvironmentName.textProperty().addListener((obs, oldText, newText) -> {
			this.setText(newText);
		});

		editableEnvironmentName.setOnMouseClicked(event -> {
			if (event.getClickCount() == 2) {
				editableEnvironmentName.setEditable(true);
				editableEnvironmentName.setFocusTraversable(true);
				setTextFieldStyle(true);
				editableEnvironmentName.requestFocus();
				editableEnvironmentName.selectAll();
			}
		});

		// When focus is lost, revert to non-editable label style
		editableEnvironmentName.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
			if (!isNowFocused) {
				editableEnvironmentName.setEditable(false);
				editableEnvironmentName.setFocusTraversable(false);
				setTextFieldStyle(false);
			}
		});

		stackPane.getChildren().add(editableEnvironmentName);
		this.setClosable(true);
		this.setContent(stackPane);
	}

	public void initNewEnvironment() {
		this.setText("New Environment");
		this.editableEnvironmentName.setText("New Environment");
	}

	private void setTextFieldStyle(boolean editing) {
		if (editing) {
			editableEnvironmentName.setStyle("""
            -fx-background-color: white;
            -fx-border-color: lightgray;
            -fx-padding: 2 4 2 4;
        """);
		} else {
			editableEnvironmentName.setStyle("""
            -fx-background-color: transparent;
            -fx-border-color: transparent;
            -fx-padding: 2 4 2 4;
        """);
		}
	}
}
