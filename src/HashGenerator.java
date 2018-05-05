import java.security.MessageDigest;

/**
 * @author WongNgaiSum 3035380875
 * @version 1.0
 * A hash generator that encrypts data.
 */
public class HashGenerator implements Hash {

    // default algorithm
    private static String algorithm = "SHA-256";

    /**
     * Generate hashed code of a string
     * @param text the string that will be encrypted
     * @return encrypted string
     */
    public String getHashCode(String text) {

        try {

            MessageDigest alg = MessageDigest.getInstance(algorithm);
            alg.update(text.getBytes());
            return bytes2Hex(alg.digest());

        } catch (Exception e) {

            throw new RuntimeException(e);

        }


    }

    /**
     * Encode byte array values to hexadecimal
     * @param bytes byte array
     * @return hex values
     */
    public String bytes2Hex(byte[] bytes) {

        StringBuffer result = new StringBuffer();
        for (byte b : bytes)
            result.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        return result.toString();

    }

    /**
     * Set the algorithm to encode data
     * @param alg algorithm (e.g. "MD5", "SHA-256")
     */
    public void setAlgorithm(String alg) {

        algorithm = alg;

    }

}
