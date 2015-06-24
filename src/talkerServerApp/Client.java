package talkerServerApp;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author amster
 */
public class Client implements Runnable {

    Socket socket;
//    PrintWriter out;
//    Scanner in;
    ObjectInput objectInput;
    ObjectOutput objectOutput;
    Integer number = 0;

    Client(Socket socket) throws IOException {
        this.socket = socket;

        objectOutput = new ObjectOutputStream(socket.getOutputStream());
        objectInput = new ObjectInputStream(socket.getInputStream());

    }

    public void sendMessage(Object message) {
        try {
            objectOutput.writeObject(message);
            objectOutput.flush();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void sendMessage(Object message, Integer receiverNumber) {
        try {
            System.out.println("sendMessage() -> receiverNumber: " + receiverNumber);
            ObjectOutput output = TalkerServerApp.getClientsMap().get(receiverNumber).objectOutput;

            output.writeObject(message);
            output.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {

        Integer receiverNumber = -1;
        Integer senderNumber = -1;
        Integer newNumber = -1;

        HashMap requestObject = null;
        Object request;
        try {
            while (true) {

//                objectInput = new ObjectInputStream(socket.getInputStream());
                if ((request = objectInput.readObject()) != null) {
                    System.out.println("otrzymany obiekt: " + request);
                    if (request instanceof HashMap) {
                        requestObject = (HashMap) request;
                    }

                    if (requestObject.get("receiverNumber") != null) {
                        receiverNumber = (Integer) requestObject.get("receiverNumber");
                    }
                    if (requestObject.get("senderNumber") != null) {
                        senderNumber = (Integer) requestObject.get("senderNumber");
                    }

                    if (receiverNumber == 0) {

                        if (requestObject.get("action").equals("REGISTRATION")) {
                            System.out.println("REGISTRATION");

                            newNumber = TalkerServerApp.clientsCounter++;
                            TalkerServerApp.getClientsMap().put(newNumber, this);
                            number = newNumber;

                            Map registrationJsonObject = new HashMap();
                            //if registration success
                            registrationJsonObject.put("senderNumber", 0);
                            registrationJsonObject.put("action", "REGISTRATION");
                            registrationJsonObject.put("status", true);
                            registrationJsonObject.put("newNumber", newNumber);

//TODO wyslanie emaila potwierdzajacego, dodanie nowego uzytkownika do bazie danych
//                            Client receiver = TalkerServerApp.getClientsNumbersMap().get(newNumber);
                            System.out.println(TalkerServerApp.getClientsMap().get(newNumber).number);
                            sendMessage(registrationJsonObject);
                            System.out.println("wyslano wiadomosc: " + registrationJsonObject);

                        }

                        if (requestObject.get("action").equals("LOGIN")) {
                            System.out.println("LOGIN");
                            if ((Integer) requestObject.get("senderNumber") instanceof Integer) {
                                TalkerServerApp.getClientsMap().put((Integer) requestObject.get("senderNumber"), this);
                            }
                            //TODO dodac odpowiedz
                        }

                        if (requestObject.get("action").equals("EXIT")) {
                            System.out.println("EXIT");
                            break;
                        }

                        if (requestObject.get("action").equals("CHANGE_PERSONAL_DATA")) {
                            System.out.println("CHANGE_PERSONAL_DATA");

                        }
                        if (requestObject.get("action").equals("CHANGE_CONTACTS")) {
                            System.out.println("CHANGE_CONTACTS");
                        }

                    }

                    if (requestObject.get("action").equals("MESSAGE")) {
                        System.out.println("request: " + requestObject);
                        //TODO zapis do bazy

                        if (TalkerServerApp.getClientsMap().get(receiverNumber) != null) {
                            sendMessage(requestObject, receiverNumber);

                        } else {
                            System.out.println("nie znalazlem odbiorcy");
                            //TODO zapisac w archiwum
                        }
                    }

//                    System.out.println("request: " + requestObject);
//objectInput.close();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            System.out.println("finally");
            try {
                objectInput.close();
                objectOutput.close();
                socket.close();
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
