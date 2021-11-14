package io.github.kavahub.learnjava.socket;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * 关于TLV:
 * TLV是一种可变的格式，意为：Type类型， Lenght长度，Value值。Type:该字段是关于标签和编码格式的信息; 
 * Length:该字段是定义数值的长度; Value:字段表示实际的数值。
 * 
 * TLV优点：
 * 1 一个编码值又称TLV(Type,Length,Value)三元组。编码可以是基本型或结构型，如果它表示一个简单类型的、
 *   完整的显式值，那么编码就是基本型 (primitive)；如果它表示的值具有嵌套结构，那么编码就是结构型 (constructed)。
 * 2 TLV打包解包效率高，省内存。
 */
public class DataInTLVFormatServer {
    public void runServer(int port) {
        //Start the server and wait for connection
        try {
            ServerSocket server = new ServerSocket(port);
            System.out.println("Server Started. Waiting for connection ...");
            Socket socket = server.accept();
            System.out.println("Got connection from client.");
            //Get input stream from socket variable and convert the same to DataInputStream
            DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            //Read type and length of data
            char dataType = in.readChar();
            int length = in.readInt();
            System.out.println("Type : "+dataType);
            System.out.println("Lenght :"+length);
            if(dataType == 's') {
                //Read String data in bytes
                byte[] messageByte = new byte[length];
                boolean end = false;
                StringBuilder dataString = new StringBuilder(length);
                int totalBytesRead = 0;
                //We need to run while loop, to read all data in that stream
                while(!end) {
                    int currentBytesRead = in.read(messageByte);
                    totalBytesRead = currentBytesRead + totalBytesRead;
                    if(totalBytesRead <= length) {
                        dataString.append(new String(messageByte,0,currentBytesRead,StandardCharsets.UTF_8));
                    } else {
                        dataString.append(new String(messageByte,0,length - totalBytesRead + currentBytesRead,StandardCharsets.UTF_8));
                    }
                    if(dataString.length()>=length) {
                        end = true;
                    }
                }
                System.out.println("Read "+length+" bytes of message from client. Message = "+dataString);
            }

            server.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
