package com.ecsail.structures;

public class DepositPDFDTO {
	boolean isSingleDeposit;
	boolean includesDetailedReport;
	boolean includesSummaryReport;
	int depositNumber;
	
	public DepositPDFDTO(boolean isSingleDeposit, boolean includesDetailedReport, boolean includesSummaryReport,
						 int depositNumber) {
		this.isSingleDeposit = isSingleDeposit;
		this.includesDetailedReport = includesDetailedReport;
		this.includesSummaryReport = includesSummaryReport;
		this.depositNumber = depositNumber;
	}	

	public DepositPDFDTO() {
		this.isSingleDeposit = false;
		this.includesDetailedReport = false;
		this.includesSummaryReport = false;
		this.depositNumber = 0;
	}

	public boolean isSingleDeposit() {
		return isSingleDeposit;
	}

	public boolean isDetailedReportIncluded() {
		return includesDetailedReport;
	}

	public boolean isSummaryReportIncluded() {
		return includesSummaryReport;
	}

	public int getDepositNumber() {
		return depositNumber;
	}

	public void setSingleDeposit(boolean isSingleDeposit) {
		this.isSingleDeposit = isSingleDeposit;
	}

	public void setIncludesDetailedReport(boolean includesDetailedReport) {
		this.includesDetailedReport = includesDetailedReport;
	}

	public void setIncludesSummaryReport(boolean includesSummaryReport) {
		this.includesSummaryReport = includesSummaryReport;
	}

	public void setDepositNumber(int depositNumber) {
		this.depositNumber = depositNumber;
	}
	
	
		
}
