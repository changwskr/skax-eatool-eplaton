package com.skax.eatool.mbc.fc.foundation.bzcrudbus.foundation.general;

import java.util.*;
import java.text.*;
import java.net.*;
import java.io.*;
import java.sql.*;
import java.util.Date;

/**
 * 공통 유틸리티 클래스
 * 
 * 프로그램명: CommonUtil.java
 * 설명: ePlaton 해외 뱅킹 패키지의 공통 유틸리티 기능을 제공하는 클래스
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - 문자열 처리 및 변환
 * - 숫자 변환 및 포맷팅
 * - 날짜/시간 처리
 * - 파일 처리
 * - 데이터베이스 연결
 * - 인코딩 변환
 * - 시스템 정보 조회
 * 
 * Copyright: Copyright (c) 2004 IMS System CO., LTD.
 * Company: IMS System
 * 
 * @version 1.0
 */
public class CommonUtil {

    // private static SimpleDateFormat timeFormat = new
    // SimpleDateFormat("HHmmssSS");
    // private static SimpleDateFormat dateFormat = new
    // SimpleDateFormat("yyyyMMdd");

    /**
     * 문자열이 숫자인지 확인하는 메서드
     * 예시: CHECKisdigit("12a","12a".length())
     * 
     * @param string   확인할 문자열
     * @param piStrLen 문자열 길이
     * @return 숫자이면 true, 아니면 false
     */
    public static final boolean CHECKisdigit(String string, int piStrLen) {
        for (int index = 0; index < piStrLen; index++) {
            if (string.charAt(index) < 48 || string.charAt(index) > 57) {
                return (false);
            }
        }
        return true;
    }

    /**
     * 구분자로 분리된 문자열에서 특정 순서의 문자열을 추출하는 메서드
     * 예시: request_name = "cashCard-listCashcard" = "system" + "-" + "method"
     * catchSTRINGseq("cashCard-listCashcard",1,"-");
     * '-' 구분자를 기준으로 첫 번째 문자열을 반환한다.
     * 
     * @param request_name 요청 이름
     * @param seq          순서
     * @param tag          구분자
     * @return 추출된 문자열
     */
    public static String catchSTRINGseq(String request_name, int seq,
            String tag) {
        if (seq == 0)
            seq = 500;
        if (tag == null)
            return null;

        StringTokenizer tokenizer = new StringTokenizer(request_name, tag);
        String[] matrix = new String[seq];

        int i = 0;
        while (tokenizer.hasMoreTokens()) {
            matrix[i] = tokenizer.nextToken();
            // System.out.println( "Tokenized String : " + matrix[i] );
            i++;
            if (i == seq)
                break;
        }
        return matrix[seq - 1];
    }

    /**
     * 문자열 치환 메서드
     * 
     * @param str     바꿀 대상 문자열
     * @param pattern 찾을 문자열
     * @param replace 바꿀 문자열
     *                예시: str="aaaaa k k", pattern="k k", replace="w w", return =
     *                "aaaaa w w"
     * @return 치환된 문자열
     */
    static String replace(String str, String pattern, String replace) {

        int s = 0; // 찾기 시작할 위치
        int e = 0; // StringBuffer에 append 할 위치
        StringBuffer result = new StringBuffer(); // 결과 문자열을 담을 곳

        while ((e = str.indexOf(pattern, s)) >= 0) {
            result.append(str.substring(s, e));
            result.append(replace);
            s = e + pattern.length();
        }
        result.append(str.substring(s));
        return result.toString();
    }

    /**
     * 시간 간격을 계산한다.
     * 
     * @param psStartTimer 시작 시간
     * @param psEndTimer   종료 시간
     * @return 시간 간격(초)
     */
    public int Interval_GetTimeSec(String psStartTimer, String psEndTimer) {
        String ss = null;
        String mm = null;
        String ee = null;
        int startsec = 99999999;
        int endsec = 99999999;

        // System.out.println("-------start Interval_GetTimesec");
        /***********************************************************************/
        /* 시작 시간 */
        /***********************************************************************/

        ss = psStartTimer.substring(0, 2);
        mm = psStartTimer.substring(2, 4);
        ee = psStartTimer.substring(4, 6);
        startsec = Str2Int(ss) * 60 * 60 + Str2Int(mm) * 60 + Str2Int(ee);

        /***********************************************************************/
        /* 종료 시간 */
        /***********************************************************************/
        ss = psEndTimer.substring(0, 2);
        mm = psEndTimer.substring(2, 4);
        ee = psEndTimer.substring(4, 6);
        endsec = Str2Int(ss) * 60 * 60 + Str2Int(mm) * 60 + Str2Int(ee);

        // System.out.println("-------end Interval_GetTimesec " + new
        // ConvUtil().Int2Str(endsec-startsec));

        return (endsec - startsec);

    }

    /**
     * 시스템 출력 메서드
     * 
     * @param msg 출력할 메시지
     */
    public static final void systemOut(String msg) {
        System.out.println(msg);
    }

    /**
     * len 길이 만큼 문자열을 자른다.
     *
     * @param str 입력 문자열
     * @param len 문자열 길이
     * @return 잘린 문자열
     */
    public static final String cutString(String str, int len) {
        if (str != null) {
            if (str.length() < len + 1) {
                return str;
            } else {
                return str.substring(0, len);
            }
        }
        return "";
    }

    /**
     * 특정 위치에서 문자열을 자른다.
     * 
     * @param str     문자열
     * @param startpo 시작 위치
     * @param len     길이
     * @return 잘린 문자열
     */
    public static final String cutString(String str, int startpo, int len) {
        if (str != null) {
            return str.substring(startpo, (len + 1));
        }
        return "";
    }

    /**
     * ASCII를 KSC5601로 변환한다.
     * 
     * @param str 변환할 문자열
     * @throws UnsupportedEncodingException 인코딩 예외
     * @return 변환된 문자열
     */
    public static final String ascToksc(String str) throws UnsupportedEncodingException {

        if (str == null)
            return "";
        return new String(str.getBytes("8859_1"), "KSC5601");

    }

