package com.ecsail.gui.tabs.membership.people.person;

import com.ecsail.sql.SqlUpdate;
import com.ecsail.structures.EmailDTO;
import javafx.beans.property.ObjectProperty;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;

public class RadioButtonCell extends TableCell<EmailDTO, Boolean> {

    private final ObjectProperty<EmailDTO> emailDTOObjectProperty;

    public RadioButtonCell(ObjectProperty<EmailDTO> emailDTOObjectProperty) {
        this.emailDTOObjectProperty = emailDTOObjectProperty;
    }

    @Override
    protected void updateItem(Boolean item, boolean empty) {
        super.updateItem(item, empty);

        if (!empty && item != null) {

            RadioButton radioButton = new RadioButton();
            radioButton.setSelected(item);
            setGraphic(radioButton);

            radioButton.selectedProperty().addListener(
                    (o, old, selected) -> {

                        if (selected) {
                            EmailDTO emailDTO = getTableRow().getItem();
                            if (emailDTOObjectProperty.get() != null) {
                                emailDTOObjectProperty.get().setPrimaryUse(false);
                                SqlUpdate.updateEmail("primary_use", emailDTOObjectProperty.get().getEmail_id(), false);
                            }

                            emailDTO.setPrimaryUse(true);
                            SqlUpdate.updateEmail("primary_use", emailDTO.getEmail_id(), true);
                            emailDTOObjectProperty.set(emailDTO);
                        }
                    });
        } else {
            setGraphic(null);
        }
    }
}
