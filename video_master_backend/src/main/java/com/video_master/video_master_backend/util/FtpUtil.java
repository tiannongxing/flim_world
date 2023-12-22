package com.video_master.video_master_backend.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class FtpUtil {

    public static boolean uploadFile(String server, int port, String user, String password,
                              String remoteFilePath, InputStream fileStream) throws IOException {

        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(server, port);
            boolean login = ftpClient.login(user, password);
            if (!login) {
                ftpClient.disconnect();
                return false;
            }

            // 设置ftp的控制编码为UTF-8格式
            ftpClient.setControlEncoding("UTF-8");
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            boolean success = ftpClient.storeFile(remoteFilePath, fileStream);
            fileStream.close();
            ftpClient.logout();
            return success;
        } finally {
            if (ftpClient.isConnected()) {
                ftpClient.disconnect();
            }
        }
    }

    public static boolean uploadFile(String server, int port,
                                     String remoteFilePath, InputStream fileStream) throws IOException {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(server, port);

            ftpClient.login("anonymous", "gaozhechen26@gmail.com");

            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            boolean success = ftpClient.storeFile(remoteFilePath, fileStream);
            fileStream.close();
            ftpClient.logout();
            return success;
        } finally {
            if (ftpClient.isConnected()) {
                ftpClient.disconnect();
            }
        }
    }
}
