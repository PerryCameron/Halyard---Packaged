package com.ecsail.dto;

public class Email_InformationDTO {
	private int membershipId;
	private String joinDate;
	private String Lname;
	private String Fname;
	private String email;
	private boolean isPrimary;
	
	public Email_InformationDTO(int membershipId, String joinDate, String lname, String fname, String email,
                                boolean isPrimary) {
		this.membershipId = membershipId;
		this.joinDate = joinDate;
		Lname = lname;
		Fname = fname;
		this.email = email;
		this.isPrimary = isPrimary;
	}

	public int getMembershipId() {
		return membershipId;
	}

	public void setMembershipId(int membershipId) {
		this.membershipId = membershipId;
	}

	public String getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}

	public String getLname() {
		return Lname;
	}

	public void setLname(String lname) {
		Lname = lname;
	}

	public String getFname() {
		return Fname;
	}

	public void setFname(String fname) {
		Fname = fname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isPrimary() {
		return isPrimary;
	}

	public void setPrimary(boolean isPrimary) {
		this.isPrimary = isPrimary;
	}

	@Override
	public String toString() {
		return "Object_Email_Information [membershipId=" + membershipId + ", joinDate=" + joinDate + ", Lname=" + Lname
				+ ", Fname=" + Fname + ", email=" + email + ", isPrimary=" + isPrimary + "]";
	}

}
