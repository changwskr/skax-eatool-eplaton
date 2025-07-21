package com.skax.eatool.mbc.web.accountas.controller;

import com.skax.eatool.mbc.as.accountas.ASMBC71001;
import com.skax.eatool.ksa.infra.po.NewKBData;
import com.skax.eatool.ksa.infra.po.NewGenericDto;
import com.skax.eatool.mbc.pc.dto.AccountPDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AccountAS 테스트 컨트롤러
 * 
 * @author SKAX Project Team
 * @version 1.0
 */
@Controller
@RequestMapping("/accountas/test")
public class AccountAsTestController {
    private static final Logger logger = LoggerFactory.getLogger(AccountAsTestController.class);
    private final ASMBC71001 asmbc71001;

    @Autowired
    public AccountAsTestController(ASMBC71001 asmbc71001) {
        this.asmbc71001 = asmbc71001;
    }

    @GetMapping
    public String showTestPage() {
        logger.info("=== AccountAsTestController.showTestPage START ===");
        logger.info("=== AccountAsTestController.showTestPage END ===");
        return "web/accountas-test";
    }

    @PostMapping
    public String runTest(@RequestParam String accountId,
                          @RequestParam String accountName,
                          @RequestParam String accountType,
                          Model model) {
        logger.info("=== AccountAsTestController.runTest START ===");
        try {
            // Build AccountPDTO
            AccountPDTO dto = new AccountPDTO();
            dto.setAccountId(accountId);
            dto.setAccountName(accountName);
            dto.setAccountType(accountType);

            // Build NewKBData with INDATA
            NewKBData reqData = new NewKBData();
            NewGenericDto inDto = reqData.getInputGenericDto();
            inDto.using(NewGenericDto.INDATA).put("AccountPDTO", dto);

            // Execute service
            asmbc71001.execute(reqData);
            model.addAttribute("result", "계정 생성 성공");
        } catch (Exception e) {
            model.addAttribute("result", "오류: " + e.getMessage());
        }
        logger.info("=== AccountAsTestController.runTest END ===");
        return "web/accountas-test";
    }
} 