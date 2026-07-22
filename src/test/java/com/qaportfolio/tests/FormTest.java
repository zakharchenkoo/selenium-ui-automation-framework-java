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
}
