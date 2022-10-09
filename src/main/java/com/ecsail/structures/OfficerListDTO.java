package com.ecsail.structures;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OfficerListDTO {
	private StringProperty commodore;
	private StringProperty viceCommodore;
	private StringProperty pastCommodore;
	private StringProperty treasurer;
	private StringProperty secretary;
	private StringProperty harbormaster;
	private StringProperty assistantHarbormaster;
	private StringProperty membership;
	private StringProperty assistantMembership;
	private StringProperty publicity;
	private StringProperty assistantPublicity;
	private StringProperty racing;
	private StringProperty assistantRacing;
	private StringProperty safety;
	private StringProperty assistantSafety;
	private StringProperty social;
	private StringProperty assistantSocial;
	private StringProperty board11; // expiration year oldest
	private StringProperty board12;
	private StringProperty board13;
	private StringProperty board14;
	private StringProperty board15;
	private StringProperty board16;
	private StringProperty board17;
	private StringProperty board18;
	private StringProperty board21; // middle expiration year
	private StringProperty board22;
	private StringProperty board23;
	private StringProperty board24;
	private StringProperty board25;
	private StringProperty board26;
	private StringProperty board27;
	private StringProperty board28;
	private StringProperty board31; // expiration year newest
	private StringProperty board32;
	private StringProperty board33;
	private StringProperty board34;
	private StringProperty board35;
	private StringProperty board36;
	private StringProperty board37;
	private StringProperty board38;
	
	public OfficerListDTO(String commodore, String viceCommodore, String pastCommodore,
						  String treasurer, String secretary, String harbormaster,
						  String assistantHarbormaster, String membership, String assistantMembership,
						  String publicity, String assistantPublicity, String racing,
						  String assistantRacing, String safety, String assistantSafety,
						  String social, String assistantSocial, String board11, String board12,
						  String board13, String board14, String board15, String board16,
						  String board17, String board18, String board21, String board22,
						  String board23, String board24, String board25, String board26,
						  String board27, String board28, String board31, String board32,
						  String board33, String board34, String board35, String board36,
						  String board37, String board38) {
		
		this.commodore = new SimpleStringProperty(commodore);
		this.viceCommodore = new SimpleStringProperty(viceCommodore);
		this.pastCommodore = new SimpleStringProperty(pastCommodore);
		this.treasurer = new SimpleStringProperty(treasurer);
		this.secretary = new SimpleStringProperty(secretary);
		this.harbormaster = new SimpleStringProperty(harbormaster);
		this.assistantHarbormaster = new SimpleStringProperty(assistantHarbormaster);
		this.membership = new SimpleStringProperty(membership);
		this.assistantMembership = new SimpleStringProperty(assistantMembership);
		this.publicity = new SimpleStringProperty(publicity);
		this.assistantPublicity = new SimpleStringProperty(assistantPublicity);
		this.racing = new SimpleStringProperty(racing);
		this.assistantRacing = new SimpleStringProperty(assistantRacing);
		this.safety = new SimpleStringProperty(safety);
		this.assistantSafety = new SimpleStringProperty(assistantSafety);
		this.social = new SimpleStringProperty(social);
		this.assistantSocial = new SimpleStringProperty(assistantSocial);
		this.board11 = new SimpleStringProperty(board11);
		this.board12 = new SimpleStringProperty(board12);
		this.board13 = new SimpleStringProperty(board13);
		this.board14 = new SimpleStringProperty(board14);
		this.board15 = new SimpleStringProperty(board15);
		this.board16 = new SimpleStringProperty(board16);
		this.board17 = new SimpleStringProperty(board17);
		this.board18 = new SimpleStringProperty(board18);
		this.board21 = new SimpleStringProperty(board21);
		this.board22 = new SimpleStringProperty(board22);
		this.board23 = new SimpleStringProperty(board23);
		this.board24 = new SimpleStringProperty(board24);
		this.board25 = new SimpleStringProperty(board25);
		this.board26 = new SimpleStringProperty(board26);
		this.board27 = new SimpleStringProperty(board27);
		this.board28 = new SimpleStringProperty(board28);
		this.board31 = new SimpleStringProperty(board31);
		this.board32 = new SimpleStringProperty(board32);
		this.board33 = new SimpleStringProperty(board33);
		this.board34 = new SimpleStringProperty(board34);
		this.board35 = new SimpleStringProperty(board35);
		this.board36 = new SimpleStringProperty(board36);
		this.board37 = new SimpleStringProperty(board37);
		this.board38 = new SimpleStringProperty(board38);
	}

	public final StringProperty commodoreProperty() {
		return this.commodore;
	}
	

	public final String getCommodore() {
		return this.commodoreProperty().get();
	}
	

	public final void setCommodore(final String commodore) {
		this.commodoreProperty().set(commodore);
	}
	

	public final StringProperty viceCommodoreProperty() {
		return this.viceCommodore;
	}
	

	public final String getViceCommodore() {
		return this.viceCommodoreProperty().get();
	}
	

	public final void setViceCommodore(final String viceCommodore) {
		this.viceCommodoreProperty().set(viceCommodore);
	}
	

	public final StringProperty pastCommodoreProperty() {
		return this.pastCommodore;
	}
	

	public final String getPastCommodore() {
		return this.pastCommodoreProperty().get();
	}
	

	public final void setPastCommodore(final String pastCommodore) {
		this.pastCommodoreProperty().set(pastCommodore);
	}
	

	public final StringProperty treasurerProperty() {
		return this.treasurer;
	}
	

	public final String getTreasurer() {
		return this.treasurerProperty().get();
	}
	

	public final void setTreasurer(final String treasurer) {
		this.treasurerProperty().set(treasurer);
	}
	

	public final StringProperty secretaryProperty() {
		return this.secretary;
	}
	

	public final String getSecretary() {
		return this.secretaryProperty().get();
	}
	

	public final void setSecretary(final String secretary) {
		this.secretaryProperty().set(secretary);
	}
	

	public final StringProperty harbormasterProperty() {
		return this.harbormaster;
	}
	

	public final String getHarbormaster() {
		return this.harbormasterProperty().get();
	}
	

	public final void setHarbormaster(final String harbormaster) {
		this.harbormasterProperty().set(harbormaster);
	}
	

	public final StringProperty assistantHarbormasterProperty() {
		return this.assistantHarbormaster;
	}
	

	public final String getAssistantHarbormaster() {
		return this.assistantHarbormasterProperty().get();
	}
	

	public final void setAssistantHarbormaster(final String assistantHarbormaster) {
		this.assistantHarbormasterProperty().set(assistantHarbormaster);
	}
	

	public final StringProperty membershipProperty() {
		return this.membership;
	}
	

	public final String getMembership() {
		return this.membershipProperty().get();
	}
	

	public final void setMembership(final String membership) {
		this.membershipProperty().set(membership);
	}
	

	public final StringProperty assistantMembershipProperty() {
		return this.assistantMembership;
	}
	

	public final String getAssistantMembership() {
		return this.assistantMembershipProperty().get();
	}
	

	public final void setAssistantMembership(final String assistantMembership) {
		this.assistantMembershipProperty().set(assistantMembership);
	}
	

	public final StringProperty publicityProperty() {
		return this.publicity;
	}
	

	public final String getPublicity() {
		return this.publicityProperty().get();
	}
	

	public final void setPublicity(final String publicity) {
		this.publicityProperty().set(publicity);
	}
	

	public final StringProperty assistantPublicityProperty() {
		return this.assistantPublicity;
	}
	

	public final String getAssistantPublicity() {
		return this.assistantPublicityProperty().get();
	}
	

	public final void setAssistantPublicity(final String assistantPublicity) {
		this.assistantPublicityProperty().set(assistantPublicity);
	}
	

	public final StringProperty racingProperty() {
		return this.racing;
	}
	

	public final String getRacing() {
		return this.racingProperty().get();
	}
	

	public final void setRacing(final String racing) {
		this.racingProperty().set(racing);
	}
	

	public final StringProperty assistantRacingProperty() {
		return this.assistantRacing;
	}
	

	public final String getAssistantRacing() {
		return this.assistantRacingProperty().get();
	}
	

	public final void setAssistantRacing(final String assistantRacing) {
		this.assistantRacingProperty().set(assistantRacing);
	}
	

	public final StringProperty safetyProperty() {
		return this.safety;
	}
	

	public final String getSafety() {
		return this.safetyProperty().get();
	}
	

	public final void setSafety(final String safety) {
		this.safetyProperty().set(safety);
	}
	

	public final StringProperty assistantSafetyProperty() {
		return this.assistantSafety;
	}
	

	public final String getAssistantSafety() {
		return this.assistantSafetyProperty().get();
	}
	

	public final void setAssistantSafety(final String assistantSafety) {
		this.assistantSafetyProperty().set(assistantSafety);
	}
	

	public final StringProperty socialProperty() {
		return this.social;
	}
	

	public final String getSocial() {
		return this.socialProperty().get();
	}
	

	public final void setSocial(final String social) {
		this.socialProperty().set(social);
	}
	

	public final StringProperty assistantSocialProperty() {
		return this.assistantSocial;
	}
	

	public final String getAssistantSocial() {
		return this.assistantSocialProperty().get();
	}
	

	public final void setAssistantSocial(final String assistantSocial) {
		this.assistantSocialProperty().set(assistantSocial);
	}
	

	public final StringProperty board11Property() {
		return this.board11;
	}
	

	public final String getBoard11() {
		return this.board11Property().get();
	}
	

	public final void setBoard11(final String board11) {
		this.board11Property().set(board11);
	}
	

	public final StringProperty board12Property() {
		return this.board12;
	}
	

	public final String getBoard12() {
		return this.board12Property().get();
	}
	

	public final void setBoard12(final String board12) {
		this.board12Property().set(board12);
	}
	

	public final StringProperty board13Property() {
		return this.board13;
	}
	

	public final String getBoard13() {
		return this.board13Property().get();
	}
	

	public final void setBoard13(final String board13) {
		this.board13Property().set(board13);
	}
	

	public final StringProperty board14Property() {
		return this.board14;
	}
	

	public final String getBoard14() {
		return this.board14Property().get();
	}
	

	public final void setBoard14(final String board14) {
		this.board14Property().set(board14);
	}
	

	public final StringProperty board15Property() {
		return this.board15;
	}
	

	public final String getBoard15() {
		return this.board15Property().get();
	}
	

	public final void setBoard15(final String board15) {
		this.board15Property().set(board15);
	}
	

	public final StringProperty board16Property() {
		return this.board16;
	}
	

	public final String getBoard16() {
		return this.board16Property().get();
	}
	

	public final void setBoard16(final String board16) {
		this.board16Property().set(board16);
	}
	

	public final StringProperty board17Property() {
		return this.board17;
	}
	

	public final String getBoard17() {
		return this.board17Property().get();
	}
	

	public final void setBoard17(final String board17) {
		this.board17Property().set(board17);
	}
	

	public final StringProperty board18Property() {
		return this.board18;
	}
	

	public final String getBoard18() {
		return this.board18Property().get();
	}
	

	public final void setBoard18(final String board18) {
		this.board18Property().set(board18);
	}
	

	public final StringProperty board21Property() {
		return this.board21;
	}
	

	public final String getBoard21() {
		return this.board21Property().get();
	}
	

	public final void setBoard21(final String board21) {
		this.board21Property().set(board21);
	}
	

	public final StringProperty board22Property() {
		return this.board22;
	}
	

	public final String getBoard22() {
		return this.board22Property().get();
	}
	

	public final void setBoard22(final String board22) {
		this.board22Property().set(board22);
	}
	

	public final StringProperty board23Property() {
		return this.board23;
	}
	

	public final String getBoard23() {
		return this.board23Property().get();
	}
	

	public final void setBoard23(final String board23) {
		this.board23Property().set(board23);
	}
	

	public final StringProperty board24Property() {
		return this.board24;
	}
	

	public final String getBoard24() {
		return this.board24Property().get();
	}
	

	public final void setBoard24(final String board24) {
		this.board24Property().set(board24);
	}
	

	public final StringProperty board25Property() {
		return this.board25;
	}
	

	public final String getBoard25() {
		return this.board25Property().get();
	}
	

	public final void setBoard25(final String board25) {
		this.board25Property().set(board25);
	}
	

	public final StringProperty board26Property() {
		return this.board26;
	}
	

	public final String getBoard26() {
		return this.board26Property().get();
	}
	

	public final void setBoard26(final String board26) {
		this.board26Property().set(board26);
	}
	

	public final StringProperty board27Property() {
		return this.board27;
	}
	

	public final String getBoard27() {
		return this.board27Property().get();
	}
	

	public final void setBoard27(final String board27) {
		this.board27Property().set(board27);
	}
	

	public final StringProperty board28Property() {
		return this.board28;
	}
	

	public final String getBoard28() {
		return this.board28Property().get();
	}
	

	public final void setBoard28(final String board28) {
		this.board28Property().set(board28);
	}
	

	public final StringProperty board31Property() {
		return this.board31;
	}
	

	public final String getBoard31() {
		return this.board31Property().get();
	}
	

	public final void setBoard31(final String board31) {
		this.board31Property().set(board31);
	}
	

	public final StringProperty board32Property() {
		return this.board32;
	}
	

	public final String getBoard32() {
		return this.board32Property().get();
	}
	

	public final void setBoard32(final String board32) {
		this.board32Property().set(board32);
	}
	

	public final StringProperty board33Property() {
		return this.board33;
	}
	

	public final String getBoard33() {
		return this.board33Property().get();
	}
	

	public final void setBoard33(final String board33) {
		this.board33Property().set(board33);
	}
	

	public final StringProperty board34Property() {
		return this.board34;
	}
	

	public final String getBoard34() {
		return this.board34Property().get();
	}
	

	public final void setBoard34(final String board34) {
		this.board34Property().set(board34);
	}
	

	public final StringProperty board35Property() {
		return this.board35;
	}
	

	public final String getBoard35() {
		return this.board35Property().get();
	}
	

	public final void setBoard35(final String board35) {
		this.board35Property().set(board35);
	}
	

	public final StringProperty board36Property() {
		return this.board36;
	}
	

	public final String getBoard36() {
		return this.board36Property().get();
	}
	

	public final void setBoard36(final String board36) {
		this.board36Property().set(board36);
	}
	

	public final StringProperty board37Property() {
		return this.board37;
	}
	

	public final String getBoard37() {
		return this.board37Property().get();
	}
	

	public final void setBoard37(final String board37) {
		this.board37Property().set(board37);
	}
	

	public final StringProperty board38Property() {
		return this.board38;
	}
	

	public final String getBoard38() {
		return this.board38Property().get();
	}
	

	public final void setBoard38(final String board38) {
		this.board38Property().set(board38);
	}
	
}
