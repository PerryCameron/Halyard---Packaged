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
    MembershipListDTO getMembershipByMsIdAndYear(int ms_id, int year);
    MembershipListDTO getMembershipListByIdAndYear(int year, int membershipId);

    MembershipDTO getCurrentMembershipChair();

    boolean memberShipExists(int ms_id);

    MembershipListDTO getMembershipByMsIdAndYear(int ms_id, String year);

    List<MembershipListDTO> getRoster(String year, boolean isActive);

    List<MembershipListDTO> getRosterOfSlipOwners();

    List<MembershipListDTO> getRosterOfSubleasedSlips();

    MembershipListDTO getMembershipFromListWithoutMembershipId(int ms_id);

    List<MembershipListDTO> getSlipRoster(String year);

    MembershipListDTO getMembershipByMembershipId(String membership_id);

    List<MembershipListDTO> getBoatOwnerRoster(int boat_id);

    void deleteMembership(int ms_id);

    void deleteFormMsIdHash(int ms_id);
}
