package com.ecsail;

import com.ecsail.dto.MembershipIdDTO;
import com.ecsail.dto.MembershipListDTO;
import com.ecsail.dto.MemoDTO;
import com.ecsail.dto.PersonDTO;
import com.ecsail.repository.implementations.MembershipIdRepositoryImpl;
import com.ecsail.repository.implementations.MembershipRepositoryImpl;
import com.ecsail.repository.implementations.PersonRepositoryImpl;
import com.ecsail.repository.interfaces.MembershipIdRepository;
import com.ecsail.repository.interfaces.MembershipRepository;
import com.ecsail.repository.interfaces.PersonRepository;
import com.ecsail.views.common.Note;
import com.ecsail.views.dialogues.Dialogue_CustomErrorMessage;
import javafx.application.Platform;
import javafx.concurrent.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;

public class CreateMembership {

    public static Logger logger = LoggerFactory.getLogger(CreateMembership.class);
    private static final PersonRepository personRepository = new PersonRepositoryImpl();
    private static final MembershipRepository membershipRepository = new MembershipRepositoryImpl();
    private static final MembershipIdRepository membershipIdRepository = new MembershipIdRepositoryImpl();

    public static void Create() { // create a membership
        if (!Launcher.tabOpen("New Membership")) {
            Dialogue_CustomErrorMessage dialogue = new Dialogue_CustomErrorMessage(false);
            dialogue.setTitle("Creating New Membership");
            Task<MembershipListDTO> task = new Task<>() {
                @Override
                protected MembershipListDTO call() {
                    Note newMemNote = new Note();
                    int membership_id = membershipIdRepository.getMembershipIdForNewestMembership(Year.now().getValue()) + 1;
                    String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    setMessage("Creating Membership", dialogue);
                    MembershipListDTO membershipListDTO = membershipRepository.insertMembership(
                            new MembershipListDTO(date, "FM", String.valueOf(Year.now().getValue())));
                    setMessage("Creating Primary Member", dialogue);
                    PersonDTO personDTO = personRepository.insertPerson(
                            new PersonDTO(membershipListDTO.getMsId(), 1, true));
                    membershipListDTO.setPid(personDTO.getpId());
					membershipRepository.updateMembership(membershipListDTO);
                    setMessage("Adding note that membership record was created", dialogue);
                    newMemNote.addMemo(
                            new MemoDTO(membershipListDTO.getMsId(), "Created new membership record", "N"));
                    setMessage("Creating Membership Id", dialogue);
                    membershipIdRepository.insert(
                            new MembershipIdDTO(String.valueOf(Year.now().getValue()), membershipListDTO.getMsId(), membership_id + ""));
                    setMessage("Successfully Created Membership", dialogue);
                    return membershipListDTO;
                }
            };
            task.setOnSucceeded(succeed -> {
                        MembershipListDTO membershipListDTO = ((Task<MembershipListDTO>) succeed.getSource()).getValue();
                        logger.info("Membership with msId=" + membershipListDTO.getMsId() + " Created");
                        BaseApplication.activeMemberships.add(membershipListDTO);
                        Launcher.createMembershipTabForRoster(membershipListDTO.getMembershipId(), membershipListDTO.getMsId());
                        BaseApplication.logger.info("Created membership");
                        dialogue.closeDialogue();
                    }
            );
            task.setOnFailed(fail -> {
                setMessage("Membership Creation failed", dialogue);
                dialogue.addCloseButton();
            });
            new Thread(task).start();
        } else {
            BaseApplication.tabPane.getSelectionModel().select(Launcher.getTabIndex("New Membership"));
        }
    }

    private static void setMessage(String message, Dialogue_CustomErrorMessage dialogue) {
        Platform.runLater(() -> {
            logger.info(message);
            dialogue.setText(message);
        });
    }
}


