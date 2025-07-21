package com.skax.eatool.mbc.fc.foundation.bzcrudbus.dao;

import com.skax.eatool.mbc.fc.foundation.bzcrudbus.business.dc.DCComExec;
import com.skax.eatool.mbc.fc.foundation.bzcrudbus.config.Env;
import com.skax.eatool.mbc.fc.foundation.bzcrudbus.foundation.utility.Utils;
import com.skax.eatool.mbc.fc.foundation.bzcrudbus.log.IIfrsLogger;

/**
 * IFRS 공통 데이터 액세스 객체 클래스
 * 
 * 프로그램명: IfrsCommonDAO.java
 * 설명: IFRS 시스템에서 사용되는 공통 시퀀스 관리를 담당하는 DAO 클래스
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - WAF 시퀀스 생성
 * - 서버 시퀀스 생성
 * - 시퀀스 포맷팅
 * - 싱글톤 패턴으로 인스턴스 관리
 *
 * @author
 * @version 1.0
 * @see
 */
public class IfrsCommonDAO {

	/** 싱글톤 인스턴스 */
	private static IfrsCommonDAO instance;

	/** 로거 */
	// private IIfrsLogger logger = IfrsLogHelper.getCom();
	private IIfrsLogger logger = null; // TODO: Initialize when logger is available

	/**
	 * 싱글톤 인스턴스를 반환한다.
	 * 
	 * @return IfrsCommonDAO 인스턴스
	 */
	public static synchronized IfrsCommonDAO getInstance() {
		if (instance == null) {
			instance = new IfrsCommonDAO();
		}
		return instance;
	}

	/**
	 * 기본 생성자
	 */
	public IfrsCommonDAO() {

	}

	/**
	 * WAF 시퀀스를 생성한다.
	 * 
	 * @return 포맷된 WAF 시퀀스 문자열
	 */
	public String getWafSeq() {

		String seq = null;

		try {
			seq = CommonDAO.getInstance().readData("common.getWafSeq", null);
			seq = "W" + Utils.addLeft(seq, 8, "0");
			// logger.infoF("[WAF SEQ 생성] #" + seq);
		} catch (Exception e) {
			// logger.infoF("[WAF SEQ 생성] error");
			e.printStackTrace();
		}
		return seq;
	}

	/**
	 * 서버 시퀀스를 생성한다.
	 * 
	 * @return 포맷된 서버 시퀀스 문자열
	 */
	public String getSvrSeq() {

		String seq = null;

		try {
			String sqlId = Env.getProperty("IfrsCommonDAO.SEQ_SQL_ID");
			// seq = CommonDAO.getInstance().readData(sqlId, null);
			seq = DCComExec.getInstance().readString(sqlId, null);
			seq = "S" + Utils.addLeft(seq, 8, "0");
			// logger.infoF("[SERVER SEQ 생성] #" + seq);
		} catch (Exception e) {
			// logger.infoF("[SERVER SEQ 생성] error");
			e.printStackTrace();
		}
		return seq;
	}

}
