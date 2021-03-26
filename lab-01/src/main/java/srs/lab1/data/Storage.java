package srs.lab1.data;

import java.io.Serializable;

/**
 * Class used to store the encrypted data and everything needed
 * to decrypt it, also to check the integrity.
 */
public class Storage implements Serializable {
    /** Encrypted map of stored [address, password] pairs */
    private byte[] vault;
    /** Initialization vector */
    private byte[] iv;
    /** Salt used to generate key */
    private byte[] keySalt;
    /** Salt used to generate key for hmac */
    private byte[] hmacSalt;
    /** Integrity calculated on all the other private members */
    private byte[] integrity;

    /**
     * Construcotr.
     * @param vault encrypted data
     * @param iv initialization vector
     * @param keySalt salt used to generate key
     * @param hmacSalt salt used to generate key for hmac
     * @param integrity integrity
     */
    public Storage(byte[] vault, byte[] iv, byte[] keySalt, byte[] hmacSalt, byte[] integrity) {
        this.vault = vault;
        this.iv = iv;
        this.keySalt = keySalt;
        this.hmacSalt = hmacSalt;
        this.integrity = integrity;
    }

    /**
     * Returns the encrypted map of [address, password] pairs.
     * @return encrypted map of [address, password] pairs
     */
    public byte[] getVault() {
        return vault;
    }

    /**
     * Returns the initialization vector.
     * @return initialization vector
     */
    public byte[] getIv() {
        return iv;
    }

    /**
     * Return the key salt
     * @return key salt
     */
    public byte[] getKeySalt() {
        return keySalt;
    }

    /**
     * Returns the hmac salt
     * @return hmac salt
     */
    public byte[] getHmacSalt() {
        return hmacSalt;
    }

    /**
     * Returns the integrity of the storage.
     * @return integrity of the storage
     */
    public byte[] getIntegrity() {
        return integrity;
    }
}
