package me.sbolsec.pwdmgr;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Simple password manager which provides methods
 * for creating a storage using a master password,
 * adding [address, password] pairs to the storage
 * and retrieving passwords by providing the address.
 */
public class Main {

    /**
     * Initializes storage for [address, password] pairs using
     * provided master password.
     * @param masterPassword master password used to initialize storage
     */
    public static void init(String masterPassword) throws NoSuchAlgorithmException, InvalidKeySpecException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException, IOException {
        Utils.checkAndCreateStorage();

        byte[] salt = Utils.generateSalt();
        IvParameterSpec iv = Utils.generateIv();
        SecretKey key = Utils.getKeyFromPassword(masterPassword, salt);

        ByteArrayOutputStream byteMap = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteMap);
        HashMap<String, String> dummy = new HashMap<>();
        dummy.put("test", "marko");
        out.writeObject(dummy);
        out.close();
        byte[] bytes = byteMap.toByteArray();
        byteMap.close();

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] storage = cipher.doFinal(bytes);

        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(key);
        byte[] integrity = mac.doFinal(storage);

        Data data = new Data(storage, iv.getIV(), salt, integrity);

        String fileName = Utils.generateFileName();
        Path filePath = Path.of("./.storage/" + fileName);
        Files.createFile(filePath);
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath.toString()))) {
            oos.writeObject(data);
            oos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Puts [address, password] pair into the storage initialized by the
     * provided master password.
     * @param masterPassword master password
     * @param address address to add
     * @param password password to add
     */
    public static void put(String masterPassword, String address, String password) {

    }

    /**
     * Returns the password from the storage initialized by the provided
     * master password which belongs to the provided address.
     * @param masterPassword master password
     * @param address address for which to return password
     */
    public static void get(String masterPassword, String address) {

    }

    /**
     * Prints error message and usage instructions then terminates the program.
     */
    public static void printErrorMessageAndTerminate() {
        System.out.println("Invalid usage! Use one of the following commands:");
        System.out.println("\t1. init [masterPassword]");
        System.out.println("\t2. put [masterPassword] [address] [password]");
        System.out.println("\t3. get [masterPassword] [address]");
        System.exit(1);
    }

    public static void test(String masterPassword) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("./.storage/5368021497"))) {
            // Read data from file
            Data data = (Data) ois.readObject();

            // Generate key from master password
            SecretKey key = Utils.getKeyFromPassword(masterPassword, data.getSalt());

            // Check the integrity
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(key);
            byte[] integrity = mac.doFinal(data.getVault());
            boolean equals = Arrays.equals(integrity, data.getIntegrity());

            // Decrypt the data
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(data.getIv()));
            byte[] vault = cipher.doFinal(data.getVault());

            // Turn byte array into useful object
            ObjectInputStream is = new ObjectInputStream(new ByteArrayInputStream(vault));
            Map<String, String> asd = (Map<String, String>) is.readObject();
            is.close();

            // Do stuff
            System.out.println("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Starting point of program.
     * Calls required method based on command line arguments.
     * @param args command line arguments
     */
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, BadPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchPaddingException, IOException {
        // check the args
        test(args[1]);

        int len = args.length;
        if (len < 2 || len > 4) printErrorMessageAndTerminate();

        // call the required method
        switch (args[0].toLowerCase()) {
            case "init":
                if (len != 2) printErrorMessageAndTerminate();
                init(args[1]);
                break;
            case "put":
                if (len != 4) printErrorMessageAndTerminate();
                put(args[1], args[2], args[3]);
                break;
            case "get":
                if (len != 3) printErrorMessageAndTerminate();
                get(args[1], args[2]);
                break;
            default:
                printErrorMessageAndTerminate();
        }
    }
}
