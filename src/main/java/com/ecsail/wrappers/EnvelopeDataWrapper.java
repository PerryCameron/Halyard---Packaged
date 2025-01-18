package com.ecsail.wrappers;


import com.ecsail.dto.MembershipListDTO;

public class EnvelopeDataWrapper {
    MembershipListDTO returnMembership;
    MembershipListDTO ToMembership;
    String[] roles;
    String envelopeType;

    public EnvelopeDataWrapper(MembershipListDTO returnMembership, MembershipListDTO toMembership, String[] roles, String envelopeType) {
        this.returnMembership = returnMembership;
        ToMembership = toMembership;
        this.roles = roles;
        this.envelopeType = envelopeType;
    }

    public String getReturnAddress() {
        return returnMembership.getAddress();
    }

    public String getReturnCityState() {
        return returnMembership.getCity() + ", "
                + returnMembership.getState() + " " + returnMembership.getZip();
    }

    public String getToAddress() {
        return ToMembership.getAddress();
    }

    public String getToCityState() {
        return ToMembership.getCity() + ", "
                + ToMembership.getState() + " " + ToMembership.getZip();
    }

    public String getReturnName() {
        for(String role : roles) {
            switch(role) {
                case "ROLE_MEMBERSHIP" -> {
                    return "ECSC Membership";
                }
                case "ROLE_HARBORMASTER" -> {
                    return "ECSC Harbormaster";
                }
                case "ROLE_TREASURER" -> {
                    return "ECSC Treasurer";
                }
            }
        }
        return returnMembership.getFullName();
    }

    public String getToName() {
        return ToMembership.getFullName() + " #" + ToMembership.getMembershipId();
    }

    public EnvelopeDataWrapper() {
    }

    public MembershipListDTO getReturnMembership() {
        return returnMembership;
    }

    public void setReturnMembership(MembershipListDTO returnMembership) {
        this.returnMembership = returnMembership;
    }

    public MembershipListDTO getToMembership() {
        return ToMembership;
    }

    public void setToMembership(MembershipListDTO toMembership) {
        ToMembership = toMembership;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public String getEnvelopeType() {
        return envelopeType;
    }

    public void setEnvelopeType(String envelopeType) {
        this.envelopeType = envelopeType;
    }
}
