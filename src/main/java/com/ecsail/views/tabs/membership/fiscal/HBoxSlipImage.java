package com.ecsail.views.tabs.membership.fiscal;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Objects;

public class HBoxSlipImage extends HBox {

	public HBoxSlipImage(String filename) {
		
	Image slipImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/slips/" + filename + "_icon.png")));
//        Image slipImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/slips/none_icon.png")));

        ImageView imageView = new ImageView(slipImage);
	VBox vboxOuter = new VBox(); // allows stretch from other side so the vboxPink doesn't stretch
	VBox vboxPink = new VBox(); // this creates a pink border around the table
	imageView.setPreserveRatio(true);
    imageView.setFitHeight(240);
    vboxPink.setId("box-pink");
    vboxPink.setPadding(new Insets(4,4,4,4)); // spacing to make pink frame around image
    setAlignment(Pos.TOP_CENTER);
    vboxPink.getChildren().add(imageView);
    vboxOuter.getChildren().add(vboxPink);
    getChildren().add(vboxOuter);
	}

}
