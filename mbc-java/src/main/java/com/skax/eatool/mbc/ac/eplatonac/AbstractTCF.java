package com.skax.eatool.mbc.ac.eplatonac;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;


/**
 * =============================================================================
 * 프로그램 명: Spring 기반 Transaction Control Framework 추상 클래스
 * =============================================================================
 * EJB에서 Spring Transaction Management로 변경
 * 
 * =============================================================================
 * 변경내역 정보:
 * =============================================================================
 * 2004년 03월 16일 1차버전 release
 * 2024년 Spring Boot 변경
 * =============================================================================
 * 
 * @author : 장우성(WooSungJang)
 * @company: IMS SYSTEM
 * @email : changwskr@yahoo.co.kr
 * @version 2.0
 *          =============================================================================
 */
public abstract class AbstractTCF {

    protected static final Logger logger = LoggerFactory.getLogger(AbstractTCF.class);
    protected String ctx;

    /**
     * @see Object#Object()
     */
    public AbstractTCF() {
        ctx = null;
    }

    /**
     * STF 로직에서 추가로 구성해야 할 부분을 관리
     */
    @Transactional(readOnly = true)
    public void stfActive01(EPlatonEvent event) throws Exception {
        logger.debug("STF Active 01 executed for event: {}", event);
        return;
    }

    @Transactional(readOnly = true)
    public void stfActive02(EPlatonEvent event) throws Exception {
        logger.debug("STF Active 02 executed for event: {}", event);
        return;
    }

    @Transactional(readOnly = true)
    public void stfActive03(EPlatonEvent event) throws Exception {
        logger.debug("STF Active 03 executed for event: {}", event);
        return;
    }

    /**
     * ROUTE 로직에서 추가로 구성해야 할 부분을 관리
     */
    @Transactional(readOnly = true)
    public void routeActive01(EPlatonEvent event) throws Exception {
        logger.debug("Route Active 01 executed for event: {}", event);
        return;
    }

    @Transactional(readOnly = true)
    public void routeActive02(EPlatonEvent event) throws Exception {
        logger.debug("Route Active 02 executed for event: {}", event);
        return;
    }

    @Transactional(readOnly = true)
    public void routeActive03(EPlatonEvent event) throws Exception {
        logger.debug("Route Active 03 executed for event: {}", event);
        return;
    }

    /**
     * ETF 로직에서 추가로 구성해야 할 부분을 관리
     */
    @Transactional(readOnly = true)
    public void etfActive01(EPlatonEvent event) throws Exception {
        logger.debug("ETF Active 01 executed for event: {}", event);
        return;
    }

    @Transactional(readOnly = true)
    public void etfActive02(EPlatonEvent event) throws Exception {
        logger.debug("ETF Active 02 executed for event: {}", event);
        return;
    }

    @Transactional(readOnly = true)
    public void etfActive03(EPlatonEvent event) throws Exception {
        logger.debug("ETF Active 03 executed for event: {}", event);
        return;
    }
}
