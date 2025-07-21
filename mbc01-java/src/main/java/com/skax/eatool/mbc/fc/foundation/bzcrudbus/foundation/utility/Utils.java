package com.skax.eatool.mbc.fc.foundation.bzcrudbus.foundation.utility;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 * 공통 유틸리티 클래스
 * 
 * 프로그램명: Utils.java
 * 설명: 문자열 처리, 숫자 포맷팅, 날짜 처리 등 공통 유틸리티 기능을 제공하는 클래스
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - 문자열 패딩 및 포맷팅
 * - 숫자 포맷팅
 * - 날짜/시간 처리
 * - 문자열 토큰 처리
 * - 인코딩 변환
 * - 컬렉션 처리
 */
public class Utils {

	/**
	 * 한 자리 숫자를 두 자리 숫자로 바꾸는 메서드
	 * 
	 * @param inum 입력 숫자
	 * @return 두 자리 숫자 문자열
	 */
	public static String addZero(int inum) {
		String snum = String.valueOf(inum);
		if (snum.length() == 1) {
			return "0" + snum;
		}
		return snum;
	}

	/**
	 * 문자열에 패딩을 추가하는 메서드
	 * 
	 * @param inStr     입력 문자열
	 * @param totalLen  전체 길이
	 * @param padChar   패딩 문자
	 * @param leftRight 좌우 정렬 ("L": 왼쪽, "R": 오른쪽)
	 * @return 패딩된 문자열
	 */
	public static String addPad(String inStr, int totalLen, String padChar, String leftRight) {

		if (inStr.length() >= totalLen) {
			return inStr;
		}

		StringBuffer padStr = new StringBuffer();

		for (int i = 0; i < totalLen; i++) {
			if (i == totalLen - inStr.length()) {
				if (leftRight.equals("R")) {
					padStr.insert(0, inStr);
				} else {
					padStr.append(inStr);
				}
				break;
			} else {
				padStr.append(padChar);
			}
		}

		return padStr.toString();
	}

	/**
	 * 왼쪽에 패딩을 추가하는 메서드
	 * 
	 * @param inStr    입력 문자열
	 * @param totalLen 전체 길이
	 * @param padChar  패딩 문자
	 * @return 왼쪽 패딩된 문자열
	 */
	public static String addLeft(String inStr, int totalLen, String padChar) {
		return addPad(inStr, totalLen, padChar, "L");
	}

	/**
	 * 오른쪽에 패딩을 추가하는 메서드
	 * 
	 * @param inStr    입력 문자열
	 * @param totalLen 전체 길이
	 * @param padChar  패딩 문자
	 * @return 오른쪽 패딩된 문자열
	 */
	public static String addRight(String inStr, int totalLen, String padChar) {
		return addPad(inStr, totalLen, padChar, "R");
	}

	/**
	 * 토큰으로 분리된 문자열의 카운트를 반환하는 메서드
	 * 
	 * @param str   입력 문자열
	 * @param token 토큰
	 * @return 토큰 개수
	 */
	public static int getTokenCount(String str, String token) {
		if (str == null) {
			return 0;
		}
		StringTokenizer st = new StringTokenizer(str, token);
		return st.countTokens();
	}

	/**
	 * '/' 문자를 제거하는 메서드
	 * 
	 * @param str 입력 문자열
	 * @return '/'가 제거된 문자열
	 */
	public static String delSlash(String str) {
		if (str == null) {
			return "";
		}
		StringBuffer strdata = new StringBuffer();
		StringTokenizer st = new StringTokenizer(str, "/");
		while (st.hasMoreTokens()) {
			strdata.append(st.nextToken());
		}
		return strdata.toString();
	}

	/**
	 * ',' 문자를 제거하는 메서드
	 * 
	 * @param str 입력 문자열
	 * @return ','가 제거된 문자열
	 */
	public static String delRest(String str) {
		if (str == null) {
			return "";
		}
		StringBuffer strnum = new StringBuffer();
		StringTokenizer st = new StringTokenizer(str, ",");
		while (st.hasMoreTokens()) {
			strnum.append(st.nextToken());
		}
		return strnum.toString();
	}

