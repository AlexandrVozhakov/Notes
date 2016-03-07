package com.av;
import java.sql.*;
import java.util.ArrayList;

/**
 * Created by av on 12.02.16.
 */
public class DataBase {

    private Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;
    private ResultSet resSet;

    private static DataBase instance = null;

    private DataBase(){

    }

    public static DataBase getInstance(){
        if(instance == null)
            instance = new DataBase();
        return instance;
    }

    public void connect() {

        connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:Notes.s3db");

        } catch (ClassNotFoundException e) { e.printStackTrace();
        } catch (SQLException e) { e.printStackTrace();}
    }

    // Create tables
    public void createDB() {

        String [] str = Service.readFile("SQL/dump.sql").split(";");
        ArrayList<String> commands = new ArrayList<String>();

        for(String com : str){
            if(!com.trim().equals(""))
                commands.add(com);
        }

        //TODO: refactor this

        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (String command : commands) {
            executeQuery(command);
        }
        if (getSections().size() == 0)
            addSection("Personal");

        //System.out.println("Таблица создана или уже существует.");
    }

    private void executeQuery(String query){

        try {
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addNote(int section_id, String text) {

        String date = Service.date();

        System.out.println(section_id);
        try {

            preparedStatement = connection.prepareStatement("INSERT INTO notes (section_id, " +
                    "date, text) VALUES (?, ?, ?);");

            preparedStatement.setInt(1, section_id);
            preparedStatement.setString(2, date);
            preparedStatement.setString(3, text);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateNote(Note note){

        try {
            preparedStatement = connection.prepareStatement("UPDATE notes SET text = ?, date = ? " +
                    "WHERE id = ?");

            preparedStatement.setString(1, note.getText());
            preparedStatement.setString(2, note.getDate());
            preparedStatement.setInt(3, note.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("save");
    }

    public void deleteNote(int id){

        executeQuery("DELETE FROM notes WHERE id = " + id);
    }

    public void addSection(String name) {

        try {
            preparedStatement = connection.prepareStatement("INSERT INTO sections (name) " +
                    "VALUES (?)");

            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateSection(int section_id, String name){

        try {
            preparedStatement = connection.prepareStatement("UPDATE sections SET " +
                    "name = ? WHERE id = ?");

            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, section_id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void deleteSection(int section_id){

        try {
            preparedStatement = connection.prepareStatement("DELETE FROM sections WHERE id = ?");
            preparedStatement.setInt(1, section_id);
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("DELETE FROM notes WHERE section_id = ?");
            preparedStatement.setInt(1, section_id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Section> getSections() {

        ArrayList<Section> sections = new ArrayList<Section>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM sections");
            resSet = preparedStatement.executeQuery();

            while(resSet.next())
            {
                Section section = new Section(resSet.getInt("id"), resSet.getString("name"));
                sections.add(section);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sections;
    }

    public ArrayList<Note> getNotes(int section_id){

        ArrayList<Note> notes = new ArrayList<Note>();

        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM notes WHERE section_id = ?");
            preparedStatement.setInt(1, section_id);
            resSet = preparedStatement.executeQuery();

            while(resSet.next())
            {
                Note n = new Note(resSet.getInt("id"), resSet.getInt("section_id"), resSet.getString("text"));
                n.setDate(resSet.getString("date"));
                notes.add(n);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notes;
    }

    public ArrayList<Note> findNotes(String text){

        ArrayList<Note> notes = new ArrayList<Note>();

        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM notes WHERE text LIKE ?");
            preparedStatement.setString(1, "%" + text + "%");
            resSet = preparedStatement.executeQuery();

            while(resSet.next())
            {
                Note n = new Note(resSet.getInt("id"), resSet.getInt("section_id"), resSet.getString("text"));
                n.setDate(resSet.getString("date"));
                notes.add(n);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notes;
    }

    public void closeDB() {

        try {
            connection.close();
            statement.close();
            resSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}