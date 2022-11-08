package com.ecsail.jotform;


import com.ecsail.jotform.structures.JotFormsDTO;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class CustomViewForms extends ScrollPane {

    public CustomViewForms() {
        VBox vBoxMain = new VBox();
        vBoxMain.setSpacing(10);
        for(int i = 0; i < 20; i++) {
            JotFormsDTO jotFormsDTO = new JotFormsDTO(3456L, "","",545,
                    "","34-43-43","","",4,240,
                    "",1,5, "jot.com");
            vBoxMain.getChildren().add(new FormHbox(jotFormsDTO));
        }
        setContent(vBoxMain);
    }
}
