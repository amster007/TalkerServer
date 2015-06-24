/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package talkerServerApp;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import javax.net.ServerSocketFactory;

/**
 *
 * @author amster
 */
public class TalkerServerApp {

    private static final int PORT = 22222;
    static int clientsCounter = 1001; //TODO zapisywac obecna wartosc i odczytywac w razie padniecia servera
    private static ConcurrentHashMap<Integer, Client> clientsMap = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<Integer, Socket> socketsMap = new ConcurrentHashMap<>();

    static ConcurrentHashMap<Integer, Client> getClientsMap() {
        return clientsMap;
    }

    static ConcurrentHashMap<Integer, Socket> getSocketsMap() {
        return socketsMap;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        /**
         * ***** START LISTENING *****
         */
        final ServerSocketFactory factory = ServerSocketFactory.getDefault();

        try (ServerSocket server = factory.createServerSocket(PORT)) {

            System.out.println("Waiting for clients...");

            while (true) {
                Socket socket = server.accept();
                if (socket != null) {
                    System.out.println("Client connected from " + socket.getLocalAddress().getHostName());

                    if (!socketsMap.contains(socket)) {
                        Client client = new Client(socket);
                        Thread t = new Thread(client);
                        t.start();
                    }
                    else{
                        System.out.println("znalazlem socket");
                    }
                    //System.out.println("number:" + client.number);
                    //clientsNumbersMap.put(client.number, client);
                }
            }
        } catch (Exception e) {
            System.out.println("An error occured.");
            e.printStackTrace();
        }
    }

}
