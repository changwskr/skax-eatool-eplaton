package com.skax.eatool.ksa.infra.po;

import java.util.List;

/**
 * NewKBData implementation
 */
public class NewKBData {

    private NewGenericDto inputGenericDto;
    private NewGenericDto outputGenericDto;

    public NewKBData() {
        this.inputGenericDto = new NewGenericDto();
        this.outputGenericDto = new NewGenericDto();
    }

    public String toString() {
        return "NewKBData{input=" + inputGenericDto + ", output=" + outputGenericDto + "}";
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        NewKBData that = (NewKBData) obj;
        return inputGenericDto.equals(that.inputGenericDto) && 
               outputGenericDto.equals(that.outputGenericDto);
    }

    public int hashCode() {
        return inputGenericDto.hashCode() * 31 + outputGenericDto.hashCode();
    }

    public NewGenericDto getInputGenericDto() {
        return inputGenericDto;
    }

    public NewGenericDto getOutputGenericDto() {
        return outputGenericDto;
    }
    
    // 추가 메서드들
    public void setCommand(String command) {
        inputGenericDto.put("command", command);
    }
    
    public String getCommand() {
        return inputGenericDto.getString("command");
    }
    
    public void setTaskName(String taskName) {
        inputGenericDto.put("taskName", taskName);
    }
    
    public String getTaskName() {
        return inputGenericDto.getString("taskName");
    }
    
    public void setDescription(String description) {
        inputGenericDto.put("description", description);
    }
    
    public String getDescription() {
        return inputGenericDto.getString("description");
    }
    
    public void setTableDefinition(Object tableDefinition) {
        inputGenericDto.put("tableDefinition", tableDefinition);
    }
    
    @SuppressWarnings("unchecked")
    public <T> T getTableDefinition(Class<T> clazz) {
        Object value = inputGenericDto.get("tableDefinition");
        if (value != null && clazz.isInstance(value)) {
            return clazz.cast(value);
        }
        return null;
    }
    
    public Object getTableDefinition() {
        return inputGenericDto.get("tableDefinition");
    }
    
    public void setTasks(List<String> tasks) {
        inputGenericDto.put("tasks", tasks);
    }
    
    @SuppressWarnings("unchecked")
    public List<String> getTasks() {
        return (List<String>) inputGenericDto.get("tasks");
    }
    
    public void setTaskId(String taskId) {
        outputGenericDto.put("taskId", taskId);
    }
    
    public String getTaskId() {
        return outputGenericDto.getString("taskId");
    }
    
    public void setStatus(String status) {
        outputGenericDto.put("status", status);
    }
    
    public String getStatus() {
        return outputGenericDto.getString("status");
    }
    
    public void setGeneratedFiles(List<String> generatedFiles) {
        outputGenericDto.put("generatedFiles", generatedFiles);
    }
    
    @SuppressWarnings("unchecked")
    public List<String> getGeneratedFiles() {
        return (List<String>) outputGenericDto.get("generatedFiles");
    }
    
    public void setValid(boolean valid) {
        outputGenericDto.put("valid", valid);
    }
    
    public boolean getValid() {
        return outputGenericDto.getBoolean("valid");
    }
    
    public void setDownloadUrl(String downloadUrl) {
        outputGenericDto.put("downloadUrl", downloadUrl);
    }
    
    public String getDownloadUrl() {
        return outputGenericDto.getString("downloadUrl");
    }
    
    public void setErrorMessage(String errorMessage) {
        outputGenericDto.put("errorMessage", errorMessage);
    }
    
    public String getErrorMessage() {
        return outputGenericDto.getString("errorMessage");
    }
    
    public void setMessage(String message) {
        outputGenericDto.put("message", message);
    }
    
    public String getMessage() {
        return outputGenericDto.getString("message");
    }
}