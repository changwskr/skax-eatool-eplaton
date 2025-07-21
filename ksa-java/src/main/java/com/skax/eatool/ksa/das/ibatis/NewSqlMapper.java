package com.skax.eatool.ksa.das.ibatis;

/**
 * Stub class for SqlMapper
 */
public class NewSqlMapper {

    public NewSqlMapper() {
        // Stub constructor
    }

    // Add stub methods as needed
    public Object queryForObject(String statementName, Object parameterObject) {
        return null;
    }

    public Object queryForList(String statementName, Object parameterObject) {
        return null;
    }

    public int update(String statementName, Object parameterObject) {
        return 0;
    }

    public int insert(String statementName, Object parameterObject) {
        return 0;
    }

    public int delete(String statementName, Object parameterObject) {
        return 0;
    }

    public static NewSqlMapClient getSqlMapClient() {
        return new NewSqlMapClient();
    }
}