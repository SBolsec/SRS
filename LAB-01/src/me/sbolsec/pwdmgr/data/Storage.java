package me.sbolsec.pwdmgr.data;

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
    /** Salt */
    private byte[] salt;
    /** Integrity calculated on all the other private members */
    private byte[] integrity;

    /**
     * Constructor.
     * @param vault encrypted map of [address, password] pairs
     * @param iv initialization vector
     * @param salt salt
     * @param integrity integrity of the storage
     */
    public Storage(byte[] vault, byte[] iv, byte[] salt, byte[] integrity) {
        this.vault = vault;
        this.iv = iv;
        this.salt = salt;
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
     * Returns the salt.
     * @return salt
     */
    public byte[] getSalt() {
        return salt;
    }

    /**
     * Returns the integrity of the storage.
     * @return integrity of the storage
     */
    public byte[] getIntegrity() {
        return integrity;
    }
}
