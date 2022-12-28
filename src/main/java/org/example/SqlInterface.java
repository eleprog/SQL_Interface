package org.example;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public interface SqlInterface extends AutoCloseable
{
    boolean connect(String url, String username, String password);


    boolean create(String tableName, String[] columns) throws Exception;

    boolean create(String tableName, List<String[]> columns) throws Exception;


    int insert(String tableName, String columns, String[] values) throws Exception;

    int insert(String tableName, String[] values) throws Exception;

    int insert(String tableName, List<String[]> values) throws Exception;


    int updateRows(String tableName, String column, String conditionColumn, String[] conditions, String[] values);

    boolean updateColumns(String tableName, String[] columns, String[] values, String condition);


    String[][] select(String tableName, String[] columns);

    boolean delete(String tableName) throws SQLException;

    int deleteRows(String tableName, String condition);

    boolean addColumn(String tableName, String column); //column = "name TEXT NOT NULL"

    //close();
}



/*public interface SqlInterface extends AutoCloseable
{
    Connection connect(String url, String username, String password);
    //"jdbc:postgresql://localhost:5432/postgres" <- url
    boolean create(String tableName, String[] columns);
    int insert(String tableName, String columns, String[] values);
    int updateRows(String tableName, String column, String conditionColumn, String[] conditions, String[] values);
    //UPDATE studnts SET name = CASE id
    //                          WHEN 23 THEN 'Alexndr'
    //                          WHEN 24 THEN 'Sasha'
    //                          WHEN 25 THEN 'ALEX'
    //                          ELSE name END
    //WHERE id IN(23, 24, 25);
    boolean updateColumns(String tableName, String[] columns, String[] values, String condition);
    //UPDATE students SET name = 'Alexandr', date = 12 WHERE school_id = 23 AND last_name LIKE 'smth';
    String[][] select(String[] columns);
    boolean delete(String tableName);
    int deleteRows(String tableName, String condition);
    boolean addColumn(String tableName, String column); //column = "name TEXT NOT NULL"
    //close()
}*/