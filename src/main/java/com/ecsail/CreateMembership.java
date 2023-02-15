package com.ecsail;

import com.ecsail.gui.common.Note;
import com.ecsail.sql.SqlInsert;
import com.ecsail.sql.select.SqlMembership_Id;
import com.ecsail.sql.select.SqlSelect;
import com.ecsail.structures.MembershipIdDTO;
import com.ecsail.structures.MembershipListDTO;
import com.ecsail.structures.MemoDTO;
import com.ecsail.structures.PersonDTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CreateMembership {

	public static void Create() { // create a membership
		// makes sure we don't have a New Membership tab open
		if (!Launcher.tabOpen("New Membership")) {
			// get next available ms_id
			int ms_id = SqlSelect.getNextAvailablePrimaryKey("membership", "ms_id");
			// get next available membership_id in a roster for a given year (last person on list)
			int membership_id = SqlMembership_Id.getHighestMembershipId(HalyardPaths.getYear()) + 1;
			// get the next available primary key for a new membership_id tuple
			int mid = SqlSelect.getNextAvailablePrimaryKey("membership_id", "mid");
			// Do we really need to create a person? yes
			int pid = SqlSelect.getNextAvailablePrimaryKey("person","p_id");
			// creates a note object
			Note newMemNote = new Note();
			// gets next available primary key to make a new memo tuple
			int note_id = SqlSelect.getNextAvailablePrimaryKey("memo","memo_id");
			// primary user creation is done in TabMembership();
			// Create a time stamp
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDateTime now = LocalDateTime.now();
			String date = dtf.format(now);

			MembershipListDTO newMembership = new MembershipListDTO(ms_id, pid, membership_id, date, "FM", "",
					"", "", 0, "", "", "", "", HalyardPaths.getYear());
			PersonDTO newPrimary = new PersonDTO(pid,ms_id,1,"","",null,"","",true,"",0);
			if (SqlInsert.addMembershipIsSucessful(newMembership)) {
				newMemNote.addMemo(new MemoDTO(note_id, ms_id, date, "Created new membership record", 0, "N",0));
				BaseApplication.activeMemberships.add(newMembership);
				SqlInsert.addPersonRecord(newPrimary);
				SqlInsert.addMembershipId(new MembershipIdDTO(mid, HalyardPaths.getYear(), ms_id, membership_id + "", true,
						"RM", false, false));
				Launcher.createMembershipTabForRoster(newMembership.getMembershipId(), newMembership.getMsid());
			}
		} else {
			BaseApplication.tabPane.getSelectionModel().select(Launcher.getTabIndex("New Membership"));
		}
	}
}


