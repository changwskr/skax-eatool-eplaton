package com.skax.eatool.ksa.infra.po;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

/**
 * NewGenericDto implementation
 */
public class NewGenericDto {

    public static final String INDATA = "INDATA";
    public static final String OUTDATA = "OUTDATA";

    private Map<String, Object> data = new HashMap<>();
    private Map<String, Object> attributes = new HashMap<>();
    private Map<String, List<Object>> arrays = new HashMap<>();
    private Map<String, List<Object>> nodes = new HashMap<>();

    public NewGenericDto() {
        // Constructor
    }

    public void put(String key, Object value) {
        data.put(key, value);
    }

    public Object get(String key) {
        return data.get(key);
    }

    public NewGenericDto using(String key) {
        // For compatibility, return this instance
        return this;
    }

    public Map<String, Object> getAttributeMap() {
        return attributes;
    }

    public void addAttribute(String key, String value) {
        attributes.put(key, value);
    }

    public void addNode(String key) {
        nodes.put(key, new ArrayList<>());
    }

    public List<NewGenericDto> getGenericDtos(String key) {
        // Return empty list for now, can be enhanced later
        return new ArrayList<>();
    }

    public int getInt(String key) {
        Object value = data.get(key);
        if (value instanceof Integer) {
            return (Integer) value;
        }
        if (value instanceof String) {
            try {
                return Integer.parseInt((String) value);
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        return 0;
    }

    public String getString(String key) {
        Object value = data.get(key);
        return value != null ? value.toString() : null;
    }

    public void setString(String key, String value) {
        data.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T[] getArray(Class<T> clazz) {
        // Return empty array for now, can be enhanced later
        return (T[]) new Object[0];
    }

    public void add(List<?> list) {
        // Add to arrays map with a default key
        arrays.put("default", new ArrayList<>(list));
    }

    public Map<String, List<Object>> getArrays() {
        return arrays;
    }

    public String toString() {
        return "NewGenericDto{data=" + data + ", attributes=" + attributes + 
               ", arrays=" + arrays + ", nodes=" + nodes + "}";
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        NewGenericDto that = (NewGenericDto) obj;
        return data.equals(that.data) && 
               attributes.equals(that.attributes) && 
               arrays.equals(that.arrays) && 
               nodes.equals(that.nodes);
    }

    public int hashCode() {
        return data.hashCode() * 31 + attributes.hashCode() * 17 + 
               arrays.hashCode() * 13 + nodes.hashCode();
    }
}