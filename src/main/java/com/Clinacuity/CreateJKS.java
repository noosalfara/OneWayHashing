package com.Clinacuity;

import java.security.*;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.security.cert.X509Certificate;

import sun.security.tools.keytool.CertAndKeyGen;
import sun.security.x509.*;

public class CreateJKS {
    public static void main(String[] args) {
        try{
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(null,null);

            keyStore.store(new FileOutputStream("mytestkey.jks"), "passphrase".toCharArray());
        }catch(Exception ex){
            ex.printStackTrace();
        }
        try{
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(new FileInputStream("mytestkey.jks"),"passphrase".toCharArray());

            CertAndKeyGen gen = new CertAndKeyGen("RSA","SHA1WithRSA");
            gen.generate(1024);

            Key key=gen.getPrivateKey();
            X509Certificate cert=gen.getSelfCertificate(new X500Name("CN=ROOT"), (long)365*24*3600);

            X509Certificate[] chain = new X509Certificate[1];
            chain[0]=cert;

            keyStore.setKeyEntry("mykey", key, "passphrase".toCharArray(), chain);

            keyStore.store(new FileOutputStream("mytestkey.jks"), "passphrase".toCharArray());
        }catch(Exception ex){
            ex.printStackTrace();
        }
        try{
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(new FileInputStream("mytestkey.jks"),"passphrase".toCharArray());

            CertAndKeyGen gen = new CertAndKeyGen("RSA","SHA1WithRSA");
            gen.generate(1024);

            X509Certificate cert=gen.getSelfCertificate(new X500Name("CN=SINGLE_CERTIFICATE"), (long)365*24*3600);

            keyStore.setCertificateEntry("single_cert", cert);

            keyStore.store(new FileOutputStream("mytestkey.jks"), "passphrase".toCharArray());
        }catch(Exception ex){
            ex.printStackTrace();
        }
        try{
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(new FileInputStream("mytestkey.jks"),"passphrase".toCharArray());

            Key key = keyStore.getKey("mykey", "passphrase".toCharArray());
//          System.out.println("Private key : "+key.toString());   //You will get a NullPointerException if you uncomment this line

            java.security.cert.Certificate[] chain =  keyStore.getCertificateChain("mykey");
            for(java.security.cert.Certificate cert:chain){
                System.out.println(cert.toString());
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        try{
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(new FileInputStream("mytestkey.jks"),"passphrase".toCharArray());

            java.security.cert.Certificate cert = keyStore.getCertificate("single_cert");

            System.out.println(cert.toString());
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
