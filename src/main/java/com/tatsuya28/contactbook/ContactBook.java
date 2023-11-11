package com.tatsuya28.contactbook;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ContactBook {

    public static final String CONTACT_BOOK_FILE_PATH = "./src/main/resources/contactbook.txt";
    public static Scanner sc = null;

    public static void main(String[] args) {

        sc = new Scanner(System.in);

        File contactBookFile = getOrCreateContactBookFile();

        Contact newContact = askNewContact();
        appendContactToContactBook(contactBookFile, newContact);

        sc.close();
    }


    public static String getUserInput(String userRequest) {
        System.out.println(userRequest);
        return sc.nextLine();
    }

    private static File getOrCreateContactBookFile() {
        File contactBookFile = new File(ContactBook.CONTACT_BOOK_FILE_PATH);

        if (contactBookFile.exists()) {
            return contactBookFile;
        }

        try {
            contactBookFile.createNewFile();
            System.out.printf("The file has been created (%s)%n", ContactBook.CONTACT_BOOK_FILE_PATH);
            return contactBookFile;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Contact askNewContact() {
        String userLastName = getUserInput("Enter contact last name: ");
        String userFirstName = getUserInput("Enter contact first name: ");
        String userPhoneNumber = getUserInput("Enter contact phone number: ");

        Contact newContact = new Contact(userLastName, userFirstName, userPhoneNumber);

        System.out.println(newContact.toString());

        return newContact;
    }

    private static void appendContactToContactBook(File contactBookFile, Contact newContact) {

        if (contactBookFile == null) {
            System.out.println("contactBookFile is null: see appendContactToContactBook");
            return;
        }

        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(contactBookFile, true))) {
            fileWriter.append(newContact.toString());
            fileWriter.append(System.lineSeparator());

            System.out.println("Contact added");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
