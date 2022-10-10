package com.ecsail.pdf.directory;

public class Object_StoreMemberPosition {

	int personStart;
	int personEnd;

	public Object_StoreMemberPosition(int personStart, int personEnd) {
		super();
		this.personStart = personStart;
		this.personEnd = personEnd;
	}

	public int getPersonStart() {
		return personStart;
	}

	public void setPersonStart(int personStart) {
		this.personStart = personStart;
	}

	public int getPersonEnd() {
		return personEnd;
	}

	public void setPersonEnd(int personEnd) {
		this.personEnd = personEnd;
	}

	@Override
	public String toString() {
		return "Object_StoreMemberPosition [personStart=" + personStart + ", personEnd=" + personEnd + "]";
	}

}
