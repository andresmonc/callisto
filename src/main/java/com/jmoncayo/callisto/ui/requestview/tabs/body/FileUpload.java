package com.jmoncayo.callisto.ui.requestview.tabs.body;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

import java.io.File;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(SCOPE_PROTOTYPE)
public class FileUpload extends StackPane {

	@Getter
	private File file = null;

	public FileUpload() {
		// Create the HBox to display the file upload UI
		HBox fileUploadBox = new HBox(10);
		fileUploadBox.setAlignment(Pos.CENTER);

		Label fileLabel = new Label("No file selected");
		Button fileButton = new Button("Choose File");

		fileButton.setOnAction(e -> {
			FileChooser fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All Files", "*.*"));
			File selectedFile = fileChooser.showOpenDialog(null);
			if (selectedFile != null) {
				this.file = selectedFile;
				fileLabel.setText("Selected file: " + selectedFile.getName());
			}
		});
		fileUploadBox.getChildren().addAll(fileButton, fileLabel);
		// Set the content of the StackPane to the file upload box
		this.getChildren().add(fileUploadBox);
	}
}
