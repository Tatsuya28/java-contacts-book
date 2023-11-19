package com.tatsuya28.contactbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ContactBook {

    public static final String CONTACT_BOOK_FILE_PATH = "./src/main/resources/contact-book.txt";
    // public static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        File contactBookFile = getOrCreateContactBookFile(CONTACT_BOOK_FILE_PATH);

        // Contact newContact = askNewContact();
        // appendContactToContactBook(contactBookFile, newContact);
        List<Contact> contactList = getContactList(contactBookFile);
        for (Contact contact : contactList) {
            System.out.println(contact);
        }

        // Contact search
        String searchName = getUserInput("Find a contact: ");
        searchContact(contactList, searchName);

        sc.close();
    }


    public static String getUserInput(String userRequest) {
        return UserInputProvider.getUserInput(userRequest);
    }

    public static File getOrCreateContactBookFile(String filePath) {
        File contactBookFile = new File(filePath);

        if (!contactBookFile.exists()) {
            try {
                contactBookFile.createNewFile();
                System.out.printf("The file has been created (%s)%n", filePath);
            } catch (IOException e) {
                throw new RuntimeException("Error creating contact book file", e);
            }
        }

        return contactBookFile;
    }

    public static Contact askNewContact() {
        String userLastName = getUserInput("Enter contact last name: ");
        String userFirstName = getUserInput("Enter contact first name: ");
        String userPhoneNumber = getUserInput("Enter contact phone number: ");

        Contact newContact = new Contact(userLastName, userFirstName, userPhoneNumber);

        System.out.println(newContact);

        return newContact;
    }

    public static void appendContactToContactBook(File contactBookFile, Contact newContact) {

        if (contactBookFile == null) {
            System.err.println("contactBookFile is null: see appendContactToContactBook");
            return;
        }

        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(contactBookFile, true))) {
            fileWriter.append(newContact.toString()).append(System.lineSeparator());
            System.out.println("Contact added");
        } catch (IOException e) {
            throw new RuntimeException("Error appending contact to contact book", e);
        }
    }

    private static String extractValueFromContactString(String part) {
        int startIndex = part.indexOf('=') + 2;
        int endIndex = part.length() - (part.endsWith("'}") ? 2 : 1);
        return part.substring(startIndex, endIndex);
    }

    public static Contact parseContact(String line) {
        // Assuming the format is always "Contact{lastName='...', firstName='...', phoneNumber='...'}"
        try {
            String[] parts = line.split(", ");
            String lastName = extractValueFromContactString(parts[0]);
            String firstName = extractValueFromContactString(parts[1]);
            String phoneNumber = extractValueFromContactString(parts[2]);

            return new Contact(lastName, firstName, phoneNumber);
        } catch (Exception e) {
            System.err.println("Error parsing contact: " + line);
            return null;
        }
    }

    public static List<Contact> getContactList(File contactBookFile) {
        List<Contact> contactList = new ArrayList<>();

        try (BufferedReader fileReader = new BufferedReader(new FileReader(contactBookFile))) {
            String line;

            while ((line = fileReader.readLine()) != null) {
                Contact contact = parseContact(line);
                if (contact != null) {
                    contactList.add(contact);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading contact book file", e);
        }

        return contactList;
    }

    public static void searchContact(List<Contact> contactList, String searchName) {
        boolean found = false;

        for (Contact contact : contactList) {
            if (contact.getLastName().equalsIgnoreCase(searchName) || contact.getFirstName().equalsIgnoreCase(searchName)) {
                System.out.println("Contact found:");
                System.out.println(contact);
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Contact not found with the given name: " + searchName);
        }
    }
}
