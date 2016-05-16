package com.github.wcvolcano.common.net.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created by canwen on 2016/5/16.
 * learn from wuyu
 */
public class UDPReceiver {
    private static byte[] getData(byte[] data, int length) {
        byte[] bytes = new byte[length];
        System.arraycopy(data, 0, bytes, 0, length);
        return bytes;
    }

    static public void startReceiver(final int port, final UDPDataHandler handler) {
        Thread t = new Thread(() -> {
            try {

                DatagramSocket socket = new DatagramSocket(port);
                byte[] buf = new byte[1024 * 10];
                DatagramPacket dp = new DatagramPacket(buf, buf.length);
                while (true) {
                    socket.receive(dp);
                    handler.handle(getData(dp.getData(), dp.getLength()));
                }
            } catch (SocketException e) {
                handler.handleSocketException(e);
            } catch (IOException e) {
                handler.handleIOException(e);
            }
        });
        t.start();
    }

}
