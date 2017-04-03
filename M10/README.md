# M10 - Testing and Code Standards
For this milestone, your team will conduct testing and quality assurance activities on your code. In resources directory under Code Review Stuff, there is a rules file that works with the intelli-j analyzer and there are forms for the manual code review.

## Individual
Each person should pick a non-trivial method (one that has loops and/or conditional statements) and test that method to achieve branch coverage.
Tests will be implemented in JUnit.
As I said in class, it is easiest to test back-end classes that do not depend on FX. If you want to drive the GUI and test a controller for example, then you will need another library like TestFX which will allow you to launch the app and manipulate inputs.

## Team
The team should conduct a code review of mission-critical sections of code.
There is not a line goal, but rather, the team should spend no more than an hour on the review.
The three required forms are Summary Report, Issue Log and Typo list. Other forms may be included if you wish.

## Javadocs and Coding Standards

Your code will be evaluated with the analysis plug-ins to ensure it conforms to coding standards and good design. Run Intelli-J Lint by selecting Analyze -> Inspect Code. To configure our rules file, Choose Analyze -> Inspect Code. In the dialog, click on the three dots by inspection profile. Select import option and pick the provided rules file.
You may check your code yourself with the provided rules files. You should not have any errors showing. If you do, you will not be penalized IF you can explain to the TA why deviation is necessary. Note that test code (Junit files) , and auto-generated files (like fxml) do not have to pass any tests.
## Grading Criteria

#### Individual

Tests for selected method achieve branch coverage .... 15
Tests coded in JUnit run to completion (can be failing, but not due to incorrect JUnit) … 35
#### Team

Code Review completed with required forms … 20
Javadoc and code standard compliance automated scans… 30

Deductions for intelli-j analyzer (unexplained or valid issues, not gross count):

    Missing javadocs from public methods: -10
    1-10 defects: -5
    10-20 defects: -10
    20-30 defects: -15
    >30 defects: -20
