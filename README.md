# M5 Register/Profile and the Domain Model
## Design
Brainstorm candidate classes/objects for the domain model. Filter those to get rid of inappropriate objects. Create the Domain Model for the application. Use the project description to brainstorm objects. List primary attributes. Note the stereotype of the classes. Annotate relationships between objects (which objects need to use other objects). Show any multiplicity requirements. There is only one domain model required, so this is a team effort.

## Coding
Add the registration screen to your app. A new user should be able to register by entering their information (like name, id, and password). If accepted, the new user should be added to the system. If cancelled, the user should not be added.

During registration, the new user may designate their account type (User, Worker, Manager or Admin). In a real application, we would have controls for these, but for this class project, we will let the individual enter their capabilities during registration.

When registration is working, you may remove the hard-coded user/pass from the app.

You should also have the ability for a user to create and edit their profile. Their profile consists of all their information that the application must maintain like email address, home address, title, etc.

NOTE THAT PERSISTENCE IS NOT REQUIRED AT THIS POINT. IT IS OK TO HAVE TO RE-ENTER USERS FROM SCRATCH EACH TIME THE APPLICATION IS STARTED.

## Grading Criteria (5)
1. Brainstormed and Filtered Classes .... 5

2. UML Domain Model prepared (50)

    Model contains appropriate classes ...... 10

    Classes are annotated with appropriate stereotypes .... 10

    Model contains appropriate interactions .... 10

    Model shows multiplicity of associations .... 10

    Model contains appropriate attributes … 10

3. Implementation (45)

    User registration screen is integrated into app .............................. 05

    Accepted registration adds new user to system ................. 05

    Cancelled registration does not add new user ................. 05

    Back-end records list of registered users (UI communicates with model) ................... 10

    User type (user, worker, manager, admin) created ..... 10

    User profile edited ...... 05

    Javadoc / comments .......................................... 05
