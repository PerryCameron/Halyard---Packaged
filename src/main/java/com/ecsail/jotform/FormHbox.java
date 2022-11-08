package com.ecsail.jotform;

import com.ecsail.BaseApplication;
import com.ecsail.gui.customwidgets.IosSwitch;
import com.ecsail.gui.customwidgets.IosSwitchBuilder;
import com.ecsail.jotform.structures.JotFormsDTO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.Objects;

public class FormHbox extends HBox {

    Image jotFormImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/jotform_icon.png")));
    ImageView imageView = new ImageView(jotFormImage);

    public FormHbox(JotFormsDTO jotFormsDTO) {
        String formText =
                "2022 ECSC Membership Renewal Form\n" +
                        jotFormsDTO.getCount() + " Submissions, Created on " + jotFormsDTO.getCreated_at()+ "\n" +
                        jotFormsDTO.getNewSubmission() + " New    " + jotFormsDTO.getArchived() + " Archived";

        var list = new VBox();
        var formTextObj = new Text();
        var selectForm = new CheckBox();
        IosSwitch checkBox1 = IosSwitchBuilder.create()
                .prefSize(76, 46)
                .selected(true)
                .selectedColor(Color.CORNFLOWERBLUE)
                .build();

        formTextObj.setId("form-hbox");
        formTextObj.setText(formText);

        list.getChildren().add(formTextObj);
        setSpacing(10);
        setPadding(new Insets(10,10,10,10));
        setAlignment(Pos.CENTER);
        setId("form-hbox");

        formTextObj.setOnMouseClicked(e -> {
            if(e.getClickCount() == 1) {
                BaseApplication.logger.info("clicked on the text");
            }
        });

        imageView.setOnMouseClicked(e -> {
            if(e.getClickCount() == 1) {
                BaseApplication.logger.info("clicked on the image");
            }
        });
        getChildren().addAll(selectForm,checkBox1,imageView, list);
    }
}
