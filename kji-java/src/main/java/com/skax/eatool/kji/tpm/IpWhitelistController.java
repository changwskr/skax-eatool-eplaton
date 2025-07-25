package com.skax.eatool.kji.tpm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * IP 화이트리스트 관리 컨트롤러
 * 
 * 허용된 IP 주소를 동적으로 관리할 수 있는 REST API를 제공합니다.
 * 
 * @author EPlaton Framework Team
 * @version 1.0
 */
@RestController
@RequestMapping("/kji/tpm/ip-whitelist")
public class IpWhitelistController {
    
    private static final Logger logger = LoggerFactory.getLogger(IpWhitelistController.class);
    
    @Autowired
    private IpWhitelistConfig ipWhitelistConfig;
    
    /**
     * IP 화이트리스트 상태 조회
     */
    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getStatus() {
        logger.info("==================[IpWhitelistController.getStatus] - IP 화이트리스트 상태 조회");
        
        Map<String, Object> response = new HashMap<>();
        response.put("enabled", ipWhitelistConfig.isEnabled());
        response.put("internalOnly", ipWhitelistConfig.isInternalOnly());
        response.put("allowedIps", ipWhitelistConfig.getAllowedIps());
        response.put("allowedDomains", ipWhitelistConfig.getAllowedDomains());
        response.put("timestamp", System.currentTimeMillis());
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * IP 제한 활성화/비활성화
     */
    @PostMapping("/enable")
    public ResponseEntity<Map<String, Object>> enableIpRestriction(@RequestBody Map<String, Object> request) {
        logger.info("==================[IpWhitelistController.enableIpRestriction] - IP 제한 활성화/비활성화");
        
        Boolean enabled = (Boolean) request.get("enabled");
        if (enabled == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "enabled field is required"));
        }
        
        ipWhitelistConfig.setEnabled(enabled);
        
        Map<String, Object> response = new HashMap<>();
        response.put("enabled", ipWhitelistConfig.isEnabled());
        response.put("message", "IP restriction " + (enabled ? "enabled" : "disabled"));
        response.put("timestamp", System.currentTimeMillis());
        
        logger.info("IP 제한 {}됨", enabled ? "활성화" : "비활성화");
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * 허용된 IP 목록 조회
     */
    @GetMapping("/allowed-ips")
    public ResponseEntity<Map<String, Object>> getAllowedIps() {
        logger.info("==================[IpWhitelistController.getAllowedIps] - 허용된 IP 목록 조회");
        
        Map<String, Object> response = new HashMap<>();
        response.put("allowedIps", ipWhitelistConfig.getAllowedIps());
        response.put("count", ipWhitelistConfig.getAllowedIps().size());
        response.put("timestamp", System.currentTimeMillis());
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * 허용된 IP 추가
     */
    @PostMapping("/allowed-ips")
    public ResponseEntity<Map<String, Object>> addAllowedIp(@RequestBody Map<String, Object> request) {
        logger.info("==================[IpWhitelistController.addAllowedIp] - 허용된 IP 추가");
        
        String ip = (String) request.get("ip");
        if (ip == null || ip.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "ip field is required"));
        }
        
        List<String> currentIps = ipWhitelistConfig.getAllowedIps();
        if (!currentIps.contains(ip)) {
            currentIps.add(ip);
            ipWhitelistConfig.setAllowedIps(currentIps);
        }
        
        Map<String, Object> response = new HashMap<>();
        response.put("added", ip);
        response.put("allowedIps", ipWhitelistConfig.getAllowedIps());
        response.put("message", "IP added to whitelist");
        response.put("timestamp", System.currentTimeMillis());
        
