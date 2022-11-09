package com.ecsail.jotform;

import com.ecsail.BaseApplication;
import com.ecsail.customwidgets.RoundCheckBox;
import com.ecsail.jotform.structures.JotFormsDTO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.Objects;

public class FormHbox extends HBox {

    Image jotFormImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/jotform_icon.png")));
    ImageView imageView = new ImageView(jotFormImage);

    public FormHbox(JotFormsDTO jotFormsDTO) {
        String[] date = jotFormsDTO.getCreated_at().split(" ");
        String formText =
                jotFormsDTO.getTitle() + "\n" +
                jotFormsDTO.getCount() + " Submissions, Created on " + date[0] + "\n" +
                jotFormsDTO.getNewSubmission() + " New    " + jotFormsDTO.getArchived() + " Archived";

        var list = new VBox();
        var formTextObj = new Text();
        var checkBox = new CheckBox();
        var checkBox1 = new RoundCheckBox();

        checkBox1.setSelected(jotFormsDTO.isFavorite());
        checkBox.setSelected(jotFormsDTO.isFavorite());

        BaseApplication.logger.info("Form " + jotFormsDTO.getId() + " is favorite: " + jotFormsDTO.isFavorite());
        formTextObj.setId("form-hbox");
        formTextObj.setText(formText);

        list.getChildren().add(formTextObj);
        setSpacing(10);
        setPadding(new Insets(10,10,10,10));
        setAlignment(Pos.CENTER_LEFT);
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
        getChildren().addAll(checkBox,checkBox1,imageView, list);
    }
}
