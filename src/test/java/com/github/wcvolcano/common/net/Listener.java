package com.github.wcvolcano.common.net;

import com.github.wcvolcano.common.net.udp.UDPDataHandler;
import com.github.wcvolcano.common.net.udp.UDPReceiver;

import java.io.IOException;
import java.net.SocketException;

/**
 * Created by canwen on 2016/7/7.
 */
public class Listener {
    public static void main(String[] args) {
        UDPReceiver.startReceiver(2507, new UDPDataHandler() {
            @Override
            public void handle(byte[] data) {
                System.out.println("2507" + new String(data));
            }

            @Override
            public void handleSocketException(SocketException ex) {
                ex.printStackTrace();
            }

            @Override
            public void handleIOException(IOException ex) {
                ex.printStackTrace();
            }
        });

        UDPReceiver.startReceiver(2525, new UDPDataHandler() {
            @Override
            public void handle(byte[] data) {
                System.out.println(new String(data));
            }

            @Override
            public void handleSocketException(SocketException ex) {

            }

            @Override
            public void handleIOException(IOException ex) {

            }
        });
    }


}
