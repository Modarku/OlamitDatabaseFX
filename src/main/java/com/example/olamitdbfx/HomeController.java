package com.example.olamitdbfx;

import com.example.olamitdbfx.Classes.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class HomeController {

    public AlertController alrt;
    public TextField inptEditUsername;
    public ToggleButton tbNight;
    public ProgressIndicator piProgress;
    public Slider slSlider;
    public ProgressBar pbProgress;

    public TextField inptSearchID;
    public Button btnSearchBook;

    public TextField inptBookTitle;
    public TextField inptBookAuthor;
    public TextField inptYearPublished;

    public TextField inptEditBookTitle;
    public TextField inptEditBookAuthor;
    public TextField inptEditYearPublished;

    public TableView<Users> tblUsers = new TableView<>();
    public TableColumn<Users, Integer> tblUsersId = new TableColumn<Users, Integer>("ID");
    public TableColumn<Users, String> tblUsersName = new TableColumn<Users, String>("Username");
    public TableColumn<Users, String> tblUsersEmail = new TableColumn<Users, String>("Email");
    public TableView<Books> tblBooks = new TableView<>();
    public TableColumn<Books, Integer> tblBooksId = new TableColumn<>("Book ID");
    public TableColumn<Books, String> tblBooksTitle = new TableColumn<>("Title");
    public TableColumn<Books, String> tblBooksAuthor = new TableColumn<>("Author");
    public TableColumn<Books, String> tblBooksDate = new TableColumn<>("Date Added");
    public TableColumn<Books, Integer> tblBooksYear = new TableColumn<>("Year Published");
    public Button btnEditBook;
    public Button btnDeleteBook;

    public int bookId = 0;


    public void onShowTableClick() {
        ArrayList<Users> users = MySqlConnection.readUsersTable();
        tblUsersId.setCellValueFactory(new PropertyValueFactory<Users, Integer>("id"));
        tblUsersName.setCellValueFactory(new PropertyValueFactory<Users, String>("name"));
        tblUsersEmail.setCellValueFactory(new PropertyValueFactory<Users, String>("email"));
        if (tblUsers.getItems() != null) {
            ArrayList<Users> selectedUsers = new ArrayList<>();
            selectedUsers.addAll(tblUsers.getItems());
            tblUsers.getItems().removeAll(selectedUsers);
        }
        if (users != null) {
            for (Users user : users) {
                tblUsers.getItems().add(user);
            }
        }

        ArrayList<Books> books = MySqlConnection.readBooksTable();
        tblBooksId.setCellValueFactory(new PropertyValueFactory<>("bookid"));
        tblBooksTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        tblBooksAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        tblBooksDate.setCellValueFactory(new PropertyValueFactory<>("dateadded"));
        tblBooksYear.setCellValueFactory(new PropertyValueFactory<>("yearpublished"));
        if (tblBooks.getItems() != null) {
            ArrayList<Books> selectedBooks = new ArrayList<>();
            selectedBooks.addAll(tblBooks.getItems());
            tblBooks.getItems().removeAll(selectedBooks);
        }
        if (books != null) {
            for (Books book : books) {
                tblBooks.getItems().add(book);
            }
        }
    }

    public void onCreateBook() {
        String title = inptBookTitle.getText();
        String author = inptBookAuthor.getText();
        try{
            int year = Integer.parseInt(inptYearPublished.getText());
            MySqlConnection.insertBookTable(title, author, year);
        }catch (Exception e){
            Alert yearNumberError = new Alert(Alert.AlertType.ERROR);
            new AlertController(yearNumberError, "Year Format Error", "Year is not the correct format!");
        }
    }

    public void onDeleteUser() {
        MySqlConnection.deleteUser(SessionManager.id);
    }

    public void onEditUsername() {
        String newName = inptEditUsername.getText();
        if(newName != null){
            if(!newName.equals("")){
                MySqlConnection.updateUsername(SessionManager.id, newName);
                Alert editUsernameSuccess = new Alert(Alert.AlertType.CONFIRMATION);
                alrt = new AlertController(editUsernameSuccess, "Edit Username Success", "New name: " + newName);
            }else{
                Alert editUsernameError = new Alert(Alert.AlertType.ERROR);
                alrt = new AlertController(editUsernameError, "Edit Username Error", "Empty Text Field");
            }
        }
    }

    public void onSearchBook() throws SQLException {
        bookId = Integer.parseInt(inptSearchID.getText());
        System.out.println(bookId);
        ArrayList<String> book = MySqlConnection.searchBookTable(bookId);
        System.out.println(book);
        if(book != null){
            inptEditBookTitle.setText(book.get(0));
            inptEditBookAuthor.setText(book.get(1));
            inptEditYearPublished.setText(book.get(2));
            inptEditBookTitle.setEditable(true);
            inptEditBookAuthor.setEditable(true);
            inptEditYearPublished.setEditable(true);
            btnEditBook.setDisable(false);
            btnDeleteBook.setDisable(false);
        }else{
            inptEditBookTitle.setEditable(false);
            inptEditBookAuthor.setEditable(false);
            inptEditYearPublished.setEditable(false);
            btnEditBook.setDisable(true);
            btnDeleteBook.setDisable(true);
            Alert emptySearch = new Alert(Alert.AlertType.ERROR);
            new AlertController(emptySearch, "Empty Search", "Search is Empty");
        }
    }

    public void onEditBook() {
        String title = inptBookTitle.getText();
        String author = inptBookAuthor.getText();
        int year = Integer.parseInt(inptYearPublished.getText());
        //if(!title.equals(""))
        MySqlConnection.editBookTable(title, author, year, bookId);
    }

    public void onDeleteBook() {
        MySqlConnection.deleteBook(bookId);
        Alert deleteSuccess = new Alert(Alert.AlertType.CONFIRMATION);
        new AlertController(deleteSuccess, "Delete Successful", "Deletion Successful!");
    }

    public void onNightModeClick() {
        if (tbNight.isSelected()) {
            tbNight.getParent().setStyle("-fx-background-color: DARKSLATEBLUE");
            tbNight.setText("DAY");
        } else {
            tbNight.getParent().setStyle("-fx-background-color: FLORALWHITE");
            tbNight.setText("NIGHT");
        }
    }

    public void onSliderChange() {
        double val = slSlider.getValue();
        piProgress.setProgress(val/100);
        pbProgress.setProgress(val/100);
    }
}