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
    /** Salt used to generate key */
    private byte[] keySalt;
    /** Salt used to generate key for hmac */
    private byte[] hmacSalt;
    /** Integrity calculated on all the other private members */
    private byte[] integrity;

    /**
     * Constructor.
     * @param vault map of [address, password] pairs
     * @param iv initialization vector
     * @param keySalt key salt
     * @param hmacSalt hmac salt
     * @param integrity integrity
     */
    public Vault(Map<String, String> vault, byte[] iv, byte[] keySalt, byte[] hmacSalt, byte[] integrity) {
        this.vault = vault;
        this.iv = iv;
        this.keySalt = keySalt;
        this.hmacSalt = hmacSalt;
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
