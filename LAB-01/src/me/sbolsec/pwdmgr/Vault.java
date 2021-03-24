package me.sbolsec.pwdmgr;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Vault implements Serializable {
    private Map<String, String> storage;
    private byte[] iv;
    private byte[] salt;
    private byte[] integrity;

    public Vault(byte[] iv, byte[] salt) {
        this.storage = new HashMap<>();
        this.iv = iv;
        this.salt = salt;
    }

    public Vault(Map<String, String> storage, byte[] iv, byte[] salt, byte[] integrity) {
        this.storage = storage;
        this.iv = iv;
        this.salt = salt;
        this.integrity = integrity;
    }

    public Map<String, String> getStorage() {
        return storage;
    }

    public byte[] getIv() {
        return iv;
    }

    public byte[] getSalt() {
        return salt;
    }

    public byte[] getIntegrity() {
        return integrity;
    }

    public void setIntegrity(byte[] integrity) {
        this.integrity = integrity;
    }
}
