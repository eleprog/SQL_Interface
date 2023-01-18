package org.example;

import java.util.List;


public interface SqlInterface extends AutoCloseable
{
    boolean connect(String url, String username, String password);

    boolean create(String tableName, List<String[]> columns);

    int insert(String tableName, String columns[], List<String[]> values);

    public int updateRow(String tableName, String[] changeColumns, String[] conditions);

    int updateColumn(String tableName, String changeColumn, String[] condition);

    List<String[]> select(String tableName, String[] targetColumns, String[] columns);

    boolean delete(String tableName);

    int deleteRows(String tableName, String[] condition);

    boolean addColumn(String tableName, String[] column);
}