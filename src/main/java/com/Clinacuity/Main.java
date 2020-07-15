package com.Clinacuity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

//import java.security.Security;
//import org.bouncycastle.jce.provider.BouncyCastleProvider;
public class Main {
    public static Metaphone3 m3 = new Metaphone3();
    public static byte[] salt = Sha384withSalt.getSalt();
    public static void main(String[] args) {
        String fileLocation = "/Users/noushin/codes/OneWayHashing/src/main/java/patients.csv";

        // Create Pseudo Id
        String PseudoID1 = "";
        String PseudoID2 = "";
        try (BufferedReader br = new BufferedReader(new FileReader(fileLocation))) {
            String line = null;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] SplitedData = line.split(",");
                String DOB = SplitedData[1];
                String SSN = SplitedData[3];
                String FirstName = SplitedData[7];
                String LastName = SplitedData[8];
                String Suffix = SplitedData[9];
                String Gender = SplitedData[14];
                //****************** Normalizing ******************
                FirstName = "2015-05-14";

                // removing numeric values from string + uppercase
                FirstName = FirstName.replaceAll("[^A-Za-z]", "").toUpperCase();
                // removing numeric values from string + uppercase
                LastName = LastName.replaceAll("[^A-Za-z]", "").toUpperCase();
                // remove - in string
                DOB = DOB.replaceAll("-", "");
                // remove - in string
                SSN = SSN.replaceAll("-", "");


                PseudoID1 = LastName + " " +  FirstName + " " + DOB + " " + Gender;
                PseudoID2 = SSN + " " + LastName + " " + FirstName + " " +  DOB;
                System.out.println(PseudoID1);
//                System.out.println("SHA-384 whole: " + Hash_SHA_Pseudo_Id_Whole(PseudoID1));
//                System.out.println("SHA-384 whole + Salt: " + Hash_SHA_Pseudo_Id_Whole_salt(PseudoID1));

                System.out.println("SHA-384 indiv: " + Hash_SHA_Pseudo_Id_Individually(PseudoID1));
//                System.out.println("SHA-384 indiv + Salt: " + Hash_SHA_Pseudo_Id_Individually_salt(PseudoID1));
                System.out.println("SHA-384 indiv + Metaphone3: " + Hash_SHA_Pseudo_Id_Individually_Metaphone3(PseudoID1));
//                System.out.println("SHA-384 indiv +  Metaphone3 + salt: " + Hash_SHA_Pseudo_Id_Individually_salt_Metaphone3(PseudoID1));


                System.out.println(PseudoID2);
//                System.out.println("SHA-384 whole: " + Hash_SHA_Pseudo_Id_Whole(PseudoID2));
//                System.out.println("SHA-384 whole + Salt: " + Hash_SHA_Pseudo_Id_Whole_salt(PseudoID2));

                System.out.println("SHA-384 indiv: " + Hash_SHA_Pseudo_Id_Individually(PseudoID2));
//                System.out.println("SHA-384 indiv + Salt: " + Hash_SHA_Pseudo_Id_Individually_salt(PseudoID2));
                System.out.println("SHA-384 indiv + Metaphone3: " + Hash_SHA_Pseudo_Id_Individually_Metaphone3(PseudoID2));
//                System.out.println("SHA-384 indiv +  Metaphone3 + salt: " + Hash_SHA_Pseudo_Id_Individually_salt_Metaphone3(PseudoID2));

            }
        } catch (IOException ie) {
            ie.printStackTrace();
        }

    }

//    public static String Hash_SHA_Pseudo_Id_Whole(String PseudoId){
//        //******************** Method1 hash using SHA-384 Pseudo-id in whole
//        String PseudoId1 = PseudoId.replaceAll(" ", "");
//        String HashedPseudoId = Sha384Hashing.encryptThisString(PseudoId1);
//        return HashedPseudoId;
//    }


    public static String Hash_SHA_Pseudo_Id_Individually(String PseudoId) {
        //******************** Method2 Hash using SHA-384 individually and then concatenate
        String HashedPseudoIds = "";
        String[] Pseudo_id_arr = PseudoId.split(" ");
        for(int i=0; i< Pseudo_id_arr.length; i++){
            HashedPseudoIds+= Sha384Hashing.encryptThisString(Pseudo_id_arr[i]);
        }
        return HashedPseudoIds;
    }

    public static String Hash_SHA_Pseudo_Id_Individually_Metaphone3(String PseudoId) {
        String HashedPseudoIds = "";
        String[] Pseudo_id_arr = PseudoId.split(" ");
        for(int i=0; i< Pseudo_id_arr.length; i++){
            m3.SetWord(Pseudo_id_arr[i]);
            m3.Encode();
            HashedPseudoIds+= Sha384Hashing.encryptThisString(m3.GetMetaph());
        }
        return HashedPseudoIds;
    }

//    public static String Hash_SHA_Pseudo_Id_Whole_salt(String PseudoId){
//        //SHA-384 with Salt https://www.javaguides.net/2020/02/java-sha-384-hash-with-salt-example.html
//        byte[] salt = Sha384withSalt.getSalt();
//        String HashedPseudoId = Sha384withSalt.getSecureString(PseudoId, salt);
//        return HashedPseudoId;
//    }
//
//    public static String Hash_SHA_Pseudo_Id_Individually_salt(String PseudoId){
//        String HashedPseudoIds = "";
//        byte[] salt = Sha384withSalt.getSalt();
//
//        String[] Pseudo_id_arr = PseudoId.split(" ");
//        for(int i=0; i< Pseudo_id_arr.length; i++){
//            HashedPseudoIds+= Sha384withSalt.getSecureString(Pseudo_id_arr[i], salt);
//        }
//        return HashedPseudoIds;
//    }

//    public static String Hash_SHA_Pseudo_Id_Individually_salt_Metaphone3(String PseudoId){
//        String HashedPseudoIds = "";
//        String[] Pseudo_id_arr = PseudoId.split(" ");
//        for(int i=0; i< Pseudo_id_arr.length; i++){
//            m3.SetWord(Pseudo_id_arr[i]);
//            m3.Encode();
//            HashedPseudoIds+= Sha384withSalt.getSecureString(Pseudo_id_arr[i], salt);
//        }
//        return HashedPseudoIds;
//    }

}


