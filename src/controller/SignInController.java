package controller;

import client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SignInController {
    DataInputStream in = null;
    DataOutputStream out = null;
    Socket socket;

    @FXML
    private Button nextButton;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button signInButton;

    PageLoader pageLoader = new PageLoader();

    public SignInController() throws IOException {
    }

    @FXML
    void onUsernameFieldClicked(ActionEvent event) {

    }

    @FXML
    void onPasswordFieldClicked(ActionEvent event) {

    }

    @FXML
    void onSignInClicked(ActionEvent event) throws IOException {
        pageLoader.loading(event, "../view/log_in.fxml", 460, 625);
    }

    @FXML
    void onNextButtonClicked(ActionEvent event) throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (username.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setContentText("Please enter username and password");
            alert.showAndWait();
        } else {
            Controller.getOut().writeUTF("SIGNUP");
            Controller.getOut().flush();
            if (usernameNotExist()) {
                try {
                    Controller.getOut().writeUTF(username);
                    Controller.getOut().writeUTF(password);
                    Thread.sleep(100);
                    Client client = new Client(username);
                    Controller.client = client;
                    pageLoader.loading(event, "../style/homepage.fxml", 700, 500);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message");
                alert.setContentText("This username is unavailable");
                alert.showAndWait();
            }
        }
    }

    public boolean usernameNotExist() throws IOException {
        while (true) {
            String user = Controller.getIn().readUTF();
            if (user.equals(usernameField.getText())) {
                Controller.getOut().writeUTF("WRONG");
                Controller.getOut().flush();
                return false;
            }
            if (user.equals("END")) {
                break;
            }
            Controller.getOut().writeUTF("NEXT");
            Controller.getOut().flush();
        }
        return true;
    }

}