    /**
     * KSC5601을 8859_1로 변환한다.
     * 
     * @param str 변환할 문자열
     * @throws UnsupportedEncodingException 인코딩 예외
     * @return 변환된 문자열
     */
    public static final String kscToasc(String str) throws UnsupportedEncodingException {

        if (str == null)
            return "";
        return new String(str.getBytes("KSC5601"), "8859_1");

    }

    /**
     * 문자열의 한글/영문 여부를 확인한다.
     * return val : 0 한글, 1 영문, 2 한글/영문
     * 
     * @param data 확인할 문자열
     * @return int
     */
    public static final int checkHan(String data) {
        int code = (int) data.charAt(0);
        int result = 1; // 영문인 경우
        if (code > 127) {
            result = 2; // 한글인 경우
        } else if (code >= 48 && code <= 57) {
            result = 0; // 숫자인 경우
        }
        return result;
    }

    /**
     * 문자열이 한글/영문 문자를 포함하는지 확인한다.
     * 문자열의 바이트 길이와 문자열 길이가 다르면 한글/영문 문자를 포함한다고 판단한다.
     * 
     * @param str 확인할 문자열
     * @return boolean
     */
    public static final boolean isHanGul(String str) {
        // 문자열의 바이트 길이와 문자열 길이가 다르면 한글/영문 문자를 포함한다고 판단
        if (str.length() == str.getBytes().length) {
            System.out.println(str + " is not Han-gul");
            return false;
        } else {
            System.out.println(str + " has Han-gul");
            return true;
        }
    }

    /**
     * 문자열이 null인지 확인하고, null이면 빈 문자열을 반환한다.
     * 
     * @param str 확인할 문자열
     * @return 문자열
     */
    public static final String nullCheckString(String str) {
        if (str != null)
            return str;
        return "";

    }

    /**
     *
     * @param time long
     * @return String
     */
    public static final String getTime(long time) {

        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss:SS");
        return (String) sdf.format(new java.util.Date(time));
    }

    /**
     *
     * @param time long
     * @param msg  String
     */
    public static final void getTime(long time, String msg) {

        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss:SS");
        System.out.println(msg + " : " +
                (String) sdf.format(new java.util.Date(time)));

    }

    /**
     *
     * @return String
     */
    public static String getTime() {

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:SS");
        return (String) sdf.format(new java.util.Date(System.currentTimeMillis()));
    }

    public static String getTimeFreeFormat(String format) {

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return (String) sdf.format(new java.util.Date(System.currentTimeMillis()));
    }

    /**
     * 날짜 변환
     *
     */

    /**
     *
     * @param str String
     * @return int
     */
    public static final int STR2INT(String str) {
        int i = 0;
        try {
            i = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            System.out.println("STR2INT ERROR" + e.toString());
        }
        return i;
    }

    /**
     *
     * @param str String
     * @return int
     */
    public static final int Str2Int(String str) {
        int i = Integer.valueOf(str).intValue();
        // System.out.println(i);
        return (i);
    }

    /**
     *
     * @param str String
     * @return int
     */
    public static final int atoi(String str) {
        int i = Integer.valueOf(str).intValue();
        // System.out.println(i);
        return (i);
    }

    /**
     *
     * @param str String
     * @return long
     */
    public static final long Str2Long(String str) {
        long l = Long.valueOf(str).longValue();
        // System.out.println(l);
        return (l);
    }

    /**
     *
     * @param str String
     * @return float
     */
    public static final float Str2Float(String str) {
        float f = Float.valueOf(str).floatValue();
        // System.out.println(f);
        return (f);
    }

    /**
     *
     * @param str String
     * @return double
     */
    public static final double Str2Double(String str) {
        double d = Double.valueOf(str).doubleValue();
        // System.out.println(d);
        return (d);
    }

    /**
     *
     * @param n int
     * @return String
     */
    public static final String Int2Str(int n) {
        String s = new Integer(n).toString();
        return (s);
    }

    /**
     *
     * @param n float
     * @return String
     */
    public static final String Float2Str(float n) {
        String s1 = new Float(n).toString();
        return (s1);
    }

    /**
     *
     * @param l long
     * @return String
     */
    public static final String Long2Str(long l) {
        String s = new Long(l).toString();
        return (s);
    }

    /**
     *
     * @param d double
     * @return String
     */
    public static final String Double2Str(double d) {
        String s = new Double(d).toString();
        return (s);
    }

    /**
     * 대소문자 변환
     *
     */
    /**
     *
     * @param str String
     * @return String
     */
    public static final String TOupper(String str) {
        String up = str.toUpperCase();
        return (up);

    }

    /**
     *
     * @param str String
     * @return String
     */
    public static final String TOlower(String str) {
        String lo = str.toLowerCase();
        return (lo);
    }

    /**
     *
     * @param ch char
     * @return String
     */
    public static final String Char2Str(char ch) {
        String lo = new Character(ch).toString();
        return (lo);
    }

    /**
     * 문자열 비교
     *
     */
    /**
     *
     * @param org String
     * @param tar String
     * @return boolean
     */
    public static final boolean exist_str(String org, String tar) {
        if (org.regionMatches(org.indexOf(tar), tar, 0, tar.length())) {
            return true;
        } else {
            return false;
        }
    }

