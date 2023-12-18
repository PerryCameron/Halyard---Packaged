package com.ecsail;

import com.ecsail.repository.implementations.*;
import com.ecsail.repository.interfaces.*;
import com.ecsail.views.common.Note;
import com.ecsail.sql.SqlInsert;
import com.ecsail.sql.select.SqlMembership_Id;
import com.ecsail.sql.select.SqlSelect;
import com.ecsail.dto.MembershipIdDTO;
import com.ecsail.dto.MembershipListDTO;
import com.ecsail.dto.MemoDTO;
import com.ecsail.dto.PersonDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;

public class CreateMembership {
	private static final PersonRepository personRepository = new PersonRepositoryImpl();
	private static final MembershipRepository membershipRepository = new MembershipRepositoryImpl();
	private static final MembershipIdRepository membershipIdRepository = new MembershipIdRepositoryImpl();
	private static final InvoiceRepository invoiceRepository = new InvoiceRepositoryImpl();
	private static final PhoneRepository phoneRepository = new PhoneRepositoryImpl();
	private static final EmailRepository emailRepository = new EmailRepositoryImpl();
	private static final OfficerRepository officerRepository = new OfficerRepositoryImpl();
	private static final BoatRepository boatRepository = new BoatRepositoryImpl();
	private static final MemoRepository memoRepository = new MemoRepositoryImpl();
	private static final SlipRepository slipRepository = new SlipRepositoryImpl();

	public static void Create() { // create a membership
		if (!Launcher.tabOpen("New Membership")) {
			Note newMemNote = new Note();
			int membership_id = membershipIdRepository.getMembershipIdForNewestMembership(Year.now().getValue()) + 1;
			String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			MembershipListDTO membershipListDTO = membershipRepository.insertMembership(
					new MembershipListDTO(date, "FM", String.valueOf(Year.now().getValue())));
			System.out.println("msid= " + membershipListDTO.getMsId());
			personRepository.insertPerson(
					new PersonDTO(membershipListDTO.getMsId(),1,true));
			newMemNote.addMemo(
					new MemoDTO(membershipListDTO.getMsId(), "Created new membership record", date,"N"));
			BaseApplication.activeMemberships.add(membershipListDTO);
			System.out.println(membershipListDTO.getMsId());
			membershipIdRepository.insert(
					new MembershipIdDTO(String.valueOf(Year.now().getValue()), membershipListDTO.getMsId(), membership_id + ""));
				Launcher.createMembershipTabForRoster(membershipListDTO.getMembershipId(), membershipListDTO.getMsId());

		} else {
			BaseApplication.tabPane.getSelectionModel().select(Launcher.getTabIndex("New Membership"));
		}
	}
}


