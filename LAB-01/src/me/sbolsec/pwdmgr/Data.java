package me.sbolsec.pwdmgr;

import java.io.Serializable;

public class Data implements Serializable {
    private byte[] vault;
    private byte[] iv;
    private byte[] salt;
    private byte[] integrity;

    public Data(byte[] vault, byte[] iv, byte[] salt, byte[] integrity) {
        this.vault = vault;
        this.iv = iv;
        this.salt = salt;
        this.integrity = integrity;
    }

    public byte[] getVault() {
        return vault;
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
}
