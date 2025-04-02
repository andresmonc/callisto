package com.jmoncayo.callisto.ui.requestview;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

import com.jmoncayo.callisto.ui.save.SaveRequestDialog;
import java.util.Arrays;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
@Scope(SCOPE_PROTOTYPE)
@Log4j2
public class RequestField extends HBox {
	private final RequestURL requestURL;
	private final Button actionButton;
	private final ComboBox<String> dropdown;

	@Autowired
	public RequestField(RequestURL requestURL, SaveRequestDialog saveRequestDialog) {
		this.requestURL = requestURL;
		dropdown = new ComboBox<>();
		dropdown.getItems()
				.addAll(Arrays.stream(HttpMethod.values()).map(HttpMethod::name).toList());
		dropdown.setValue(HttpMethod.GET.name());
		actionButton = new Button("Submit");
		actionButton.setOnAction(event -> {
			log.info("Selected Option: " + dropdown.getValue());
			log.info("URL: " + requestURL.getText());
		});

		Button saveButton = new Button("Save");
		FontIcon saveIcon = new FontIcon(FontAwesomeSolid.SAVE);
		saveButton.setGraphic(saveIcon);
		saveButton.setOnAction(event -> {
			log.info("Saving request...");
			saveRequestDialog.open((Stage) this.getScene().getWindow());
		});

		this.getChildren().addAll(dropdown, requestURL, actionButton, saveButton);
		this.setSpacing(10);
	}

	public RequestURL getRequestURL() {
		return requestURL;
	}

	public Button getActionButton() {
		return actionButton;
	}

	public ComboBox<String> getMethod() {
		return dropdown;
	}
}
