package com.example.olamitdbfx.Classes;
import java.sql.*;
import java.util.ArrayList;

public class MySqlConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/anything";
    private static final String uname = "root";
    private static final String pass = "";
    public static Connection getConnection(){
        Connection c = null;
        try {
            c = DriverManager.getConnection(URL, uname, pass);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try(PreparedStatement userCreation = c.prepareStatement(
                "CREATE TABLE IF NOT EXISTS users (" +
                        "  id INT AUTO_INCREMENT KEY PRIMARY KEY," +
                        "  name VARCHAR(50) NOT NULL DEFAULT 'John'," +
                        "  email VARCHAR(100)NOT NULL DEFAULT 'Doe'" +
                        ")"
        );
        PreparedStatement bookCreation = c.prepareStatement(
                "CREATE TABLE IF NOT EXISTS books (" +
                        "  bookid INT AUTO_INCREMENT KEY PRIMARY KEY," +
                        "  title VARCHAR(100) NOT NULL DEFAULT 'Book title'," +
                        "  author VARCHAR(1000)NOT NULL DEFAULT 'John Doe'," +
                        "  dateadded DATE," +
                        "  yearpublished INT(4)NOT NULL DEFAULT '0000'" +
                        ")"
        )){
            userCreation.execute();
            bookCreation.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return c;
    }

    //READ User
    public static ArrayList<Users> readUsersTable(){
        try(Connection c = getConnection();
            PreparedStatement statement = c.prepareStatement("SELECT * FROM users")){
            ResultSet s = statement.executeQuery();
            ArrayList<Users> users = new ArrayList<>();
            while(s.next()){
                users.add(new Users(s.getInt("id"), s.getString("name"), s.getString("email")));
            }
            System.out.println("Data read successfully!!!!!!!!!!!!!!!!!!");
            return users;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Books> readBooksTable(){
        try(Connection c = getConnection();
            PreparedStatement statement = c.prepareStatement("SELECT * FROM books")){
            ResultSet s = statement.executeQuery();
            ArrayList<Books> books = new ArrayList<>();
            while(s.next()){
                books.add(new Books(s.getInt("bookid"), s.getString("title"), s.getString("author"), s.getString("dateadded"), s.getInt("yearpublished")));
            }
            System.out.println("Data read successfully!!!!!!!!!!!!!!!!!!");
            return books;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //USERS LOGIN
    public static boolean searchUserTable(String name, String email){
        try(Connection c = getConnection();
            PreparedStatement statement = c.prepareStatement(
                    "SELECT * FROM users WHERE name LIKE \"" + name + "\" AND email LIKE \"" + email + "\"")
        ){
            ResultSet s = statement.executeQuery();
            if(s.next()){
                SessionManager.id = s.getInt("id");
                SessionManager.name = s.getString("name");
                SessionManager.email = s.getString("email");
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    //USERS REGISTER
    public static void insertUsersTable(String name, String email){
        try(Connection c = getConnection();
            PreparedStatement statement = c.prepareStatement(
                    "INSERT INTO users (name, email) VALUE (?,?)"
            )){
            c.setAutoCommit(false);
            statement.setString(1, name);
            statement.setString(2, email);
            int row = statement.executeUpdate();
            System.out.println("Row: " + row + " inserted successfully!!!!!!!!!!!!");
            c.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //USERS DELETE
    public static void deleteUser(int id){
        try(Connection c = getConnection();
            PreparedStatement statement = c.prepareStatement(
                    "DELETE FROM users WHERE id = " + id
            )){
            c.setAutoCommit(false);
            int row = statement.executeUpdate();
            if(row > 0) c.commit();
            System.out.println("Row: " + row + " deleted successfully!!!!!!!!!!!!");
            c.rollback();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //USERS UPDATE
    public static void updateUsername(int id, String username){
        try(Connection c = getConnection();
            PreparedStatement statement = c.prepareStatement(
                    "UPDATE users set name = ? WHERE id = " + id
            )){
            c.setAutoCommit(false);
            statement.setString(1, username);
            int row = statement.executeUpdate();
            if(row > 0) c.commit();
            System.out.println("Row: " + row + " updated successfully!!!!!!!!!!!!");
            c.rollback();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //BOOK INSERT
    public static void insertBookTable(String title, String author, int year) {
        try(Connection c = getConnection();
            PreparedStatement statement = c.prepareStatement(
                    "INSERT INTO books (title, author, dateadded, yearpublished) VALUE (?,?, NOW(), ?)"
            )){
            c.setAutoCommit(false);
            statement.setString(1, title);
            statement.setString(2, author);
            statement.setInt(3, year);
            int row = statement.executeUpdate();
            System.out.println("Row: " + row + " inserted successfully!!!!!!!!!!!!");
            c.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static ArrayList<String> searchBookTable(int id){
        try(Connection c = getConnection();
            PreparedStatement statement = c.prepareStatement(
                    "SELECT * FROM books WHERE bookid = " + id)
        ){
            ArrayList<String> book = new ArrayList<>();
            ResultSet s = statement.executeQuery();
            while(s.next()){
                book.add(s.getString("title"));
                book.add(s.getString("author"));
                book.add(s.getString("yearpublished"));
            }
            return book;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static void editBookTable(String title, String author, int year, int id) {
        try(Connection c = getConnection();
            PreparedStatement statement = c.prepareStatement(
                    "UPDATE books set title = ?, author = ?, year = ?, WHERE bookid = " + id
            )){
            c.setAutoCommit(false);
            statement.setString(1, title);
            statement.setString(2, author);
            statement.setInt(3, year);
            int row = statement.executeUpdate();
            if(row > 0) c.commit();
            System.out.println("Row: " + row + " updated successfully!!!!!!!!!!!!");
            c.rollback();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void deleteBook(int id){
        try(Connection c = getConnection();
            PreparedStatement statement = c.prepareStatement(
                    "DELETE FROM books WHERE bookid = " + id
            )){
            c.setAutoCommit(false);
            int row = statement.executeUpdate();
            if(row > 0) c.commit();
            System.out.println("Row: " + row + " deleted successfully!!!!!!!!!!!!");
            c.rollback();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        getConnection();
    }
}
