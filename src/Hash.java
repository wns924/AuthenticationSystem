/**
 * @author WongNgaiSum 3035380875
 * @version 1.0
 * An interface that defines the methods of cryptographic hash generator
 */
public interface Hash {

    /**
     * Generate hashed code of a string
     * @param text the string that will be encrypted
     * @return encrypted string
     */
    String getHashCode(String text);

    /**
     * Encode byte array values to hexadecimal
     * @param bytes byte array
     * @return hex values
     */
    String bytes2Hex(byte[] bytes);

    /**
     * Set the algorithm to encode data
     * @param alg algorithm (e.g. "MD5", "SHA-256")
     */
    void setAlgorithm(String alg);

}
