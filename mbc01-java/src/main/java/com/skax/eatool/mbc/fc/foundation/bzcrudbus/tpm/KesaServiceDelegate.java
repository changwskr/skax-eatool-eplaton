package com.skax.eatool.mbc.fc.foundation.bzcrudbus.tpm;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;

import com.skax.eatool.mbc.fc.foundation.bzcrudbus.config.Env;
import com.skax.eatool.mbc.fc.foundation.bzcrudbus.constant.Constants;
import com.skax.eatool.mbc.fc.foundation.bzcrudbus.exception.DelegateException;
import com.skax.eatool.mbc.fc.foundation.bzcrudbus.foundation.utility.Dom4jUtil;
import com.skax.eatool.mbc.fc.foundation.bzcrudbus.foundation.utility.Utils;
import com.skax.eatool.mbc.fc.foundation.bzcrudbus.kesa.KesaXmlManager;
import com.skax.eatool.mbc.fc.foundation.bzcrudbus.kesa.KesaXmlVO;
import com.skax.eatool.mbc.fc.foundation.bzcrudbus.transfer.IFRSEvent;

/**
 * KESA 서비스 델리게이트 클래스
 * 
 * 프로그램명: KesaServiceDelegate.java
 * 설명: KESA 시스템과의 HTTP 통신을 담당하는 서비스 델리게이트 클래스
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - KESA XML 데이터 전송
 * - HTTP POST 요청 처리
 * - XML 응답 처리
 * - 에러 처리 및 로깅
 * - 파라미터 변환
 * 
 * @version 1.0
 */
public class KesaServiceDelegate implements IServiceDelegate {

	/*
	 * public String sendData(CommonForm commFrm) throws DelegateException {
	 * 
	 * // request 파라미터들을 InData에 설정 (세션정보는 별도로 설정하므로 필요없지만 기타 파라미터들은 InData에 들어갈 수
	 * 있음)
	 * HashMap paramData = commFrm.getRequestMap();
	 * 
	 * try {
	 * 
	 * // KESA로 전송할 XML의 파라미터들을 담을 VO 객체를 생성하고 설정
	 * KesaXmlVO xmlVo = new KesaXmlVO(commFrm.getHostServer(), commFrm.getHostAs(),
	 * commFrm.getHostPc(), commFrm.getDtoName(), commFrm.getSqlId(), paramData);
	 * 
	 * // KESA XML 생성
	 * String xmlData = KesaXmlManager.getInstance().createXml(xmlVo);
	 * System.out.println("######## xmlData : " + xmlData);
	 * 
	 * HashMap paramMap = new HashMap();
	 * paramMap.put(Env.getProperty("KesaServiceDelegate.NGIANT_PARAM_NAME"),
	 * xmlData);
	 * 
	 * // 전송
	 * String resultXml = Dom4jUtil.extractXml(Constants.OUTDATA,
	 * sendHttpPost(paramMap));
	 * //String resultXml = sendHttpPost(paramMap);
	 * 
	 * System.out.println("######## resultXml : " + resultXml);
	 * 
	 * return resultXml;
	 * 
	 * }
	 * catch (Exception e) {
	 * throw new DelegateException();
	 * }
	 * 
	 * }
	 */

	/**
	 * IFRSEvent를 사용하여 데이터를 전송한다.
	 * 
	 * @param ifrsEvent IFRS 이벤트 객체
	 * @return 결과 XML 문자열
	 * @throws DelegateException 델리게이트 예외
	 */
	public String sendData(IFRSEvent ifrsEvent) throws DelegateException {

		// request 파라미터들을 InData에 설정 (세션정보는 별도로 설정하므로 필요없지만 기타 파라미터들은 InData에 들어갈 수 있음)
		HashMap paramData = (HashMap) ifrsEvent.getCommonDto().getFromMap();

		try {

			// KESA로 전송할 XML의 파라미터들을 담을 VO 객체를 생성하고 설정
			KesaXmlVO xmlVo = new KesaXmlVO(ifrsEvent.getCommonDto().getHostServer(),
					ifrsEvent.getCommonDto().getHostAs(), ifrsEvent.getCommonDto().getKey(), paramData);

			// KESA XML 생성
			String xmlData = KesaXmlManager.getInstance().createXml(xmlVo);

			HashMap paramMap = new HashMap();
			paramMap.put(Env.getProperty("KesaServiceDelegate.NGIANT_PARAM_NAME"), xmlData);

			// 전송
			String resultXml = Dom4jUtil.extractXml(Constants.OUTDATA,
					sendHttpPost(ifrsEvent.getCommonDto().getHostServer(), paramMap));
			// String resultXml = sendHttpPost(paramMap);

			// IfrsLogHelper.getWaf().info(ifrsEvent.getCommonDto().getSeqNo(),
			// "[Kesa Service Delegate] RESULT XML : " + resultXml);

			return resultXml;

		} catch (Exception e) {
			throw new DelegateException();
		}

	}

	/**
	 * Post 방식으로 KESA 서버에 XML 전송
	 * 
	 * @param hostServer 호스트 서버
	 * @param paramMap   파라미터 맵
	 * @return 응답 HTML 문자열
	 */
	private String sendHttpPost(String hostServer, HashMap paramMap) {

		HttpClient client = new HttpClient();
		String hostAddr = hostServer;

		if (hostAddr == null || hostAddr.equals("")) {
			hostAddr = Env.getProperty("KesaServiceDelegate.NGIANT_DEFAULT_HOST");
		}

		GetMethod method = new GetMethod(hostAddr + Env.getProperty("KesaServiceDelegate.NGIANT_SERVLET_URL"));

		HashMap postMap = paramMap;
		Set set = postMap.keySet();

		String keyName = null;
		NameValuePair[] params = new NameValuePair[postMap.size()];
		int i = 0;
		for (Iterator itr = Utils.snapshotIterator(set); itr.hasNext();) {
			keyName = (String) itr.next();
			params[i] = new NameValuePair(keyName, (String) postMap.get(keyName));
			i++;
		}

		// 파라미터 설정
		method.setQueryString(params);

		String resultHtml = "";

		try {

			int statusCode = client.executeMethod(method);

			if (statusCode != HttpStatus.SC_OK) {
				// IfrsLogHelper.getWaf().infoF("[Kesa Service Delegate] Method failed: " +
				// method.getStatusLine());
			}

			byte[] responseBody = method.getResponseBody();
			resultHtml = new String(responseBody, Env.getProperty("KesaServiceDelegate.HTTP_RESPONSE_ENCODING")); // byte[]
																													// ->
																													// String

		} catch (HttpException he) {
			// IfrsLogHelper.getWaf().errorF("[Kesa Service Delegate] Fatal protocol
			// violation: " + he.getMessage());
		} catch (IOException ioe) {
			// IfrsLogHelper.getWaf().errorF("[Kesa Service Delegate] Fatal transport error:
			// " + ioe.getMessage());
		} finally {
			method.releaseConnection();
		}

		return resultHtml;
	}

	/**
	 * 파일에서 XML을 읽어온다.
	 * 
	 * @param fileStr 파일 경로
	 * @return XML 바이트 배열
	 */
	private static byte[] getFileXml(String fileStr) {
		File f;
		byte readByte[];
		BufferedInputStream bin;
		f = new File(fileStr);
		readByte = new byte[(int) f.length()];
		bin = null;
		try {
			bin = new BufferedInputStream(new FileInputStream(f), readByte.length);
			bin.read(readByte, 0, readByte.length);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return readByte;
	}
}
