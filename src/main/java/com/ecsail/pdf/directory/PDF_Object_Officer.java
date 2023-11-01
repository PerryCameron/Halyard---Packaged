package com.ecsail.pdf.directory;

public class PDF_Object_Officer {
    String firstName;
    String lastName;
    String officerType;
    String boardTermEndYear;
    String fiscalYear;
    Boolean officerPlaced;

    public PDF_Object_Officer(String firstName, String lastName, String officerType, String boardTermEndYear, String fiscalYear) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.officerType = officerType;
        this.boardTermEndYear = boardTermEndYear;
        this.fiscalYear = fiscalYear;
        this.officerPlaced = false;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getOfficerType() {
        return officerType;
    }

    public void setOfficerType(String officerType) {
        this.officerType = officerType;
    }

    public String getBoardTermEndYear() {
        return boardTermEndYear;
    }

    public void setBoardTermEndYear(String boardTermEndYear) {
        this.boardTermEndYear = boardTermEndYear;
    }

    public String getFiscalYear() {
        return fiscalYear;
    }

    public void setFiscalYear(String fiscalYear) {
        this.fiscalYear = fiscalYear;
    }

    public Boolean getOfficerPlaced() {
        return officerPlaced;
    }

    public void setOfficerPlaced(Boolean officerPlaced) {
        this.officerPlaced = officerPlaced;
    }

	@Override
	public String toString() {
		return "PDF_Object_Officer{" +
				"firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", officerType='" + officerType + '\'' +
				", boardTermEndYear='" + boardTermEndYear + '\'' +
				", fiscalYear='" + fiscalYear + '\'' +
				", officerPlaced=" + officerPlaced +
				'}';
	}
}
