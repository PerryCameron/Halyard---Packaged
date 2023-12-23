package com.ecsail.repository.interfaces;

import com.ecsail.dto.DepositDTO;

public interface DepositRepository {
    int updateDeposit(DepositDTO depositDTO);

    DepositDTO insertDeposit(DepositDTO d);

    Boolean depositIsUsed(int year, int batch);

    Boolean depositRecordExists(String year, int batch);

    int getNumberOfDepositsForYear(int year);
}
