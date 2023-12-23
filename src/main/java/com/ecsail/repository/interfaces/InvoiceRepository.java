package com.ecsail.repository.interfaces;



import com.ecsail.dto.*;
import com.ecsail.views.tabs.deposits.InvoiceWithMemberInfoDTO;

import java.util.List;
import java.util.Set;

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
    InvoiceDTO insertInvoice(InvoiceDTO m);
    InvoiceItemDTO insertInvoiceItem(InvoiceItemDTO i);
    FeeDTO insertFee(FeeDTO feeDTO);
    int updateDeposit(DepositDTO depositDTO);
    int updatePayment(PaymentDTO paymentDTO);
    String getTotalAmount(int invoiceId);
    List<PaymentDTO> getPaymentsWithInvoiceId(int invoiceId);
    List<InvoiceItemDTO> getInvoiceItemsByInvoiceId(int invoiceId);
    Boolean invoiceItemPositionCreditExistsWithValue(int year, int msId);
    Boolean membershipHasOfficerForYear(int msid, int year);

    int updateInvoiceItem(InvoiceItemDTO item);

    void updateInvoice(InvoiceDTO invoice);

    Boolean paymentExists(int invoiceId);

    List<DbInvoiceDTO> getDbInvoiceByYear(int year);

    List<FeeDTO> getFeesFromYear(int year);

    void updateFeeByDescriptionAndFieldName(FeeDTO feeDTO, String oldDescription);

    Set<FeeDTO> getRelatedFeesAsInvoiceItems(DbInvoiceDTO dbInvoiceDTO);

    List<FeeDTO> getAllFeesByDescription(String description);

    List<FeeDTO> getAllFeesByFieldNameAndYear(FeeDTO feeDTO);
    FeeDTO getFeeByMembershipTypeForFiscalYear(int year, int msId);

    void deleteFee(FeeDTO f);

    void deleteFeesByDbInvoiceId(DbInvoiceDTO dbInvoiceDTO);

    void deleteDbInvoice(DbInvoiceDTO dbInvoiceDTO);

    DepositDTO insertDeposit(DepositDTO d);

    Boolean depositIsUsed(int year, int batch);

    Boolean depositRecordExists(String year, int batch);

    Boolean invoiceExists(String year, MembershipDTO membership);

    void updateDbInvoice(DbInvoiceDTO dbInvoiceDTO);

    int updateFee(FeeDTO feeDTO);
}
