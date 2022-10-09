package com.ecsail.structures;

import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class RosterRadioButtonsDTO {
	RadioButton radioAll = new RadioButton("All");
	RadioButton radioActive = new RadioButton("Active"); 
    RadioButton radioNonRenew = new RadioButton("Non-Renew"); 
    RadioButton radioNewMembers = new RadioButton("New Members");
    RadioButton radioReturnMembers = new RadioButton("Return Members");
    RadioButton radioLatePaymentMembers = new RadioButton("Late Dues");
    
    RadioButton radioSlipWaitList = new RadioButton("Slip Waitlist");
    RadioButton radioSlip = new RadioButton("Slip Owners");
    RadioButton radioWantsToSublease = new RadioButton("Slips for Sublease");
    RadioButton radioOpenSlips = new RadioButton("Open Slips");
    RadioButton radioSlipChange = new RadioButton("Wants New Slip");
    RadioButton radioSubLeasedSlips = new RadioButton("Subleased Slips");
    
    RadioButton radioKayakRackWaitList = new RadioButton("Kayak Rack WaitList");
    RadioButton radioKayakRackOwners = new RadioButton("Kayak Rack");
    RadioButton radioShedWaitList = new RadioButton("Kayak Shed WaitList");
    RadioButton radioShedOwner = new RadioButton("Kayak Shed");
    ToggleGroup tg1 = new ToggleGroup();
    
	public RosterRadioButtonsDTO(RadioButton radioAll, RadioButton radioActive, RadioButton radioNonRenew,
                                 RadioButton radioNewMembers, RadioButton radioNewReturnMembers, RadioButton radioAllActiveMembers, RadioButton radioSlipWaitList,
                                 RadioButton radioSlip, RadioButton radioWantsToSublease, RadioButton radioOpenSlips,
                                 RadioButton radioSlipChange, RadioButton radioSubLeasedSlips, RadioButton radioKayakRackWaitList,
                                 RadioButton radioKayakRackOwners, RadioButton radioShedWaitList, RadioButton radioShedOwner) {
		this.radioAll = radioAll;
		this.radioActive = radioActive;
		this.radioNonRenew = radioNonRenew;
		this.radioNewMembers = radioNewMembers;
		this.radioReturnMembers = radioNewReturnMembers;
		this.radioLatePaymentMembers = radioLatePaymentMembers;
		this.radioSlipWaitList = radioSlipWaitList;
		this.radioSlip = radioSlip;
		this.radioWantsToSublease = radioWantsToSublease;
		this.radioOpenSlips = radioOpenSlips;
		this.radioSlipChange = radioSlipChange;
		this.radioSubLeasedSlips = radioSubLeasedSlips;
		this.radioKayakRackWaitList = radioKayakRackWaitList;
		this.radioKayakRackOwners = radioKayakRackOwners;
		this.radioShedWaitList = radioShedWaitList;
		this.radioShedOwner = radioShedOwner;
	}
	
	public RosterRadioButtonsDTO() {
		setSameToggleGroup();
		setUserData();
	}

	public void setUserData () {
		radioAll.setUserData("all");
		radioActive.setUserData("active");
		radioNonRenew.setUserData("non-renew");
		radioNewMembers.setUserData("new-members");
		radioReturnMembers.setUserData("return");
		radioLatePaymentMembers.setUserData("late");
		radioSlipWaitList.setUserData("slip-waitlist");
		radioSlip.setUserData("ownes slip");
		radioWantsToSublease.setUserData("wants to sublease");
		radioOpenSlips.setUserData("available slips");
		radioSlipChange.setUserData("wants slip change");
		radioSubLeasedSlips.setUserData("subleased slips");
		radioKayakRackWaitList.setUserData("kayak rack waitlist");
		radioShedWaitList.setUserData("kayak shed waitlist");
		radioShedOwner.setUserData("owns shed spot");
	}
	
	public void setSameToggleGroup () {
		radioAll.setToggleGroup(tg1);
		radioActive.setToggleGroup(tg1);
		radioNonRenew.setToggleGroup(tg1);
		radioNewMembers.setToggleGroup(tg1);
		radioReturnMembers.setToggleGroup(tg1);
		radioLatePaymentMembers.setToggleGroup(tg1);
		radioSlipWaitList.setToggleGroup(tg1);
		radioSlip.setToggleGroup(tg1);
		radioWantsToSublease.setToggleGroup(tg1);
		radioOpenSlips.setToggleGroup(tg1);
		radioSlipChange.setToggleGroup(tg1);
		radioSubLeasedSlips.setToggleGroup(tg1);
		radioKayakRackWaitList.setToggleGroup(tg1);
		radioKayakRackOwners.setToggleGroup(tg1);
		radioShedWaitList.setToggleGroup(tg1);
		radioShedOwner.setToggleGroup(tg1);
	}
	
	public RadioButton getRadioAll() {
		return radioAll;
	}

	public RadioButton getRadioActive() {
		return radioActive;
	}

	public RadioButton getRadioNonRenew() {
		return radioNonRenew;
	}

	public RadioButton getRadioNewMembers() {
		return radioNewMembers;
	}

	public RadioButton getRadioReturnMembers() {
		return radioReturnMembers;
	}

	public RadioButton getRadioSlipWaitList() {
		return radioSlipWaitList;
	}

	public RadioButton getRadioSlip() {
		return radioSlip;
	}

	public RadioButton getRadioWantsToSublease() {
		return radioWantsToSublease;
	}

	public RadioButton getRadioOpenSlips() {
		return radioOpenSlips;
	}

	public RadioButton getRadioSlipChange() {
		return radioSlipChange;
	}

	public RadioButton getRadioSubLeasedSlips() {
		return radioSubLeasedSlips;
	}

	public RadioButton getRadioKayakRackWaitList() {
		return radioKayakRackWaitList;
	}

	public RadioButton getRadioKayakRackOwners() {
		return radioKayakRackOwners;
	}

	public RadioButton getRadioShedWaitList() {
		return radioShedWaitList;
	}

	public RadioButton getRadioShedOwner() {
		return radioShedOwner;
	}

	public RadioButton getRadioLatePaymentMembers() {
		return radioLatePaymentMembers;
	}

	public ToggleGroup getTg1() {
		return tg1;
	}

	@Override
	public String toString() {
		return "Object_RosterRadioButtons [radioAll=" + radioAll + ", radioActive=" + radioActive + ", radioNonRenew="
				+ radioNonRenew + ", radioNewMembers=" + radioNewMembers + ", radioNewReturnMembers="
				+ radioReturnMembers + ", radioAllActiveMembers=" + radioLatePaymentMembers + ", radioSlipWaitList="
				+ radioSlipWaitList + "]";
	}
}
