package com.skax.eatool.mbb.as.template;

import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLogHelper;
import com.skax.eatool.ksa.exception.NewBusinessException;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

/**
 * 템플릿 처리 서비스
 * 
 * FreeMarker와 Velocity 템플릿 엔진을 지원합니다.
 * 
 * @author AI Assistant
 * @version 1.0
 * @since 2024-01-01
 */
@Service
public class TemplateService {
    
    private static final NewIKesaLogger logger = NewKesaLogHelper.getBiz();
    
    private final Configuration freemarkerConfig;
    private final VelocityEngine velocityEngine;
    
    /**
     * 기본 생성자
     */
    public TemplateService() {
        // FreeMarker 설정
        this.freemarkerConfig = new Configuration(Configuration.VERSION_2_3_31);
        this.freemarkerConfig.setClassLoaderForTemplateLoading(this.getClass().getClassLoader(), "templates");
        this.freemarkerConfig.setDefaultEncoding("UTF-8");
        
        // Velocity 설정
        this.velocityEngine = new VelocityEngine();
        this.velocityEngine.init();
    }
    
    /**
     * 템플릿을 처리한다.
     * 
     * @param templateName 템플릿명
     * @param data 템플릿 데이터
     * @return 처리된 결과
     * @throws NewBusinessException 비즈니스 예외
     */
    public String processTemplate(String templateName, Map<String, Object> data) throws NewBusinessException {
        logger.info("TemplateService", "=== TemplateService.processTemplate START ===");
        
        try {
            // FreeMarker 템플릿 처리 (기본)
            return processFreeMarkerTemplate(templateName, data);
            
        } catch (Exception e) {
            logger.error("TemplateService", "=== TemplateService.processTemplate - Error: " + e.getMessage() + " ===");
            throw new NewBusinessException("템플릿 처리 중 오류가 발생했습니다.", e);
        }
    }
    
    /**
     * FreeMarker 템플릿을 처리한다.
     * 
     * @param templateName 템플릿명
     * @param data 템플릿 데이터
     * @return 처리된 결과
     * @throws NewBusinessException 비즈니스 예외
     */
    public String processFreeMarkerTemplate(String templateName, Map<String, Object> data) throws NewBusinessException {
        try {
            // 1. 템플릿 로드
            freemarker.template.Template template = freemarkerConfig.getTemplate(templateName);
            
            // 2. 데이터 모델 생성
            freemarker.template.TemplateModel model = new freemarker.template.SimpleHash(data);
            
            // 3. 템플릿 처리
            StringWriter writer = new StringWriter();
            template.process(model, writer);
            
            return writer.toString();
            
        } catch (IOException | TemplateException e) {
            throw new NewBusinessException("FreeMarker 템플릿 처리 중 오류가 발생했습니다.", e);
        }
    }
    
    /**
     * Velocity 템플릿을 처리한다.
     * 
     * @param templateName 템플릿명
     * @param data 템플릿 데이터
     * @return 처리된 결과
     * @throws NewBusinessException 비즈니스 예외
     */
    public String processVelocityTemplate(String templateName, Map<String, Object> data) throws NewBusinessException {
        try {
            // 1. Velocity 컨텍스트 생성
            VelocityContext context = new VelocityContext(data);
            
            // 2. 템플릿 로드
            org.apache.velocity.Template template = velocityEngine.getTemplate(templateName);
            
            // 3. 템플릿 처리
            StringWriter writer = new StringWriter();
            template.merge(context, writer);
            
            return writer.toString();
            
        } catch (Exception e) {
            throw new NewBusinessException("Velocity 템플릿 처리 중 오류가 발생했습니다.", e);
        }
    }
} 