package com.skax.eatool.mbc.as.bzcrudbus.business.as;

import com.skax.eatool.ksa.exception.NewBusinessException;
import com.skax.eatool.ksa.infra.po.NewKBData;

/**
 * Common Application Service Execution class
 */
public class ASComExec {

    public NewKBData execute(NewKBData reqData) throws NewBusinessException {
        // Default implementation - can be overridden by subclasses
        return reqData;
    }
}