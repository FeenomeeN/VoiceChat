package controller;

import client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LogIn {
    @FXML
    private Button nextButton;

    @FXML
    private TextField usernameField;

    @FXML
    private Button createOneButton;

    @FXML
    private PasswordField passwordField;

    PageLoader pageLoader = new PageLoader();

    public LogIn() throws IOException {
    }

    @FXML
    void onUsernameFieldClicked(ActionEvent event) {

    }

    @FXML
    void onPasswordFieldClicked(ActionEvent event) {

    }

    @FXML
    void onCreateOneClicked(ActionEvent event) throws IOException {
        pageLoader.loading(event,"../style/sign_in.fxml",460,625);
    }

    @FXML
    void onNextButtonClicked(ActionEvent event) throws IOException {
        if(usernameField.getText().equals("") || passwordField.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setContentText("please enter username and password");
            alert.showAndWait();
        }
        else {
            Controller.getOut().writeUTF("LOGIN");
            Controller.getOut().flush();
            if(checkLogin(usernameField.getText(),passwordField.getText())) {
                try {
                    Thread.sleep(100);
                    Client client = new Client(usernameField.getText());
                    Controller.client = client;
                    pageLoader.loading(event,"../view/homepage.fxml",700,500);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Massage");
                alert.setContentText("Wrong username or password");
                alert.showAndWait();
            }
        }
    }

    public boolean checkLogin(String username,String password) throws IOException {
        while (true){
            String user = Controller.getIn().readUTF();
            String pass = Controller.getIn().readUTF();
            System.out.println(user);
            System.out.println(pass);
            if(user.equals("END") && pass.equals("END")) {
                break;
            }
            if(user.equals(username) && pass.equals(password)) {
                Controller.getOut().writeUTF("CORRECT");
                Controller.getOut().flush();
                return true;
            }
            else {
                Controller.getOut().writeUTF("NEXT");
                Controller.getOut().flush();
            }
        }
        return false;
    }
}