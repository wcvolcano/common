package com.github.wcvolcano.common.net.udp;

import java.io.IOException;
import java.net.SocketException;

/**
 * Created by canwen on 2016/5/16.
 *
 */
public interface UDPDataHandler {
    void handle(byte[] data);

    /**
     * @param ex can not open socket
     */
    void handleSocketException(SocketException ex);

    /**
     *
     * @param ex communication error
     */
    void handleIOException(IOException ex);
}
