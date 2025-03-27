package com.jmoncayo.callisto.ui.requestview.tabs;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Scope(SCOPE_PROTOTYPE)
@Order(6)
public class SettingsTab extends Tab {
	private final ToggleButton sslVerificationToggle;
	private final ToggleButton autoRedirectToggle;
	private final ToggleButton originalMethodRedirectToggle;
	private final ToggleButton authHeaderRedirectToggle;
	private final ToggleButton removeRefererHeaderToggle;
	private final ToggleButton strictParserToggle;
	private final ToggleButton autoEncodeUrlToggle;
	private final ToggleButton disableCookieJarToggle;
	private final ToggleButton serverCipherSuiteToggle;

	public SettingsTab() {
		this.setText("Settings");
		this.setClosable(false);

		// Create the toggle buttons with titles and default them to "off"
		sslVerificationToggle = createToggleButton(
				"Enable SSL certificate verification",
				"Verify SSL certificates when sending a request. Verification failures will result in the request being aborted.");
		autoRedirectToggle =
				createToggleButton("Automatically follow redirects", "Follow HTTP 3xx responses as redirects.");
		originalMethodRedirectToggle = createToggleButton(
				"Follow original HTTP Method",
				"Redirect with the original HTTP method instead of the default behavior of redirecting with GET.");
		authHeaderRedirectToggle = createToggleButton(
				"Follow Authorization header",
				"Retain authorization header when a redirect happens to a different hostname.");
		removeRefererHeaderToggle = createToggleButton(
				"Remove referer header on redirect", "Remove the referer header when a redirect happens.");
		strictParserToggle =
				createToggleButton("Enable strict HTTP parser", "Restrict responses with invalid HTTP headers.");
		autoEncodeUrlToggle = createToggleButton(
				"Encode URL automatically", "Encode the URL's path, query parameters, and authentication fields.");
		disableCookieJarToggle = createToggleButton(
				"Disable cookie jar", "Prevent cookies used in this request from being stored in the cookie jar.");
		serverCipherSuiteToggle = createToggleButton(
				"Use server cipher suite during handshake",
				"Use the server's cipher suite order instead of the client's during handshake.");

		// Arrange all settings in a VBox
		VBox settingsBox = new VBox(
				10,
				createSettingRow(
						sslVerificationToggle,
						"Enable SSL certificate verification",
						"Verify SSL certificates when sending a request. Verification failures will result in the request being aborted."),
				createSettingRow(
						autoRedirectToggle,
						"Automatically follow redirects",
						"Follow HTTP 3xx responses as redirects."),
				createSettingRow(
						originalMethodRedirectToggle,
						"Follow original HTTP Method",
						"Redirect with the original HTTP method instead of the default behavior of redirecting with GET."),
				createSettingRow(
						authHeaderRedirectToggle,
						"Follow Authorization header",
						"Retain authorization header when a redirect happens to a different hostname."),
				createSettingRow(
						removeRefererHeaderToggle,
						"Remove referer header on redirect",
						"Remove the referer header when a redirect happens."),
				createSettingRow(
						strictParserToggle,
						"Enable strict HTTP parser",
						"Restrict responses with invalid HTTP headers."),
				createSettingRow(
						autoEncodeUrlToggle,
						"Encode URL automatically",
						"Encode the URL's path, query parameters, and authentication fields."),
				createSettingRow(
						disableCookieJarToggle,
						"Disable cookie jar",
						"Prevent cookies used in this request from being stored in the cookie jar."),
				createSettingRow(
						serverCipherSuiteToggle,
						"Use server cipher suite during handshake",
						"Use the server's cipher suite order instead of the client's during handshake."));
		settingsBox.setSpacing(10);

		// Wrap the VBox in a ScrollPane to make it scrollable
		ScrollPane scrollPane = new ScrollPane(settingsBox);
		scrollPane.setFitToWidth(true); // Ensures the VBox fits the width of the ScrollPane
		scrollPane.setFitToHeight(true); // Ensures the VBox fits the height of the ScrollPane

		// Set the content of the tab to the ScrollPane
		this.setContent(new StackPane(scrollPane));
	}

	private HBox createSettingRow(ToggleButton toggleButton, String title, String description) {
		// Create the VBox for title and description (left part)
		VBox textBox = new VBox(5);
		Label titleLabel = new Label(title);
		titleLabel.setStyle("-fx-font-weight: bold;");
		Label descriptionLabel = new Label(description);
		descriptionLabel.setWrapText(true);
		textBox.getChildren().addAll(titleLabel, descriptionLabel);

		// Create the HBox with left part (VBox) and right part (ToggleButton)
		HBox settingRow = new HBox(10, textBox, toggleButton);
		settingRow.setAlignment(Pos.CENTER_LEFT); // Align left for text

		// Align toggle button to the right side of the HBox
		HBox.setHgrow(textBox, Priority.ALWAYS); // Allow the text box to expand
		settingRow.setPadding(new Insets(5, 10, 5, 10)); // Optional padding for aesthetics

		return settingRow;
	}

	private ToggleButton createToggleButton(String title, String description) {
		// Create the toggle button with the title and default it to "off"
		ToggleButton toggleButton = new ToggleButton();
		toggleButton.setText("Off");
		toggleButton.setSelected(false);

		// Add any additional functionality to the toggle button here if needed

		return toggleButton;
	}
}
