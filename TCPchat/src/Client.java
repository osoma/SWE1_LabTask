import java.net.*;
import java.io.*;
import java.util.*;
import java.util.logging.*;

public class Client {
    public static void main(String[] args)
    {
        try
        {
            InetAddress ip = InetAddress.getLocalHost();
            Socket ClientSocket = new Socket(ip,4000);
            System.out.println("Connecting to the server....");

            DataInputStream Input = new DataInputStream(ClientSocket.getInputStream());
            DataOutputStream Output = new DataOutputStream(ClientSocket.getOutputStream());

            Scanner scanner = new Scanner(System.in);
            String ConnectConfirm = Input.readUTF();
            System.out.println("Server: " + ConnectConfirm);

            while (true)
            {
                String ServerAsk = Input.readUTF();
                System.out.println("Server: " + ServerAsk);

                String request = scanner.nextLine();
                Output.writeUTF(request);

                if(request.equals("close"))
                {
                    System.out.println("Closing connection to server .....");
                    ClientSocket.close();
                    System.out.println("Connection is closed.");
                    break;
                }

                String reply = Input.readUTF();
                System.out.println("Server:" + reply);
            }
        } catch (UnknownHostException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
