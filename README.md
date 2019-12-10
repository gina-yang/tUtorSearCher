# tUtorSearCher
Tutor Searcher is an Android application that facilitates matching between USC student tutors and
students seeking help in their classes.

### Installation
1. Install and open Android Studio.
2. Unzip the zip file.
3. In Android Studio select *File > New > Import Project...*
4. Select the unzipped directory.
5. Continue through the steps to import the project.
6. Add the "app" configuration to the project.
7. Choose Pixel 2 XL API 23 as the device for the emulator.
8. Run the app.

# Tips for Usage
1. Start by creating a Tutor account. Enter some credentials and hit Register.
2. Once you've been logged in, you can go to the Profile tab and enter the rest of your information, including courses and availability.
3. For courses, please note that only the following courses are compatible with the app: CSCI103, CSCI104, CSCI109, CSCI170, CSCI201, CSCI270, CSCI310. Note that courses must be inputted with this exact format (All caps, no space).
4. For availability, enter times with the following format: start with the 3 letter abbreviation for the day followed by a space (Mon, Tue, etc...). After the space you can enter the starting hour of the time block. For example "Mon 9" represents Monday from 9 am to 10 am. All time blocks are one hour intervals. Also note that this is 24 hour time, so to represent Tuesday from 2 pm to 3 pm you would use "Tue 14", similarly Wednesday from 9 pm to 10 pm would be "Wed 21".
5. After you've filled out the Tutor's availability and course information, you can log out by force quitting the app. Do this by swiping the app to the side in the multitasking dock.
6. Once you force quit the app and open it again, you will be logged out. You can now register as a Tutee and login.
7. On the search page you can search for the Tutor you just created. Once you find the Tutor you can send a request.
8. Once you've sent a request from the Tutee account, log out again by force quitting the app and log back in as the Tutor. You should see the request in the Requests tab. If you accept it, you will be shown the email of the Tutee. Similarly if you log back in as the Tutee you will see that your request has been accepted, and you can view the email of your Tutor.
To refresh the Request view page, just tap the Request tab on the bottom of the screen again.

# New Features
1. Fix issue where tutor or tutee email displays incorrectly after request is accepted
2. Fix issue display the “Accept” request button where it shows up as “ACCEP”
3. Impose a limit of a single request from a given tutee to any given tutor
4. Implement a log out button on the Profile Page
5. Add drop-down menu to choose courses when searching (rather than having a freeform type-in text field)
6. Add functionality where clicking on a button in the search results allows a user to view a tutor’s profile
7. Add feature where accepting a tutee’s request automatically removes that timeslot from a tutor’s availability

