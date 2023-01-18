package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlTerminal implements SqlInterface  {

    /** ссылка на единственный экземпляр класса SqlTerminal
     */
    private static SqlTerminal instance = null;

    private Connection conn;
    private Statement statement;

    /** флаг печати SQL запросов в консоль
     * true - печатать SQL запросы в консоль, false - не печатать
     */
    private boolean printConsoleFlag = true;


    /** для реализации singletone конструктор класса находится в зоне доступа private
     */
    private SqlTerminal() { }

    /**  Подключение к базе данных
     *
     *  @param url ссылка на базу данных
     *  @param username имя учётной записи
     *  @param password пароль
     *
     *  @return
     *      true    - подключение успешно;
     *      false   - при подключении произошла ошибка
     */
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

    /**  Создание таблицы в базе данных
     *
     *  @param tableName название таблицы
     *  @param columns список имён и параметров полей
     *
     *  @return
     *      true    - таблица создана;
     *      false   - таблица не создана
     */
    @Override
    public boolean create(String tableName, List<String[]> columns) {
        String Buff = "CREATE TABLE " + tableName + " (";   // переменная для формирования SQL запроса
        List<String> primaryKeyList = new ArrayList<>();    // список полей для добавления в primary key

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

        // отправление запроса в базу данных и вывод return
        try {
            statement.execute(Buff);
            return true;
        }
        catch(Exception e) {
            return false;
        }
    }

    /**  Добавление значений в базу данных
     *
     *  @param tableName название таблицы
     *  @param columns массив полей в которые записываются данные
     *  @param values список значений полей для добавления
     *
     *  @return (0-...) количество успешно добавленных строк в базу данных;
     *          -1 при добавлении значений произошла ошибка
     */
    @Override
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

        // отправление запроса в базу данных и вывод return
        try {
            return statement.executeUpdate(Buff);
        }
        catch(Exception e) {
            return -1;
        }
    }

    /**
     * @param tableName название таблицы
     * @param changeColumns выражение для обновления колонок в строке
     * @param conditions условия для обновлнения
     * @return (0 - ...) количество обновлённых строк;
     *         -1 при обновлении значений произошла ошибка
     */
    @Override
    public int updateRow(String tableName, String[] changeColumns, String[] conditions) {
        String Buff = "UPDATE " + tableName + " SET ";    // переменная для формирования SQL запроса

        // формирование запроса для обновления столбцов
        for(int i = 0 ; i < changeColumns.length; i++) {
            Buff += changeColumns[i];
            if(i < changeColumns.length - 1)
                Buff += ", ";
        }

        // формирование запроса для условия обновления столбцов
        Buff += " WHERE ";
        for(int i = 0 ; i < conditions.length; i++) {
            Buff += conditions[i];
            if(i < conditions.length - 1)
                Buff += " AND ";
        }
        Buff += ";";

        // печать SQL запроса в консоль
        if(printConsoleFlag)
            System.out.println(Buff);

        // отправление запроса в базу данных и вывод return
        try {
            return statement.executeUpdate(Buff);
        }
        catch(Exception e) {
            return -1;
        }
    }

    /**
     * @param tableName название таблицы
     * @param changeColumn выражение для обновления колонки
     * @param conditions условия для обновлнения
     * @return (0 - ...) количество обновлённых значений;
     *         -1 при обновлении значений произошла ошибка
     */
    @Override
    public int updateColumn(String tableName, String changeColumn, String[] conditions) {
        String Buff = "UPDATE " + tableName + " SET ";    // переменная для формирования SQL запроса

        // формирование запроса для обновления столбца
        Buff += changeColumn;

        // формирование запроса для условия обновления столбцов
        Buff += " WHERE ";
        for(int i = 0 ; i < conditions.length; i++) {
            Buff += conditions[i];
            if(i < conditions.length - 1)
                Buff += " AND ";
        }
        Buff += ";";

        // печать SQL запроса в консоль
        if(printConsoleFlag)
            System.out.println(Buff);

        // отправление запроса в базу данных и вывод return
        try {
            return statement.executeUpdate(Buff);
        }
        catch(Exception e) {
            return -1;
        }
    }

    /**  Получение данных из таблицы
     *
     *  @param tableName название таблицы
     *  @param targetCol массив полей которые будут возвращены
     *  @param conditions список условий
     *
     *  @return список массивов строк, где в каждом списке хранятся значения одной строки базы данных,
     *  а в каждой строке хранится значение столбца
     */
    @Override
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

    /**  Удаление таблицы
     *
     *  @param tableName название таблицы
     *
     *  @return
     *      true таблица удалена;
     *      false в процессе удаления произошла ошибка
     */
    @Override
    public boolean delete(String tableName) {
        String Buff = "DROP TABLE IF EXISTS " + tableName + ";"; // переменная для формирования SQL запроса

        // печать SQL запроса в консоль
        if(printConsoleFlag)
            System.out.println(Buff);

        // отправление запроса в базу данных и вывод return
        try {
            statement.execute(Buff);
            return true;
        }
        catch(Exception e) {
            return false;
        }
    }

    /**  Удаление строк таблицы
     *
     *  @param tableName название таблицы
     *  @param condition условие
     *
     *  @return (0 - ...) количество уделённых строк в базе данных;
     *          -1 при удалении произошла ошибка
     *
     */
    @Override
    public int deleteRows(String tableName, String[] condition) {

        String Buff = "DELETE FROM " + tableName + " WHERE "; // переменная для формирования SQL запроса

        // формирование запроса для условий
        for(int i = 0; i < condition.length; i++) {
            Buff += condition[i];
            if(i < condition.length - 1)
                Buff += " AND ";
        }

        // печать SQL запроса в консоль
        if(printConsoleFlag)
            System.out.println(Buff);

        // отправление запроса в базу данных и вывод return
        try {
            return statement.executeUpdate(Buff);
        }
        catch(Exception e) {
            return -1;
        }
    }

    /**  Добавление столбца
     *
     *  @param tableName название таблицы
     *  @param column название поля и параметры
     *
     *  @return
     *      true столбец добавлен;
     *      false столбец не добавлен
     */
    @Override
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

        // отправление запроса в базу данных и вывод return
        try {
            statement.execute(Buff);
            return true;
        }
        catch(Exception e) {
            return false;
        }
    }

    @Override
    public void close() throws Exception {
        statement.close();
        conn.close();
    }

    /** Получение ссылки на экземпляр класса SqlTerminal
     *
     * @return ссылка на единственный экземпляр класса
     */
    static public SqlTerminal getInstance() {
        if(instance == null)
            instance = new SqlTerminal();

        return instance;
    }
}