package com.jmoncayo.callisto.ui.environmentview;

import javafx.scene.control.Tab;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
public class EnvironmentTab extends Tab {

    public EnvironmentTab() {
        this.setClosable(true);
    }

    public void initNewEnvironment(){
        this.setText("New Environment");
    }
}
