package server;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

//клиентский обработчик: работа с потоками читать поток, писать в поток
public class ClientHandler {
    
    private Socket socket;
    private Server server;
    private  AuthService authService;
    private DataOutputStream out; //выходной поток
    private DataInputStream in;  //входной поток

    ClientHandler(Server server, Socket socket) {
        
        try {
            this.socket = socket;
            this.server = server;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            this.authService = new AuthServiceImpl();
            new Thread(() -> {
                try {
                    autorization();
                    read();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            close();
        }
    }


    private void read() {
        while (true) {
            try {
                String str = in.readUTF();
                if (str.equalsIgnoreCase("/end")) {
                    sendMsg("/serverclosed");
                    break;
                }
                server.broadcast(str); //читалка сообщений и отправляет всем в чате
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // /auth login1 pass1
    private void autorization() throws IOException {
        while (true){
            String str = in.readUTF();
            if (str.startsWith("/auth")) {
                String[] tokens = str.split(" "); //делит на слова по пробелу
                String  nick = authService.getNick(tokens[1], tokens[2]); //отправляет в сервис авторизации логин и пароль получаем никнейм
                if(nick != null) {
                    sendMsg("/authOK"); //авторизация прошла успешно
                    server.subscribe(this); //подписываем нашего нового клиентаб кот только что добавили
                    // т.е. текущего клиентхендлера отправляем в сабскрайб
                    break;
                } else {
                    sendMsg("Неверный логин/пароль");
                }
            }
        }
    }

    private void close() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    void sendMsg(String message) {
        try {
            out.writeUTF(message); //обратимся к выходному потоку и запишем туда это сообщение
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
