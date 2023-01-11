package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlTerminal implements SqlInterface
{
    private static SqlTerminal instance = null;
    private Connection conn;
    private Statement statement;
    private boolean printConsoleFlag = true; // true - печатать SQL запросы в консоль, false - не печатать

    private SqlTerminal() {

    }

    @Override
    /*  Подключение к базе данных
     *
     *  url      - ссылка на базу данных
     *  username - имя учётной записи
     *  password - пароль
     */
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
    /*  Создание таблицы в базе данных
     *
     *  tableName - название таблицы
     *  columns - список имён и параметров полей
     */
    public boolean create(String tableName, List<String[]> columns) {
        String Buff = "CREATE TABLE IF NOT EXISTS " + tableName + " ("; // переменная для формирования SQL запроса
        List<String> primaryKeyList = new ArrayList<>();                // список полей для добавления в primary key

        // формирование запроса для создания полей
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

        // создание запроса для primary key
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

        // печать SQL запроса в консоль
        if(printConsoleFlag)
            System.out.println(Buff);

        try {
            statement.execute(Buff);
        }
        catch(Exception e) {
            return false;
        }

        return true;
    }

    @Override
    /*  Добавление значений в базу данных
     *
     *  tableName   - название таблицы
     *  columns     - массив полей в которые записываются данные
     *  values      - список значений полей для добавления
     */
    public int insert(String tableName, String[] columns, List<String[]> values) {
        String Buff = "INSERT INTO " + tableName + " (" ;   // переменная для формирования SQL запроса

        // формирование запроса для полей которые добавляются
        for(int i = 0; i < columns.length; i++) {
            Buff += columns[i];

            if(i < columns.length - 1)
                Buff += ", ";
            else
                Buff += ") ";
        }

        // формирование запроса для значений которые добавляются
        Buff += "VALUES ";
        for(int i = 0; i < values.size(); i++) {
            String[] tmp = values.get(i);
            Buff +="(";
            int tmpLen = tmp.length;
            for(int j = 0; j < tmpLen; j++) {
                Buff += tmp[j];
                if(j < tmpLen - 1)
                    Buff += ", ";
                else if(i < values.size() - 1)
                    Buff += "), ";
                else
                    Buff +=");";
            }
        }

        // печать SQL запроса в консоль
        if(printConsoleFlag)
            System.out.println(Buff);

        try {
            return statement.executeUpdate(Buff);
        }
        catch(Exception e) {
            return -1;
        }
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
    /*  Получение данных из таблицы
     *
     *  tableName   - название таблицы
     *  targetCol   - массив полей которые будут возвращены
     *  conditions  - список условий
     */
    public List<String[]> select(String tableName, String[] targetCol, String[] conditions) {
        String Buff = "SELECT ";    // переменная для формирования SQL запроса

        // формирование запроса для полей которые будут возвращены
        if(targetCol == null)
            Buff += "* ";
        else {
            for (int i = 0; i < targetCol.length; i++) {
                Buff += targetCol[i];

                if (i < targetCol.length - 1)
                    Buff += ", ";
                else
                    Buff += " ";
            }
        }

        // формирование запроса для условий
        Buff += "FROM " + tableName + " WHERE ";
        for(int i = 0; i < conditions.length; i++) {
            Buff += conditions[i];
            if(i < conditions.length - 1)
                Buff += " AND ";
            else
                Buff += ";";
        }

        // печать SQL запроса в консоль
        if(printConsoleFlag)
            System.out.println(Buff);


        ArrayList<String[]> resultOutput = new ArrayList<>(); // список для формирования return

        // return полученных значений
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
            return null;
        }
    }

    @Override
    /*  Удаление таблицы
     *
     *  tableName   - название таблицы
     */
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
    /*  Добавление столбца
     *
     *  tableName   - название таблицы
     *  column      - название и параметры поля
     *
     *  return:
     *      true - столбец добавлен
     *      false - столбец не добавлен
     */
    public boolean addColumn(String tableName, String[] column){
        String Buff = "ALTER TABLE " + tableName + " ADD COLUMN ";

        // формирование запроса для создания полей
        for (int i = 0; i < column.length; i++) {
            Buff += column[i];

            if (i < column.length - 1)
                Buff += " ";
        }
        Buff += ";";

        // печать SQL запроса в консоль
        if(printConsoleFlag)
            System.out.println(Buff);

        try {
            statement.execute(Buff);
        }
        catch(Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public void close() throws Exception {
        statement.close();
        conn.close();
    }

    // Метод реализации singletone
    static public SqlTerminal getInstance() {
        if(instance == null)
            instance = new SqlTerminal();

        return instance;
    }
}
