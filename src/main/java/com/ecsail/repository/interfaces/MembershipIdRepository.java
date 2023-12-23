package com.ecsail.repository.interfaces;


import com.ecsail.dto.MembershipIdDTO;

import java.util.List;

public interface MembershipIdRepository {
    List<MembershipIdDTO> getIds();
    List<MembershipIdDTO> getIds(int ms_id);
    List<MembershipIdDTO> getAllMembershipIdsByYear(int year);
    List<MembershipIdDTO> getActiveMembershipIdsByYear(String year);
    MembershipIdDTO getCurrentId(int ms_id);
    MembershipIdDTO getMembershipIdObject(int mid);
    MembershipIdDTO insert(MembershipIdDTO membershipIdDTO);
    int getId(int msId);
    int getMembershipIDFromMsid(int msid);
    int getMsidFromMembershipID(int membershipId);
    int getNonRenewNumber(int year);
    int getMsidFromYearAndMembershipId(int year, String membershipId);
    int update(MembershipIdDTO membershipIdDTO);
    int delete(MembershipIdDTO membershipIdDTO);
    int getMembershipIdForNewestMembership(int year);
    String getMembershipIdByYearAndMsId(String year, int msId);
    boolean isRenewedByMsidAndYear(int msId, String year);
    boolean membershipIdBlankRowExists(String msid);
    void deleteBlankMembershipIdRow();
    void deleteMembershipId(int msId);

    int rowExists(MembershipIdDTO membershipIdDTO);

    void updateMembershipId(int msId, int year, boolean value);
}
