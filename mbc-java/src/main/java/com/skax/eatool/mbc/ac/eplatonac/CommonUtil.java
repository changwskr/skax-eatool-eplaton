package com.skax.eatool.mbc.ac.eplatonac;

/**
 * <PRE>
 * Class    : CommonUtil
 * Function : Common Utility class
 * Comment  : 공통 유틸리티 클래스
 * </PRE>
 * @version : 1.0
 * @author  : EPlaton Framework Team
 */

import java.io.*;
import java.net.InetAddress;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;

public class CommonUtil {

  /**
   * 숫자 체크 함수
   * CHECKisdigit("12a","12a".length())
   *
   * @param string 체크할 문자열
   * @param piStrLen 문자열 길이
   * @return 숫자인지 여부
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
   * request_name = "cashCard-listCashcard"
   * = "system" + "-" + "method"
   *
   * catchSTRINGseq("cashCard-listCashcard",1,"-");
   * '-' 구분자를 가지면서 첫번째 문자를 구한다
   * 
   * @param request_name 요청명
   * @param seq 순서
   * @param tag 구분자
   * @return 분리된 문자열
   */
  public static String catchSTRINGseq(String request_name, int seq, String tag) {
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
   * 문자열 치환 함수
   * @param str 바꿀 문자열을 가진 텍스트
   * @param pattern 찾을 문자열
   * @param replace 바꿔넣을 문자열
   * str="aaaaa k k"
   * pattern="k k"
   * replace="w w"
   * return = "aaaaa w w"
   */
  static String replace(String str, String pattern, String replace) {

    int s = 0; // 찾기 시작할 위치
    int e = 0; // StringBuffer에 append할 위치
    StringBuffer result = new StringBuffer(); // 새 문자열을 담을 버퍼
    while ((e = str.indexOf(pattern, s)) >= 0) {
      result.append(str.substring(s, e));
      result.append(replace);
      s = e + pattern.length();
    }
    result.append(str.substring(s));
    return result.toString();
  }

  public static final boolean runtime(String cmd) {
    try {
      // System.out.println(cmd);
      Runtime runtime = Runtime.getRuntime();
      runtime.exec("/home/cosif/jtm/bin/jmboot " + cmd);
    } catch (IOException ex) {
      // System.out.println("예외발생"+ex.getMessage());
      ex.printStackTrace();
      return false;
    }
    return true;
  }

  public int Interval_GetTimeSec(String psStartTimer, String psEndTimer) {
    String ss = null;
    String mm = null;
    String ee = null;
    int startsec = 99999999;
    int endsec = 99999999;

    // System.out.println("-------start Interval_GetTimesec");
    /***********************************************************************/
    /* 시작시간 */
    /***********************************************************************/

    ss = psStartTimer.substring(0, 2);
    mm = psStartTimer.substring(2, 4);
    ee = psStartTimer.substring(4, 6);
    startsec = Str2Int(ss) * 60 * 60 + Str2Int(mm) * 60 + Str2Int(ee);

    /***********************************************************************/
    /* 종료시간 */
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
   * System.out.println으로 화면에 보여준다
   * 
   * @param String str
   * @return void
   */
  public static final void systemOut(String msg) {
    System.out.println(msg);
  }

  /**
   * len 보다 긴 문자열일 경우 자른다
   *
   * @param str 입력 문자열
   * @param len 문자열 길이
   * @return String
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

  public static final String cutString(String str, int startpo, int len) {
    if (str != null) {
      return str.substring(startpo, (len + 1));
    }
    return "";
  }

  /**
   * KSC5601로 변환한다
   *
   * @param String str
   * @return String
   * @exception UnsupportedEncodingException
   */
  public static final String ascToksc(String str) throws UnsupportedEncodingException {

    if (str == null)
      return "";
    return new String(str.getBytes("8859_1"), "KSC5601");

  }

  /**
   * 8859_1로 변환한다
   *
   * @param String str
   * @return String
   * @exception UnsupportedEncodingException
   */
  public static final String kscToasc(String str) throws UnsupportedEncodingException {

    if (str == null)
      return "";
    return new String(str.getBytes("KSC5601"), "8859_1");

  }

  /**
   * 입력된 String의 종류, 영문, 한글, 기타를 체크한다.
   *
   * @param String data
   * @return int
   *         0인 경우 영문,1인 경우 한글, 2인 경우 기타
   */
  public static final int checkHan(String data) {
    int result = 0;
    for (int i = 0; i < data.length(); i++) {
      char ch = data.charAt(i);
      if (ch >= 0xAC00 && ch <= 0xD7A3) {
        result = 1;
        break;
      } else if (ch >= 0x0041 && ch <= 0x005A || ch >= 0x0061 && ch <= 0x007A) {
        result = 0;
      } else {
        result = 2;
      }
    }
    return result;
  }

  /**
   * 한글인지 체크한다
   *
   * @param String str
   * @return boolean
   */
  public static final boolean isHanGul(String str) {
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      if (ch >= 0xAC00 && ch <= 0xD7A3) {
        return true;
      }
    }
    return false;
  }

  /**
   * ?력??String??null???체크?다.
   *
   * @param String str
   * @return String
   *         null??경우 return ""
   */
  public static final String nullCheckString(String str) {
    if (str != null)
      return str;
    return "";

  }

  /**
   * ?간관??util
   *
   */
  public static final String getTime(long time) {

    SimpleDateFormat sdf = new SimpleDateFormat("mm:ss:SS");
    return (String) sdf.format(new Date(time));
  }

  public static final void getTime(long time, String msg) {

    SimpleDateFormat sdf = new SimpleDateFormat("mm:ss:SS");
    System.out.println(msg + " : " + (String) sdf.format(new Date(time)));

  }

  public static String getTime() {

    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:SS");
    return (String) sdf.format(new Date(System.currentTimeMillis()));
  }

  public static String getTimeFreeFormat(String format) {

    SimpleDateFormat sdf = new SimpleDateFormat(format);
    return (String) sdf.format(new Date(System.currentTimeMillis()));
  }

  /**
   * ?자변?? *
   */

  public static final int STR2INT(String str) {
    int i = 0;
    try {
      i = Integer.parseInt(str);
    } catch (NumberFormatException e) {
      System.out.println("STR2INT ERROR" + e.toString());
    } finally {
      return (i);
    }
  }

  public static final int Str2Int(String str) {
    int i = Integer.parseInt(str);
    // System.out.println(i);
    return (i);
  }

  public static final int atoi(String str) {
    int i = Integer.parseInt(str);
    // System.out.println(i);
    return (i);
  }

  public static final long Str2Long(String str) {
    long l = Long.parseLong(str);
    // System.out.println(l);
    return (l);
  }

  public static final float Str2Float(String str) {
    float f = Float.parseFloat(str);
    // System.out.println(f);
    return (f);
  }

  public static final double Str2Double(String str) {
    double d = Double.parseDouble(str);
    // System.out.println(d);
    return (d);
  }

  public static final String Int2Str(int n) {
    String s = String.valueOf(n);
    return (s);
  }

  public static final String Float2Str(float n) {
    String s1 = String.valueOf(n);
    return (s1);
  }

  public static final String Long2Str(long l) {
    String s = String.valueOf(l);
    return (s);
  }

  public static final String Double2Str(double d) {
    String s = String.valueOf(d);
    return (s);
  }

  /**
   * 문자관??변?? *
   */
  public static final String TOupper(String str) {
    String up = str.toUpperCase();
    return (up);

  }

  public static final String TOlower(String str) {
    String lo = str.toLowerCase();
    return (lo);
  }

  public static final String Char2Str(char ch) {
    String lo = String.valueOf(ch);
    return (lo);
  }

  /**
   * string 관?? *
   */
  public static final boolean exist_str(String org, String tar) {
    if (org.regionMatches(org.indexOf(tar), tar, 0, tar.length())) {
      return true;
    } else {
      return false;
    }
  }

  // org : 1234567 tar : 345 -> org?�서 tar ?�트링이 ?�함?�어 ?�는지 ?�인?�다.
  public static final boolean check_contain_string(String org, String tar) {
    if (org.regionMatches(org.indexOf(tar), tar, 0, tar.length())) {
      return true;
    } else {
      return false;
    }
  }

  public static final boolean memcmp(String st1, String st2) {
    if (st1.equals(st2))
      return (true);
    else
      return (false);
  }

  public synchronized static String GetEnvValue(String envfilename, String tmp) {
    String ConfigPath = null;
    Properties p = new Properties();
    FileInputStream fis = null;
    String tmpv = null;
    try {
      fis = new FileInputStream(envfilename);
      p.load(fis);
      tmpv = p.getProperty(tmp);
    } catch (IOException e) {
      System.out.println("GetEnvValue() ?�서 ?�경?�팅??2 ?�외발생");
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
   * ?일관?? *
   */

  // ?렉?리?????메??�보�?보여준??
  // filepath ex) C:\\MYTEST\\CHB\\src\\common
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

  public static final void printOne(File f) {
    if (f.exists()) {
      System.out.print(f.canRead() ? "r" : "-");
      System.out.print(f.canWrite() ? "w" : "-");
      System.out.print(f.isDirectory() ? "x" : "-");
      System.out.print('\t');

      System.out.print(f.length());
      System.out.print('\t');

      System.out.print(new Date(f.lastModified()));
      System.out.print('\t');
    } else {
      System.out.print("\t\t\t\t\t ?일??존재?? ?습?다.");
    }

    System.out.println("?일?: " + f.getName());
  }

  // -------------------------------------------------------------------
  // _cpfile2file : copy iname oname
  // ex) iname == "./kk.txt" oname=="./kk.txt1"
  // rtn : 복사???�일???�기 , error : -1
  // -------------------------------------------------------------------

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

  // ?정 ?렉?리???용??모두 지?기
  // args : C:\windows
  public static final void deleteDIR(String args) throws Exception {

    File directory = new File(args);

    if (!directory.isDirectory()) {
      System.err.println(args + " is not directory");
      return;
    }

    deleteAll(directory);
  }

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
  // writebuff : 복사??string
  // -1 : error , 0 : success
  // -------------------------------------------------------------------

  public static final int appendFileWrite(String path2filename, String writebuff) {
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
   * ?스????UTIL
   *
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

  public static final String GetSysTime() {
    SimpleDateFormat fmt = new SimpleDateFormat("HHmmssSSS");
    return fmt.format(new Date()).toString();
  }

  public static final String GetDate(Connection conn, String itvdate) {
    String seqtmp = null;
    int seq = 0;
    Statement stmt = null;
    ResultSet rs = null;

    try {

      String tSQL = "SELECT TO_CHAR( SYSDATE - " + itvdate + ", 'YYYYMMDD' ) FROM DUAL";
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

  public static final String GetSysDate() {
    SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
    return fmt.format(new Date()).toString();
  }

  /**
   * ?함??간??자?리턴?다.
   * 
   * @return String - Current Date, time
   */

  public static final String getCurrentDate() {
    String currentDate = "";

    Calendar c = Calendar.getInstance();

    String yyyy = String.valueOf(c.get(Calendar.YEAR));
    String mm = String.valueOf(c.get(Calendar.MONTH) + 1);
    String dd = String.valueOf(c.get(Calendar.DATE));
    String hr = String.valueOf(c.get(Calendar.HOUR));
    String min = String.valueOf(c.get(Calendar.MINUTE));
    String sec = String.valueOf(c.get(Calendar.SECOND));

    if (mm.trim().length() == 1) {
      mm = "0" + mm;
    }

    if (dd.trim().length() == 1) {
      dd = "0" + dd;
    }

    currentDate = mm + "/" + dd + "/" + yyyy + "-" + ZeroToStr(hr, 2) + ":" + ZeroToStr(min, 2) + ":"
        + ZeroToStr(sec, 2);

    return currentDate;
  }

  /**
   * ?작?간?종료?간??롱값?로 ?산??Interval 차이?계산?다.
   * - ?것? 초단?�로 ?�산?�다.
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
   * ?��? / ?�어 encoder
   *
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
   * RunTime Execution
   *
   */
  public static final int exec_com(String command) {
    String cmd = command;
    Process p = null;

    try {
      p = Runtime.getRuntime().exec(cmd);
    } catch (IOException ex) {
      System.out.println(ex.getMessage());
      return -1;
    }
    // return(p.exitValue());
    return (0);
  }

  public static final int execcommand(String args) {
    String command = args;
    Process ps = null;
    OutputStream out = null;
    BufferedReader in = null;
    String res = null;

    String[] cmd = { "cmd.exe", "/c", "empty" };
    cmd[2] = command;

    try {
      ps = Runtime.getRuntime().exec(cmd);
    } catch (IOException e) {
      System.out.println(e);
      return -1;
    }

    in = new BufferedReader(new InputStreamReader(ps.getInputStream()));
    out = ps.getOutputStream();
    try {
      while ((res = in.readLine()) != null) {

        System.out.println(res);
      }
    } catch (IOException e) {
      System.out.println(e);
      return -1;
    }

    return 0;
  }

  /**
   * HOST SEQ 구한??
   * 
   * CREATE SEQUENCE SQ_FALOGGHT_HOSTSEQ
   * INCREMENT BY 1
   * START WITH 1
   * MAXVALUE 99999999
   * CYCLE
   * ORDER
   *
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
      connTemp = DriverManager.getConnection("jdbc:weblogic:pool:oraclePool", null);
      // System.out.println("Drivermanager okay!!!!!");
    } catch (SQLException e) {
      System.out.println("SQLException in getConnection()");
      e.printStackTrace();
    }
    return connTemp;
  }

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
    } catch (SQLException e) {
      System.out.println("Error : " + e);
      e.printStackTrace();
      try {
        if (stmt != null)
          stmt.close();
        if (rs != null)
          rs.close();
      } catch (Exception ex) {
        ex.printStackTrace();
      }
      return null;
    } catch (Exception e) {
      System.out.println("Error : " + e);
      e.printStackTrace();
      try {
        if (stmt != null)
          stmt.close();
        if (rs != null)
          rs.close();
      } catch (Exception ex) {
        ex.printStackTrace();
      }
      return null;
    }

    try {
      if (stmt != null)
        stmt.close();
      if (rs != null)
        rs.close();
    } catch (Exception ex) {
    }
    return (CommonUtil.ZeroToStr8(seqtmp));
  }

  /**
   * ???�수??property file (C:\MYTEST\CHB\src\webserver\webserver.properties)�? *
   * 부???�큐먼트루트?�렉?�리,?�터,로그?�렉?�리???�보�??�어?� ?�?�한??
   * 
   * @return boolean - true, if the file exits and parameters are specified.
   *         false, otherwise.
   *         -----------------------------------------------------------------------------
   *         webserver.properties ?�일???�용
   *         #HOME_DIRECTORY=c:\\test\\v3\\
   *         HOME_DIRECTORY=C:\\MYTEST\\CHB\\src\\webserver\\root_docu\\
   *         #LOG_FILE=c:\\test\\log.txt
   *         LOG_FILE=C:\\MYTEST\\CHB\\src\\webserver\\log\\log.txt
   *         PORT_NUMBER=90
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
        PORT_NUMBER = Integer.parseInt(value);
      }
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  /*
   * @ int [] array???�는 ?�용???�트?�기
   *
   *
   */

  public static final int[] SortINTarray(int num[]) {
    int tar[];
    tar = num;
    Arrays.sort(tar);
    return tar;
  }

  /**
   * ?어??double?에 ????수 2째자리까지 ?�어?�기
   *
   * @param pattern : ######.##
   * @double : 123456.789
   */

  public static final double cutDecimalFormat(String pattern, double num) {
    // String pattern = "######.##";

    DecimalFormat dformat = new DecimalFormat(pattern);

    // double num = 123456.789;

    System.out.println(dformat.format(num));

    return num;

  }

  /**
   * ??��???�자�??�식??
   ** ?�식???�터 ?�트링에 ?�용?�는 ?�수 문자 **
   * 0 : ?�나???�자
   * # : ?�나???�자?�나 0 ??경우 공백 출력
   * . : ?�수?�을 ?��??�는 문자
   * , : ?�자�?comma �?분리?�는 문자
   * ; : ?�턴???�수 ?�턴�??�수 ?�턴?�로 ?�눔
   * - : ?�의 부?��? ?��??�는 문자
   * % : 100 ??곱한 ???�센?�로 ?��??? 기�? 문자 : ?�턴???�뒤???�용?????�다.
   * ' : ?�턴???�뒤?�서 ?�수 문자�?보통 문자�??�용?????�게 ??
   */

  public static final String ZeroToStr8(String str) {
    DecimalFormat fmt = new DecimalFormat("00000000");
    fmt = new DecimalFormat("00000000");
    return (fmt.format(Str2Int(str)));
  }

  public static final String ZeroToStr(String str, int size) {
    StringBuffer tt = new StringBuffer();
    for (int i = 1; i <= size; i++) {
      tt.append('0');
    }

    DecimalFormat fmt = new DecimalFormat(tt.toString());
    fmt = new DecimalFormat(tt.toString());
    return (fmt.format(Str2Int(str)));
  }

  public static final String LZeroToStr(String str, int size) {

    StringBuffer tt = new StringBuffer();
    for (int i = 1; i <= size; i++) {
      tt.append('0');
    }

    DecimalFormat fmt = new DecimalFormat(tt.toString());
    fmt = new DecimalFormat(tt.toString());
    return (fmt.format(Str2Long(str)));
  }

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

  public static final String ConvertStrToStar(String str, int size) {
    if (str == null)
      str = "*";
    StringBuffer tt = new StringBuffer();
    for (int i = 0; i < size; i++) {
      tt.setCharAt(i, '*');
    }
    return tt.toString();
  }

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

  public static final String ZeroToStr8(int str) {

    DecimalFormat fmt = new DecimalFormat("00000000");
    fmt = new DecimalFormat("00000000");
    System.out.println("int");
    return (fmt.format(str));
  }

  public static final String ZeroToStr8(long str) {

    DecimalFormat fmt = new DecimalFormat("00000000");
    fmt = new DecimalFormat("00000000");
    System.out.println("long");
    return (fmt.format(str));
  }

  public static final int getRandomNum(int k) {

    Random ran = new Random(17);
    // 17?� ?�반?�인 ?�수(Prime Number)?�데
    // Prime Number�??�야 ?�수가 골고�?분포?�니??
    // 물론 ?�른 ?�로?�도 괜찮?�니?? ?�수�???좋고??.

    int randomNumber = ran.nextInt(); // ?�제�??�수?�나�?만들?�내???�업?�니??
    if (randomNumber < 0)
      randomNumber *= (-1);

    return (randomNumber % k);
    // ?�건 0~100 ?�이�??�수????�� 규정?�려?? // 것인???�제�??�수?�???��??�는 루틴?�니??
  }

  /////////////////////////////////////////////////////////////////////////////
  // ?나???일???어 ?인?�위별로 출력?�다.
  /////////////////////////////////////////////////////////////////////////////
  static void GetLineStrFromFile() {

    String infile = ".\\test.in";
    String line = null;

    try {
      BufferedReader in = new BufferedReader(new FileReader(infile));

      // ?일???인?위??고?�???�싱???�다.
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
  ///////////////////////////////////////////////////////////////////////////// ??

  public void readlinefromfile() throws IOException {
    BufferedReader in = new BufferedReader(new FileReader("filename"));

    for (String line; (line = in.readLine()) != null;)
      System.out.println(line);
  }

  public static final int isExistFile(String fname, String fsize) {
    String ConfigPath =
        // "C:\\MYTEST\\CHB\\src\\설정-본부용\\Ver1.0\\Config.INI";
        "C:\\MYTEST\\INETCHB\\envConfig.INI";
    Properties p = new Properties();

    try {
      p.load(new FileInputStream(ConfigPath));
      String TargetPath = p.getProperty("FTPTARPATH");

      if (fname == null || fsize == null || fname == "" || fsize == "") {
        System.out.println("fname is null,empty or fsize is null,empty ");
        return -1;
      }

      File f = new File(ascToksc(TargetPath) + fname);

      if (f.exists()) {
        System.out.println("파일 크기 : " + f.length());
        System.out.println("파일" + f.getName() + "이 존재합니다");

        if (fsize.equals(CommonUtil.Int2Str((int) f.length())))
          return 0;
        else
          return -1;
      } else {
        System.out.println("파일" + fname + "이 존재하지 않습니다");
        return -1;
      }
    } catch (Exception e) {
      return -1;
    }

  }

  public static void main(String args[]) {
    float kk = 300 / 1000;
    cutDecimalFormat("######.##", kk);
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
     * String aa4 = "가나다";
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
     * String str3 = "testing....테스트..";
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
     * appendFileWrite("./hhhhhh.txt","한글aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
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
