
import java.net.*;
import java.io.*;
import java.util.*;
import java.util.logging.*;public class Server {
    static  ServerSocket serversocket;
    public static void main(String[] args)
    {
        try
        {
            serversocket = new ServerSocket(4000);
            System.out.println("Server is booting up & is waiting for clients to connect...");

            Socket clientSocket = serversocket.accept();
            System.out.println("A new client [" + clientSocket + "] is connected to the server...");

            ClientConnection clientconnection = new ClientConnection(clientSocket);
            clientconnection.start();

            DataInputStream input = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());
            output.writeUTF("Connected..");

            while (true)
            {
                output.writeUTF("Send your request or 'close' to close the connection.");

                String request = input.readUTF();
                System.out.println("Client: [" + clientSocket + "]" + request);

                if(request.toLowerCase().equals("close"))
                {
                    System.out.println("Closing connection with this client....");
                    System.out.println("Connection with this client [" + clientSocket + "] is Closed");
                    clientSocket.close();
                    break;
                }
                Scanner scanner = new Scanner(System.in);
                String reply = scanner.nextLine();
                output.writeUTF(reply);

                input.close();
                output.close();
            }
        } catch (IOException ex) {
            System.out.println("problem with server socket.");
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    static  class ClientConnection extends Thread {
        Socket ClientSocket;

        ClientConnection(Socket ClientSocket) {
            this.ClientSocket = ClientSocket;
        }

        public  void  run(){
            try{
                DataInputStream input = new DataInputStream(ClientSocket.getInputStream());
                DataOutputStream output = new DataOutputStream(ClientSocket.getOutputStream());
                output.writeUTF("connected");

                while (true)
                {
                    output.writeUTF("Send your request or 'close' to close the connection.");

                    String request = input.readUTF();
                    System.out.println("Client: " + request);

                    if(request.equals("close"))
                    {
                        System.out.println("Closing connection with this client....");
                        System.out.println("Connection with this client is Closed");
                        ClientSocket.close();
                        break;
                    }
                    Scanner scanner = new Scanner(System.in);
                    String reply = scanner.nextLine();
                    output.writeUTF(reply);

                    input.close();
                    output.close();
                }

            }
            catch (Exception e){
                e.getStackTrace();
            }
        }

    }
}
