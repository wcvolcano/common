package com.github.wcvolcano.common.net.ftp;


import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by canwen on 2016/8/30.
 * mainly for a usage example
 */
public class FtpFileTransfer {
    private final FTPClient ftpClient;

    public FtpFileTransfer(String ip, int port, String user, String password) throws IOException {
        ftpClient = new FTPClient();
        ftpClient.connect(ip, port);
        ftpClient.login(user, password);
        ftpClient.enterLocalPassiveMode();
    }

    public boolean upload(File localFile, String remoteRelativePath) throws IOException {
        return ftpClient.storeFile(remoteRelativePath, new FileInputStream(localFile));
    }
}
