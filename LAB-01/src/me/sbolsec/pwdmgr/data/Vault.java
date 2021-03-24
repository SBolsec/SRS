package me.sbolsec.pwdmgr.data;

import java.util.Map;

/**
 * Vault that makes it easier to work with the unencrypted data
 */
public class Vault {
    /** Map of stored [address, password] pairs */
    private Map<String, String> vault;
    /** Initialization vector */
    private byte[] iv;
    /** Salt */
    private byte[] salt;
    /** Integrity calculated on all the other private members */
    private byte[] integrity;

    /**
     * Constructor.
     * @param vault map of [address, password]
     * @param iv initialization vector
     * @param salt salt
     * @param integrity integrity
     */
    public Vault(Map<String, String> vault, byte[] iv, byte[] salt, byte[] integrity) {
        this.vault = vault;
        this.iv = iv;
        this.salt = salt;
        this.integrity = integrity;
    }

    /**
     * Returns the map of [address, password] pairs.
     * @return map of [address, password] pairs
     */
    public Map<String, String> getVault() {
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
