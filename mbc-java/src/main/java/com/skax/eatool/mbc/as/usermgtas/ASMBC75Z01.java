package com.skax.eatool.mbc.as.usermgtas;

import java.util.List;
import java.util.Map;

import com.skax.eatool.mbc.dc.usermgtdc.IDCUser;
import com.skax.eatool.mbc.dc.usermgtdc.User;
import com.skax.eatool.mbc.dc.usermgtdc.dto.UserDDTO;
import com.skax.eatool.ksa.exception.NewBusinessException;
import com.skax.eatool.ksa.infra.po.NewApplicationContext;
import com.skax.eatool.ksa.infra.po.NewGenericDto;
import com.skax.eatool.ksa.infra.po.NewKBData;
import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLogHelper;
import com.skax.eatool.ksa.oltp.biz.NewIApplicationService;
import com.skax.eatool.mbc.dc.usermgtdc.DCUser;

/**
 * 사용자 관리 Application Service
 * 
 * @author KBSTAR
 * @version 1.0.0
 */
public class ASMBC75Z01 implements NewIApplicationService {

	protected NewIKesaLogger logger = NewKesaLogHelper.getBiz();

	public NewKBData execute(NewKBData reqData) throws NewBusinessException {

		if (logger.isDebugEnabled())
			logger.debug(this.getClass().getName() + ", log test입니다.");

		List<User> resUsers = null;
		IDCUser idcuser = null;

		// 표준전문 Common 영역의 거래코드를 가져온다.
		String TransactionId = (String) NewApplicationContext.get(NewApplicationContext.StndTelgmRecvTranCd);
		String rsux = TransactionId.substring(8, 10);

		// 거래코드가 R/S/U/X 중 어떤 것인지 판단
		if (rsux != null && rsux.equals("R0")) {

			logger.debug("R0 process");

			// UserDDTO [] userDDTOs = (UserDDTO[])reqData.getInputGenericDto()
			// .using(GenericDto.INDATA).getArray(UserDDTO.class);

			NewGenericDto input = reqData.getInputGenericDto().using(NewGenericDto.INDATA);

			List<NewGenericDto> userList = input.getGenericDtos("User");
			UserDDTO[] userDDTOs = new UserDDTO[userList.size()];
			UserDDTO userDDTO = null;
			Map<String, Object> userAttr = null;
			logger.debug("userList.size() = " + userList.size());
			for (int i = 0; i < userList.size(); i++) {
				userDDTO = new UserDDTO();
				userAttr = userList.get(i).getAttributeMap();
				logger.debug("uID =" + userAttr.get("uID"));

				userDDTO.setUserID((String) userAttr.get("uID"));
				userDDTO.setUserName((String) userAttr.get("userName"));
				userDDTO.setUserPwd((String) userAttr.get("userPwd"));
				userDDTO.setUserDstcd((String) userAttr.get("userDstcd"));
				userDDTO.setSsno((String) userAttr.get("ssno"));
				userDDTO.setLangDstcd((String) userAttr.get("langDstcd"));
				userDDTO.setDvsnNo((String) userAttr.get("dvsnNo"));
				userDDTO.setDvsnName((String) userAttr.get("dvsnName"));
				userDDTO.setJobclDstcd((String) userAttr.get("jobclDstcd"));
				userDDTO.setJobclName((String) userAttr.get("jobclName"));
				userDDTO.setInstiCd((String) userAttr.get("instiCd"));
				userDDTO.setInstiName((String) userAttr.get("instiName"));
				userDDTO.setTelno((String) userAttr.get("telno"));
				userDDTO.setCphnNo((String) userAttr.get("cphnNo"));
				userDDTO.setEmad((String) userAttr.get("emad"));
				userDDTO.setFaxNo((String) userAttr.get("faxNo"));
				userDDTO.setBrdt((String) userAttr.get("brdt"));
				userDDTO.setAddr((String) userAttr.get("addr"));
				userDDTO.setZip((String) userAttr.get("zip"));
				userDDTO.setEmalRecvYn((String) userAttr.get("emalRecvYn"));
				userDDTO.setSmsRecvYn((String) userAttr.get("sMSRecvYn"));
				userDDTO.setUseYn((String) userAttr.get("useYn"));
				userDDTO.setPrcssStusDstcd((String) userAttr.get("prcssStusDstcd"));
				userDDTO.setEntcoYmd((String) userAttr.get("entcoYmd"));
				userDDTO.setRtireYmd((String) userAttr.get("rtireYmd"));
				userDDTO.setRegiYmd((String) userAttr.get("regiYmd"));
				userDDTO.setRegsntID((String) userAttr.get("regsntID"));
				userDDTO.setAmndrID((String) userAttr.get("amndrID"));
				userDDTO.setAmndYmd((String) userAttr.get("amndYmd"));
				userDDTO.setCrud((String) userAttr.get("crud"));

				userDDTOs[i] = userDDTO;
			}

			idcuser = new DCUser();
			idcuser.crudUser(userDDTOs);

		} else if (rsux != null && rsux.equals("S0")) {

			UserDDTO userDDTO = new UserDDTO();
			NewGenericDto subDto = reqData.getInputGenericDto().using(NewGenericDto.INDATA);

			Map<String, Object> attrMap = subDto.getAttributeMap();
			if (subDto != null) {
				userDDTO.setUserDstcd((String) attrMap.get("userDstcd"));
				userDDTO.setUserName((String) attrMap.get("userName"));
				userDDTO.setUserID((String) attrMap.get("uID"));
				userDDTO.setDvsnName((String) attrMap.get("dvsnName"));
				userDDTO.setUseYn((String) attrMap.get("useYn"));
			}
			idcuser = new DCUser();
			resUsers = idcuser.getListUser(userDDTO);
			reqData.getOutputGenericDto().using(NewGenericDto.OUTDATA).add(resUsers);

		}

		return reqData;
	}

}
