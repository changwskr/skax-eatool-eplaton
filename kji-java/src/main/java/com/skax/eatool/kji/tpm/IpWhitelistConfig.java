package com.skax.eatool.kji.tpm;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * IP 화이트리스트 설정
 * 
 * HttpTPSsendrecv에서 허용할 IP 주소를 관리합니다.
 * 
 * @author EPlaton Framework Team
 * @version 1.0
 */
@Component
@ConfigurationProperties(prefix = "http.tps.ip-whitelist")
public class IpWhitelistConfig {
    
    private static final Logger logger = LoggerFactory.getLogger(IpWhitelistConfig.class);
    
    /**
     * 허용된 IP 주소 목록
     */
    private List<String> allowedIps = Arrays.asList(
        "127.0.0.1",           // localhost
        "localhost",           // localhost 도메인
        "192.168.0.0/16",      // 사설 네트워크 A
        "10.0.0.0/8",          // 사설 네트워크 B
        "172.16.0.0/12"        // 사설 네트워크 C
    );
    
    /**
     * IP 제한 활성화 여부
     */
    private boolean enabled = true;
    
    /**
     * 내부 네트워크만 허용 여부
     */
    private boolean internalOnly = true;
    
    /**
     * 허용된 도메인 목록 (내부 도메인)
     */
    private List<String> allowedDomains = Arrays.asList(
        "localhost",
        "*.internal",
        "*.local",
        "*.dev",
        "*.test"
    );
    
    @PostConstruct
    public void init() {
        logger.info("==================[IpWhitelistConfig.init] - IP 화이트리스트 설정 초기화");
        logger.info("IP 제한 활성화: {}", enabled);
        logger.info("내부 네트워크만 허용: {}", internalOnly);
        logger.info("허용된 IP 개수: {}", allowedIps.size());
        logger.info("허용된 도메인 개수: {}", allowedDomains.size());
        
        if (enabled) {
            logger.info("허용된 IP 목록: {}", allowedIps);
            logger.info("허용된 도메인 목록: {}", allowedDomains);
        }
    }
    
    /**
     * IP 주소가 허용되는지 확인
     */
    public boolean isAllowed(String ipOrHost) {
        if (!enabled) {
            return true; // IP 제한이 비활성화된 경우 모든 IP 허용
        }
        
        if (ipOrHost == null || ipOrHost.trim().isEmpty()) {
            return false;
        }
        
        String target = ipOrHost.trim().toLowerCase();
        
        // localhost 체크
        if (target.equals("localhost") || target.equals("127.0.0.1")) {
            return true;
        }
        
        // 허용된 IP 목록 체크
        for (String allowedIp : allowedIps) {
            if (isIpMatch(target, allowedIp)) {
                return true;
            }
        }
        
        // 허용된 도메인 목록 체크
        for (String allowedDomain : allowedDomains) {
            if (isDomainMatch(target, allowedDomain)) {
                return true;
            }
        }
        
        // 내부 네트워크만 허용하는 경우 추가 체크
        if (internalOnly && isInternalNetwork(target)) {
            return true;
        }
        
        logger.warn("==================[IpWhitelistConfig.isAllowed] - IP/도메인 거부됨: {}", target);
        return false;
    }
    
    /**
     * IP 주소 매칭 확인 (CIDR 표기법 지원)
     */
    private boolean isIpMatch(String target, String allowedIp) {
        if (target.equals(allowedIp)) {
            return true;
        }
        
        // CIDR 표기법 체크 (예: 192.168.0.0/16)
        if (allowedIp.contains("/")) {
            return isIpInCidrRange(target, allowedIp);
        }
        
        return false;
    }
    
    /**
     * CIDR 범위 내 IP 확인
     */
    private boolean isIpInCidrRange(String target, String cidr) {
        try {
            String[] parts = cidr.split("/");
            String network = parts[0];
            int prefixLength = Integer.parseInt(parts[1]);
            
            long networkLong = ipToLong(network);
            long targetLong = ipToLong(target);
            long mask = (0xFFFFFFFFL << (32 - prefixLength)) & 0xFFFFFFFFL;
            
            return (networkLong & mask) == (targetLong & mask);
        } catch (Exception e) {
            logger.error("CIDR 범위 체크 중 오류: {} - {}", cidr, e.getMessage());
            return false;
        }
    }
    
    /**
     * IP 주소를 long으로 변환
     */
    private long ipToLong(String ip) {
        String[] parts = ip.split("\\.");
        long result = 0;
        for (int i = 0; i < 4; i++) {
            result = result << 8 | Integer.parseInt(parts[i]);
        }
        return result;
    }
    
    /**
     * 도메인 매칭 확인 (와일드카드 지원)
     */
    private boolean isDomainMatch(String target, String allowedDomain) {
        if (target.equals(allowedDomain)) {
            return true;
        }
        
        // 와일드카드 도메인 체크 (예: *.internal)
        if (allowedDomain.startsWith("*.")) {
            String suffix = allowedDomain.substring(1);
            return target.endsWith(suffix);
        }
        
        return false;
    }
    
    /**
     * 내부 네트워크 IP 확인
     */
    private boolean isInternalNetwork(String ip) {
        try {
            long ipLong = ipToLong(ip);
            
            // 사설 네트워크 범위 체크
            return isPrivateNetwork(ipLong);
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * 사설 네트워크 IP 확인
     */
    private boolean isPrivateNetwork(long ipLong) {
        // 10.0.0.0/8
        if ((ipLong & 0xFF000000L) == 0x0A000000L) {
            return true;
        }
        
        // 172.16.0.0/12
        if ((ipLong & 0xFFF00000L) == 0xAC100000L) {
            return true;
        }
        
        // 192.168.0.0/16
        if ((ipLong & 0xFFFF0000L) == 0xC0A80000L) {
            return true;
        }
        
        return false;
    }
    
    // Getter and Setter methods
    public List<String> getAllowedIps() {
        return allowedIps;
    }
    
    public void setAllowedIps(List<String> allowedIps) {
        this.allowedIps = allowedIps;
    }
    
    public boolean isEnabled() {
        return enabled;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    public boolean isInternalOnly() {
        return internalOnly;
    }
    
    public void setInternalOnly(boolean internalOnly) {
        this.internalOnly = internalOnly;
    }
    
    public List<String> getAllowedDomains() {
        return allowedDomains;
    }
    
    public void setAllowedDomains(List<String> allowedDomains) {
        this.allowedDomains = allowedDomains;
    }
} 