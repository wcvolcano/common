package com.github.wcvolcano.common.net;

import com.github.wcvolcano.common.net.udp.UDPSender;

/**
 * Created by canwen on 2016/7/7.
 */
public class Sender {
    public static void main(String[] args) throws InterruptedException {
        UDPSender sender = new UDPSender("127.0.0.1:2507");
        String msg = "java : hello c++";

        while (true) {
            sender.send(msg.getBytes());
            System.out.println(msg);
            Thread.sleep(300);
        }
    }
}
