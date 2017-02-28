# M6 User Water Report, Robustness Diagrams, Architecture and UI Prototypes
## Design
Each person will take their individual detailed user story from M4, and create a robustness/analysis diagram from it. (again, you do not have to cover for a team mate, just do your own). The diagram should show interaction between the system and your actors using the 4 symbols we discussed in class (boundary, control, etity, external actor). An example diagram is here: external link: http://www.math-cs.gordon.edu/courses/cs211/ATMExample/AnalysisClasses.html

The team should create an architecture diagram that displays the chosen software architecture of the system. Be sure and annotate your trust boundaries on the diagram.

The team should create a UI flow diagram as shown in class that plans for the navigation of the screens required for the application. Also, the team should have a prototype (sketches) of the major screens in the application. you may use an automated tool like FluidUI for this if desired.

## Implementation
Note: Databases are extra credit, therefore, you can assume for testing of this app all data will fit into the device memory. Since persistence is not yet required, entering users and other data can be done on each run of the application.

In this milestone, we implement the ability to submit the user water reports. You should have some kind of input screen for the report where all the information is captured. The report should be stored somewhere in the model.

## Requirements
After login, your application should display the main screen of the application.
You should have a way to navigate to the submit report screen.
The submit report should prompt for all required information.
The submit report screen should not be accessible till after login.
Canceling the report does not save any information
Submitting the report should store it in the model.
Need a way to view a list of all reports in the system.
## Grading Criteria
Robustness/Analysis Diagrams .................... 15

UI Flow and Prototypes .......................... 15

Architecture / Trust boundaries ................. 10

Login/Registration/Profile still works ...... 05

Can navigate to Submit report functionality ...................... 05

Report screen enters all required information .................... 10

After submission report is stored in model ....................... 10

List of reports can viewed.................................10

Cancel does not save the report .......................... 05

Report number auto-assigned by system .................... 05

Javadoc, Comments and Design Update / discussion with TA ........................................10
