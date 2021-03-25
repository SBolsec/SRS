package me.sbolsec.pwdmgr;

import me.sbolsec.pwdmgr.data.Storage;
import me.sbolsec.pwdmgr.data.Vault;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.nio.file.Path;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;

/**
 * Simple password manager which provides methods
 * for creating a storage using a master password,
 * adding [address, password] pairs to the storage
 * and retrieving passwords by providing the address.
 */
public class Main {

    /** Path to the storage file on the disk */
    private static final Path storagePath = Path.of("./.storage");

    /**
     * Initializes storage for [address, password] pairs using provided master password.
     * @param masterPassword master password used to initialize storage
     */
    public static void init(String masterPassword) throws NoSuchAlgorithmException, InvalidKeySpecException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException, IOException {
        // Generate needed values
        byte[] keySalt = Utils.generateSalt();
        byte[] hmacSalt = Utils.generateSalt();
        IvParameterSpec iv = Utils.generateIv();
        SecretKey aesKey = Utils.getKeyFromPassword("AES", masterPassword, keySalt);
        SecretKey hmacKey = Utils.getKeyFromPassword("HmacSHA256", masterPassword, hmacSalt);

        // encrypt data
        byte[] encryptedData = Utils.encryptData(new HashMap<String, String>(), aesKey, iv);

        // calculate integrity
        byte[] integrity = Utils.calculateIntegrity(encryptedData, iv.getIV(), keySalt, hmacSalt, hmacKey);

        // save the storage to the disk
        Storage storage = new Storage(encryptedData, iv.getIV(), keySalt, hmacSalt, integrity);
        Utils.saveStorageToDisk(storagePath, storage);

        System.out.println("Password manager initialized.");
    }

    /**
     * Puts [address, password] pair into the storage initialized by the
     * provided master password.
     * @param masterPassword master password
     * @param address address to add
     * @param password password to add
     */
    public static void put(String masterPassword, String address, String password) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, ClassNotFoundException {
        // Get the vault from the disk
        Vault vault = Utils.getVault(storagePath, masterPassword);

        // Add the [address, password] pair
        vault.getVault().put(address, password);

        // Generate needed values
        byte[] keySalt = Utils.generateSalt();
        byte[] hmacSalt = Utils.generateSalt();
        IvParameterSpec iv = Utils.generateIv();
        SecretKey aesKey = Utils.getKeyFromPassword("AES", masterPassword, keySalt);
        SecretKey hmacKey = Utils.getKeyFromPassword("HmacSHA256", masterPassword, hmacSalt);

        // encrypt data
        byte[] encryptedData = Utils.encryptData(vault.getVault(), aesKey, iv);

        // calculate integrity
        byte[] integrity = Utils.calculateIntegrity(encryptedData, iv.getIV(), keySalt, hmacSalt, hmacKey);

        // save the storage to the disk
        Storage storage = new Storage(encryptedData, iv.getIV(), keySalt, hmacSalt, integrity);
        Utils.saveStorageToDisk(storagePath, storage);

        System.out.println("Stored password for " + address);
    }

    /**
     * Returns the password from the storage initialized by the provided
     * master password which belongs to the provided address.
     * @param masterPassword master password
     * @param address address for which to return password
     */
    public static void get(String masterPassword, String address) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, ClassNotFoundException {
        // Get the vault from the disk
        Vault vault = Utils.getVault(storagePath, masterPassword);

        // Get the password
        String pass = vault.getVault().get(address);

        if (pass != null)
            System.out.println("Password for " + address + " is: " + pass);
        else
            System.out.println("There is no password stored for " + address);
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

    /**
     * Starting point of program.
     * Calls required method based on command line arguments.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        int len = args.length;
        if (len < 2 || len > 4) printErrorMessageAndTerminate();

        // call the required method
        try {
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
        } catch (Exception e) {
            System.out.println("Wrong password!");
        }
    }
}
