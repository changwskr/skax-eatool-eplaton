
package com.skax.eatool.mbc.as.usermgtas;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.skax.eatool.ksa.exception.NewBusinessException;
import com.skax.eatool.ksa.infra.po.NewApplicationContext;
import com.skax.eatool.ksa.infra.po.NewGenericDto;
import com.skax.eatool.ksa.infra.po.NewKBData;
import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLogHelper;
import com.skax.eatool.ksa.oltp.biz.NewIApplicationService;
import com.skax.eatool.mbc.dc.usermgtdc.DCUser;
import com.skax.eatool.mbc.dc.usermgtdc.IDCUser;
import com.skax.eatool.mbc.dc.usermgtdc.Tree;

import com.skax.eatool.mbc.dc.usermgtdc.dto.TreeDDTO;

public class ASMBC75Z02 implements NewIApplicationService {

        protected NewIKesaLogger logger = NewKesaLogHelper.getBiz();

        public NewKBData execute(NewKBData reqData) throws NewBusinessException {

                IDCUser idcuser = null;
                List<Tree> resTrees = null;
                TreeDDTO treeDDTO = new TreeDDTO();

                String testString = null;
                int testInt = 0;
                Integer testInteger = 0;
                BigDecimal testDecimal = new BigDecimal(0);
                float testFloat = 0;
                double testDouble = 0;

                NewGenericDto input = reqData.getInputGenericDto().using(NewGenericDto.INDATA);
                NewGenericDto output = reqData.getOutputGenericDto().using(NewGenericDto.OUTDATA);

                Map<String, Object> attrMap = input.getAttributeMap();

                logger.debug("testString = " + attrMap.get("testString"));
                logger.debug("testInt = " + attrMap.get("testInt"));
                logger.debug("testInteger = " + attrMap.get("testInteger"));
                logger.debug("testDecimal = " + attrMap.get("testDecimal"));
                logger.debug("testFloat = " + attrMap.get("testFloat"));
                logger.debug("testDouble = " + attrMap.get("testDouble"));
                logger.debug("nodeid = " + attrMap.get("nodeid"));

                // Get Common transaction code from ApplicationContext
                String TransactionId = (String) NewApplicationContext.get(NewApplicationContext.StndTelgmRecvTranCd);
                String rsux = TransactionId.substring(8, 10);

                System.out.println(
                                "StndCicsTrncd :" + NewApplicationContext.get(NewApplicationContext.StndCicsTrncd));
                System.out.println(
                                "StndIntnlStndTelgmLen :"
                                                + NewApplicationContext
                                                                .get(NewApplicationContext.StndIntnlStndTelgmLen));
                System.out.println(
                                "StndTranBaseYmd :"
                                                + NewApplicationContext.get(NewApplicationContext.StndTranBaseYmd));
                System.out.println("StndGuIdNo :" + NewApplicationContext.get(NewApplicationContext.StndGuIdNo));
                System.out.println("StndTelgmDmndDstcd :"
                                + NewApplicationContext.get(NewApplicationContext.StndTelgmDmndDstcd));
                System.out.println("StndTelgmFmatDstcd :"
                                + NewApplicationContext.get(NewApplicationContext.StndTelgmFmatDstcd));
                System.out.println(
                                "StndTelgmValdYmd :" + NewApplicationContext
                                                .get(NewApplicationContext.StndTelgmValdYmd));
                // System.out.println("StndHdrSpareArea :" +
                // NewApplicationContext.get(NewApplicationContext.Key.StndHdrSpareArea));
                System.out.println(
                                "StndGroupCoCd :" + NewApplicationContext.get(NewApplicationContext.StndGroupCoCd));
                System.out
                                .println("StndTelgmRecvTranCd :"
                                                + NewApplicationContext
                                                                .get(NewApplicationContext.StndTelgmRecvTranCd));
                System.out.println("StndPrcssRtdTranCd :"
                                + NewApplicationContext.get(NewApplicationContext.StndPrcssRtdTranCd));
                System.out.println("StndScrenNo :" + NewApplicationContext.get(NewApplicationContext.StndScrenNo));
                System.out.println(
                                "StndOsidInstiCd :"
                                                + NewApplicationContext.get(NewApplicationContext.StndOsidInstiCd));
                System.out.println(
                                "StndTranSerno :" + NewApplicationContext.get(NewApplicationContext.StndTranSerno));
                System.out.println("StndInoPartlDstcd :"
                                + NewApplicationContext.get(NewApplicationContext.StndInoPartlDstcd));
                System.out.println(
                                "StndCmpxTranDmndDstcd :"
                                                + NewApplicationContext
                                                                .get(NewApplicationContext.StndCmpxTranDmndDstcd));
                System.out.println(
                                "StndSysOperEvirnDstcd :"
                                                + NewApplicationContext
                                                                .get(NewApplicationContext.StndSysOperEvirnDstcd));
                System.out
                                .println("StndItsmMoniTagetYn :"
                                                + NewApplicationContext
                                                                .get(NewApplicationContext.StndItsmMoniTagetYn));
                System.out.println("StndTranPtrnDstcd :"
                                + NewApplicationContext.get(NewApplicationContext.StndTranPtrnDstcd));
                System.out.println("StndRbndTranYn :"
                                + NewApplicationContext.get(NewApplicationContext.StndRbndTranYn));
                System.out
                                .println("StndTermlWaitRqstYn :"
                                                + NewApplicationContext
                                                                .get(NewApplicationContext.StndTermlWaitRqstYn));
                System.out.println(
                                "StndOgtranRstrYn :" + NewApplicationContext
                                                .get(NewApplicationContext.StndOgtranRstrYn));
                System.out.println(
                                "StndOgtranGuIdNo :" + NewApplicationContext
                                                .get(NewApplicationContext.StndOgtranGuIdNo));
                System.out.println("StndBnkCd :" + NewApplicationContext.get(NewApplicationContext.StndBnkCd));
                System.out.println(
                                "StndTranBrncd :" + NewApplicationContext.get(NewApplicationContext.StndTranBrncd));
                System.out.println("StndFeePrcssBrncd :"
                                + NewApplicationContext.get(NewApplicationContext.StndFeePrcssBrncd));
                System.out.println("StndRelayChnlDstcd :"
                                + NewApplicationContext.get(NewApplicationContext.StndRelayChnlDstcd));
                System.out.println(
                                "StndChnlDstcd :" + NewApplicationContext.get(NewApplicationContext.StndChnlDstcd));
                // System.out.println("StndChnlDBzwkDstcd :" +
                // NewApplicationContext.get(NewApplicationContext.Key.StndChnlDBzwkDstcd));
                System.out.println(
                                "StndMdiaDstcd :" + NewApplicationContext.get(NewApplicationContext.StndMdiaDstcd));
                System.out.println(
                                "StndLangDstcd :" + NewApplicationContext.get(NewApplicationContext.StndLangDstcd));
                System.out.println("StndTrmno :" + NewApplicationContext.get(NewApplicationContext.StndTrmno));
                System.out.println(
                                "StndUserEmpid :" + NewApplicationContext.get(NewApplicationContext.StndUserEmpid));
                System.out.println(
                                "StndTermlOprtrno :" + NewApplicationContext
                                                .get(NewApplicationContext.StndTermlOprtrno));
                System.out.println(
                                "StndTermlSpvsrNo :" + NewApplicationContext
                                                .get(NewApplicationContext.StndTermlSpvsrNo));
                System.out.println("StndN1StSpvsrBrncd :"
                                + NewApplicationContext.get(NewApplicationContext.StndN1StSpvsrBrncd));
                System.out.println("StndN2NdSpvsrBrncd :"
                                + NewApplicationContext.get(NewApplicationContext.StndN2NdSpvsrBrncd));
                System.out.println("StndN2NdSpvsrTrmno :"
                                + NewApplicationContext.get(NewApplicationContext.StndN2NdSpvsrTrmno));
                System.out.println(
                                "StndInptMsgPtrnDstcd :"
                                                + NewApplicationContext
                                                                .get(NewApplicationContext.StndInptMsgPtrnDstcd));
                System.out.println("StndInptMsgCtnnYn :"
                                + NewApplicationContext.get(NewApplicationContext.StndInptMsgCtnnYn));
                System.out.println(
                                "StndInptMsgSerno :" + NewApplicationContext
                                                .get(NewApplicationContext.StndInptMsgSerno));
                System.out.println("StndInptMsgWritYms :"
                                + NewApplicationContext.get(NewApplicationContext.StndInptMsgWritYms));
                System.out.println("StndAthorFnshDstcd :"
                                + NewApplicationContext.get(NewApplicationContext.StndAthorFnshDstcd));
                System.out
                                .println("StndSpvsrAthorDstcd :"
                                                + NewApplicationContext
                                                                .get(NewApplicationContext.StndSpvsrAthorDstcd));
                System.out.println("StndN1StSpvsrEmpid :"
                                + NewApplicationContext.get(NewApplicationContext.StndN1StSpvsrEmpid));
                System.out.println("StndN2NdSpvsrEmpid :"
                                + NewApplicationContext.get(NewApplicationContext.StndN2NdSpvsrEmpid));
                System.out
                                .println("StndSpvsrAResnNoitm :"
                                                + NewApplicationContext
                                                                .get(NewApplicationContext.StndSpvsrAResnNoitm));
                System.out.println(
                                "StndSpvsrAthorResnCd :"
                                                + NewApplicationContext
                                                                .get(NewApplicationContext.StndSpvsrAthorResnCd));
                System.out.println(
                                "StndClsngAfYn :" + NewApplicationContext.get(NewApplicationContext.StndClsngAfYn));
                // System.out.println("StndBnkbkTranYn :" +
                // NewApplicationContext.get(NewApplicationContext.Key.StndBnkbkTranYn));
                System.out.println("StndLsdtTranYn :"
                                + NewApplicationContext.get(NewApplicationContext.StndLsdtTranYn));
                System.out.println("StndIdtrek :" + NewApplicationContext.get(NewApplicationContext.StndIdtrek));
                System.out.println(
                                "StndBzoprDdDstcd :" + NewApplicationContext
                                                .get(NewApplicationContext.StndBzoprDdDstcd));
                System.out.println(
                                "StndSodBbrnPtrnDstcd :"
                                                + NewApplicationContext
                                                                .get(NewApplicationContext.StndSodBbrnPtrnDstcd));
                System.out.println(
                                "StndSodUserPtrnDstcd :"
                                                + NewApplicationContext
                                                                .get(NewApplicationContext.StndSodUserPtrnDstcd));
                System.out.println("StndInotAbilYn :"
                                + NewApplicationContext.get(NewApplicationContext.StndInotAbilYn));
                System.out.println(
                                "StndNoRtaUserYn :"
                                                + NewApplicationContext.get(NewApplicationContext.StndNoRtaUserYn));
                System.out.println("StndTranDscnDmndYn :"
                                + NewApplicationContext.get(NewApplicationContext.StndTranDscnDmndYn));
                System.out.println(
                                "StndCallgPgmName :" + NewApplicationContext
                                                .get(NewApplicationContext.StndCallgPgmName));
                System.out.println("StndRecvLuName :"
                                + NewApplicationContext.get(NewApplicationContext.StndRecvLuName));
                System.out.println(
                                "StndCnclDstcd :" + NewApplicationContext.get(NewApplicationContext.StndCnclDstcd));
                System.out.println("StndCnclPtrnDstcd :"
                                + NewApplicationContext.get(NewApplicationContext.StndCnclPtrnDstcd));
                System.out
                                .println("StndTranCcResnDstcd :"
                                                + NewApplicationContext
                                                                .get(NewApplicationContext.StndTranCcResnDstcd));
                System.out.println("StndTranCcResnCtnt :"
                                + NewApplicationContext.get(NewApplicationContext.StndTranCcResnCtnt));
                System.out.println(
                                "StndOgtranYms :" + NewApplicationContext.get(NewApplicationContext.StndOgtranYms));
                System.out.println(
                                "StndCnclRstrInfoCtnt :"
                                                + NewApplicationContext
                                                                .get(NewApplicationContext.StndCnclRstrInfoCtnt));
                System.out.println("StndDscnTranDstcd :"
                                + NewApplicationContext.get(NewApplicationContext.StndDscnTranDstcd));
                System.out.println("StndIdiviDataEdtYn :"
                                + NewApplicationContext.get(NewApplicationContext.StndIdiviDataEdtYn));
                System.out.println("StndOpbrnCd :" + NewApplicationContext.get(NewApplicationContext.StndOpbrnCd));
                // System.out.println("StndCncutIScopAddr :" +
                // NewApplicationContext.get(NewApplicationContext.Key.StndCncutIScopAddr));
                System.out
                                .println("StndOutptDPtrnDstcd :"
                                                + NewApplicationContext
                                                                .get(NewApplicationContext.StndOutptDPtrnDstcd));
                System.out.println(
                                "StndOutptMsgPtrnDstcd :"
                                                + NewApplicationContext
                                                                .get(NewApplicationContext.StndOutptMsgPtrnDstcd));
                System.out.println("StndOutptMsgCtnnYn :"
                                + NewApplicationContext.get(NewApplicationContext.StndOutptMsgCtnnYn));
                System.out.println("StndOutptMsgSerno :"
                                + NewApplicationContext.get(NewApplicationContext.StndOutptMsgSerno));
                System.out
                                .println("StndOutptMsgWritYms :"
                                                + NewApplicationContext
                                                                .get(NewApplicationContext.StndOutptMsgWritYms));
                System.out
                                .println("StndUserPaNotacrdYn :"
                                                + NewApplicationContext
                                                                .get(NewApplicationContext.StndUserPaNotacrdYn));
                System.out.println("StndErrcd :" + NewApplicationContext.get(NewApplicationContext.StndErrcd));
                System.out.println("StndTreatCd :" + NewApplicationContext.get(NewApplicationContext.StndTreatCd));
                System.out
                                .println("StndCncutDscnRqstYn :"
                                                + NewApplicationContext
                                                                .get(NewApplicationContext.StndCncutDscnRqstYn));
                // System.out.println("StndComSpareArea :" +
                // NewApplicationContext.get(NewApplicationContext.Key.StndComSpareArea));

                // Check transaction code for R/S/U/X
                if (rsux != null && rsux.equals("R0")) {

                        treeDDTO.setNodeid((String) attrMap.get("nodeid"));

                        idcuser = new DCUser();
                        resTrees = idcuser.getListTree(treeDDTO);

                        reqData.getOutputGenericDto().using(NewGenericDto.OUTDATA).add(resTrees);

                } else if (rsux != null && rsux.equals("S0")) {

                        for (int i = 0; i < 3; i++) {
                                for (int j = 0; j < 5; j++) {
                                        output.addNode("treeGrid");
                                        // Set attributes for tree DTO

                                        output.addAttribute("treeId", "10" + i + j);
                                        output.addAttribute("treeNm", "data=treeGridlevel" + j + ";level=" + j);
                                        output.addAttribute("treeCol1", "treeCol1");
                                        output.addAttribute("treeCol2", "treeCol2");

                                }
                        }
                }
                return reqData;
        }

}
