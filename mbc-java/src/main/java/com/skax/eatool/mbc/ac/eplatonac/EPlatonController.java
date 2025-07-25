package com.skax.eatool.mbc.ac.eplatonac;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * EPlaton Framework Controller
 * 
 * Provides REST API endpoints for EPlaton business delegate operations
 * using POST method for all operations.
 */
@Controller
@RequestMapping("/mbc/eplaton")
public class EPlatonController {

    private static final Logger logger = LoggerFactory.getLogger(EPlatonController.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HHmmss");

    @Autowired
    private EPlatonBizDelegateSBBean ePlatonBizDelegateSBBean;

    /**
     * EPlaton 메인 페이지 - 관리 페이지로 리다이렉트
     */
    @GetMapping("")
    public String eplatonMain() {
        logger.info("==================[EPlatonController.eplatonMain START]");
        logger.info("==================[EPlatonController.eplatonMain END] - Redirecting to manage page");
        return "redirect:/mbc/eplaton/manage";
    }

    /**
     * EPlaton 관리 페이지 표시
     */
    @GetMapping("/manage")
    public String showEPlatonManagePage(Model model) {
        logger.info("==================[EPlatonController.showEPlatonManagePage START]");

        // 기본 설정값들을 모델에 추가
        model.addAttribute("defaultBankCode", "001");
        model.addAttribute("defaultBranchCode", "001");
        model.addAttribute("defaultUserId", "USER001");
        model.addAttribute("defaultSystemName", "CashCard");
        model.addAttribute("defaultActionName", "CashCardBizAction");
        model.addAttribute("defaultOperationName", "COMMO1000");
        model.addAttribute("defaultOperationMethod", "getCardInfo");
        model.addAttribute("defaultReqName", "CardInfoRequest");

        logger.info("==================[EPlatonController.showEPlatonManagePage END]");
        return "eplaton/eplaton-manage";
    }

    /**
     * Execute EPlaton business delegate operation
     * 
     * @param requestBody Request body containing operation details
     * @return ResponseEntity with operation result
     */
    @PostMapping("/api/execute")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> executeOperation(@RequestBody Map<String, Object> requestBody) {
        logger.info("==================[EPlatonController.executeOperation START] - Request: {}", requestBody);

        try {
            // Create EPlatonEvent from request
            EPlatonEvent event = createEPlatonEventFromRequest(requestBody);

            // Execute business delegate operation
            EPlatonEvent resultEvent = ePlatonBizDelegateSBBean.execute(event);

            // Create response
            Map<String, Object> response = createResponseFromEvent(resultEvent);

            logger.info("==================[EPlatonController.executeOperation END] - Success");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("==================[EPlatonController.executeOperation ERROR] - {}", e.getMessage(), e);

            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("errorCode", "ECON001");
            errorResponse.put("errorMessage", "Operation execution failed: " + e.getMessage());
            errorResponse.put("timestamp", LocalDateTime.now().toString());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * Execute read-only EPlaton business delegate operation
     * 
     * @param requestBody Request body containing operation details
     * @return ResponseEntity with operation result
     */
    @PostMapping("/api/execute-readonly")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> executeReadOnlyOperation(@RequestBody Map<String, Object> requestBody) {
        logger.info("==================[EPlatonController.executeReadOnlyOperation START] - Request: {}", requestBody);

        try {
            // Create EPlatonEvent from request
            EPlatonEvent event = createEPlatonEventFromRequest(requestBody);

            // Execute read-only business delegate operation
            EPlatonEvent resultEvent = ePlatonBizDelegateSBBean.executeReadOnly(event);

            // Create response
            Map<String, Object> response = createResponseFromEvent(resultEvent);

            logger.info("==================[EPlatonController.executeReadOnlyOperation END] - Success");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("==================[EPlatonController.executeReadOnlyOperation ERROR] - {}", e.getMessage(),
                    e);

            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("errorCode", "ECON002");
            errorResponse.put("errorMessage", "Read-only operation execution failed: " + e.getMessage());
            errorResponse.put("timestamp", LocalDateTime.now().toString());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * Route to specific business action
     * 
     * @param requestBody Request body containing action details
     * @return ResponseEntity with action result
     */
    @PostMapping("/api/route-action")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> routeToAction(@RequestBody Map<String, Object> requestBody) {
        logger.info("==================[EPlatonController.routeToAction START] - Request: {}", requestBody);

        try {
            // Create EPlatonEvent from request
            EPlatonEvent event = createEPlatonEventFromRequest(requestBody);

            // Route to specific business action
            EPlatonEvent resultEvent = ePlatonBizDelegateSBBean.routeToAction(event);

            // Create response
            Map<String, Object> response = createResponseFromEvent(resultEvent);

            logger.info("==================[EPlatonController.routeToAction END] - Success");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("==================[EPlatonController.routeToAction ERROR] - {}", e.getMessage(), e);

            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("errorCode", "ECON003");
            errorResponse.put("errorMessage", "Action routing failed: " + e.getMessage());
            errorResponse.put("timestamp", LocalDateTime.now().toString());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * Health check endpoint
     * 
     * @return ResponseEntity with health status
     */
    @PostMapping("/api/health")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> healthCheck() {
        logger.info("==================[EPlatonController.healthCheck START]");

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("status", "UP");
        response.put("service", "EPlatonController");
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("message", "EPlaton Controller is running");

        logger.info("==================[EPlatonController.healthCheck END] - Success");
        return ResponseEntity.ok(response);
    }

    /**
     * Create EPlatonEvent from request body
     */
    private EPlatonEvent createEPlatonEventFromRequest(Map<String, Object> requestBody) {
        EPlatonEvent event = new EPlatonEvent();

        // Set common information
        EPlatonCommonDTO common = new EPlatonCommonDTO();
        if (requestBody.containsKey("common")) {
            Map<String, Object> commonData = (Map<String, Object>) requestBody.get("common");
            if (commonData.containsKey("bankCode")) {
                common.setBankCode((String) commonData.get("bankCode"));
            }
            if (commonData.containsKey("branchCode")) {
                common.setBranchCode((String) commonData.get("branchCode"));
            }
            if (commonData.containsKey("userId")) {
                common.setUserID((String) commonData.get("userId"));
            }
        }
        event.setCommon(common);

        // Set TPSVCINFO
        TPSVCINFODTO tpmsvc = new TPSVCINFODTO();
        if (requestBody.containsKey("actionName")) {
            tpmsvc.setAction_name((String) requestBody.get("actionName"));
        }
        if (requestBody.containsKey("systemName")) {
            tpmsvc.setSystem_name((String) requestBody.get("systemName"));
        }
        if (requestBody.containsKey("operationName")) {
            tpmsvc.setOperation_name((String) requestBody.get("operationName"));
        }
        if (requestBody.containsKey("operationMethod")) {
            tpmsvc.setOperation_method((String) requestBody.get("operationMethod"));
        }
        if (requestBody.containsKey("reqName")) {
            tpmsvc.setReqName((String) requestBody.get("reqName"));
        }

        // Set current timestamp
        String currentDate = LocalDateTime.now().format(DATE_FORMATTER);
        String currentTime = LocalDateTime.now().format(TIME_FORMATTER);
        tpmsvc.setSystem_date(currentDate);
        tpmsvc.setWeb_intime(currentTime);

        event.setTPSVCINFODTO(tpmsvc);

        // Set request data
        if (requestBody.containsKey("request")) {
            // Create a simple DTO for request data
            RequestDTO requestDTO = new RequestDTO();
            requestDTO.setData(requestBody.get("request"));
            event.setRequest(requestDTO);
        }

        // Set action
        if (requestBody.containsKey("action")) {
            event.setAction((String) requestBody.get("action"));
        }

        // Set source and type
        event.setSource("REST_API");
        event.setType("POST");

        return event;
    }

    /**
     * Create response from EPlatonEvent
     */
    private Map<String, Object> createResponseFromEvent(EPlatonEvent event) {
        Map<String, Object> response = new HashMap<>();

        if (event != null) {
            response.put("success", true);
            response.put("timestamp", LocalDateTime.now().toString());

            // Add TPSVCINFO
            if (event.getTPSVCINFODTO() != null) {
                Map<String, Object> tpmsvc = new HashMap<>();
                tpmsvc.put("actionName", event.getTPSVCINFODTO().getAction_name());
                tpmsvc.put("systemName", event.getTPSVCINFODTO().getSystem_name());
                tpmsvc.put("operationName", event.getTPSVCINFODTO().getOperation_name());
                tpmsvc.put("errorCode", event.getTPSVCINFODTO().getErrorcode());
                tpmsvc.put("errorMessage", event.getTPSVCINFODTO().getError_message());
                tpmsvc.put("systemDate", event.getTPSVCINFODTO().getSystem_date());
                tpmsvc.put("webInTime", event.getTPSVCINFODTO().getWeb_intime());
                tpmsvc.put("webOutTime", event.getTPSVCINFODTO().getWeb_outtime());
                response.put("tpmsvc", tpmsvc);
            }

            // Add common info
            if (event.getCommon() != null) {
                Map<String, Object> common = new HashMap<>();
                common.put("bankCode", event.getCommon().getBankCode());
                common.put("branchCode", event.getCommon().getBranchCode());
                common.put("userId", event.getCommon().getUserID());
                response.put("common", common);
            }

            // Add response data
            if (event.getResponse() != null) {
                response.put("response", event.getResponse());
            }

            // Add request data
            if (event.getRequest() != null) {
                response.put("request", event.getRequest());
            }

            // Add action
            response.put("action", event.getAction());
            response.put("source", event.getSource());
            response.put("type", event.getType());

        } else {
            response.put("success", false);
            response.put("errorMessage", "No response event received");
        }

        return response;
    }

    /**
     * Simple DTO for request data
     */
    private static class RequestDTO implements IDTO {
        private String id;
        private String status;
        private String message;
        private Object data;

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }

        @Override
        public String getId() {
            return id;
        }

        @Override
        public void setId(String id) {
            this.id = id;
        }

        @Override
        public String getStatus() {
            return status;
        }

        @Override
        public void setStatus(String status) {
            this.status = status;
        }

        @Override
        public String getMessage() {
            return message;
        }

        @Override
        public void setMessage(String message) {
            this.message = message;
        }

        @Override
        public String toString() {
            return "RequestDTO{id='" + id + "', status='" + status + "', message='" + message + "', data=" + data + "}";
        }
    }
}
