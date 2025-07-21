package com.skax.eatool.mbc.fc.foundation.bzcrudbus.constant;

import java.math.BigDecimal;

import com.skax.eatool.mbc.fc.foundation.bzcrudbus.foundation.general.CommonUtil;

/**
 * TCF(Transaction Control Framework) 상수 정의 클래스
 * 
 * 프로그램명: TCFConstants.java
 * 설명: Transaction Control Framework에서 사용되는 모든 상수들을 정의하는 클래스
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - 트랜잭션 관련 상수 정의
 * - 로깅 관련 상수 정의
 * - 채널 타입 상수 정의
 * - 에러 코드 상수 정의
 * - 시스템 설정 상수 정의
 * 
 * <p>
 * Title: ePlaton
 * </p>
 * <p>
 * Description: This is oversea banking package.
 * </p>
 * Transaction Control Framework에서 사용되는 모든 상수들을 정의하여 관리하는 클래스.
 * <p>
 * Copyright: Copyright (c) 2004 IMS System CO., LTD.
 * </p>
 * <p>
 * Company: IMS System
 * </p>
 * 
 * @author xx xx xx
 * @version 1.0
 */
public final class TCFConstants {

  /**
   * eplaton 설정 파일 경로 설정
   */
  // public static final String CONFIG_FILE_NAME=
  // "/kdb03/ok/parm/sysconfig/EPLconfig.xml";

  /**
   * url 설정 정보
   * Config.getInstance().getElement("jndi-server0").getChild("url").getTextTrim();
   */
  // public static final String call_url=
  // Config.getInstance().getElement("jndi-server0").getChild("url").getTextTrim();

  /**
   * 로그 관련 eplaton 설정 파일 경로 설정
   * "/weblogic/bea/wlserver6.1/config/coses_US/applicationConfig/maps/epllogej.xml";
   */
  // public static final String LOGEJ_CONFIG_FILE_NAME=
  // Config.getInstance().getElement("log-manage-info").getChild("config-file-path").getTextTrim();

  /**
   * 트랜잭션 블로킹 설정 파일 경로
   */
  public static String TX_BLOCKING_CONFIG_FILE_NAME = "/kdb03/ok/parm/sysconfig/EPLtxblocking.xml";

  /**
   * 로그 파일 경로
   * "/kdb03/ok/log/outlog/TPMTRACE";
   */
  // public static String TPMTRACE_LoggingFileName =
  // Config.getInstance().getElement("log-manage-info").getChild("tpmtrace-file-path").getTextTrim();

  /**
   * 입력 로그 파일명
   * "/kdb03/ok/log/input/";
   */
  public static String INPUT_LOGFILENAME = "FMK.IN.LOG";
  // Config.getInstance().getElement("log-manage-info").getChild("input-file-path").getTextTrim();

  /**
   * 출력 로그 파일명
   * "/kdb03/ok/log/output/";
   */
  // public static String OUTPUT_LOGFILENAME =
  // Config.getInstance().getElement("log-manage-info").getChild("output-file-path").getTextTrim();

  /**
   * 트랜잭션 정보 로그 파일명
   * "/kdb03/ok/log/outlog/TPMTRACE_TRANSACTIONINFO";
   */
  // public static String TCF_TRANSACTION_INFO_FILE_NAME=
  // Config.getInstance().getElement("log-manage-info").getChild("transactioninfo-file-path").getTextTrim();

  /**
   * System.getProperties().getProperty("line.separator")<br>
   * 줄 구분자
   */
  public static final String LINE_SEPARATOR = System.getProperties().getProperty("line.separator");

  /**
   * 빈 문자열
   * ""
   */
  public static final String BLANK = "";

  /**
   * BigDecimal 상수들
   * new BigDecimal("0.0")<br>
   * 숫자 0의 BigDecimal
   */
  public static final BigDecimal ZERO = new BigDecimal("0");
  public static final BigDecimal ONE = new BigDecimal("1");
  public static final BigDecimal HUNDRED = new BigDecimal("100");
  public static final BigDecimal ZERO_DOT_ONE = new BigDecimal("0.1");

