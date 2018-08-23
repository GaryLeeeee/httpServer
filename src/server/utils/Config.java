package server.utils;

import java.io.File;

/**
 * Created by GaryLee on 2018-08-19 20:29.
 */
public class Config {
    public static int port = 8888;
    public static String staticPath = System.getProperty("user.dir")+"\\src\\server\\static";

    public static void main(String[] args) {
        System.out.println(staticPath);
        System.out.println(new File(staticPath).exists());
        byte c = 'c';
        System.out.println(c);
    }
}
