package com.ecsail.dto;

import com.ecsail.BaseApplication;

import java.time.Year;

public class DepositDTO {
	private Integer deposit_id;
	private String depositDate;
	private String fiscalYear;
	private Integer batch;
	
	public DepositDTO(Integer deposit_id, String depositDate, String fiscalYear,
                      Integer batch) {
		this.deposit_id = deposit_id;
		this.depositDate = depositDate;
		this.fiscalYear = fiscalYear;
		this.batch = batch;
	}

	public DepositDTO() {
		this.batch = 1;
		this.fiscalYear = String.valueOf(Year.now().getValue());
	}

	public Integer getDeposit_id() {
		return deposit_id;
	}

	public void setDeposit_id(Integer deposit_id) {
		this.deposit_id = deposit_id;
	}

	public String getDepositDate() {
		return depositDate;
	}

	public void setDepositDate(String depositDate) {
		this.depositDate = depositDate;
	}

	public String getFiscalYear() {
		return fiscalYear;
	}

	public void setFiscalYear(String fiscalYear) {
		this.fiscalYear = fiscalYear;
	}

	public Integer getBatch() {
		return batch;
	}

	public void setBatch(Integer batch) {
		this.batch = batch;
	}

	public void clear() {
		this.deposit_id = 0;
		this.depositDate = "";
		this.fiscalYear = "";
		this.batch = 0;
	}

	@Override
	public String toString() {
		return "DepositDTO{" +
				"deposit_id=" + deposit_id +
				", depositDate='" + depositDate + '\'' +
				", fiscalYear='" + fiscalYear + '\'' +
				", batch=" + batch +
				'}';
	}
}
