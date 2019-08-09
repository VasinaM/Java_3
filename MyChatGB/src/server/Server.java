package server;

import javafx.scene.layout.BorderImage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

class StartServer {
    public static void main(String[] args) {
        new Server();
    }
}

class Server {

    private List<ClientHandler> peers;

    public Server() {
        AuthService authService = new AuthServiceImpl();
        peers = new CopyOnWriteArrayList<>();
        ServerSocket serverSocket = null;
        Socket socket = null;


        try {
            authService.connect();
            serverSocket = new ServerSocket(8181);
            System.out.println("Сервер запущен!");

            while (true) {
                socket = serverSocket.accept();
                System.out.println("Клиент подключился!");
                new ClientHandler(this, socket); //нужно задать обработчик, каждый клиент будет обрабатываться отдельным обработчиком
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            authService.disconnect();
        }
    }

    //метод рассылка сообщений всем кто есть (broadcast широковещательная рассылка на неск хостов - в сетях)
    void broadcast (String message) {
        for (ClientHandler clientHandler : peers) {
            clientHandler.sendMsg(message);
        }

    }

    //метод подписка
    void subscribe(ClientHandler clientHandler) {
        peers.add(clientHandler); //c отдельным клиентом работает один обработчик
    }

    //метод отписка
    void unsubscribe(ClientHandler clientHandler) {
        peers.remove(clientHandler);
    }


}
