package com.Clinacuity;
import java.security.Security;

import org.bouncycastle.jcajce.provider.digest.SHA384;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.crypto.digests.SHA384Digest;
import java.security.MessageDigest;

public class BouncyCastleSHA384 {
    public static void main(String[] args) {
        Security.addProvider(new BouncyCastleProvider());
        String plainString = "Plaintext Secret";

        // instantiate the BouncyCastle digest directly.
        MessageDigest messageDigest = new SHA384.Digest();
        byte[] hashedString = messageDigest.digest(plainString.getBytes());
        System.out.println(hashedString);

    }

}
