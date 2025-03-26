package com.jmoncayo.callisto.ui.requestview;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

import com.jmoncayo.callisto.ui.codearea.AutoDetectCodeArea;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(SCOPE_PROTOTYPE)
public class ResponseArea extends VBox {
	private final AutoDetectCodeArea responseDisplay;
	private WebView responseHtmlDisplay;
	private final Tab preview;

	public ResponseArea() {
		TabPane tabPane = new TabPane();
		tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
		Tab raw = new Tab("JSON");
		this.preview = new Tab("Preview");
		tabPane.getTabs().add(raw);
		tabPane.getTabs().add(preview);
		HBox responseAreaNav = new HBox();
		Text label = new Text("Response | ");
		ComboBox<String> dropdown = new ComboBox<>();
		dropdown.getItems().addAll("Option 1", "Option 2", "Option 3");
		this.responseDisplay = new AutoDetectCodeArea();
		responseDisplay.setEditable(false);
		responseDisplay.setMinHeight(0);
		responseDisplay.setPrefHeight(400);
		responseDisplay.setMaxWidth(Double.MAX_VALUE);
		responseAreaNav.getChildren().addAll(label, dropdown);
		this.getChildren().addAll(responseAreaNav, tabPane);
		raw.setContent(responseDisplay);
	}

	public AutoDetectCodeArea getResponseDisplay() {
		return responseDisplay;
	}

	public void htmlPreview(String html) {
		if (this.responseHtmlDisplay == null) {
			this.responseHtmlDisplay = new WebView();
			this.preview.setContent(this.responseHtmlDisplay);
		}
		this.responseHtmlDisplay.getEngine().loadContent(html);
	}

	public void rawPreview(String text) {
		this.responseDisplay.replaceText(text);
	}
}
