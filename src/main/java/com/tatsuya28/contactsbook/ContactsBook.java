package com.tatsuya28.contactsbook;

import java.util.Scanner;

public class ContactsBook {

    public static Scanner sc = null;

    public static void main(String[] args) {

        sc = new Scanner(System.in);
        String userLastName = getUserInput("Enter your last name: ");
        String userFirstName = getUserInput("Enter your first name: ");
        String userPhoneNumber = getUserInput("Enter your phone number: ");

        Contact newContact = new Contact(userLastName, userFirstName, userPhoneNumber);

        System.out.println(userLastName);
        System.out.println(userFirstName);
        System.out.println(userPhoneNumber);
        sc.close();
    }

    public static String getUserInput(String userRequest){
        System.out.println(userRequest);
        return sc.nextLine();
    }
}
