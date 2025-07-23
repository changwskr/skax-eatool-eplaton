package com.skax.eatool.mba.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * MBA 애플리케이션 기본 설정 클래스
 * 
 * @author KBSTAR
 * @version 1.0.0
 * @since 2024
 */
@Configuration
@ConfigurationProperties(prefix = "mba")
public class MbaConfig {
    
    private App app = new App();
    private Security security = new Security();
    private Database database = new Database();
    
    public static class App {
        private String name;
        private String version;
        private String description;
        
        // Getters and Setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public String getVersion() { return version; }
        public void setVersion(String version) { this.version = version; }
        
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }
    
    public static class Security {
        private Jwt jwt = new Jwt();
        
        public static class Jwt {
            private String secret;
            private long expiration;
            
            // Getters and Setters
            public String getSecret() { return secret; }
            public void setSecret(String secret) { this.secret = secret; }
            
            public long getExpiration() { return expiration; }
            public void setExpiration(long expiration) { this.expiration = expiration; }
        }
        
        // Getters and Setters
        public Jwt getJwt() { return jwt; }
        public void setJwt(Jwt jwt) { this.jwt = jwt; }
    }
    
    public static class Database {
        private String schema;
        private String data;
        
        // Getters and Setters
        public String getSchema() { return schema; }
        public void setSchema(String schema) { this.schema = schema; }
        
        public String getData() { return data; }
        public void setData(String data) { this.data = data; }
    }
    
    // Getters and Setters
    public App getApp() { return app; }
    public void setApp(App app) { this.app = app; }
    
    public Security getSecurity() { return security; }
    public void setSecurity(Security security) { this.security = security; }
    
    public Database getDatabase() { return database; }
    public void setDatabase(Database database) { this.database = database; }
} 