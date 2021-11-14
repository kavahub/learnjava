package io.github.kavahub.learnjava.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class DataInTLVFormatClient {
    public void runClient(String ip, int port) {
        try {
            Socket socket = new Socket(ip, port);
            System.out.println("Connected to server ...");
            DataInputStream in = new DataInputStream(System.in);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            
            char type = 's'; // s for string
            int length = 29;
            String data = "This is a string of length 29";
            byte[] dataInBytes = data.getBytes(StandardCharsets.UTF_8);         
            //Sending data in TLV format        
            out.writeChar(type);
            out.writeInt(length);
            out.write(dataInBytes);

            System.out.println("Client send data completed");
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }   
}
