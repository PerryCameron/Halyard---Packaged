package com.ecsail.enums;

import java.util.Arrays;

public enum MemberType {
    PRIMARY(1, "Primary"),
    SECONDARY(2, "Secondary"),
    DEPENDANT(3, "Dependant");

    private Integer code;
    private String text;

    private MemberType(Integer code, String text) {
        this.code = code;
        this.text = text;
    }

    public Integer getCode() {
        return code;
    }

    public String getText() {
        return text;
    }

    public static MemberType getByCode(int memberCode) {
        return Arrays.stream(MemberType.values())
				.filter(g -> g.code == memberCode)
				.findFirst().orElse(null);
    }
    @Override
    public String toString() {
        return this.text;
    }
}