        logger.info("IP 추가됨: {}", ip);
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * 허용된 IP 제거
     */
    @DeleteMapping("/allowed-ips/{ip}")
    public ResponseEntity<Map<String, Object>> removeAllowedIp(@PathVariable String ip) {
        logger.info("==================[IpWhitelistController.removeAllowedIp] - 허용된 IP 제거: {}", ip);
        
        List<String> currentIps = ipWhitelistConfig.getAllowedIps();
        boolean removed = currentIps.remove(ip);
        
        if (removed) {
            ipWhitelistConfig.setAllowedIps(currentIps);
        }
        
        Map<String, Object> response = new HashMap<>();
        response.put("removed", ip);
        response.put("success", removed);
        response.put("allowedIps", ipWhitelistConfig.getAllowedIps());
        response.put("message", removed ? "IP removed from whitelist" : "IP not found in whitelist");
        response.put("timestamp", System.currentTimeMillis());
        
        logger.info("IP 제거됨: {} (성공: {})", ip, removed);
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * IP 테스트 (허용 여부 확인)
     */
    @PostMapping("/test")
    public ResponseEntity<Map<String, Object>> testIp(@RequestBody Map<String, Object> request) {
        logger.info("==================[IpWhitelistController.testIp] - IP 테스트");
        
        String ip = (String) request.get("ip");
        if (ip == null || ip.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "ip field is required"));
        }
        
        boolean isAllowed = ipWhitelistConfig.isAllowed(ip);
        
        Map<String, Object> response = new HashMap<>();
        response.put("ip", ip);
        response.put("allowed", isAllowed);
        response.put("enabled", ipWhitelistConfig.isEnabled());
        response.put("internalOnly", ipWhitelistConfig.isInternalOnly());
        response.put("timestamp", System.currentTimeMillis());
        
        logger.info("IP 테스트 결과: {} -> {}", ip, isAllowed ? "허용" : "거부");
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * 허용된 도메인 목록 조회
     */
    @GetMapping("/allowed-domains")
    public ResponseEntity<Map<String, Object>> getAllowedDomains() {
        logger.info("==================[IpWhitelistController.getAllowedDomains] - 허용된 도메인 목록 조회");
        
        Map<String, Object> response = new HashMap<>();
        response.put("allowedDomains", ipWhitelistConfig.getAllowedDomains());
        response.put("count", ipWhitelistConfig.getAllowedDomains().size());
        response.put("timestamp", System.currentTimeMillis());
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * 허용된 도메인 추가
     */
    @PostMapping("/allowed-domains")
    public ResponseEntity<Map<String, Object>> addAllowedDomain(@RequestBody Map<String, Object> request) {
        logger.info("==================[IpWhitelistController.addAllowedDomain] - 허용된 도메인 추가");
        
        String domain = (String) request.get("domain");
        if (domain == null || domain.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "domain field is required"));
        }
        
        List<String> currentDomains = ipWhitelistConfig.getAllowedDomains();
        if (!currentDomains.contains(domain)) {
            currentDomains.add(domain);
            ipWhitelistConfig.setAllowedDomains(currentDomains);
        }
        
        Map<String, Object> response = new HashMap<>();
        response.put("added", domain);
        response.put("allowedDomains", ipWhitelistConfig.getAllowedDomains());
        response.put("message", "Domain added to whitelist");
        response.put("timestamp", System.currentTimeMillis());
        
        logger.info("도메인 추가됨: {}", domain);
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * 내부 네트워크만 허용 설정
     */
    @PostMapping("/internal-only")
    public ResponseEntity<Map<String, Object>> setInternalOnly(@RequestBody Map<String, Object> request) {
        logger.info("==================[IpWhitelistController.setInternalOnly] - 내부 네트워크만 허용 설정");
        
        Boolean internalOnly = (Boolean) request.get("internalOnly");
        if (internalOnly == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "internalOnly field is required"));
        }
        
        ipWhitelistConfig.setInternalOnly(internalOnly);
        
        Map<String, Object> response = new HashMap<>();
        response.put("internalOnly", ipWhitelistConfig.isInternalOnly());
        response.put("message", "Internal network only " + (internalOnly ? "enabled" : "disabled"));
        response.put("timestamp", System.currentTimeMillis());
        
        logger.info("내부 네트워크만 허용 {}됨", internalOnly ? "활성화" : "비활성화");
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * IP 화이트리스트 관리 페이지
     */
    @GetMapping("/page")
    public String getIpWhitelistPage() {
        logger.info("==================[IpWhitelistController.getIpWhitelistPage] - IP 화이트리스트 관리 페이지");
        return "ip-whitelist";
    }
} 