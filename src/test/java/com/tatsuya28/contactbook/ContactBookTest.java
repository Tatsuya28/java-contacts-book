package com.tatsuya28.contactbook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class ContactBookTest {

    private File testFile;

    @BeforeEach
    void setUp() throws IOException {
        testFile = new File("./src/test/resources/test-contact-book.txt");
    }

    @AfterEach
    void tearDown() {
        testFile.delete();
    }

    @Test
    void testGetOrCreateContactBookFile() throws IOException {
        // Arrange

        // Act
        File result = ContactBook.getOrCreateContactBookFile(testFile.getPath());

        // Assert
        assertEquals(testFile, result);
        assertTrue(testFile.exists());
    }

    @Test
    void testAskNewContact() {
        // Arrange
        String simulatedInput = "Doe\nJohn\n123456789\n";
        InputStream stdin = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(stdin);
        UserInputProvider.setUserInputProvider(new Scanner(System.in));

        // Act
        Contact result = ContactBook.askNewContact();

        // Assert
        assertNotNull(result);
        assertEquals("Doe", result.getLastName());
        assertEquals("John", result.getFirstName());
        assertEquals("123456789", result.getPhoneNumber());


        // Clean up
        UserInputProvider.closeScanner();
        System.setIn(System.in);
    }

    @Test
    void testAppendContactToContactBook() throws IOException {
        // Arrange
        Contact testContact = new Contact("Doe", "John", "123456789");

        // Act
        ContactBook.appendContactToContactBook(testFile, testContact);

        // Assert
        List<String> lines = Files.readAllLines(testFile.toPath());
        assertEquals(1, lines.size());
        assertTrue(lines.get(0).contains("Doe"));
        assertTrue(lines.get(0).contains("John"));
        assertTrue(lines.get(0).contains("123456789"));
    }

    @Test
    void testParseContact() {
        // Arrange
        String input = "Contact{lastName='Doe', firstName='John', phoneNumber='123456789'}";

        // Act
        Contact result = ContactBook.parseContact(input);

        // Assert
        assertNotNull(result);
        assertEquals("Doe", result.getLastName());
        assertEquals("John", result.getFirstName());
        assertEquals("123456789", result.getPhoneNumber());
    }

    @Test
    void testParseContactInvalidInput() {
        // Arrange
        String input = "InvalidInput"; // Provide an invalid input

        // Act
        Contact result = ContactBook.parseContact(input);

        // Assert
        assertNull(result); // Expecting null as the result for an invalid input
    }

    @Test
    void testGetContactList() throws IOException {
        // Arrange
        String contactString = "Contact{lastName='Doe', firstName='John', phoneNumber='123456789'}";
        Files.write(testFile.toPath(), List.of(contactString));

        // Act
        List<Contact> result = ContactBook.getContactList(testFile);

        // Assert
        assertEquals(1, result.size());
        assertEquals("Doe", result.get(0).getLastName());
        assertEquals("John", result.get(0).getFirstName());
        assertEquals("123456789", result.get(0).getPhoneNumber());
    }
}