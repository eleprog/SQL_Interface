package org.example;

import java.sql.SQLException;
import java.util.List;


public interface SqlInterface extends AutoCloseable
{
    boolean connect(String url, String username, String password);

    boolean create(String tableName, List<String[]> columns);

    int insert(String tableName, String columns[], List<String[]> values);

    int updateRow(String tableName, String columns[], String[] values, String[] conditions);

    int updateColumns(String tableName, String[] changesColumns, String[] condition);

    List<String[]> select(String tableName, String[] targetColumns, String[] columns);

    boolean delete(String tableName) throws SQLException;

    int deleteRows(String tableName, String[] condition);

    boolean addColumn(String tableName, String[] column);
}