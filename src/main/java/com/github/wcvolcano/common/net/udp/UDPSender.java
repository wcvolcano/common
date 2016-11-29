package com.github.wcvolcano.common.net.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

/**
 * Created by canwen on 2016/7/7.
 */
public class UDPSender {
    private DatagramSocket ds = null;
    private InetSocketAddress target = null;

    public UDPSender(String ipPort) {
        try {
            ds = new DatagramSocket();
            String[] tags = ipPort.split(":");
            String ip = tags[0];
            int port = Integer.parseInt(tags[1]);
            target = new InetSocketAddress(ip, port);
        } catch (SocketException e) {
            //// TODO: 2016/7/7  error
            e.printStackTrace();
        }
    }

    public void send(byte[] bytes) {
        if (target == null) {
            //// TODO: 2016/7/7 error
            System.out.println("target is null");
            return;
        }
        try {
            DatagramPacket dp = new DatagramPacket(bytes, bytes.length, target);
            ds.send(dp);
        } catch (IOException e) {
            //// TODO: 2016/7/7 error
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String ipPort = "120.26.122.191:20212";
        String msg = "hello world";
        int count = 0;

        UDPSender sender = new UDPSender(ipPort);
        while (true) {
            sender.send((count + " " + msg).getBytes());
            System.out.println("send " + count);
            Thread.sleep(100);
            ++count;
        }
    }
}
