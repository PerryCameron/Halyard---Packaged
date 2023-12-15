package com.ecsail.repository.interfaces;


import com.ecsail.dto.MembershipIdDTO;

import java.util.List;

public interface MembershipIdRepository {
    List<MembershipIdDTO> getIds();
    List<MembershipIdDTO> getIds(int ms_id);
    MembershipIdDTO getId(int ms_id);
    MembershipIdDTO getCurrentId(int ms_id);
    MembershipIdDTO getMembershipIdFromMsid(int msid);

    Integer getMsidFromMembershipID(int membership_id);

    MembershipIdDTO getMembershipId(String year, int ms_id);
    MembershipIdDTO getMembershipIdObject(int mid);
    MembershipIdDTO getHighestMembershipId(String year);
    boolean isRenewedByMsidAndYear(int ms_id, String year);

    List<MembershipIdDTO> getAllMembershipIdsByYear(int year);

    List<MembershipIdDTO> getActiveMembershipIdsByYear(String year);
    int getNonRenewNumber(int year);
    int getMsidFromYearAndMembershipId(int year, String membershipId);
    int update(MembershipIdDTO o);

    int delete(MembershipIdDTO membershipIdDTO);

    int insert(MembershipIdDTO membershipIdDTO);
}