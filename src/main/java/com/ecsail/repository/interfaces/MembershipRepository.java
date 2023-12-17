package com.ecsail.repository.interfaces;

import com.ecsail.dto.MembershipDTO;
import com.ecsail.dto.MembershipListDTO;

import java.util.List;

public interface MembershipRepository {

    List<MembershipListDTO> getActiveRoster(String selectedYear);
    List<MembershipListDTO> getInActiveRoster(String selectedYear);
    List<MembershipListDTO> getAllRoster(String selectedYear);
    List<MembershipListDTO> getNewMemberRoster(String selectedYear);
    List<MembershipListDTO> getReturnMemberRoster(String selectedYear);
    List<MembershipListDTO> getSlipWaitList(String selectedYear);
    MembershipListDTO getMembershipFromList(int ms_id, int year);
    MembershipListDTO getMembershipListByIdAndYear(int year, int membershipId);

    MembershipDTO getCurrentMembershipChair();

    boolean memberShipExists(int ms_id);

    MembershipListDTO getMembershipFromList(int ms_id, String year);

    List<MembershipListDTO> getRoster(String year, boolean isActive);
}
