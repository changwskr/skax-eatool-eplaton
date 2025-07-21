package com.skax.eatool.mbc.fc.foundation.bzcrudbus.business.ic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.skax.eatool.mbc.fc.foundation.bzcrudbus.business.dc.DCComExec;
import com.skax.eatool.mbc.fc.foundation.bzcrudbus.exception.CommonDCException;
import com.skax.eatool.mbc.fc.foundation.bzcrudbus.exception.CommonPCException;
import com.skax.eatool.mbc.fc.foundation.bzcrudbus.transfer.IFRSCommonDTO;

/**
 * IC 공통 실행 클래스
 * 
 * 프로그램명: ICComExec.java
 * 설명: Integration Component 공통 실행 클래스
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - IC 컴포넌트 공통 실행
 * - DC 계층과의 연동
 * - 예외 처리 및 로깅
 * 
 * @version 1.0
 */
public class ICComExec extends IICComExec {

	/** IFRS 공통 DTO */
	private IFRSCommonDTO commonDTO;

	/** 입력 맵 */
	private HashMap fromMap;

	/** 출력 맵 */
	private HashMap toMap;

	/**
	 * 검색 조건에 따라 데이터를 조회한다.
	 * 
	 * @param searchOption 검색 옵션
	 * @throws CommonPCException 공통 PC 예외
	 * @return 조회 결과 문자열
	 */
	public String getData(Map searchOption) throws CommonPCException {

		try {

			return DCComExec.getInstance().readData((String) searchOption.get("sqlId"), searchOption);

		} catch (CommonDCException dce) {
			throw new CommonPCException(dce.getErrorCode(), dce.toString());
		} catch (Exception e) {
			throw new CommonPCException("S9101001", e.toString());
		}

	}

	/**
	 * IFRSCommonDTO를 사용하여 데이터를 조회한다.
	 * 
	 * @param commonDTO IFRS 공통 DTO
	 * @return 조회 결과 문자열
	 * @throws CommonPCException 공통 PC 예외
	 */
	public String getData(IFRSCommonDTO commonDTO) throws CommonPCException {

		try {

			return DCComExec.getInstance().readData(commonDTO);

		} catch (CommonDCException dce) {
			throw new CommonPCException(dce.getErrorCode(), dce.toString());
		} catch (Exception e) {
			throw new CommonPCException("S9102001", e.toString());
		}

	}

	/**
	 * 검색 조건에 따라 리스트를 조회한다.
	 * 
	 * @param searchOption 검색 옵션
	 * @throws CommonPCException 공통 PC 예외
	 * @return 조회 결과 리스트
	 */
	public List<HashMap> getList(Map searchOption) throws CommonPCException {

		try {

			return DCComExec.getInstance().readList((String) searchOption.get("sqlId"), searchOption);

		} catch (CommonDCException dce) {
			throw new CommonPCException(dce.getErrorCode(), dce.toString());
		} catch (Exception e) {
			throw new CommonPCException("S9103001", e.toString());
		}

	}

	/**
	 * IFRSCommonDTO를 사용하여 리스트를 조회한다.
	 * 
	 * @param commonDTO IFRS 공통 DTO
	 * @return 조회 결과 리스트
	 * @throws CommonPCException 공통 PC 예외
	 */
	public List<HashMap> getList(IFRSCommonDTO commonDTO) throws CommonPCException {

		try {

			return DCComExec.getInstance().readList(commonDTO);

		} catch (CommonDCException dce) {
			throw new CommonPCException(dce.getErrorCode(), dce.toString());
		} catch (Exception e) {
			throw new CommonPCException("S9104001", e.toString());
		}
	}

	/**
	 * 데이터 저장 메서드(INSERT / UPDATE / DELETE 처리)
	 * 
	 * @param searchOption 검색 옵션
	 * @throws CommonPCException 공통 PC 예외
	 * @return 처리된 행 수
	 */
	public int saveData(Map searchOption) throws CommonPCException {

		try {

			return DCComExec.getInstance().save((String) searchOption.get("sqlId"), searchOption);

		} catch (CommonDCException dce) {
			throw new CommonPCException(dce.getErrorCode(), dce.toString());
		} catch (Exception e) {
			throw new CommonPCException("S9105001", e.toString());
		}

	}

