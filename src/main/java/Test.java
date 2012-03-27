import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringUtils;


public class Test {

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            verify("key-6zwuz0tx6qryy2lgo6ww0qlr1uxazke9", "5zkg57r9b3lhhdggib2v9vatfqu95jb53gzdomm1eb2v6gcwo3", "1332527315", "630376847a4f3e2665049d09176027d0dc2d922c33af69df08c22f6cfee158dc");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static boolean verify(String mailgunKey, String token, String timestamp, String signature) throws Exception {


        
        String key = mailgunKey;
        byte[] keyBytes = key.getBytes();

        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(keySpec);

        String encode = timestamp + token;
        
        byte[] hmacBytes = mac.doFinal(encode.getBytes());

        String newSignature = new String(Hex.encodeHex(hmacBytes));
        
        
        
        boolean result = StringUtils.equals(newSignature, signature);
        return result;
    }
    
}
