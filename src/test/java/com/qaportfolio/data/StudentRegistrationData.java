package com.qaportfolio.data;

import java.nio.file.Path;
import java.time.LocalDate;

public record StudentRegistrationData(String firstName,
                                      String lastName,
                                      String email,
                                      String gender,
                                      String mobileNumber,
                                      LocalDate dateOfBirth,
                                      String subject,
                                      String hobby,
                                      Path picturePath,
                                      String currentAddress,
                                      String state,
                                      String city) {
    public String fullName() {
        return firstName + " " + lastName;
    }

    public String dateOfBirthForModal() {
        return dateOfBirth.getDayOfMonth() + " "
                + dateOfBirth.getMonth().name().charAt(0)
                + dateOfBirth.getMonth().name().substring(1).toLowerCase()
                + ","
                + dateOfBirth.getYear();
    }

    public String pictureFileName() {
        return picturePath.getFileName().toString();
    }
}
