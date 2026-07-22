package com.qaportfolio.steps;

import com.qaportfolio.data.StudentRegistrationData;
import com.qaportfolio.pages.FormPage;
import io.qameta.allure.Step;

import static org.assertj.core.api.Assertions.assertThat;

public class FormSteps {

    private final FormPage formPage;

    public FormSteps(FormPage formPage) {
        this.formPage = formPage;
    }

    @Step("Open student registration form")
    public FormSteps openStudentRegistrationForm() {
        formPage.open();
        return this;
    }

    @Step("Fill student registration form with generated data")
    public FormSteps fillStudentRegistrationForm(StudentRegistrationData data) {
        formPage.fillStudentRegistrationForm(data);
        return this;
    }

    @Step("Submit student registration form")
    public FormSteps submitForm() {
        formPage.submit();
        return this;
    }

    @Step("Verify that form submission modal is displayed")
    public FormSteps verifySubmissionModalIsDisplayed() {
        assertThat(formPage.isResultModalDisplayed())
                .as("Submission result modal should be displayed")
                .isTrue();

        return this;
    }

    @Step("Verify submitted student data")
    public FormSteps verifySubmittedStudentData(StudentRegistrationData data) {
        assertThat(formPage.getSubmittedValue("Student Name"))
                .as("Submitted student name should match")
                .isEqualTo(data.fullName());

        assertThat(formPage.getSubmittedValue("Student Email"))
                .as("Submitted student email should match")
                .isEqualTo(data.email());

        assertThat(formPage.getSubmittedValue("Gender"))
                .as("Submitted gender should match")
                .isEqualTo(data.gender());

        assertThat(formPage.getSubmittedValue("Mobile"))
                .as("Submitted mobile number should match")
                .isEqualTo(data.mobileNumber());

        assertThat(formPage.getSubmittedValue("Date of Birth"))
                .as("Submitted date of birth should match")
                .isEqualTo(data.dateOfBirthForModal());

        assertThat(formPage.getSubmittedValue("Subjects"))
                .as("Submitted subject should match")
                .isEqualTo(data.subject());

        assertThat(formPage.getSubmittedValue("Hobbies"))
                .as("Submitted hobby should match")
                .isEqualTo(data.hobby());

        assertThat(formPage.getSubmittedValue("Picture"))
                .as("Submitted picture file name should match")
                .isEqualTo(data.pictureFileName());

        assertThat(formPage.getSubmittedValue("Address"))
                .as("Submitted address should match")
                .isEqualTo(data.currentAddress());

        assertThat(formPage.getSubmittedValue("State and City"))
                .as("Submitted state and city should match")
                .isEqualTo(data.state() + " " + data.city());

        return this;
    }
}
