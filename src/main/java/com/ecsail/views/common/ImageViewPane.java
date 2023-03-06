package com.ecsail.views.common;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class ImageViewPane extends Region {
    private ObjectProperty<ImageView> imageViewProperty = new SimpleObjectProperty<ImageView>();
    
    public ImageViewPane(ImageView imageView) {
    	VBox.setVgrow(this, Priority.ALWAYS);
    	HBox.setHgrow(this, Priority.ALWAYS);
        imageViewProperty.addListener(new ChangeListener<ImageView>() {
            @Override
            public void changed(ObservableValue<? extends ImageView> arg0, ImageView oldIV, ImageView newIV) {
                if (oldIV != null) {
                    getChildren().remove(oldIV);
                    //System.out.println("removing oldIV");
                }
                if (newIV != null) {
                    getChildren().add(newIV);
                    //System.out.println("adding newIV");
                }
                //System.out.println("Listerner called");
            }
            
        });
        this.imageViewProperty.set(imageView);
    }

    public ObjectProperty<ImageView> imageViewProperty() {
        return imageViewProperty;
    }

    public ImageView getImageView() {
        return imageViewProperty.get();
    }

    public void setImageView(ImageView imageView) {
        this.imageViewProperty.set(imageView);
    }

    public ImageViewPane() {
        this(new ImageView());
    }

    @Override
    protected void layoutChildren() {
        ImageView imageView = imageViewProperty.get();
        if (imageView != null) {
            imageView.setFitWidth(this.getWidth());
            imageView.setFitHeight(this.getHeight());
            layoutInArea(imageView, 0, 0, this.getWidth(), this.getHeight(), 0, HPos.CENTER, VPos.CENTER);
        }
        super.layoutChildren();
    }
}