  /**
   * "bizaction-map-filename"<br>
   * BizAction class에서 사용하는 태그. BizDelegate 변환 시 사용하는 config 파일 이름이 있는 tag 이름
   */
  public static final String BIZDELEGATE_TAG = "bizaction-map-filename";

  /**
   * "bizaction-class"<br>
   * BizAction class에서 사용하는 태그. BizAction class 이름이 있는 tag 이름
   */
  public static final String ACTIONCLASS_TAG = "action-class-name";

  /** 트랜잭션 가능 여부 */
  public static final String TRANSACTIONABLE = "transaction-yn";

  /**
   * "bizaction-method"<br>
   * BizAction class에서 사용하는 태그.
   * BizAction class에서 호출할 system component의 operation 이름이 있는 tag 이름
   */
  public static final String METHOD_TAG = "method-name";

  /**
   * "bizaction-parametertype"<br>
   * BizAction class에서 사용하는 태그.
   * BizAction class에서 호출할 system component의 operation에서 사용하는
   * CDTO의 type 이름이 있는 tag 이름
   */
  public static final String TYPE_TAG = "parameter-name";

  /**
   * "CosesCommonDTO ChannelType"<br>
   * CosesCommonDTO에서 ChannelType을 설정하는 상수(일반 UI 화면에서 사용하는 경우).<br>
   */
  public static final String GENERAL_UI_CHANNEL = "01";

  /**
   * "CosesCommonDTO ChannelType"<br>
   * CosesCommonDTO에서 ChannelType을 설정하는 상수(배치 프로그램에서 사용하는 경우).<br>
   */
  public static final String BATCH_CHANNEL = "02";

  /**
   * "CosesCommonDTO ChannelType"<br>
   * CosesCommonDTO에서 ChannelType을 설정하는 상수(ATM 사용하는 경우).<br>
   */
  public static final String ATM_CHANNEL = "03";

  /**
   * "CosesCommonDTO ChannelType"<br>
   * CosesCommonDTO에서 ChannelType을 설정하는 상수(INTERNET 화면에서 사용하는 경우).<br>
   */
  public static final String INTERNET_BANK_CHANNEL = "10";

  /**
   * 이 class는 인스턴스화 할 수 없음.
   */

  /**
   * 호스트 IP 포트
   */
  public static String HOST_IP_PORT = "localhost";
  // Config.getInstance().getElement("jndi-server0").getChild("url").getTextTrim();
  public static String USER_TRANSACTION_MSG = "usertransaction";
  public static String NO_TRANSACTION_MSG = "no_transaction_type";
  public static String CONTAINER_TRANSACTION_MSG = "container";
  public static String BIZDELEGATE_CALL_METHOD_NAME = "execute";
  public static String TCF_TRANSACTION_DEFAULT_TIMEOUT = "180";
  public static String TCF_SUCCESS_ERRCODE = "IZZ000";

  // Fields
  public static final String CALL_BEAN_TYPE_TAG = "action-call-bean-type";
  public static final String BEAN_TRANSACTION_TYPE_TAG = "bean-transaction-type";
  public static final String OPERATION_TAG = "operation-class";
  public static final String LOG_SYSTEM_TAG = "epllogej";
  public static final String PRINT_MODE_TAG = "print_mode";
  public static final String ERROR_MODE_TAG = "error_mode";

  //
  public static final String BEAN_TYPE = "remote";
  public static final String BEAN_LOCAL_TYPE = "local";
  public static final String NO_TX_BEAN_TYPE = "no_transaction_type";

  //
  public static final String HOSTSEQ_INITIAL_STR = "********";
  public static final String EVENTNO_INITIAL_STR = "********";

  // TPFQ 상수
  public static final String SERVER_SERVER_INTERCHANGE_LOC = "100";
  public static final String WAF_SERVER_INTERCHANGE_LOC = "200";
  public static final String NATIVE_SERVER_INTERCHANGE_LOC = "300";
  public static final String ATM_SERVER_INTERCHANGE_LOC = "400";
  public static final String INTERNETBANK_SERVER_INTERCHANGE_LOC = "500";
  public static final String BATCHCLIENT_SERVER_INTERCHANGE_LOC = "600";

