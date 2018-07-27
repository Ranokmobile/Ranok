package com.ranok.nfc.nfc_models;


public class RDTUrl extends BaseRecord {
    public String url;
    public int prefix;


    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("Prefix:" + prefix + "   " + NFCProtocol.getProtocol(prefix) +  url);
        return buffer.toString();
    }

}
