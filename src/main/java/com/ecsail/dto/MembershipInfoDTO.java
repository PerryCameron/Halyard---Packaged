package com.ecsail.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.List;

public class MembershipInfoDTO {
    private int msId;
    private int pId;
    private LocalDate joinDate;
    private String memType;
    private String address;
    private String city;
    private String state;
    private String zip;
    private int membershipId;
    private boolean renew;
    private int fiscalYear;
    List<BoatDTO> boats; // all boats tied to this membership
    List<PersonDTO> people; // all people tied to this membership through common MS_ID
    SlipDTO slip;

    public MembershipInfoDTO(@JsonProperty("MS_ID") int msId,
                             @JsonProperty("P_ID") int pId,
                             @JsonProperty("JOIN_DATE") LocalDate joinDate,
                             @JsonProperty("MEM_TYPE") String memType,
                             @JsonProperty("ADDRESS") String address,
                             @JsonProperty("CITY") String city,
                             @JsonProperty("STATE") String state,
                             @JsonProperty("ZIP") String zip,
                             @JsonProperty("MEMBERSHIP_ID") int membershipId,
                             @JsonProperty("RENEW") boolean renew,
                             @JsonProperty("FISCAL_YEAR") int fiscalYear
    )
                             {
        this.msId = msId;
        this.pId = pId;
        this.joinDate = joinDate;
        this.memType = memType;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.membershipId = membershipId;
        this.renew = renew;
        this.fiscalYear = fiscalYear;
    }

    public int getMsId() {
        return msId;
    }

    public void setMsId(int msId) {
        this.msId = msId;
    }

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }

    public String getMemType() {
        return memType;
    }

    public void setMemType(String memType) {
        this.memType = memType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public int getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(int membershipId) {
        this.membershipId = membershipId;
    }

    public boolean isRenew() {
        return renew;
    }

    public void setRenew(boolean renew) {
        this.renew = renew;
    }

    public int getFiscalYear() {
        return fiscalYear;
    }

    public void setFiscalYear(int fiscalYear) {
        this.fiscalYear = fiscalYear;
    }

    public List<BoatDTO> getBoats() {
        return boats;
    }

    public void setBoats(List<BoatDTO> boats) {
        this.boats = boats;
    }

    public List<PersonDTO> getPeople() {
        return people;
    }

    public void setPeople(List<PersonDTO> people) {
        this.people = people;
    }

    public SlipDTO getSlip() {
        return slip;
    }

    public void setSlip(SlipDTO slip) {
        this.slip = slip;
    }

    @Override
    public String toString() {
        return "MembershipInfoDTO{" +
                "msId=" + msId +
                ", pId=" + pId +
                ", joinDate=" + joinDate +
                ", memType='" + memType + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zip='" + zip + '\'' +
                ", membershipId=" + membershipId +
                ", renew=" + renew +
                ", fiscalYear=" + fiscalYear +
                ", boats=" + boats +
                ", people=" + people +
                ", slip=" + slip +
                '}';
    }
}
