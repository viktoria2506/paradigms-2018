import java.io.*;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256Sum {

    private static BigInteger hashByBytes(byte[] bytes) {
        try {
            MessageDigest m = MessageDigest.getInstance("SHA-256");
            m.update(bytes);
            return new BigInteger(1, m.digest());
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Algorithm is no available");
        }
        return BigInteger.ZERO;
    }

    public static void main(String[] args) {
        if (args == null) {
            System.err.println("Wrong input format.");
            return;
        }
        if (args.length == 0) {
            try {
                byte[] bytes = new byte[System.in.available()];
                System.in.read(bytes);
                BigInteger hash = hashByBytes(bytes);
                System.out.println(String.format("%064x", hash).toUpperCase() + " *-");
            } catch (IOException e) {
                System.err.println("I/O error.");
            }
        } else {
            try {
                for (String file : args) {
                    BigInteger i = hashByBytes(Files.readAllBytes(Paths.get(file)));
                    System.out.println(String.format("%064x", i).toUpperCase() + " *" + file);
                }
            } catch (FileNotFoundException e) {
                System.err.println("File doesn't found.");
            } catch (IOException e) {
                System.err.println("I/O error.");
            }
        }
    }
}