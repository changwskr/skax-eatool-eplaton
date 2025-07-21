/**
 * 
 */
package com.skax.eatool.mbc.pc.user;

import java.util.List;

import com.skax.eatool.mbc.dc.usermgtdc.IDCUser;
import com.skax.eatool.mbc.dc.usermgtdc.User;
import com.skax.eatool.ksa.exception.NewBusinessException;
import com.skax.eatool.mbc.as.bzcrudbus.transfer.ICommonDTO;

/**
 * @author S001849
 *
 */
public class PCUser {

	public List getUserList(ICommonDTO commonDto) throws NewBusinessException {
		// KBData reqData = commonDto.getKbData();

		IDCUser dcUser = new User();

		System.out.println("############ getUserList ############");

		List usrList = dcUser.getUserList(commonDto);

		/*
		 * if(usrList != null) {
		 * 
		 * reqData.getOutputGenericDto().using(GenericDto.OUTDATA).addNode("/UserList").
		 * add(usrList);
		 * 
		 * }
		 */

		return usrList;
	}

}