	/**
	 * 문자열이 null 이거나 공백이면 빈 문자열을 반환하는 메서드
	 * 
	 * @param value 입력 값
	 * @return 처리된 문자열
	 */
	public static String delNull(String value) {
		if (value == null) {
			return "";
		} else {
			return value;
		}
	}

	/**
	 * 화폐단위로 구분된 숫자를 반환하는 메서드 (천 단위)
	 * 
	 * @param value 입력 값
	 * @return 포맷된 숫자 문자열
	 */
	public static String numFormat(int value) {
		java.text.NumberFormat nf = NumberFormat.getInstance();
		return nf.format(value);
	}

	/**
	 * long 타입 숫자 포맷팅
	 * 
	 * @param value 입력 값
	 * @return 포맷된 숫자 문자열
	 */
	public static String numFormat(long value) {
		java.text.NumberFormat nf = NumberFormat.getInstance();
		return nf.format(value);
	}

	/**
	 * float 타입 숫자 포맷팅
	 * 
	 * @param value 입력 값
	 * @return 포맷된 숫자 문자열
	 */
	public static String numFormat(float value) {
		java.text.NumberFormat nf = NumberFormat.getInstance();
		return nf.format(value);
	}

	/**
	 * double 타입 숫자 포맷팅
	 * 
	 * @param value 입력 값
	 * @return 포맷된 숫자 문자열
	 */
	public static String numFormat(double value) {
		java.text.DecimalFormat nf = new DecimalFormat("#,##0.0####");
		return nf.format(value);
	}

	/**
	 * BigDecimal 타입 숫자 포맷팅
	 * 
	 * @param value 입력 값
	 * @return 포맷된 숫자 문자열
	 */
	public static String numFormat(BigDecimal value) {
		if (value == null) {
			return "0";
		}
		java.text.NumberFormat nf = NumberFormat.getInstance();
		long val = value.longValue();
		return nf.format(val);
	}

	/**
	 * Integer 타입 숫자 포맷팅
	 * 
	 * @param value 입력 값
	 * @return 포맷된 숫자 문자열
	 */
	public static String numFormat(Integer value) {
		if (value == null) {
			return "0";
		}
		java.text.NumberFormat nf = NumberFormat.getInstance();
		long val = value.longValue();
		return nf.format(val);
	}

	/**
	 * Long 타입 숫자 포맷팅
	 * 
	 * @param value 입력 값
	 * @return 포맷된 숫자 문자열
	 */
	public static String numFormat(Long value) {
		if (value == null) {
			return "0";
		}
		java.text.NumberFormat nf = NumberFormat.getInstance();
		long val = value.longValue();
		return nf.format(val);
	}

	/**
	 * Float 타입 숫자 포맷팅
	 * 
	 * @param value 입력 값
	 * @return 포맷된 숫자 문자열
	 */
	public static String numFormat(Float value) {
		if (value == null) {
			return "0";
		}
		java.text.NumberFormat nf = NumberFormat.getInstance();
		float val = value.floatValue();
		return nf.format(val);
	}

	/**
	 * Double 타입 숫자 포맷팅
	 * 
	 * @param value 입력 값
	 * @return 포맷된 숫자 문자열
	 */
	public static String numFormat(Double value) {
		if (value == null) {
			return "0";
		}
		java.text.DecimalFormat nf = new DecimalFormat("#,##0.0####");
		double val = value.doubleValue();
		return nf.format(val);
	}

	public static String numFormat(String value) {
		if (value == null) {
			return "0";
		}
		java.text.NumberFormat nf = NumberFormat.getInstance();
		long val = Long.parseLong(value);
		return nf.format(val);
	}

	public static String numFormatInt(double value) {
		java.text.DecimalFormat nf = new DecimalFormat("#,##0");
		return nf.format(value);
	}

