package com.qaportfolio.data;

import com.github.javafaker.Faker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Locale;

public final class TestDataFactory {

    private static final Faker FAKER = new Faker(Locale.ENGLISH);

    private TestDataFactory() {
    }

    public static StudentRegistrationData studentRegistrationData() {
        return new StudentRegistrationData(
                FAKER.name().firstName(),
                FAKER.name().lastName(),
                FAKER.internet().emailAddress(),
                "Female",
                generateMobileNumber(),
                LocalDate.of(1998, 5, 15),
                "Computer Science",
                "Reading",
                createTemporaryPicture(),
                FAKER.address().fullAddress(),
                "NCR",
                "Delhi"
        );
    }

    private static String generateMobileNumber() {
        return FAKER.number().digits(10);
    }

    private static Path createTemporaryPicture() {
        try {
            Path picturePath = Files.createTempFile("demoqa-upload-", ".txt");
            Files.writeString(picturePath, "DemoQA upload test file");
            picturePath.toFile().deleteOnExit();

            return picturePath;
        } catch (IOException exception) {
            throw new IllegalStateException("Failed to create temporary upload file.", exception);
        }
    }

    public static TableRecordData tableRecordData() {
        return new TableRecordData(
                FAKER.name().firstName(),
                FAKER.name().lastName(),
                FAKER.internet().emailAddress(),
                FAKER.number().numberBetween(20, 60),
                FAKER.number().numberBetween(30000, 90000),
                "QA"
        );
    }
}
