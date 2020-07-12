package com.rltx.wspay.utils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadPicture {


    public static void main(String[] args) throws Exception {
        downloadPicture("http://testimg.wht56.com/0ea09390-4647-4b8a-ab4e-e1465788e238.jpg","/storage/photo/driver/0ea09390-4647-4b8a-ab4e-e1465788e238.jpg");
    }

    public static void downloadPicture(String urlList,String path) {
        URL url = null;
        try {
            url = new URL(urlList);
            DataInputStream dataInputStream = new DataInputStream(url.openStream());
            FileOutputStream fileOutputStream = new FileOutputStream(new File(path));
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = dataInputStream.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            fileOutputStream.write(output.toByteArray());
            dataInputStream.close();
            fileOutputStream.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
