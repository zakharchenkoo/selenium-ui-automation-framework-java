package com.qaportfolio.tests;

import com.qaportfolio.data.StudentRegistrationData;
import com.qaportfolio.data.TestDataFactory;
import com.qaportfolio.pages.FormPage;
import com.qaportfolio.pages.base.BaseTest;
import com.qaportfolio.steps.FormSteps;
import io.qameta.allure.*;
import org.testng.annotations.Test;

@Epic("DemoQA UI")
@Feature("Student Registration Form")
public class FormTest extends BaseTest {

    @Test(
            groups = {"smoke", "regression", "form"},
            description = "Student registration form should be submitted with valid generated data"
    )
    @Story("Submit student registration form")
    @Severity(SeverityLevel.CRITICAL)
    public void shouldSubmitStudentRegistrationFormWithValidData() {
        StudentRegistrationData studentData = TestDataFactory.studentRegistrationData();

        FormPage formPage = new FormPage(driver());
        FormSteps formSteps = new FormSteps(formPage);

        formSteps
                .openStudentRegistrationForm()
                .fillStudentRegistrationForm(studentData)
                .submitForm()
                .verifySubmissionModalIsDisplayed()
                .verifySubmittedStudentData(studentData);
    }

    @Test(groups = {"regression", "negative"}, description = "Form should not be submitted when all fields are empty")
    @Story("Negative: empty required fields")
    @Severity(SeverityLevel.NORMAL)
    public void shouldNotSubmitFormWithEmptyRequiredFields() {
        FormPage formPage = new FormPage(driver());
        FormSteps formSteps = new FormSteps(formPage);

        formSteps
                .openStudentRegistrationForm()
                .submitForm()
                .verifySubmissionModalIsNotDisplayed();
    }

    @Test(groups = {"regression", "negative"}, description = "Form should not be submitted with invalid mobile number")
    @Story("Negative: invalid mobile number")
    @Severity(SeverityLevel.NORMAL)
    public void shouldNotSubmitFormWithInvalidMobileNumber() {
        FormPage formPage = new FormPage(driver());
        FormSteps formSteps = new FormSteps(formPage);

        formSteps
                .openStudentRegistrationForm()
                .fillRequiredFieldsOnly("John", "Doe", "123")
                .submitForm()
                .verifySubmissionModalIsNotDisplayed();
    }
}
