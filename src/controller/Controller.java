package controller;

import client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Controller {

    @FXML
    private Button signInButton;

    @FXML
    private TextField serverIpField;

    @FXML
    private TextField serverPortField;

    PageLoader pageLoader = new PageLoader();
    public static Socket socket;
    public static DataInputStream in;
    public static DataOutputStream out;
    public static Client client = null;
    public static String ip;
    public static int port;

    @FXML
    void onSignInClicked(ActionEvent event) {
        try {
            socket = new Socket(serverIpField.getText(), Integer.parseInt(serverPortField.getText()));
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.err.println("Cannot connect to server " + serverIpField.getText() + ":" + serverPortField.getText());
        }
        ip = serverIpField.getText();
        port = Integer.parseInt(serverPortField.getText());

        try {
            pageLoader.loading(event, "../view/sign_in.fxml", 460, 625);
        } catch (IOException e) {
            System.err.println("Cannot load Sign In window");
        }
    }

    public static DataOutputStream getOut() {
        return out;
    }

    public static DataInputStream getIn() {
        return in;
    }

    public static Socket getSocket() {
        return socket;
    }

    public static Client getClient() {
        return client;
    }
}
