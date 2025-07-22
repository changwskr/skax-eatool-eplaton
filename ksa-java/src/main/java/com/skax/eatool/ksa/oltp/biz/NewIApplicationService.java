package com.skax.eatool.ksa.oltp.biz;

import com.skax.eatool.ksa.infra.po.NewKBData;
import com.skax.eatool.ksa.exception.NewBusinessException;

/**
 * New IApplicationService interface
 * 
 * This is a stub implementation to replace the missing KSA framework
 * IApplicationService
 */
public interface NewIApplicationService {
    /**
     * Execute the application service
     * @param reqData Request data
     * @return Response data
     * @throws NewBusinessException Business exception
     */
    NewKBData execute(NewKBData reqData) throws NewBusinessException;
}