    // org : 1234567 tar : 345 -> org tar Ʈ ԵǾ ִ ȮѴ.
    /**
     *
     * @param org String
     * @param tar String
     * @return boolean
     */
    public static final boolean check_contain_string(String org, String tar) {
        if (org.regionMatches(org.indexOf(tar), tar, 0, tar.length())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @param st1 String
     * @param st2 String
     * @return boolean
     */
    public static final boolean memcmp(String st1, String st2) {
        if (st1.equals(st2))
            return (true);
        else
            return (false);
    }

    /**
     *
     * @param envfilename String
     * @param tmp         String
     * @return String
     */
    public synchronized static String GetEnvValue(String envfilename,
            String tmp) {
        String ConfigPath = null;
        Properties p = new Properties();
        FileInputStream fis = null;
        String tmpv = null;
        try {
            fis = new FileInputStream(envfilename);
            p.load(fis);
            tmpv = p.getProperty(tmp);
        } catch (IOException e) {
            System.out.println("GetEnvValue() ���� ȯ����ý�12 ���ܹ߻�");
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (Exception ignore) {
            }
        }
        return tmpv;
    }

    /**
     *
     * @param st1 String
     * @param st2 String
     * @param len int
     * @return boolean
     */
    public static final boolean memcmp(String st1, String st2, int len) {
        String s1 = null;
        String s2 = null;

        try {
            s1 = st1.substring(0, len);
            s2 = st2.substring(0, len);
        } catch (Exception e) {
            e.printStackTrace();
            // System.out.println(e.getMessage());
            return false;
        }

        if (s1.equals(s2))
            return (true);
        else
            return (false);
    }

    /**
     *
     * @param org String
     * @param len int
     * @return String
     */
    public static final String memcpy(String org, int len) {
        String tar = null;
        try {
            String s1 = org.substring(0, len);
            System.out.println("s1 " + s1);
            tar = s1;
        } catch (Exception e) {
            e.printStackTrace();
            // System.out.println(e.getMessage());
            return null;
        }
        return (tar);
    }

    /**
     * 파일 처리
     *
     */

    // 파일 정보 조회
    // filepath ex) C:\\MYTEST\\CHB\\src\\common
    /**
     *
     * @param filepath String
     * @return int
     */
    public static final int FileInfo(String filepath) {
        if (filepath.length() == 0) {
            return -1;
        }

        File f1 = new File(filepath);
        String[] ls;

        int i;
        for (ls = f1.list(), i = 0; ls != null && i < ls.length; printOne(new File(f1, ls[i])), i++)
            ;
        return 0;
    }

    /**
     *
     * @param f File
     */
    public static final void printOne(File f) {
        if (f.exists()) {
            System.out.print(f.canRead() ? "r" : "-");
            System.out.print(f.canWrite() ? "w" : "-");
            System.out.print(f.isDirectory() ? "x" : "-");
            System.out.print('\t');

            System.out.print(f.length());
            System.out.print('\t');

            System.out.print(new java.util.Date(f.lastModified()));
            System.out.print('\t');
        } else {
            System.out.print("\t\t\t\t\t 파일이 존재하지 않습니다.");
        }

        System.out.println("파일명 : " + f.getName());
    }

    // -------------------------------------------------------------------
    // _cpfile2file : copy iname oname
    // ex) iname == "./kk.txt" oname=="./kk.txt1"
    // rtn : 복사된 파일 크기, error : -1
    // -------------------------------------------------------------------
    /**
     *
     * @param iname String
     * @param oname String
     * @return int
     */
    public static final int cpFile2File(String iname, String oname) {
        int fsize = 0;

        try {
            FileInputStream ifp = new FileInputStream(iname);
            FileOutputStream ofp = new FileOutputStream(oname);
            int ch;

            while ((ch = ifp.read()) > -1) {
                ofp.write(ch);
                fsize++;
            }
            ifp.close();
            ofp.close();

        } catch (IOException e) {
            System.err.println("I/O Exception error." + e.getMessage());
            return -1;
        }

        return fsize;
    }

    // 디렉토리 삭제
    // args : C:\windows
    /**
     *
     * @param args String
     * @throws Exception
     */
    public static final void deleteDIR(String args) throws Exception {

        File directory = new File(args);

        if (!directory.isDirectory()) {
            System.err.println(args + " is not directory");
            return;
        }

        deleteAll(directory);
    }

    /**
     *
     * @param directory File
     */
    public static final void deleteAll(File directory) {
        File[] files = directory.listFiles();

        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory())
                deleteAll(files[i]);
            else
                files[0].delete();
        }
        directory.delete();
    }

    // -------------------------------------------------------------------
    // appendFileWrite : write a file (mode append)
    // ex) path2filename=="./kk.txt"
    // writebuff : 쓸 문자열
    // -1 : error , 0 : success
    // -------------------------------------------------------------------
    /**
     *
     * @param path2filename String
     * @param writebuff     String
     * @return int
     */
    public static final int appendFileWrite(String path2filename,
            String writebuff) {
        /*
         * if ( writebuff.length() > 2024 ){
         * String tmpbuff;
         * tmpbuff=writebuff.substring(1,2024);
         * 
         * try {
         * FileOutputStream fos = new FileOutputStream(path2filename, true);
         * PrintStream ps = new PrintStream(fos);
         * ps.print(tmpbuff+'\n');
         * ps.close();
         * }catch(Exception e){
         * Syste.out.println("appendFileWrite Exception");
         * e.printStackTrace();
         * return(-1);
         * }
         * }
         * 
         * else{
         * try {
         * FileOutputStream fos = new FileOutputStream(path2filename, true);
         * PrintStream ps = new PrintStream(fos);
         * ps.print(writebuff+'\n');
         * ps.close();
         * }catch(Exception e){
         * e.printStackTrace();
         * Syste.out.println("appendFileWrite Exception");
         * }
         * }
         */

        try {
            FileOutputStream fos = new FileOutputStream(path2filename, true);
            PrintStream ps = new PrintStream(fos);
            ps.print(writebuff + '\n');
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
            // System.out.println("appendFileWrite Exception");
            return -1;
        }
        return 0;
    }

    /**
     * 시스템 정보 조회
     *
     */
    /**
     *
     * @return String
     */
    public static final String getLocalHostName() {
        InetAddress Address = null;

        try {
            Address = InetAddress.getLocalHost();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return (null);
        }
        return (Address.getHostName());
    }

    /**
     *
     * @return String
     */
    public static final String GetHostName() {
        InetAddress Address = null;

        try {
            Address = InetAddress.getLocalHost();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return (null);
        }
        return (Address.getHostName());
    }

    /**
     *
     * @return String
     */
    public static final String GetSysTime() {
        return new SimpleDateFormat("HHmmssSSS").format(new java.util.Date()).substring(0, 8);
    }