	/**
	 * IFRSCommonDTO를 사용하여 데이터를 저장한다.
	 * 
	 * @param commonDTO IFRS 공통 DTO
	 * @return 처리된 행 수
	 * @throws CommonPCException 공통 PC 예외
	 */
	public int saveData(IFRSCommonDTO commonDTO) throws CommonPCException {

		try {

			return DCComExec.getInstance().save(commonDTO);

		} catch (CommonDCException dce) {
			throw new CommonPCException(dce.getErrorCode(), dce.toString());
		} catch (Exception e) {
			throw new CommonPCException("S9106001", e.toString());
		}
	}

	/**
	 * 그리드 데이터 저장 메서드(INSERT / UPDATE / DELETE 그리드 처리)
	 * 
	 * @param commonDTO IFRS 공통 DTO
	 * @throws CommonPCException 공통 PC 예외
	 */
	public void insertList(IFRSCommonDTO commonDTO) throws CommonPCException {

		try {
			Map searchOption = commonDTO.getFromMap();
			Vector gridList = null;

			String[] gridName = commonDTO.getGridName();

			if (gridName != null) {

				Map rowMap = null;
				String state = null;
				Object crudObj = null;

				for (int i = 0; i < gridName.length; i++) {

					gridList = (Vector) searchOption.get(gridName[i].trim());

					for (int j = 0; j < gridList.size(); j++) {
						rowMap = (Map) gridList.get(j);
						crudObj = rowMap.get("CRUD");

						if (crudObj == null) {
							state = "C";
						} else if (((String) crudObj).trim().equals("")) {
							state = "Z";
						} else {
							state = ((String) rowMap.get("CRUD")).toUpperCase();
						}

						if (state != null && state.equals("C")) {
							DCComExec.getInstance().save(commonDTO.getSqlId(), rowMap);
						}
					}
				}
			}
		} catch (CommonDCException dce) {
			throw new CommonPCException(dce.getErrorCode(), dce.toString());
		} catch (Exception e) {
			throw new CommonPCException("S9107001", e.toString());
		}
	}

	/**
	 * 그리드 데이터 업데이트 메서드
	 * 
	 * @param commonDTO IFRS 공통 DTO
	 * @throws CommonPCException 공통 PC 예외
	 */
	public void updateList(IFRSCommonDTO commonDTO) throws CommonPCException {

		try {
			Map searchOption = commonDTO.getFromMap();
			Vector gridList = null;

			String[] gridName = commonDTO.getGridName();

			if (gridName != null) {

				Map rowMap = null;
				String state = null;
				Object crudObj = null;

				for (int i = 0; i < gridName.length; i++) {

					gridList = (Vector) searchOption.get(gridName[i].trim());

					for (int j = 0; j < gridList.size(); j++) {
						rowMap = (Map) gridList.get(j);
						crudObj = rowMap.get("CRUD");

						if (crudObj == null) {
							state = "U";
						} else if (((String) crudObj).trim().equals("")) {
							state = "Z";
						} else {
							state = ((String) rowMap.get("CRUD")).toUpperCase();
						}

						if (state != null && state.equals("U")) {
							DCComExec.getInstance().save(commonDTO.getSqlId(), rowMap);
						}
					}
				}
			}
		} catch (CommonDCException dce) {
			throw new CommonPCException(dce.getErrorCode(), dce.toString());
		} catch (Exception e) {
			throw new CommonPCException("S9108001", e.toString());
		}
	}

