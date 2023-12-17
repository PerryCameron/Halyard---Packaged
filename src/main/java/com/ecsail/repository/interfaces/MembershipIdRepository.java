package com.ecsail.repository.interfaces;


import com.ecsail.dto.MembershipIdDTO;

import java.util.List;

public interface MembershipIdRepository {
    List<MembershipIdDTO> getIds();
    List<MembershipIdDTO> getIds(int ms_id);
    int getId(int ms_id);
    MembershipIdDTO getCurrentId(int ms_id);
    int getMembershipIDFromMsid(int msid);
    Integer getMsidFromMembershipID(int membership_id);
//    String getMembershipId(String year, int ms_id);
    MembershipIdDTO getMembershipIdObject(int mid);
    MembershipIdDTO getHighestMembershipId(String year);
    boolean isRenewedByMsidAndYear(int ms_id, String year);
    List<MembershipIdDTO> getAllMembershipIdsByYear(int year);
    List<MembershipIdDTO> getActiveMembershipIdsByYear(String year);
    int getNonRenewNumber(int year);
    int getMsidFromYearAndMembershipId(int year, String membershipId);
    int update(MembershipIdDTO o);
    int delete(MembershipIdDTO membershipIdDTO);

    void deleteMembershipId(int ms_id);

    int insert(MembershipIdDTO membershipIdDTO);
    String getMembershipIdByYearAndMsId(String year, int msId);
}
