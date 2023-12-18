package com.ecsail.views.tabs.membership.people.person;

import com.ecsail.repository.implementations.EmailRepositoryImpl;
import com.ecsail.repository.interfaces.EmailRepository;
import com.ecsail.sql.SqlUpdate;
import com.ecsail.dto.EmailDTO;
import javafx.beans.property.ObjectProperty;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;

public class RadioButtonCell extends TableCell<EmailDTO, Boolean> {

    private final ObjectProperty<EmailDTO> emailDTOObjectProperty;
    private final EmailRepository emailRepository = new EmailRepositoryImpl();

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
                                emailRepository.updateEmail(emailDTO);
                            }

                            emailDTO.setPrimaryUse(true);
                            emailRepository.updateEmail(emailDTO);
                            emailDTOObjectProperty.set(emailDTO);
                        }
                    });
        } else {
            setGraphic(null);
        }
    }
}
