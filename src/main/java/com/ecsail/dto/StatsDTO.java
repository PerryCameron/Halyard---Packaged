package com.ecsail.dto;


public class StatsDTO {
	
int statId;
int fiscalYear;
int activeMemberships;
int nonRenewMemberships;
int returnMemberships;
int newMemberships;
int secondaryMembers;
int dependants;
int numberOfBoats;
int family;
int regular;
int social;
int lakeAssociates;
int lifeMembers;
int raceFellows;
int student;
double deposits;
double initiation;

public StatsDTO(int statId, int fiscalYear, int activeMemberships, int nonRenewMemberships,
                int returnMemberships, int newMemberships, int secondaryMembers, int dependants, int numberOfBoats, int family,
                int regular, int social, int lakeAssociates, int lifeMembers, int raceFellows, int student, double deposits,
                double initiation) {
	super();
	this.statId = statId;
	this.fiscalYear = fiscalYear;
	this.activeMemberships = activeMemberships;
	this.nonRenewMemberships = nonRenewMemberships;
	this.returnMemberships = returnMemberships;
	this.newMemberships = newMemberships;
	this.secondaryMembers = secondaryMembers;
	this.dependants = dependants;
	this.numberOfBoats = numberOfBoats;
	this.family = family;
	this.regular = regular;
	this.social = social;
	this.lakeAssociates = lakeAssociates;
	this.lifeMembers = lifeMembers;
	this.raceFellows = raceFellows;
	this.student = student;
	this.deposits = deposits;
	this.initiation = initiation;
}

public int getStatId() {
	return statId;
}

public void setStatId(int statId) {
	this.statId = statId;
}

public int getFiscalYear() {
	return fiscalYear;
}

public void setFiscalYear(int fiscalYear) {
	this.fiscalYear = fiscalYear;
}

public int getActiveMemberships() {
	return activeMemberships;
}

public void setActiveMemberships(int activeMemberships) {
	this.activeMemberships = activeMemberships;
}

public int getNonRenewMemberships() {
	return nonRenewMemberships;
}

public void setNonRenewMemberships(int nonRenewMemberships) {
	this.nonRenewMemberships = nonRenewMemberships;
}

public int getReturnMemberships() {
	return returnMemberships;
}

public void setReturnMemberships(int returnMemberships) {
	this.returnMemberships = returnMemberships;
}

public int getNewMemberships() {
	return newMemberships;
}

public void setNewMemberships(int newMemberships) {
	this.newMemberships = newMemberships;
}

public int getSecondaryMembers() {
	return secondaryMembers;
}

public void setSecondaryMembers(int secondaryMembers) {
	this.secondaryMembers = secondaryMembers;
}

public int getDependants() {
	return dependants;
}

public void setDependants(int dependants) {
	this.dependants = dependants;
}

public int getNumberOfBoats() {
	return numberOfBoats;
}

public void setNumberOfBoats(int numberOfBoats) {
	this.numberOfBoats = numberOfBoats;
}

public int getFamily() {
	return family;
}

public void setFamily(int family) {
	this.family = family;
}

public int getSocial() {
	return social;
}

public void setSocial(int social) {
	this.social = social;
}

public int getLakeAssociates() {
	return lakeAssociates;
}

public void setLakeAssociates(int lakeAssociates) {
	this.lakeAssociates = lakeAssociates;
}

public int getLifeMembers() {
	return lifeMembers;
}

public void setLifeMembers(int lifeMembers) {
	this.lifeMembers = lifeMembers;
}

public int getRaceFellows() {
	return raceFellows;
}

public void setRaceFellows(int raceFellows) {
	this.raceFellows = raceFellows;
}

public int getStudent() {
	return student;
}

public void setStudent(int student) {
	this.student = student;
}

public double getDeposits() {
	return deposits;
}

public void setDeposits(double deposits) {
	this.deposits = deposits;
}

public double getInitiation() {
	return initiation;
}

public void setInitiation(double initiation) {
	this.initiation = initiation;
}

public int getRegular() {
	return regular;
}

public void setRegular(int regular) {
	this.regular = regular;
}



}
