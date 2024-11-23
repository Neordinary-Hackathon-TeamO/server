package org.example.secret_santa.common.exception;

import org.example.secret_santa.member.exception.MemberDuplicationException;
import org.example.secret_santa.member.exception.MemberNotFoundException;
import org.springframework.http.HttpStatus;

import java.util.LinkedHashMap;
import java.util.Map;

public class ExceptionMapper { // 예외 객체 -> 예외 상태로 바꿔주는 mapper

    private static final Map<Class<? extends Exception>, ExceptionSituation> mapper = new LinkedHashMap<>();

    static {
        setUpMemberException();
    }

    private static void setUpMemberException() {
        mapper.put(MemberNotFoundException.class,
                ExceptionSituation.of("해당 사용자가 존재하지 않습니다.", HttpStatus.NOT_FOUND, 1000));
        mapper.put(MemberDuplicationException.class,
                ExceptionSituation.of("해당 사용자가 중복됩니다.", HttpStatus.CONFLICT, 1001));
    }

    public static ExceptionSituation getSituationOf(Exception exception) {
        return mapper.get(exception.getClass());
    }

}
