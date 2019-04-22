import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public class TestSample {
    public static void main(String[] args) {

        // Base 64 decode using Apache commons library. You should replace this
        // with whatever you use internally.
        try {
            byte[] codeString = Base64.decodeBase64(Decrypter.unWebSafeAndPad("YWJjMTIzZGVmNDU2Z2hpN7fhCuPemCce_6msaw").getBytes());

            byte[] encryptionKeyBytes = Base64.decodeBase64(Decrypter.unWebSafeAndPad("skU7Ax_NL5pPAFyKdkfZjZz2-VhIN8bjj1rVFOaJ_5o=").getBytes());
            byte[] integrityKeyBytes = Base64.decodeBase64(Decrypter.unWebSafeAndPad("arO23ykdNqUQ5LEoQ0FVmPkBd7xB5CO89PDZlSjpFxo=").getBytes());

            byte[] plaintext;
            SecretKey encryptionKey = new SecretKeySpec(encryptionKeyBytes, "HmacSHA1");
            SecretKey integrityKey = new SecretKeySpec(integrityKeyBytes, "HmacSHA1");
            try {
                plaintext = Decrypter.decrypt(codeString, encryptionKey, integrityKey);
            } catch (DecrypterException e) {
                System.err.println("Failed to decode ciphertext. " + e.getMessage());
                return;
            }
            DataInputStream dis = new DataInputStream(new ByteArrayInputStream(plaintext));
            final long value = dis.readLong();
            System.out.println("The value is: " + value);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
