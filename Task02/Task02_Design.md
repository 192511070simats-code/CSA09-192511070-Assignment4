TASK 2 – FROM REQUIREMENT TO EVENT-DRIVEN DESIGN
2.1 Model the Events
The Hospital Outpatient Appointment Desk uses Java Swing components and event listeners to manage patient appointments.
Event Source	Event Object	Listener Interface	Action Taken
Department JComboBox	ItemEvent	ItemListener	Detects department selection and refreshes the doctor list
Doctor JComboBox	ItemEvent	ItemListener	Detects and records the selected doctor
Time Slot JComboBox	ItemEvent	ItemListener	Detects and records the selected appointment time
Book Appointment JButton	ActionEvent	ActionListener	Validates the details and processes the appointment booking
Patient ID JTextField	ActionEvent (when Enter is pressed)	ActionListener	Checks the entered patient ID
Clear JButton	ActionEvent	ActionListener	Clears the entered appointment details
Main JFrame	WindowEvent	WindowListener	Checks whether a booking is in progress before allowing the window to close

2.2 Design the Component Hierarchy
The application uses JFrame as its main window. JPanel containers organise the input fields, buttons and status message.
Component Containment Tree
JFrame – Hospital Appointment Desk
|  Layout: BorderLayout
|
+-- JPanel – Heading
|   |  Layout: FlowLayout
|   +-- JLabel – Hospital Outpatient Appointment Desk
|
+-- JPanel – Appointment Form
|   |  Layout: GridLayout
|   +-- JLabel – Patient ID
|   +-- JTextField – Patient ID
|   +-- JLabel – Department
|   +-- JComboBox – Department List
|   +-- JLabel – Doctor
|   +-- JComboBox – Doctor List
|   +-- JLabel – Time Slot
|   +-- JComboBox – Time Slot List
|
+-- JPanel – Buttons
|   |  Layout: FlowLayout
|   +-- JButton – Book Appointment
|   +-- JButton – Clear
|
+-- JLabel – Status Message
AWT/Swing Classes Used
Component	Swing Class	Purpose
Main window	JFrame	Displays the application
Container	JPanel	Groups related components
Text label	JLabel	Displays field names and messages
Patient input	JTextField	Accepts patient ID
Selection list	JComboBox	Selects department, doctor and time slot
Button	JButton	Performs booking and clearing actions
Layout Managers
•	BorderLayout: Divides the main window into heading, centre and bottom regions.
•	FlowLayout: Arranges the heading and buttons horizontally.
•	GridLayout: Arranges the appointment form in rows and columns.
2.3 Design the Handler Logic
(a) Pseudocode – Refresh Doctor List When Department Changes
BEGIN
    READ selected department

    CLEAR existing doctor list

    IF selected department is Cardiology
        LOAD cardiology doctors
    ELSE IF selected department is Neurology
        LOAD neurology doctors
    ELSE IF selected department is Orthopaedics
        LOAD orthopaedic doctors
    END IF

    UPDATE doctor JComboBox
    SELECT the first available doctor, if any
END
(b) Pseudocode – Validate and Book Appointment
BEGIN
    READ patient ID
    READ selected department
    READ selected doctor
    READ selected time slot

    IF patient ID is empty
        DISPLAY "Enter patient ID"
        STOP
    END IF

    IF department, doctor or time slot is not selected
        DISPLAY "Select all appointment details"
        STOP
    END IF

    SET bookingInProgress = TRUE

    ASK user to confirm appointment

    IF user confirms
        PROCESS appointment booking

        IF booking is successful
            DISPLAY appointment confirmation
        ELSE
            DISPLAY booking error
        END IF
    ELSE
        DISPLAY "Booking cancelled"
    END IF

    SET bookingInProgress = FALSE
END

Window Closing Behaviour
When the receptionist tries to close the application:
1.	The windowClosing() method is called.
2.	The application checks whether a booking is in progress.
3.	If a booking is in progress, a confirmation dialog is displayed.
4.	If the user selects Yes, the application allows the window to close.
5.	If the user selects No, the application remains open.
6.	If no booking is in progress, the application can close normally.
7.	After closing, the windowClosed() method is notified.
2.4 Trace the Event Sequence
Event Sequence from Opening to Closing
Step	Method / Event	Description
1	main()	Starts the application
2	SwingUtilities.invokeLater()	Schedules GUI creation on the Event Dispatch Thread
3	JFrame constructor	Creates the main window and its components
4	setVisible(true)	Displays the appointment window
5	ItemListener	Handles department selection and updates the doctor list
6	ActionListener	Handles the Book Appointment button
7	Validation method	Checks patient ID and appointment details
8	Confirmation dialog	Requests confirmation before booking
9	windowClosing()	Handles the user's attempt to close the window
10	dispose()	Closes and releases the window resources
11	windowClosed()	Handles notification that the window has closed




Comparison with the Java Applet Life Cycle
Applet Method	Purpose
init()	Initialises the applet
start()	Starts or resumes applet execution
paint()	Displays graphics and content
stop()	Pauses applet execution
destroy()	Releases applet resources

A Swing desktop application does not follow the applet life cycle. It uses a constructor to create the GUI, setVisible() to display the window, event listeners to respond to user actions, and windowClosing() and dispose() to manage window closing.
Alternative Concept and Complexity Analysis
The doctor information can be obtained using two approaches.
Approach 1: Fetch Doctors from the Database Each Time
•	A database query is executed whenever the department changes.
•	The database returns the doctors belonging to the selected department.
•	Application memory is required only for the currently retrieved doctor list.
Approach 2: Load Doctors Once into Map<String, List<Doctor>>
•	The department name is stored as the key.
•	The corresponding list of doctors is stored as the value.
•	Doctor information is retrieved from memory whenever the department changes.
•	Repeated database queries can be avoided.
Complexity Comparison
Let:
•	d = number of departments.
•	m = total number of doctors across all departments.
•	k = number of doctors in the selected department.
•	Tdb = time taken for a database query.
Factor	Database Query Each Time	Map-Based Storage
Initial loading	No need to load all doctors	O(d + m)
Time per department change	Tdb + O(k)	O(1) average lookup + O(k) to display doctors
Application memory	O(k) for the current result	O(d + m) for all departments and doctors

Analysis
The database approach uses less application memory because it retrieves only the required department's doctors. However, every department change requires a database query.
The Map-based approach requires O(d + m) memory to store all departments and doctors, but it provides faster repeated lookups without repeatedly accessing the database.
Conclusion
The event-driven design provides an organised approach to developing the hospital appointment system. Suitable Swing components and event listeners manage user interactions. Validation, booking confirmation and window-closing checks improve the reliability and usability of the application.
