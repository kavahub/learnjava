package io.github.kavahub.learnjava;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class GetAllMacAddressesExample {
    public static void main(String[] args) throws SocketException {
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();

        enumerationAsStream(networkInterfaces).map(t -> {
            try {
                return t.getHardwareAddress();
            } catch (SocketException e) {
                throw new RuntimeException(e);
            }
        }).filter(Objects::nonNull)
        .forEach((hardwareAddress) -> {
            String[] hexadecimalFormat = new String[hardwareAddress.length];
            for (int i = 0; i < hardwareAddress.length; i++) {
                hexadecimalFormat[i] = String.format("%02X", hardwareAddress[i]);
            }

            System.out.println(String.join("-", hexadecimalFormat));
        });


        // while (networkInterfaces.hasMoreElements()) {
        //     NetworkInterface ni = networkInterfaces.nextElement();
        //     byte[] hardwareAddress = ni.getHardwareAddress();
        //     if (hardwareAddress != null) {
        //         String[] hexadecimalFormat = new String[hardwareAddress.length];
        //         for (int i = 0; i < hardwareAddress.length; i++) {
        //             hexadecimalFormat[i] = String.format("%02X", hardwareAddress[i]);
        //         }
        //         System.out.println(String.join("-", hexadecimalFormat));
        //     }
        // }
    }

    public static <T> Stream<T> enumerationAsStream(Enumeration<T> e) {
        return StreamSupport.stream(
            Spliterators.spliteratorUnknownSize(
                new Iterator<T>() {
                    public T next() {
                        return e.nextElement();
                    }
                    public boolean hasNext() {
                        return e.hasMoreElements();
                    }
                },
                Spliterator.ORDERED), false);
    }

}
