package com.ecsail.repository.interfaces;

import com.ecsail.dto.Memo2DTO;
import com.ecsail.dto.MemoDTO;
import com.ecsail.views.tabs.deposits.InvoiceWithMemberInfoDTO;

import java.util.List;

public interface MemoRepository {

    List<MemoDTO> getMemosByMsId(int ms_id);
    List<MemoDTO> getMemosByBoatId(int boat_id);
    MemoDTO getMemoByInvoiceIdAndCategory(InvoiceWithMemberInfoDTO invoice, String category);
    List<Memo2DTO> getAllMemosForTabNotes(String year, String category);

    void deleteMemo(MemoDTO memo);

    void deleteMemos(int msId);

    MemoDTO insertMemo(MemoDTO m);

    void updateMemo(int memoId, String field, String attribute);
}
