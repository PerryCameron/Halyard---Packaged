package com.ecsail.gui.dialogues;


import com.sun.javafx.scene.control.skin.resources.ControlResources;
import javafx.beans.InvalidationListener;
import javafx.beans.NamedArg;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CustomAlert extends Dialog<ButtonType> {

        public static enum AlertType {
            NONE,
            INFORMATION,
            WARNING,
            CONFIRMATION,
            ERROR
        }


        private WeakReference<DialogPane> dialogPaneRef;

        private boolean installingDefaults = false;
        private boolean hasCustomButtons = false;
        private boolean hasCustomTitle = false;
        private boolean hasCustomHeaderText = false;

        private final InvalidationListener headerTextListener = o -> {
            if (!installingDefaults) hasCustomHeaderText = true;
        };

        private final InvalidationListener titleListener = o -> {
            if (!installingDefaults) hasCustomTitle = true;
        };

        private final ListChangeListener<ButtonType> buttonsListener = change -> {
            if (!installingDefaults) hasCustomButtons = true;
        };

        public CustomAlert(@NamedArg("alertType") CustomAlert.AlertType alertType) {
            this(alertType, "");
        }


        public CustomAlert(@NamedArg("alertType") CustomAlert.AlertType alertType,
                     @NamedArg("contentText") String contentText,
                     @NamedArg("buttonTypes") ButtonType... buttons) {
            super();

            final DialogPane dialogPane = getDialogPane();
            dialogPane.setContentText(contentText);
            getDialogPane().getStyleClass().add("alert");

            dialogPaneRef = new WeakReference<>(dialogPane);

            hasCustomButtons = buttons != null && buttons.length > 0;
            if (hasCustomButtons) {
                for (ButtonType btnType : buttons) {
                    dialogPane.getButtonTypes().addAll(btnType);
                }
            }

            setAlertType(alertType);

            // listening to property changes on Dialog and DialogPane
            dialogPaneProperty().addListener(o -> updateListeners());
            titleProperty().addListener(titleListener);
            updateListeners();
        }

        private final ObjectProperty<CustomAlert.AlertType> alertType = new SimpleObjectProperty<CustomAlert.AlertType>(null) {
            final String[] styleClasses = new String[] { "information", "warning", "error", "confirmation" };

            @Override
            protected void invalidated() {
                String newTitle = "";
                String newHeader = "";
//            Node newGraphic = null;
                String styleClass = "";
                ButtonType[] newButtons = new ButtonType[] { ButtonType.OK };
                switch (getAlertType()) {
                    case NONE: {
                        newButtons = new ButtonType[] { };
                        break;
                    }
                    case INFORMATION: {
                        newTitle = ControlResources.getString("Dialog.info.title");
                        newHeader = ControlResources.getString("Dialog.info.header");
                        styleClass = "information";
                        break;
                    }
                    case WARNING: {
                        newTitle = ControlResources.getString("Dialog.warning.title");
                        newHeader = ControlResources.getString("Dialog.warning.header");
                        styleClass = "warning";
                        break;
                    }
                    case ERROR: {
                        newTitle = ControlResources.getString("Dialog.error.title");
                        newHeader = ControlResources.getString("Dialog.error.header");
                        styleClass = "error";
                        break;
                    }
                    case CONFIRMATION: {
                        newTitle = ControlResources.getString("Dialog.confirm.title");
                        newHeader = ControlResources.getString("Dialog.confirm.header");
                        styleClass = "confirmation";
                        newButtons = new ButtonType[] { ButtonType.OK, ButtonType.CANCEL };
                        break;
                    }
                }

                installingDefaults = true;
                if (!hasCustomTitle) setTitle(newTitle);
                if (!hasCustomHeaderText) setHeaderText(newHeader);
                if (!hasCustomButtons) getButtonTypes().setAll(newButtons);

                // update the style class based on the alert type. We use this to
                // specify the default graphic to use (i.e. via CSS).
                DialogPane dialogPane = getDialogPane();
                if (dialogPane != null) {
                    List<String> toRemove = new ArrayList<>(Arrays.asList(styleClasses));
                    toRemove.remove(styleClass);
                    dialogPane.getStyleClass().removeAll(toRemove);
                    if (! dialogPane.getStyleClass().contains(styleClass)) {
                        dialogPane.getStyleClass().add(styleClass);
                    }
                }

                installingDefaults = false;
            }
        };

        public final CustomAlert.AlertType getAlertType() {
            return alertType.get();
        }

        public final void setAlertType(CustomAlert.AlertType alertType) {
            this.alertType.setValue(alertType);
        }

        public final ObjectProperty<CustomAlert.AlertType> alertTypeProperty() {
            return alertType;
        }


        /**
         * Returns an {@link ObservableList} of all {@link ButtonType} instances that
         * are currently set inside this Alert instance. A ButtonType may either be one
         * of the pre-defined types (e.g. {@link ButtonType#OK}), or it may be a
         * custom type (created via the {@link ButtonType#ButtonType(String)} or
         * {@link ButtonType#ButtonType(String, javafx.scene.control.ButtonBar.ButtonData)}
         * constructors.
         *
         * <p>Readers should refer to the {@link ButtonType} class documentation for more details,
         * but at a high level, each ButtonType instance is converted to
         * @return an {@link ObservableList} of all {@link ButtonType} instances that
         * are currently set inside this Alert instance
         */
        // --- buttonTypes
        public final ObservableList<ButtonType> getButtonTypes() {
            return getDialogPane().getButtonTypes();
        }



        /* ************************************************************************
         *
         * Private Implementation
         *
         **************************************************************************/

        private void updateListeners() {
            DialogPane oldPane = dialogPaneRef.get();

            if (oldPane != null) {
                oldPane.headerTextProperty().removeListener(headerTextListener);
                oldPane.getButtonTypes().removeListener(buttonsListener);
            }

            // listen to changes to properties that would be changed by alertType being
            // changed, so that we only change values that are still at their default
            // value (i.e. the user hasn't changed them, so we are free to set them
            // to a new default value when the alertType changes).

            DialogPane newPane = getDialogPane();
            if (newPane != null) {
                newPane.headerTextProperty().addListener(headerTextListener);
                newPane.getButtonTypes().addListener(buttonsListener);
            }

            dialogPaneRef = new WeakReference<DialogPane>(newPane);
        }

}