	/**
	 * ���ڿ� ����� '-'�� �ٿ��ִ� �޼ҵ�
	 * 
	 * @param str
	 * @param inum
	 * @return String
	 */
	public static String addMinus(String str, int inum) {
		if (str == null) {
			return "";
		}
		int len = str.length();
		String rtn = "";
		if (str.length() == 0 || str.length() < inum) {
			return "";
		}
		rtn = str.substring(0, inum - 1) + "-" + str.substring(inum - 1, len);
		return rtn;
	}

	/**
	 * ���ڿ� �ȿ� '-' ���� �����ϴ� �޼ҵ�
	 * 
	 * @param str
	 * @return String
	 */
	public static String delMinus(String str) {
		if (str == null) {
			return "";
		}
		StringTokenizer st = new StringTokenizer(str, "-");
		StringBuffer sBuf = new StringBuffer();
		while (st.hasMoreTokens()) {
			sBuf.append(st.nextToken());
		}
		return sBuf.toString();
	}

	/**
	 * ���ڿ� �ȿ� ':' ���� �����ϴ� �޼ҵ�
	 * 
	 * @param str
	 * @return String
	 */
	public static String delColon(String str) {
		if (str == null) {
			return "";
		}
		StringTokenizer st = new StringTokenizer(str, ":");
		StringBuffer sBuf = new StringBuffer();
		while (st.hasMoreTokens()) {
			sBuf.append(st.nextToken());
		}
		return sBuf.toString();
	}

	/**
	 * ���ڿ��� ���������� �߰��ϴ� �޼ҵ�
	 * 
	 * @param str
	 * @return String
	 */
	public static String addRevSlash(String str) {
		if (str == null || str == "") {
			return "";
		}
		StringBuffer content = new StringBuffer();
		char[] strchar = str.toCharArray();
		for (int i = 0; i < strchar.length; i++) {
			if (strchar[i] == '\'') {
				content.append("\\" + "'");
			} else {
				content.append(strchar[i]);
			}
		}
		return content.toString();
	}

	/**
	 * ���������� ���ڿ��� �����ϴ� �޼ҵ�
	 * 
	 * @param value
	 * @return String
	 */
	public static String toDate(String value) {
		if (value == null) {
			return "";
		}
		String rtn = "";
		int len = value.trim().length();
		if (len == 8) {
			rtn = value.substring(0, 4) + "/" + value.substring(4, 6) + "/" + value.substring(6, 8);
		} else if (len == 6) {
			rtn = value.substring(0, 4) + "/" + value.substring(4, 6);
		} else {
			rtn = "";
		}
		return rtn;
	}

	/**
	 * ����� ��Ϲ�ȣ�� '-'�� �߰��ؼ� �����ϴ� �޼ҵ�
	 * 
	 * @param value
	 * @return String
	 */
	public static String toCmpno(String value) {
		if (value == null) {
			return "";
		}
		String rtn = "";
		int len = value.trim().length();
		if (len == 10) {
			rtn = value.substring(0, 3) + "-" + value.substring(3, 5) + "-" + value.substring(5, 10);
		} else {
			rtn = "";
		}
		return rtn;
	}

	/**
	 * �ֹι�ȣ ���ڿ��� '-'�� �߰��ؼ� �����ϴ� �޼ҵ�
	 * 
	 * @param value
	 * @return String
	 */
	public static String toJumin(String value) {
		if (value == null) {
			return "";
		}
		String rtn = "";
		int len = value.trim().length();
		if (len == 13) {
			rtn = value.substring(0, 6) + "-" + value.substring(6, 13);
		} else {
			rtn = "";
		}
		return rtn;
	}

	/**
	 * ������ȣ ���ڿ��� '-'�� �߰��ؼ� �����ϴ� �޼ҵ�
	 * 
	 * @param value
	 * @return String
	 */
	public static String toPostno(String value) {
		if (value == null) {
			return "";
		}
		String rtn = "";
		int len = value.trim().length();
		if (len == 6) {
			rtn = value.substring(0, 3) + "-" + value.substring(3, 6);
		} else {
			rtn = "";
		}
		return rtn;
	}

