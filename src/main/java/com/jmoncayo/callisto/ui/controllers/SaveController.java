package com.jmoncayo.callisto.ui.controllers;

import com.jmoncayo.callisto.ui.events.CloseTabEvent;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Log4j2
public class SaveController {

    private final RequestController requestController;

    public SaveController(RequestController requestController) {
        this.requestController = requestController;
    }

    @EventListener(CloseTabEvent.class)
    public void handleCloseTabEvent(CloseTabEvent event) {

        log.info("Handling CloseTabEvent for ID: {} and TabType: {}", event.getId(), event.getTabType());
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Unsaved Changes");
            alert.setHeaderText("You have unsaved changes.");
            alert.setContentText("Do you want to save before closing?");

            ButtonType saveButton = new ButtonType("Save");
            ButtonType dontSaveButton = new ButtonType("Don't Save");
            ButtonType cancelButton = ButtonType.CANCEL;

            alert.getButtonTypes().setAll(saveButton, dontSaveButton, cancelButton);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent()) {
                if (result.get() == saveButton) {
                    switch (event.getTabType()) {
                        case REQUEST -> requestController.closeRequest(event.getId());
                    }
                    closeTab(event.getTab());
                } else if (result.get() == dontSaveButton) {
                    switch (event.getTabType()) {
                        case REQUEST -> requestController.destroyUnsavedRequest(event.getId());
                    }
                    closeTab(event.getTab());
                }
            }
        });
    }

    private void closeTab(Tab tab) {
        tab.getTabPane().getTabs().remove(tab);
    }
}