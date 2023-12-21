package com.ecsail.repository.interfaces;



import com.ecsail.dto.DbInvoiceDTO;
import com.ecsail.dto.DepositDTO;
import com.ecsail.dto.InvoiceDTO;
import com.ecsail.dto.PaymentDTO;
import com.ecsail.views.tabs.deposits.InvoiceWithMemberInfoDTO;

import java.util.List;

public interface InvoiceRepository {
    List<InvoiceDTO> getInvoicesByMsid(int ms_id);
    List<InvoiceDTO> getAllInvoices();
    List<InvoiceWithMemberInfoDTO> getInvoicesWithMembershipInfoByDeposit(DepositDTO d);
    List<InvoiceWithMemberInfoDTO> getInvoicesWithMembershipInfoByYear(String year);
    int getBatchNumber(String year);
    void deletePayment(PaymentDTO p);
    void deletePaymentByInvoiceID(int invoiceId);
    void deleteInvoiceItemByInvoiceID(int invoiceId);
    void deleteInvoiceByID(int invoiceId);
    void deleteAllPaymentsAndInvoicesByMsId(int msId);
    DbInvoiceDTO insertDbInvoice(DbInvoiceDTO d);
    PaymentDTO insertPayment(PaymentDTO op);
}
