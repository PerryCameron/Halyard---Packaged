package com.ecsail.dto;

public record BoardPositionDTO(long id, String position, String identifier, int order, boolean isOfficer, boolean isChair, boolean isAssist) { }
