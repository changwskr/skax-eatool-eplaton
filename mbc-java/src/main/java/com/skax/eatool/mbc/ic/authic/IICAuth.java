package com.skax.eatool.mbc.ic.authic;

public interface IICAuth {

	/**
	 * 
	 * Returns button event processing authority for the specified user
	 * 
	 * @param usrId    : User ID
	 * @param grpCd    : Group code
	 * @param winNo    : Window number
	 * @param buttonCd : Button code
	 * @return boolean
	 */
	public boolean isButtonHasAuth(String usrId, String grpCd, String winNo, String buttonCd);

}