	/**
	 * �ð����� ǥ�ø� �ؼ� ��ȯ�ϴ� �޼ҵ�
	 * 
	 * @param value
	 * @return String
	 */
	public static String toTime(String value) {
		if (value == null) {
			return "";
		}
		String rtn = "";
		int len = value.trim().length();
		if (len == 6) {
			rtn = value.substring(0, 2) + ":" + value.substring(2, 4) + ":" + value.substring(4, 6);
		} else if (len == 4) {
			rtn = value.substring(0, 2) + ":" + value.substring(2, 4);
		} else {
			rtn = "";
		}
		return rtn;
	}

	public static String toEnc(String str, String inCharSet, String ourCharSet) {
		try {
			if (str != null) {
				return (new String(str.getBytes(inCharSet), ourCharSet));
			}
		} catch (UnsupportedEncodingException e) {
			System.err.println("Encoding Error Ksc2Uni : " + e);
		}
		return str;
	}

	/**
	 * ���� �ý��� ���ڸ� ��ȯ�ϴ� �޼ҵ�
	 * 
	 * @return String
	 */
	public static String getSysDate() {
		SimpleDateFormat dt = new SimpleDateFormat("yyyy/MM/dd");
		java.util.Date curTime = new java.util.Date();
		return dt.format(curTime);
	}

	/**
	 * ���� �ý��� ���ڸ� �ѱ۸����� ��ȯ�ϴ� �޼ҵ�
	 * 
	 * @return String
	 */
	public static String getSysDateToKor() {
		String date = getSysDate("");
		date = date.substring(0, 4) + "�� " + date.substring(4, 6) + "�� " + date.substring(6, 8) + "�� ";

		return date;
	}

	/**
	 * ���� �ý��� ���ڸ� ���Ŀ� �°� ��ȯ�ϴ� �޼ҵ�
	 * 
	 * @param token
	 * @return String
	 */
	public static String getSysDate(String token) {
		SimpleDateFormat dt = new SimpleDateFormat("yyyy" + token + "MM" + token + "dd");
		java.util.Date curTime = new java.util.Date();
		return dt.format(curTime);
	}

	/**
	 * ���� �ý��� ����, �ð��� ��ȯ�ϴ� �޼ҵ�
	 * 
	 * @return String
	 */
	public static String getSysDateTime() {
		SimpleDateFormat dt = new SimpleDateFormat("yyyyMMddHHmmss");
		java.util.Date curTime = new java.util.Date();
		return dt.format(curTime);
	}

	/**
	 * Method Description area
	 * 
	 * @return
	 */
	public static String getCurTime() {
		SimpleDateFormat dt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		java.util.Date curTime = new java.util.Date();
		return dt.format(curTime);
	}

	/**
	 * ���� �ý��� ����, �ð��� ���Ŀ� �°� ��ȯ�ϴ� �޼ҵ�
	 * 
	 * @param token
	 * @return String
	 */
	public static String getCurTime(String token) {
		SimpleDateFormat dt = new SimpleDateFormat("yyyy" + token + "MM" + token + "dd HH:mm:ss");
		java.util.Date curTime = new java.util.Date();
		return dt.format(curTime);
	}

	/**
	 * ������ null �Ǵ� �������� Ȯ�����ִ� �޼ҵ�
	 * 
	 * @param src
	 * @return boolean
	 */
	public static boolean isEmpty(String str) {
		if (str == null) {
			return true;
		} else if (str.equals("") || str.equals("null")) {
			return true;
		} else {
			return false;
		}
	}

	public static Iterator snapshotIterator(Collection collection) {
		return new ArrayList(collection).iterator();
	}

	public static void main(String[] args) {

		System.out.println("LPAD : " + Utils.addLeft("123", 8, "0"));
		System.out.println("RPAD : " + Utils.addRight("124", 8, "0"));
		// System.out.println(getDiffOfDays("20071201", "20071201"));
		// strCutSize("���������������������������������äŤǤˤ̤Ф�", 100, false);
	}

}