    /**
     *
     * @param conn    Connection
     * @param itvdate String
     * @return String
     */
    public static final String GetDate(Connection conn, String itvdate) {
        String seqtmp = null;
        int seq = 0;
        Statement stmt = null;
        ResultSet rs = null;

        try {

            String tSQL = "SELECT TO_CHAR( SYSDATE - " + itvdate +
                    ", 'YYYYMMDD' ) FROM DUAL";
            // System.out.println(tSQL);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(tSQL);
            rs.next();

            seq = rs.getInt(1);
            // System.out.println("bbbbbbbbbbbbbbbbbb" + seq);
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        if (seq == 0)
            return null;
        // System.out.println("aaaaaaaaaaaa" + seq);
        return (Int2Str(seq));
    }

    /**
     *
     * @return String
     */
    public static final String GetSysDate() {
        return new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
    }

    /**
     * 현재 날짜와 시간을 반환한다.
     * 
     * @return String - 현재 날짜, 시간
     */
    /**
     *
     * @return String
     */
    public static final String getCurrentDate() {
        String currentDate = "";

        Calendar c = Calendar.getInstance();

        String yyyy = new Integer(c.get(Calendar.YEAR)).toString();
        String mm = new Integer(c.get(Calendar.MONTH) + 1).toString();
        String dd = new Integer(c.get(Calendar.DATE)).toString();
        String hr = new Integer(c.get(Calendar.HOUR)).toString();
        String min = new Integer(c.get(Calendar.MINUTE)).toString();
        String sec = new Integer(c.get(Calendar.SECOND)).toString();

        if (mm.trim().length() == 1) {
            mm = "0" + mm;
        }

        if (dd.trim().length() == 1) {
            dd = "0" + dd;
        }

        currentDate = mm + "/" + dd + "/" + yyyy + "-" + ZeroToStr(hr,
                2) + ":" + ZeroToStr(min, 2) + ":" + ZeroToStr(sec, 2);

        return currentDate;
    }

    /**
     * 시간 간격을 계산한다.
     * - 초 단위로 계산
     */
    /**
     *
     * @param psStartTimer String
     * @param psEndTimer   String
     * @return int
     */
    public static final int ItvSec(String psStartTimer, String psEndTimer) {

        String ss = null;
        String mm = null;
        String ee = null;
        int startsec = 99999999;
        int endsec = 99999999;

        try {
            ss = psStartTimer.substring(0, 2);
            mm = psStartTimer.substring(2, 4);
            ee = psStartTimer.substring(4, 6);
            startsec = atoi(ss) * 60 * 60 + atoi(mm) * 60 + atoi(ee);

            ss = psEndTimer.substring(0, 2);
            mm = psEndTimer.substring(2, 4);
            ee = psEndTimer.substring(4, 6);
            endsec = atoi(ss) * 60 * 60 + atoi(mm) * 60 + atoi(ee);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return (-1);
        }
        if (endsec < startsec)
            return -1;
        else
            return (endsec - startsec);
    }

    /**
     * 인코딩 변환
     *
     */
    /**
     *
     * @param ko String
     * @return String
     */
    public static final String en(String ko) {
        String new_str = null;
        try {
            new_str = new String(ko.getBytes("KSC5601"), "8859_1");
        } catch (UnsupportedEncodingException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
        return new_str;
    }

    /**
     *
     * @param en String
     * @return String
     */
    public static final String ko(String en) {
        String new_str = null;
        try {
            new_str = new String(en.getBytes("8859_1"), "KSC5601");
        } catch (UnsupportedEncodingException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
        return new_str;
    }

    /**
     * HOST SEQ 조회
     * 
     * CREATE SEQUENCE SQ_FALOGGHT_HOSTSEQ
     * INCREMENT BY 1
     * START WITH 1
     * MAXVALUE 99999999
     * CYCLE
     * ORDER
     *
     */
    /**
     *
     * @return Connection
     */
    public static final Connection getDBConnection() {
        Connection connTemp = null;
        try {
            Class.forName("weblogic.jdbc.pool.Driver");
        } catch (ClassNotFoundException e) {
            // System.out.println("ClassNotFoundException raised!!!!!");
            e.printStackTrace();
        }
        try {
            connTemp = DriverManager.getConnection(
                    "jdbc:weblogic:pool:oraclePool", null);
            // System.out.println("Drivermanager okay!!!!!");
        } catch (SQLException e) {
            System.out.println("SQLException in getConnection()");
            e.printStackTrace();
        }
        return connTemp;
    }

    /**
     *
     * @param usr      String
     * @param password String
     * @return Connection
     */
    public static final Connection getDBConnection(String usr, String password) {
        String url = new String("jdbc:oracle:thin:@21.101.3.49:1521:DEVMIS");
        String user = usr;
        String passwd = password;
        Connection conn = null;
        try {
            // DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            conn = DriverManager.getConnection(url, user, passwd);
        } catch (Exception e) {
            System.out.println("DriverManager.getConnection Exception ");
            return null;
        }
        return conn;
    }

    /**
     *
     * @return String
     */
    public static final String GetHostSeq() {
        String seqtmp = null;
        int seq = 0;
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;

        try {
            conn = getDBConnection("scott", "tiger");
            String tSQL = "select SQ_FALOGGHT_HOSTSEQ.nextval from dual";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(tSQL);
            rs.next();
            seqtmp = Int2Str(rs.getInt(1));

        } catch (SQLException e) {
            try {
                rs.close();
                conn.close();
            } catch (Exception ex) {
            }
            System.out.println("Error : " + e);
            return null;
        }
        try {
            rs.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error : " + e);
            return null;
        }
        return (ZeroToStr8(seqtmp));
    }

    /**
     *
     * @param conn Connection
     * @return String
     */
    public static final String gethostseq(Connection conn) {
        String seqtmp = null;
        int seq = 0;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            String tSQL = "select SQ_FALOGGHT_HOSTSEQ.nextval from dual";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(tSQL);
            rs.next();
            seqtmp = Int2Str(rs.getInt(1));
            rs.close();
        } catch (Exception e) {
            System.out.println("Error : " + e);
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
            } catch (Exception ex) {
            }
        }
        return (CommonUtil.ZeroToStr8(seqtmp));
    }

    /**
     *
     * @param conn     Connection
     * @param bankCode String
     * @return String
     */
    public static final String getBusinessDate(Connection conn, String bankCode) {
        String businessDate = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            String tSQL = "select date_value1 from system_parameter where bank_code = '"
                    + bankCode + "' and system = 'COR' and group_code = 'SETUP' and kind = 'BIZDATE' ";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(tSQL);
            rs.next();
            businessDate = rs.getString(1);
            rs.close();
        } catch (Exception e) {
            // System.out.println("Error : " + e);
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
            } catch (Exception ex) {
            }
        }
        return businessDate;
    }

    /**
     *
     * @param conn     Connection
     * @param bankCode String
     * @return String
     */
    public static final String getOpenBit(Connection conn, String bankCode) {
        String openBit = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            String tSQL = "select char_value1 from system_parameter where bank_code = '"
                    + bankCode +
                    "' and system = 'COR' and group_code = 'SETUP' and kind = 'BIZOPENBIT' ";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(tSQL);
            rs.next();
            return rs.getString(1);
        } catch (Exception e) {
            // System.out.println("Error : " + e);
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (rs != null)
                    rs.close();
            } catch (Exception ex) {
            }
            try {
                if (stmt != null)
                    stmt.close();
            } catch (Exception ex) {
            }

        }
    }

    /**
     *
     * @param conn     Connection
     * @param bankCode String
     * @return String
     */
    public static final String getXMLVersion(Connection conn, String bankCode) {
        String xmlVersion = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            String tSQL = "select num_value1 from system_parameter where bank_code = '"
                    + bankCode +
                    "' and system = 'COR' and group_code = 'SETUP' and kind = 'XMLVER' ";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(tSQL);
            rs.next();
            return rs.getString(1);
        } catch (Exception e) {
            // System.out.println("Error : " + e);
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (rs != null)
                    rs.close();
            } catch (Exception ex) {
            }
            try {
                if (stmt != null)
                    stmt.close();
            } catch (Exception ex) {
            }

        }
    }

    /**
     *
     * @param conn     Connection
     * @param bankCode String
     * @return String
     */
    public static final String getBaseCurrency(Connection conn, String bankCode) {
        String baseCurrency = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            String tSQL = "select char_value1 from system_parameter where bank_code = '"
                    + bankCode +
                    "' and system = 'COR' and group_code = 'SETUP' and kind = 'BASECCY' ";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(tSQL);
            rs.next();
            return rs.getString(1);
        } catch (Exception e) {
            // System.out.println("Error : " + e);
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (rs != null)
                    rs.close();
            } catch (Exception ex) {
            }
            try {
                if (stmt != null)
                    stmt.close();
            } catch (Exception ex) {
            }
        }
    }

    /**
     * /**
     * /**
     * 로그 기록
     * 파일이 존재하지 않으면 생성하고 기록한다.
     * 
     * @param contentToWrite 기록할 내용
     * @return boolean - 성공하면 true, 실패하면 false
     */
    public static final boolean log(String contentToWrite) {
        boolean result = true;

        try {
            contentToWrite = getCurrentDate() + " - " + contentToWrite + "\n";
            DataOutputStream dos = new DataOutputStream(new FileOutputStream(
                    "C:\\mytest\\text.log", true));
            dos.writeBytes(contentToWrite);
            dos.close();
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    /**
     * 프로퍼티 파일 (C:\MYTEST\CHB\src\webserver\webserver.properties)을 읽어와서
     * 파라미터를 지정하고 파일이 존재하는지 확인한다.
     * 
     * @return boolean - 파일이 존재하고 파라미터가 지정되면 true, 아니면 false
     *         -----------------------------------------------------------------------------
     *         webserver.properties 파일 내용
     *         #HOME_DIRECTORY=c:\\test\\v3\\
     *         HOME_DIRECTORY=C:\\MYTEST\\CHB\\src\\webserver\\root_docu\\
     *         #LOG_FILE=c:\\test\\log.txt
     *         LOG_FILE=C:\\MYTEST\\CHB\\src\\webserver\\log\\log.txt
     *         PORT_NUMBER=90
     */

    /**
     *
     * @return boolean
     */
    public static final boolean getProperties() {
        /*
         * private static final String PROPERTIES_FILE="WebServer.properties";
         * private static final String PARAM_HOME_DIRECTORY="HOME_DIRECTORY";
         * private static final String PARAM_LOG_FILE="LOG_FILE";
         * private static final String PARAM_PORT_NUMBER="PORT_NUMBER";
         * 
         * public static String HOME_DIRECTORY;
         * public static String LOG_FILE;
         * public static int PORT_NUMBER;
         */

        String PROPERTIES_FILE = "WebServer.properties";
        String PARAM_HOME_DIRECTORY = "HOME_DIRECTORY";
        String PARAM_LOG_FILE = "LOG_FILE";
        String PARAM_PORT_NUMBER = "PORT_NUMBER";

        String HOME_DIRECTORY;
        String LOG_FILE;
        int PORT_NUMBER;

        Properties p = new Properties();
        String value = null;

        try {
            p.load(new FileInputStream(new File(PROPERTIES_FILE)));
            if ((value = p.getProperty(PARAM_HOME_DIRECTORY)) != null) {
                HOME_DIRECTORY = value;
            }
            if ((value = p.getProperty(PARAM_LOG_FILE)) != null) {
                LOG_FILE = value;
            }
            if ((value = p.getProperty(PARAM_PORT_NUMBER)) != null) {
                PORT_NUMBER = new Integer(value).intValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /*
     * @ int [] array�� �ִ� ������ ��Ʈ�ϱ�
     *
     *
     */

    /**
     *
     * @param num int[]
     * @return int[]
     */
    public static final int[] SortINTarray(int[] num) {
        int tar[];
        tar = num;
        Arrays.sort(tar);
        return tar;
    }

    /**
     * 숫자를 소수점 2자리까지 포맷팅한다.
     *
     * @param pattern : ######.##
     * @param num     double
     */
    /**
     *
     * @param pattern String
     * @param num     double
     * @return double
     */
    public static final double cutDecimalFormat(String pattern, double num) {
        // String pattern = "######.##";

        DecimalFormat dformat = new DecimalFormat(pattern);

        // double num = 123456.789;

        System.out.println(dformat.format(num));

        return num;

    }

    /**
     * 날짜 포맷
     ** 
     * 포맷 규칙 **
     * 0 : 전체 문자열
     * # : 전체 문자열 중 0이 아닌 숫자만 표시
     * . : 소수점 이하 자리수 표시
     * , : 천 단위 구분자
     * ; : 소수점 이하 숫자 표시
     * - : 음수 표시
     * % : 100% 표시
     * 표시 규칙 : 소수점 이하 첫 번째 자리까지 표시
     * ' : 한글 주석 표시
     */
    /**
     *
     * @param str String
     * @return String
     */
    public static final String ZeroToStr8(String str) {
        DecimalFormat fmt = new DecimalFormat("00000000");
        fmt = new DecimalFormat("00000000");
        return (fmt.format(Str2Int(str)));
    }

    /**
     *
     * @param str  String
     * @param size int
     * @return String
     */
    public static final String ZeroToStr(String str, int size) {
        StringBuffer tt = new StringBuffer();
        for (int i = 1; i <= size; i++) {
            tt.append('0');
        }

        DecimalFormat fmt = new DecimalFormat(tt.toString());
        fmt = new DecimalFormat(tt.toString());
        return (fmt.format(Str2Int(str)));
    }

    /**
     *
     * @param str  String
     * @param size int
     * @return String
     */
    public static final String LZeroToStr(String str, int size) {

        StringBuffer tt = new StringBuffer();
        for (int i = 1; i <= size; i++) {
            tt.append('0');
        }

        DecimalFormat fmt = new DecimalFormat(tt.toString());
        fmt = new DecimalFormat(tt.toString());
        return (fmt.format(Str2Long(str)));
    }

    /**
     *
     * @param str  String
     * @param size int
     * @return String
     */
    public static final String BigZeroToStr(String str, int size) {
        if (str == null)
            str = "0";

        StringBuffer tt = new StringBuffer();
        for (int i = 1; i <= size - 3; i++) {
            tt.append('0');
        }

        DecimalFormat fmt = new DecimalFormat(tt.toString() + ".00");
        fmt = new DecimalFormat(tt.toString() + ".00");
        return (fmt.format(CommonUtil.Str2Double(str)));
    }

    /**
     *
     * @param str  String
     * @param size int
     * @return String
     */
    public static final String CutSpaceToStr(String str, int size) {
        if (str == null) {
            return null;
        }
        StringBuffer tt = new StringBuffer();
        for (int i = 0; i < size; i++) {
            if (str.charAt(i) == ' ')
                break;
            tt.append(str.charAt(i));
        }
        return tt.toString();
    }

    /**
     *
     * @param str  String
     * @param size int
     * @return String
     */
    public static final String SpaceToStr(String str, int size) {

        if (str == null)
            str = " ";

        StringBuffer tt = new StringBuffer();
        for (int i = 1; i <= size; i++) {
            tt.append(' ');
        }

        for (int i = size - 1, ii = str.length() - 1; ii >= 0; i--, ii--) {
            tt.setCharAt(i, str.charAt(ii));
        }
        // System.out.println("str char[" + tt.toString() +"]"+tt.toString().length() );
        return tt.toString();
    }

    /**
     *
     * @param str  String
     * @param size int
     * @return String
     */
    public static final String StarToStr(String str, int size) {
        if (str == null)
            str = "*";
        StringBuffer tt = new StringBuffer();
        for (int i = 1; i <= size; i++)
            tt.append('*');
        for (int i = size - 1, ii = str.length() - 1; ii >= 0; i--, ii--)
            tt.setCharAt(i, str.charAt(ii));
        return tt.toString();
    }

    /**
     *
     * @param str  String
     * @param size int
     * @return String
     */
    public static final String ConvertStrToStar(String str, int size) {
        if (str == null)
            str = "*";
        StringBuffer tt = new StringBuffer();
        for (int i = 0; i < size; i++) {
            tt.setCharAt(i, '*');
        }
        return tt.toString();
    }

    /**
     *
     * @param str1 String
     * @param size int
     * @return String
     */
    public static final String PSpaceToStr(String str1, int size) {
        if (str1 == null)
            str1 = " ";

        String str = null;
        if (str1.length() >= size) {
            str = str1.substring(0, size);
        } else
            str = str1;

        StringBuffer tt = new StringBuffer();
        for (int i = 1; i <= size; i++) {
            tt.append(' ');
        }
        for (int i = 0; i < str.length(); i++) {
            tt.setCharAt(i, str.charAt(i));
        }

        return tt.toString();
    }

    /**
     *
     * @param str1 String
     * @param size int
     * @return String
     */
    public static final String AtferSpaceToStr(String str1, int size) {
        if (str1 == null)
            str1 = " ";

        String str = null;
        if (str1.length() >= size) {
            str = str1.substring(0, size);
        } else
            str = str1;

        StringBuffer tt = new StringBuffer();
        for (int i = 1; i <= size; i++) {
            tt.append(' ');
        }
        for (int i = 0; i < str.length(); i++) {
            tt.setCharAt(i, str.charAt(i));
        }

        return tt.toString();
    }

    /**
     *
     * @param str1 String
     * @param size int
     * @return String
     */
    public static final String FillSpaceToStr(String str1, int size) {
        if (str1 == null) {
            str1 = " ";
        }
        String str = null;
        if (str1.length() >= size) {
            str = str1.substring(0, size);
        } else
            str = str1;

        StringBuffer tt = new StringBuffer();
        for (int i = 1; i <= size; i++) {
            tt.append(' ');
        }
        for (int i = 0; i < str.length(); i++) {
            tt.setCharAt(i, str.charAt(i));
        }

        return tt.toString();

    }

    /**
     *
     * @param str int
     * @return String
     */
    public static final String ZeroToStr8(int str) {

        DecimalFormat fmt = new DecimalFormat("00000000");
        fmt = new DecimalFormat("00000000");
        System.out.println("int");
        return (fmt.format(str));
    }

    /**
     *
     * @param str long
     * @return String
     */
    public static final String ZeroToStr8(long str) {

        DecimalFormat fmt = new DecimalFormat("00000000");
        fmt = new DecimalFormat("00000000");
        System.out.println("long");
        return (fmt.format(str));
    }

    /**
     *
     * @param k int
     * @return int
     */
    public static final int getRandomNum(int k) {

        Random ran = new Random(17);
        // 17번 루프(Prime Number) 반복
        // Prime Number는 소수를 의미한다.
        // 다른 숫자를 사용하면 다른 결과가 나올 수 있다.

        int randomNumber = ran.nextInt(); // 랜덤 숫자를 얻는다.
        if (randomNumber < 0)
            randomNumber *= (-1);

        return (randomNumber % k);
        // 0~100 범위의 랜덤 숫자를 반환
        // 소수 결과는 다른 결과가 나올 수 있다.
    }

    /////////////////////////////////////////////////////////////////////////////
    // 시스템 정보 조회
    /////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    static void GetLineStrFromFile() {

        String infile = ".\\test.in";
        String line = null;

        try {
            BufferedReader in = new BufferedReader(new FileReader(infile));

            // 파일 내용을 읽어온다.
            for (;;) {
                if ((line = getFileLine(in)) != null) {
                    System.out.println(line);
                } else {
                    in.close();
                    break;
                }
            }
        } catch (IOException ioe) {
            System.out.println("cmdFser IOException " + ioe.getMessage());
            ioe.printStackTrace();
        }
        return;
    }

    /**
     *
     * @param in BufferedReader
     * @return String
     */
    static String getFileLine(BufferedReader in) {

        try {
            String line = null;
            if ((line = in.readLine()) != null)
                return line;
            else
                return null;
        } catch (IOException ioe) {
            System.out.println("cmdFser IOException " + ioe.getMessage());
            ioe.printStackTrace();
        }
        return null;
    }

    ///////////////////////////////////////////////////////////////////////////// <
    ///////////////////////////////////////////////////////////////////////////// 파일
    ///////////////////////////////////////////////////////////////////////////// 읽기
    /**
     *
     * @throws IOException
     */
    public void readlinefromfile() throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("filename"));

        for (String line; (line = in.readLine()) != null;)
            System.out.println(line);
    }

    /**
     *
     * @param fname String
     * @param fsize String
     * @return int
     */
    public static final int isExistFile(String fname, String fsize) {
        String ConfigPath =
                // "C:\\MYTEST\\CHB\\src\\����-������ Ver1.0\\Config.INI";
                "C:\\MYTEST\\INETCHB\\envConfig.INI";
        Properties p = new Properties();

        try {
            p.load(new FileInputStream(ConfigPath));
            String TargetPath = p.getProperty("FTPTARPATH");

            if (fname == null || fsize == null || fname == "" || fsize == "") {
                System.out.println(
                        "fname is null,empty or fsize is null,empty ");
                return -1;
            }

            File f = new File(ascToksc(TargetPath) + fname);

            if (f.exists()) {
                System.out.println("파일 크기 : " + f.length());
                System.out.println("파일" + f.getName() + "이 존재합니다.");

                if (fsize.equals(CommonUtil.Int2Str((int) f.length())))
                    return 0;
                else
                    return -1;
            } else {
                System.out.println("파일" + fname + "이 존재하지 않습니다.");
                return -1;
            }
        } catch (Exception e) {
            return -1;
        }

    }

    /**
     *
     * @param args String[]
     */
    public static void main(String[] args) {
        float kk = 300 / 1000;
        cutDecimalFormat("######.##", kk);

        System.out.println(getTimeFreeFormat("hhmmss"));
        System.out.println(getTimeFreeFormat("hh:mm:ss:ss"));
        System.out.println(getTime());

        /*
         * String aa3 = "a.gz";
         * int ii;
         * 
         * 
         * if( aa3.charAt( (ii=(aa3.length()-1)) )=='z' &&
         * aa3.charAt( (ii=(aa3.length()-2)) )=='g' &&
         * aa3.charAt( (ii=(aa3.length()-3)) )=='.'
         * 
         * ){
         * System.out.println("-------------kkkkkk--------------");
         * }
         * 
         * 
         * //System.out.println(compreTo(".gz" ));
         * System.out.println("--------------["
         * +CommonUtil.SpaceToStr(aa3,25)+"]--------------");
         * aa3 = "1";
         * System.out.println("--------------["
         * +CommonUtil.SpaceToStr(aa3,25)+"]--------------");
         * aa3 = "12";
         * System.out.println("--------------["
         * +CommonUtil.SpaceToStr(aa3,25)+"]--------------");
         * aa3 = "134444";
         * System.out.println("--------------["
         * +CommonUtil.SpaceToStr(aa3,25)+"]--------------");
         * 
         * String aa4 = "한글 문자열";
         * System.out.println("--------------["
         * +CommonUtil.SpaceToStr(aa4,25)+"]--------------");
         * 
         * execcommand("start dir");
         * 
         * System.out.println(ZeroToStr8(99999999));
         * System.out.println(GetHostSeq()+"---00000000000");
         * System.out.println(GetSysDate()+"---00000000000");
         * System.out.println(getTimeFreeFormat("hhmmssSS"));
         * System.out.println(getTimeFreeFormat("hh:mm:ss:SS"));
         * System.out.println(getTime());
         * 
         * System.out.println(memcmp("string","string"));
         * System.out.println(memcmp("string","staing",5));
         * 
         * int [] tar = {9,1,2,3,4,5,6,7};
         * SortINTarray(tar);
         * for(int i=0;i<tar.length;i++)
         * System.out.println(tar[i]);
         * 
         * execcommand("start dir");
         * 
         * try {
         * deleteDIR("C:\\MYTEST\\CHB\\src\\common\\tmp");
         * }
         * catch(Exception e){}
         * 
         * 
         * String str1 = "test string";
         * String str2 = "한글 테스트";
         * String str3 = "testing....한글...";
         * isHanGul(str1);
         * isHanGul(str2);
         * isHanGul(str3);
         * 
         * cutDecimalFormat("######.##",12345678.4454489);
         * 
         * 
         * FileInfo("C:\\MYTEST\\CHB\\src\\common");
         * 
         * 
         * 
         * ENVINI ini = new ENVINI();
         * ini.savingINI() ;
         * ini.loadingINI();
         * 
         * File info = new File("./CommonUtil.java");
         * if (info.exists() ){
         * System.out.println("aaaaaaaaaaaaaaaaaaaaa" + info.length());
         * if( info.length() == (int) 25717 )
         * System.out.println("rrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
         * }
         * else
         * System.out.println("ssssssssssssssss");
         * 
         * String aa = null;
         * //if( aa.equals("") ) System.out.println("null kkkkkkkkkkkkk");
         * 
         * 
         * 
         * ////////////////////////////////////////////////////
         * // Hashtable test
         * Hashtable kk = new Hashtable();
         * class FF {
         * public String aa;
         * public String bb;
         * 
         * public FF(String aa,String bb) {
         * this.aa=aa;
         * this.bb=bb;
         * }
         * 
         * }
         * 
         * 
         * kk.put( "1", new FF("aa","bb") );
         * kk.put( "2", new FF("cc","bb") );
         * kk.put( "3", new FF("dd","bb") );
         * 
         * FF f1 = (FF) kk.get("1");
         * f1.aa = "DDDDDDDDDDDDDDDDDDDDDD";
         * 
         * FF f2 = (FF) kk.get("1");
         * System.out.println(f2.aa);
         * 
         * appendFileWrite("./hhhhhh.txt","한글 테스트");
         * 
         * isExistFile("kkk.in","1");
         * System.out.println("---------------------------");
         * 
         * Connection conn = DBMSManager.dbConn("scott","tiger");
         * System.out.println( GetDate(conn,"2") );
         * 
         * System.out.println("---------------------------............");
         * GetLineStrFromFile();
         * System.out.println("---------------------------..........fffffffffffffff..");
         * ///////////////////////////////////////////////////
         * 
         * class Input {
         * String type;
         * String start;
         * String end;
         * public String toString(){
         * return (type+start+end);
         * }
         * public Input getIn(String str){
         * Input in = new Input();
         * in.type = str.substring(0,1);
         * in.start = str.substring(1,9);
         * in.end = str.substring(9,17);
         * System.out.println(in.type);
         * System.out.println(in.start);
         * System.out.println(in.end);
         * return in;
         * }
         * }
         * 
         * Input in = new Input();
         * in.getIn("a1111111122222222");
         */
    }

}

/**
 *
 * <p>
 * Title: ePlaton
 * </p>
 * <p>
 * Description: This is oversea banking package.
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004 IMS System CO., LTD.
 * </p>
 * <p>
 * Company: IMS System
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
class ENVINITEST {
    String ATTR_1 = null;
    String ATTR_2 = null;
    String ATTR_3 = null;
    String ATTR_4 = null;
    String ATTR_5 = null;
    String ATTR_6 = null;

    /**
     *
     * @return boolean
     */
    public boolean loadingINI() {
        try {
            Properties p = new Properties();
            p.load(new FileInputStream("./Config.ini"));
            ATTR_1 = p.getProperty("ATTR_1");
            ATTR_2 = p.getProperty("ATTR_2");
            ATTR_3 = p.getProperty("ATTR_3");
            ATTR_4 = p.getProperty("ATTR_4");
            ATTR_5 = p.getProperty("ATTR_5");
            ATTR_6 = p.getProperty("ATTR_6");
            return true;
        } catch (Exception e) {
            // loading ERR
            return false;
        }
    }

    /**
     * saving config.ini
     * 
     * @return boolean 저장 성공 여부
     */
    /**
     *
     * @return boolean
     */
    public boolean savingINI() {
        try {
            Properties p = new Properties();
            p.load(new FileInputStream("./Config.ini"));

            p.setProperty("ATTR_1", "ON");
            p.setProperty("ATTR_2", "xxx");
            p.setProperty("ATTR_3", "xxx");
            p.setProperty("ATTR_4", "ttt");
            p.setProperty("ATTR_5", "yyy");
            p.setProperty("ATTR_6", "1000");

            p.store(new FileOutputStream("./Config.ini"),
                    "INI REFERENCE[ConfigReference.ini]");
            return true;
        } catch (Exception e) {
            // saving ERR
            return false;
        }

    }

    /*
     * -----------------------------
     * config.ini 내용
     * #content
     * ATTR_1=ON
     * ATTR_2=xxx
     * ATTR_3=xxx
     * ATTR_4=ttt
     * ATTR_5=yyy
     * ATTR_6=1000
     * ------------------------------
     */

}