  public static final int SERVER_SERVER_INTERCHANGE_LOC_NUM = CommonUtil
      .STR2INT(TCFConstants.SERVER_SERVER_INTERCHANGE_LOC);
  public static final int WAF_SERVER_INTERCHANGE_LOC_NUM = CommonUtil.STR2INT(TCFConstants.WAF_SERVER_INTERCHANGE_LOC);
  public static final int NATIVE_SERVER_INTERCHANGE_LOC_NUM = CommonUtil
      .STR2INT(TCFConstants.NATIVE_SERVER_INTERCHANGE_LOC);
  public static final int ATM_SERVER_INTERCHANGE_LOC_NUM = CommonUtil.STR2INT(TCFConstants.ATM_SERVER_INTERCHANGE_LOC);
  public static final int BATCHCLIENT_SERVER_INTERCHANGE_LOC_NUM = CommonUtil
      .STR2INT(TCFConstants.BATCHCLIENT_SERVER_INTERCHANGE_LOC);
  public static final int INTERNETBANK_SERVER_INTERCHANGE_LOC_NUM = CommonUtil
      .STR2INT(TCFConstants.INTERNETBANK_SERVER_INTERCHANGE_LOC);

  // errorcode
  public static final String SUCCESS_ERRCODE = "IZZ000";

  // logging level
  public static int GENERAL_LOG_LEVEL = 1;
  public static int WARN_LOG_LEVEL = 2;
  public static int FATAL_LOG_LEVEL = 10;

  public static int TCF_LOG_LEVEL = 5;
  public static int STF_LOG_LEVEL = 4;
  public static int ETF_LOG_LEVEL = 4;
  public static int BTF_LOG_LEVEL = 5;
  public static int TPM_LOG_LEVEL = 6;

  // timezone
  public static String TIME_ZONE = "GMT+09:00";

  // FX CNT
  public static int FX_RATE_COUNT = 1;
  public static int GLOABL_TX_INITIAL_MODE = 0;
  public static int GLOABL_TX_ALREADY_MODE = 1;

  // TCF ���������
  public static String TCF_STF_INTIME_INITIAL = "XXXXXXXX";
  public static String TCF_STF_OUTTIME_INITIAL = "XXXXXXXX";
  public static String TCF_BTF_INTIME_INITIAL = "XXXXXXXX";
  public static String TCF_BTF_OUTTIME_INITIAL = "XXXXXXXX";
  public static String TCF_ETF_INTIME_INITIAL = "XXXXXXXX";
  public static String TCF_ETF_OUTTIME_INITIAL = "XXXXXXXX";
  public static String TCF_LOGIC_LEVEL = "XXXX";
  public static String TCF_BEFORE_MSG = "Before";
  public static String TCF_AFTER_MSG = "After";

  public static String STF_MSG = "STF";
  public static String BTF_MSG = "BTF";
  public static String ETF_MSG = "ETF";

  /**
   * "machine-service-mode"
   * "/weblogic/bea/wlserver6.1/config/coses_US/applicationConfig/EPLconfig.xml"
   * ���Ͽ��� ���� �ӽſ��� �¶��μ������� ��뼭�������� �����ϴ� �������̴�.
   *
   */
  public static final String MACHINE_SERVICE_MODE_TAG = "machine-service-mode";
  public static final String MACHINE_SERVICE_MODE_DEV = "DEV";

  /**
   * WAF-EJB���� ť�׽ð��� ���� ������ �������� ������ �ð�
   */
  public static final int WEB_TRANSACTION_MINUS_SECONDS = 4;
  public static final int GLOBAL_TRANSACTION_MINUS_SECONDS = 2;

  /**
   *
   */
  public static final int LINE_COUNT_PER = 2047;

  /**
   * RULE/THING ���� �ʱ�ȭ ��
   */
  public static final String RULE_THING_INITIAL_VAL = "^^^";

}
