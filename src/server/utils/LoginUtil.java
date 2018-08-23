package server.utils;

/**
 * Created by GaryLee on 2018-08-23 14:55.
 */
public class LoginUtil {
    public static boolean login(String postStr){
        int index = postStr.indexOf("&password=");
        String username = postStr.substring("username=".length(),index);
        String password = postStr.substring(index+"&password=".length());
        if(username.equals("root")&&password.equals("admin")) {
            System.out.println("用户"+username+"登陆成功");
            return true;
        }
        System.out.println("用户"+username+"登陆失败");
        return false;

    }

    public static void main(String[] args) {
        String str = "username=usr&password=name";
        int b = str.indexOf("&password=");
        System.out.println(str.substring("username=".length(),b));
        System.out.println(str.substring(b+"&password=".length()));
    }
}
