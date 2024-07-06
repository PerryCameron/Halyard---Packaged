package com.ecsail.dto;

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
    MembershipIdDTO currentId; // this will be the current year only
    List<BoatDTO> boats; // all boats tied to this membership
    List<PersonDTO> people; // all people tied to this membership through common MS_ID
    // getters and setters removed for brevity...
}
