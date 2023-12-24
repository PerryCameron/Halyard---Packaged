package com.ecsail.repository.interfaces;

import com.ecsail.dto.DepositDTO;
import com.ecsail.dto.DepositTotalDTO;

public interface DepositRepository {
    int updateDeposit(DepositDTO depositDTO);

    DepositDTO insertDeposit(DepositDTO d);

    Boolean depositIsUsed(int year, int batch);

    Boolean depositRecordExists(String year, int batch);

    int getNumberOfDepositsForYear(int year);

    DepositDTO getDeposit(int year, int batch);

    DepositTotalDTO getTotals(DepositDTO d, boolean getAll);

    int getNumberOfDepositBatches(String year);
}
