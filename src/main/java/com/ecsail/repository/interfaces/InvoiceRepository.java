package com.ecsail.repository.interfaces;



import com.ecsail.dto.DepositDTO;
import com.ecsail.dto.InvoiceDTO;
import com.ecsail.gui.tabs.deposits.InvoiceWithMemberInfoDTO;

import java.util.List;

public interface InvoiceRepository {
    List<InvoiceDTO> getInvoicesByMsid(int ms_id);
    List<InvoiceDTO> getAllInvoices();
    List<InvoiceWithMemberInfoDTO> getInvoicesWithMembershipInfoByDeposit(DepositDTO d);
    List<InvoiceWithMemberInfoDTO> getInvoicesWithMembershipInfoByYear(String year);
    int getBatchNumber(String year);

}
