package com.skax.eatool.mbc.dc.usermgtdc;

import java.util.HashMap;
import java.util.List;
import com.skax.eatool.ksa.exception.NewBusinessException;
import com.skax.eatool.mbc.as.bzcrudbus.transfer.ICommonDTO;
import com.skax.eatool.mbc.dc.usermgtdc.dto.UserDDTO;
import com.skax.eatool.mbc.dc.usermgtdc.dto.PageDDTO;
import com.skax.eatool.mbc.dc.usermgtdc.dto.TreeDDTO;

/**
 * Stub interface for IDCUser
 */
public interface IDCUser {

    String getUserId();

    void setUserId(String userId);

    String getUserName();

    void setUserName(String userName);

    String getUserEmail();

    void setUserEmail(String userEmail);

    List<HashMap> getUserList(ICommonDTO commonDto) throws NewBusinessException;

    // Add missing methods
    User selectUser(String userId) throws Exception;

    void crudUser(UserDDTO[] userDDTOs) throws NewBusinessException;

    List<User> getListUser(UserDDTO userDDTO) throws NewBusinessException;

    List<Page> getListPage(PageDDTO pageDDTO) throws NewBusinessException;

    List<Tree> getListTree(TreeDDTO treeDDTO) throws NewBusinessException;

    // Add other stub methods as needed
}