	public void deleteList(IFRSCommonDTO commonDTO) throws CommonPCException {

		try {
			Map searchOption = commonDTO.getFromMap();
			Vector gridList = null;

			String[] gridName = commonDTO.getGridName();

			if (gridName != null) {

				Map rowMap = null;
				String state = null;
				Object crudObj = null;

				for (int i = 0; i < gridName.length; i++) {

					gridList = (Vector) searchOption.get(gridName[i].trim());

					for (int j = 0; j < gridList.size(); j++) {
						rowMap = (Map) gridList.get(j);
						crudObj = rowMap.get("CRUD");

						if (crudObj == null) {
							state = "D";
						} else if (((String) crudObj).trim().equals("")) {
							state = "Z";
						} else {
							state = ((String) rowMap.get("CRUD")).toUpperCase();
						}

						if (state != null && state.equals("D")) {
							DCComExec.getInstance().save(commonDTO.getSqlId(), rowMap);
						}
					}
				}
			}
		} catch (CommonDCException dce) {
			throw new CommonPCException(dce.getErrorCode(), dce.toString());
		} catch (Exception e) {
			throw new CommonPCException("S9109001", e.toString());
		}
	}

	public void saveList(IFRSCommonDTO commonDTO) throws CommonPCException {

		try {
			Map searchOption = commonDTO.getFromMap();
			Vector gridList = null;

			String[] gridName = commonDTO.getGridName();

			if (gridName != null) {

				Map rowMap = null;
				String state = null;
				Object crudObj = null;

				for (int i = 0; i < gridName.length; i++) {

					gridList = (Vector) searchOption.get(gridName[i].trim());

					for (int j = 0; j < gridList.size(); j++) {
						rowMap = (Map) gridList.get(j);
						crudObj = rowMap.get("CRUD");

						if (crudObj == null) {
							state = "U";
						} else if (((String) crudObj).trim().equals("")) {
							state = "Z";
						} else {
							state = ((String) rowMap.get("CRUD")).toUpperCase();
						}

						if (state != null && state.equals("U")) {
							int rowCnt = DCComExec.getInstance().save(getSaveListSqlId("update", commonDTO.getSqlId()),
									rowMap);

							if (rowCnt <= 0) {
								DCComExec.getInstance().save(getSaveListSqlId("insert", commonDTO.getSqlId()), rowMap);
							}
						}
					}
				}
			}
		} catch (CommonDCException dce) {
			throw new CommonPCException(dce.getErrorCode(), dce.toString());
		} catch (Exception e) {
			throw new CommonPCException("S9110001", e.toString());
		}
	}

	public void saveGrid(IFRSCommonDTO commonDTO) throws CommonPCException {

		try {
			Map searchOption = commonDTO.getFromMap();
			Vector gridList = null;

			String[] gridName = commonDTO.getGridName();

			if (gridName != null) {

				Map rowMap = null;
				String state = null;
				Object crudObj = null;

				for (int i = 0; i < gridName.length; i++) {

					gridList = (Vector) searchOption.get(gridName[i].trim());

					for (int j = 0; j < gridList.size(); j++) {
						rowMap = (Map) gridList.get(j);
						crudObj = rowMap.get("CRUD");

						if (crudObj == null || ((String) crudObj).trim().equals("")) {
							state = "Z";
						} else {
							state = ((String) rowMap.get("CRUD")).toUpperCase();
						}

						switch (state.charAt(0)) {

							case 'C':

								DCComExec.getInstance().save(getSaveListSqlId("insert", commonDTO.getSqlId()), rowMap);
								break;

							case 'U':

								DCComExec.getInstance().save(getSaveListSqlId("update", commonDTO.getSqlId()), rowMap);
								break;

							case 'D':

								DCComExec.getInstance().save(getSaveListSqlId("delete", commonDTO.getSqlId()), rowMap);
								break;
						}
					}
				}
			}
		} catch (CommonDCException dce) {
			throw new CommonPCException(dce.getErrorCode(), dce.toString());
		} catch (Exception e) {
			throw new CommonPCException("S9111001", e.toString());
		}
	}

	public static String getSaveListSqlId(String command, String sqlId) {

		String[] sqlIdArr = sqlId.split("\\.");

		if (sqlIdArr.length > 1) {
			StringBuffer sBuf = new StringBuffer();
			for (int i = 0; i < sqlIdArr.length; i++) {

				if (i == sqlIdArr.length - 1) {
					sBuf.append(command);
					sBuf.append(sqlIdArr[i]);
				} else {
					sBuf.append(sqlIdArr[i]);
					sBuf.append(".");
				}
			}

			return sBuf.toString();
		} else {
			return command + sqlId;
		}
	}

}