/**
 *
 * <p>
 * Title: ePlaton
 * </p>
 * <p>
 * Description: This is oversea banking package.
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004 IMS System CO., LTD.
 * </p>
 * <p>
 * Company: IMS System
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
class InfoDataTest {
    String seq;
    String sysdate;
    String systime;
    String hostseq;
    String filename;
    String filesize;
    String stat;

    /**
     *
     */
    InfoDataTest() {
        seq = null;
        sysdate = null;
        systime = null;
        hostseq = null;
        filename = null;
        filesize = null;
        stat = null;
    }

    /**
     *
     * @return int
     */
    public int chkData() {
        if (seq == null) {
            System.out.println("infodata.seq is null");
            return -1;
        }

        if (systime == null) {
            System.out.println("infodata.systime is null");
            return -1;
        }

        if (sysdate == null) {
            System.out.println("infodata.sysdate is null");
            return -1;
        }

        if (hostseq == null) {
            System.out.println("infodata.hostseq is null");
            return -1;
        }

        if (filename == null) {
            System.out.println("infodata.filename is null");
            return -1;
        }

        if (filesize == null) {
            System.out.println("infodata.filesize is null");
            return -1;
        }

        if (stat == null) {
            System.out.println("infodata.stat is null");
            return -1;
        }

        return 0;
    }

    /**
     *
     */
    public void prntInfo() {
        System.out.println(seq + "|" + sysdate + "|" + systime + "|" + hostseq +
                "|" + filename + "|" + filesize + "|" + stat);
    }

}
