package movie.example.ls.vvmoviemanager.utils;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * Created By: lsw
 * Author : Admin [FR]
 * Date :  2016/8/31
 * Email : lsw
 */
public class MD5Utils {
    /**
     * MD5加密
     * @param inputStr
     * @return
     */
    public static String MD5(String inputStr){
        BigInteger bigInteger=null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] inputData = inputStr.getBytes();
            md.update(inputData);
            bigInteger = new BigInteger(md.digest());
        } catch (Exception e) {e.printStackTrace();}

        return bigInteger.toString(16);
    }

}
