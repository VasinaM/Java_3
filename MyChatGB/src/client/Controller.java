package client;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TreeCell;
import javafx.scene.layout.HBox;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;


public class Controller {

    @FXML
    TextField msgField;
    @FXML
    TextArea chatArea;
    @FXML
    HBox bottomPanel;
    @FXML
    HBox upperPanel;
    @FXML
    TextField loginField;
    @FXML
    PasswordField passwordField;

    Socket socket;
    DataInputStream in;
    DataOutputStream out;

    final String ADDRESS = "localhost";
    final int PORT = 8181;

    private boolean isAutorized

    private void setAutorized(boolean isAutorized) {
        this.isAutorized = isAutorized
    }

    public void connect() {
        try {
            socket = new Socket(ADDRESS, PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            new Thread(() -> {
                auth();
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void auth() {
        while (true) {
            try {
                String str = in.readUTF();
                if (str.startsWith("/authOK")) {
                    setAutorized(true); //флаг
                    break;
                }else {
                    chatArea.appendText(str + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }



    public void tryToAuth() {
        if (socket == null || socket.isClosed()) {
            connect();
        }
        try {
            out.writeUTF("/auth " + loginField.getText() + " " + passwordField.getText());
            loginField.clear();
            passwordField.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg() {
    }
}
