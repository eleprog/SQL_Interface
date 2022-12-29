package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlTerminal implements SqlInterface
{
    private static SqlTerminal instance = null;
    private Connection conn;
    private Statement statement;
    private boolean printConsoleFlag = true;

    private SqlTerminal() {

    }

    @Override
    public boolean connect(String url, String username, String password) {
        try {
            conn = DriverManager.getConnection(url, username, password);
            statement = conn.createStatement();
            return true;
        }
        catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean create(String tableName, String[] columns) throws Exception {
        int len = columns.length;
        String Buff = "CREATE TABLE IF NOT EXISTS " + tableName + " (";

        for(int i = 0; i < len; i++) {
            Buff += columns[i];

            if(i != len - 1)
                Buff += ", ";
            else
                Buff += ");";
        }

        if(printConsoleFlag)
            System.out.println(Buff);

        return statement.execute(Buff);
    }

    @Override
    public boolean create(String tableName, List<String[]> columns) throws Exception {
        String Buff = "CREATE TABLE IF NOT EXISTS " + tableName + " (";
        List<String> primaryKeyList = new ArrayList();

        for(int i = 0; i < columns.size(); i++) {
            String[] tmp = columns.get(i);

            for (int j = 0; j < tmp.length; j++) {
                if (!tmp[j].equals("PK")) {
                    if (j > 0)
                        Buff += " ";

                    Buff += tmp[j];
                }
                else
                    primaryKeyList.add(tmp[0]);
            }
            if (i < columns.size() - 1)
                Buff += ", ";
        }

        if(!primaryKeyList.isEmpty()) {
            Buff += ", PRIMARY KEY (";
            for(int j = 0; j < primaryKeyList.size(); j++) {
                Buff += primaryKeyList.get(j);
                if(j < primaryKeyList.size() - 1)
                    Buff += ", ";
                else
                    Buff += ")";
            }
        }
        Buff += ");";

        if(printConsoleFlag)
            System.out.println(Buff);

        return statement.execute(Buff);
    }

    @Override
    public int insert(String tableName, String columns, String[] values) throws Exception {
        String Buff = "INSERT INTO " + tableName + " (" + columns + ")" + " VALUES (";
        for(int i = 0; i < values.length - 1; i++)
            Buff += values[i] + ',';
        Buff += values[values.length - 1] + ");";

        if(printConsoleFlag)
            System.out.println(Buff);

        statement.execute(Buff);
        return 0;
    }

    @Override
    public int insert(String tableName, String[] values) throws Exception {
        int maxIndexParam = values.length - 1;
        String Buff = "INSERT INTO " + tableName + " VALUES (";

        for(int i = 0; i < maxIndexParam; i++)
            Buff += values[i] + ',';
        Buff += values[maxIndexParam] + ");";

        if(printConsoleFlag)
            System.out.println(Buff);

        statement.execute(Buff);
        return 0;
    }

    @Override
    public int insert(String tableName, List<String[]> values) throws Exception {

        int maxIndexParam = values.size();
        String Buff = "INSERT INTO " + tableName + " VALUES " ;

        for(int i = 0; i < maxIndexParam; i++) {
            String[] tmp = values.get(i);
            Buff +="(";
            int tmpLen = tmp.length;
            for(int j = 0; j < tmpLen; j++) {
                Buff += tmp[j];
                if(j < tmpLen - 1)
                    Buff += ", ";
                else if(i < maxIndexParam - 1)
                    Buff += "), ";
                else
                    Buff +=");";
            }
        }
        if(printConsoleFlag)
            System.out.println(Buff);

        statement.execute(Buff);
        return 0;
    }

    @Override
    public int updateRows(String tableName, String column, String conditionColumn, String[] conditions, String[] values) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean updateColumns(String tableName, String[] columns, String[] values, String condition) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


    @Override
    public List<String[]> select(String tableName, String[] columns) {
        String Buff = "SELECT * FROM " + tableName + " WHERE ";

        for(int i = 0; i < columns.length; i++) {
            Buff += columns[i];
            if(i < columns.length - 1)
                Buff += " AND ";
            else
                Buff += ";";
        }
        if(printConsoleFlag)
            System.out.println(Buff);


        ArrayList<String[]> resultOutput = new ArrayList<>();
        try {
            ResultSet resultInput = statement.executeQuery(Buff);

            int len = resultInput.getMetaData().getColumnCount();
            while(resultInput.next()) {
                String[] line = new String[len];
                for (int j = 1; j <= len; j++)
                    line[j - 1] = resultInput.getString(j);
                resultOutput.add(line);
            }

            if(resultOutput.isEmpty())
                return null;

            return resultOutput;
        }
        catch (SQLException e) {
            return null; //new String[][] {{"","","err","err","err"}};
        }
    }

    @Override
    public boolean delete(String tableName) throws SQLException {
        String Buff = "DROP TABLE IF EXISTS " + tableName + ";";

        if(printConsoleFlag)
            System.out.println(Buff);

        statement.execute(Buff);

        return false;
    }

    @Override
    public int deleteRows(String tableName, String condition) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean addColumn(String tableName, String column) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void close() throws Exception {
        statement.close();
        conn.close();
    }


    static public SqlTerminal getInstance() {
        if(instance == null)
            instance = new SqlTerminal();

        return instance;
    }
}
/*  UPDATE config
 *   SET config_value = CASE config_name
 *                      WHEN 'name1' THEN 'value'
 *                      WHEN 'name2' THEN 'value2'
 *                      ELSE config_value
 *                      END
 *   WHERE config_name IN('name1', 'name2');
 */
