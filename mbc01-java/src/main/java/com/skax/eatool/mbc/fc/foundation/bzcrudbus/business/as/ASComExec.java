package com.skax.eatool.mbc.fc.foundation.bzcrudbus.business.as;

// import com.skax.eatool.ksa.exception.BusinessException;
// import com.skax.eatool.ksa.infra.po.KBData;
// import com.skax.eatool.ksa.oltp.biz.IApplicationService;
import com.skax.eatool.mbc.fc.foundation.bzcrudbus.constant.Constants;
import com.skax.eatool.mbc.fc.foundation.bzcrudbus.log.IIfrsLogger;
import com.skax.eatool.mbc.fc.foundation.bzcrudbus.log.IfrsLogHelper;

/**
 * AS 공통 실행 클래스
 * 
 * 프로그램명: ASComExec.java
 * 설명: Application Service 공통 실행 클래스로, TCF를 통한 비즈니스 로직 실행을 담당한다.
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - TPM을 통한 데이터 수신 및 전송
 * - TCF를 통한 비즈니스 로직 실행
 * - 에러 처리 및 예외 관리
 * - 로깅 처리
 * 
 * @version 1.0
 */
public class ASComExec /* implements IApplicationService */ {

	/** IFRS 로거 */
	protected IIfrsLogger logger = IfrsLogHelper.getServer();

	/** 트랜잭션 모드 */
	public static String transactionMode = Constants.TRANSACTION_MODE_SERVER;

	/**
	 * KBData를 실행한다.
	 * 
	 * @param reqData 요청 데이터
	 * @return 응답 데이터
	 * @throws BusinessException 비즈니스 예외
	 */
	/*
	 * public KBData execute(KBData reqData) throws BusinessException {
	 * /*
	 * if (logger.isDebugEnabled()) {
	 * logger.debug(this.getClass().getName() + ", log test입니다.");
	 * }
	 */
	/*
	 * try {
	 * 
	 * // TPM을 통한 데이터 수신
	 * IFRSEvent ifrsEvent = TPMUtil.TPSRecv(reqData);
	 * 
	 * // TCF 생성 및 실행
	 * TCF tcf = new TCF(transactionMode);
	 * 
	 * ifrsEvent = tcf.execute(ifrsEvent);
	 * 
	 * // TCF 실행 후 에러 코드 체크
	 * String errorCode = ifrsEvent.getCommonDto().getErrorCode();
	 * if
	 * (ErrorCheckUtil.getInstance().isError(ifrsEvent.getCommonDto().getErrorCode()
	 * .charAt(0))) {
	 * 
	 * logger.info(ifrsEvent.getCommonDto().getSeqNo(),
	 * "[CommExecAS] Exception Catched And Throw BusinessException (ErrorCode : " +
	 * errorCode + ")");
	 * 
	 * throw ifrsEvent.getException();
	 * }
	 * 
	 * // TPM을 통한 데이터 전송
	 * return TPMUtil.TPSSend(ifrsEvent);
	 * } catch (TPMRecvException re) {
	 * re.printStackTrace();
	 * throw new BusinessException("S9001001", "TPMRECV", re.toString());
	 * } catch (BTFSPException spe) {
	 * spe.printStackTrace();
	 * throw new BusinessException(spe.getErrorCode(), "TCFEXEC", spe.toString());
	 * } catch (TPMSendException se) {
	 * se.printStackTrace();
	 * throw new BusinessException("S9001002", "TPMSEND", se.toString());
	 * } catch (Exception e) {
	 * e.printStackTrace();
	 * throw new BusinessException("S9001003", "COMMEXECAS", e.toString());
	 * }
	 * 
	 * }
	 */
}
