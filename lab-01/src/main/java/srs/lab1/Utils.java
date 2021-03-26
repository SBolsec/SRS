package srs.lab1;

import srs.lab1.data.Storage;
import srs.lab1.data.Vault;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Utility functions
 */
public class Utils {

    /**
     * Creates storage file.
     * If it already exists, first deletes the existing storage then creates new storage.
     * @throws IOException
     */
    public static void createStorage(Path storagePath) throws IOException {
        Files.deleteIfExists(storagePath);
        Files.createFile(storagePath);
    }

    /**
     * Generates initialization vector using SecureRandom.
     * @return initialization vector
     */
    public static IvParameterSpec generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    /**
     * Generates salt using SecureRandom.
     * @return salt
     */
    public static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[32];
        random.nextBytes(salt);
        return salt;
    }

    /**
     * Generates SecretKey using provided plain text master password and salt using "PBKDF2WithHmacSHA256".
     * @param password master password
     * @param salt salt
     * @return generated key
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static SecretKey getKeyFromPassword(String algorithm, String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65537, 256);
        SecretKey key = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), algorithm);
        return key;
    }

    /**
     * Encrypts the given object using the provided key and initialization vector using "AES/CBC/PKCS5Padding".
     * @param data object to be encrypted
     * @param key symmetric key
     * @param iv initialization vector
     * @return encrypted data
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     */
    public static byte[] encryptData(Object data, SecretKey key, IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException, IOException, BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException, InvalidKeyException {
        // Turn object into byte[]
        ByteArrayOutputStream byteMap = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteMap);
        out.writeObject(data);
        out.close();
        byte[] bytes = byteMap.toByteArray();
        byteMap.close();

        // Encrypt byte[]
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        return cipher.doFinal(bytes);
    }

    /**
     * Decrypts the given byte[] using the provided key and initialization vector using "AES/CBC/PKCS5Padding".
     * @param data data
     * @param key key
     * @param iv initialization vector
     * @return decrypted data
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public static byte[] decryptData(byte[] data, SecretKey key, IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        return cipher.doFinal(data);
    }

    /**
     * Calculates the integrity using "HmacSHA256" of the given data, initialization vector and salt.
     * using the provided key.
     * @param data data
     * @param iv initialization vector
     * @param keySalt key salt
     * @param hmacSalt hmac salt
     * @param key key
     * @return integrity
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    public static byte[] calculateIntegrity(byte[] data, byte[] iv, byte[] keySalt, byte[] hmacSalt, SecretKey key) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(key);
        mac.update(data);
        mac.update(iv);
        mac.update(keySalt);
        mac.update(hmacSalt);
        byte[] integrity = mac.doFinal();
        return integrity;
    }

    /**
     * Writes the provided object to the provided file path.
     * @param path file to which to write
     * @param object object which to write
     */
    public static void writeObjectToDisk(Path path, Object object) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(object);
        oos.flush();
        byte[] data = bos.toByteArray();
        oos.close();
        bos.close();

        FileOutputStream fos = new FileOutputStream(path.toString());
        fos.write(data);
        fos.flush();
        fos.close();
    }

    /**
     * Reads object from provided file path.
     * @param path file from which to read
     * @return object read from file
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Object readObjectFromDisk(Path path) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(path.toString());
        byte[] data = fis.readAllBytes();
        fis.close();

        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bis);

        Object obj = ois.readObject();
        int available = ois.available();
        ois.close();
        bis.close();

        if (available != 0) {
            System.out.println("File has been tampered with!");
            System.exit(1);
        }

        return obj;
    }

    /**
     * Returns vault, map of [address, password] pairs from byte array
     * @param data byte array
     * @return vault object
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static HashMap<String, String> getVaultFromByteArray(byte[] data) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
        HashMap<String, String> vault = (HashMap<String, String>) ois.readObject();
        ois.close();
        return vault;
    }

    /**
     * Saves storage to disk
     * @param storagePath
     * @param storage
     * @throws IOException
     */
    public static void saveStorageToDisk(Path storagePath, Storage storage) throws IOException {
        Utils.createStorage(storagePath);
        Utils.writeObjectToDisk(storagePath, storage);
    }

    /**
     * Fetches the vault from the disk and decrypts it
     * @param masterPassword master password
     * @return vault from disk
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws InvalidKeySpecException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidAlgorithmParameterException
     * @throws NoSuchPaddingException
     */
    public static Vault getVault(Path storagePath, String masterPassword) throws IOException, ClassNotFoundException, NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException {
        // Check if the storage file exists
        if (!Files.exists(storagePath)) {
            System.out.println("Password manager has not been initialized!");
            System.out.println("Use the init command to get started:");
            System.out.println("\tinit [masterPassword]");
            System.exit(1);
        }

        // Read data from file
        Storage storage = (Storage) Utils.readObjectFromDisk(storagePath);

        // Generate keys from master password
        SecretKey aesKey = Utils.getKeyFromPassword("AES", masterPassword, storage.getKeySalt());
        SecretKey hmacKey = Utils.getKeyFromPassword("HmacSHA256", masterPassword, storage.getHmacSalt());

        // Check the integrity of the file
        byte[] integrity = Utils.calculateIntegrity(storage.getVault(), storage.getIv(), storage.getKeySalt(), storage.getHmacSalt(), hmacKey);
        if (!Arrays.equals(integrity, storage.getIntegrity())) {
            System.out.println("Wrong master password!");
            System.exit(1);
        }

        // Decrypt data
        byte[] decryptedData = Utils.decryptData(storage.getVault(), aesKey, new IvParameterSpec(storage.getIv()));

        // Get vault from decrypted data
        HashMap<String, String> vault = Utils.getVaultFromByteArray(decryptedData);

        return new Vault(vault, storage.getIv(), storage.getKeySalt(), storage.getHmacSalt(), integrity);
    }
}
