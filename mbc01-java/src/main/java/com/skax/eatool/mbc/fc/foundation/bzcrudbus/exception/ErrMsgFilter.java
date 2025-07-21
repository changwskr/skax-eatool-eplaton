package com.skax.eatool.mbc.fc.foundation.bzcrudbus.exception;

/**
 * 에러 메시지 필터 클래스
 * 
 * 프로그램명: ErrMsgFilter.java
 * 설명: SQL 예외 메시지를 파싱하고 필터링하는 유틸리티 클래스
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - SQL 예외 코드 추출
 * - SQL 예외 메시지 추출
 * - 예외 이름 추출
 * - 중복 키 예외 처리
 */
public class ErrMsgFilter {
    /** 에러 메시지 */
    private String errmsg = "";

    /**
     * 에러 메시지를 받는 생성자
     * 
     * @param msg 에러 메시지
     */
    public ErrMsgFilter(String msg) {
        this.errmsg = msg;
    }

    /**
     * SQL 예외 코드를 추출한다.
     * 
     * @return SQL 예외 코드
     */
    public String getSQLExceptionCode() {
        int Sqlindex = 0;
        String sqlerr = "java.sql.SQLException:";
        String sqlerr2 = "DuplicateKeyException:";

        Sqlindex = this.errmsg.indexOf(sqlerr);
        String tmpMsg = this.errmsg.substring(Sqlindex + sqlerr.length());
        tmpMsg = tmpMsg.substring(0, tmpMsg.indexOf(":"));
        tmpMsg = tmpMsg.substring(tmpMsg.indexOf("-") + 1);

        Sqlindex = 0;

        if ((Sqlindex = this.errmsg.indexOf(sqlerr2)) > 0) {
            tmpMsg = this.errmsg.substring(Sqlindex + sqlerr2.length());
            tmpMsg = tmpMsg.substring(0, tmpMsg.indexOf(":"));
            tmpMsg = tmpMsg.substring(tmpMsg.indexOf("-") + 1);
        }
        return tmpMsg;
    }

    /**
     * SQL 예외 메시지를 추출한다.
     * 
     * @return SQL 예외 메시지
     */
    public String getSQLExceptionMsg() {
        int Sqlindex = 0;
        String sqlerr = "java.sql.SQLException:";
        String sqlerr2 = "DuplicateKeyException:";

        Sqlindex = this.errmsg.indexOf(sqlerr);
        String tmpMsg = this.errmsg.substring(Sqlindex + sqlerr.length());

        Sqlindex = 0;

        if ((Sqlindex = this.errmsg.indexOf(sqlerr2)) > 0) {
            tmpMsg = this.errmsg.substring(Sqlindex + sqlerr2.length());
        }

        return tmpMsg;
    }

    /**
     * 예외 이름을 추출한다.
     * 예시: nested exception is: java.sql.SQLException: ORA-00001: unique constraint
     * (TEST.PK_S3ORDERM) violated;
     * 
     * @return 예외 이름
     */
    public String getExceptionName() {
        int Sqlindex = 0;
        String sqlerr = "is: ";

        Sqlindex = this.errmsg.lastIndexOf(sqlerr);
        String tmpMsg = this.errmsg.substring(Sqlindex + sqlerr.length());
        tmpMsg = tmpMsg.substring(0, tmpMsg.indexOf(":"));

        return tmpMsg;
    }

}