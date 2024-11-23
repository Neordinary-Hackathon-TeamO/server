package org.example.secret_santa.common.enums;

public enum TeamType {
    STUDENT, WORKER;

    // 문자열을 TeamType으로 변환하는 메서드
    public static TeamType toType(String type) {
        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("Type cannot be null or blank");
        }
        switch (type.trim().toUpperCase()) {
            case "STUDENT":
                return TeamType.STUDENT;
            case "WORKER":
                return TeamType.WORKER;
            default:
                throw new IllegalArgumentException("Unknown type: " + type);
        }
    }
}

