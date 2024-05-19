package com.ecsail.repository.interfaces;

import com.ecsail.dto.MembershipListDTO;
import com.ecsail.dto.SlipDTO;
import com.ecsail.dto.SlipStructureDTO;
import com.ecsail.dto.WaitListDTO;
import com.ecsail.pdf.directory.Object_SlipInfo;

import java.util.List;

public interface SlipRepository {
    void updateWaitList(int ms_id, String field, Boolean attribute);

    Boolean waitListExists(int ms_id);

    WaitListDTO getWaitList(int ms_id);

    void insertWaitList(WaitListDTO w);

    void releaseSlip(MembershipListDTO membership);

    void reAssignSlip(int ms_id, MembershipListDTO membership);

    void subleaserReleaseSlip(int subleasee);

    Boolean slipRentExists(int subMsid);

    void updateSlip(int ms_id, MembershipListDTO membership);

    SlipDTO getSlip(int ms_id);

    Boolean slipExists(int ms_id);

    SlipDTO getSubleasedSlip(int ms_id);

    void deleteWaitList(int msId);

    void updateSlipMsIdToZero(int msId);

    boolean existsSlipWithMsId(int msId);

    List<Object_SlipInfo> getSlipsForDock(String dock);

    List<SlipDTO> getSlips();

    List<SlipStructureDTO> getSlipStructure();
}
