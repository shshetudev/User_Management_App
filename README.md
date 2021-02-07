# User Management Application
User Management Application is a simple crud application that manipulate user data based on specific requirements. This is developed by following SDLC (Software Devlopment Life Cycle). This documentation is sectioned according to the SDLC.  Hope you will enjoy!

# Sections:
<ol type="a">
	<li> <h2> Enviroment Setup </h2> </li>
	<li> <h2> Quick Start </h2> </li>
	<li> 
		<h2>According to SDLC:<h2>
<ol type="1">
	<li>Requirements Analysis</li>
	<li>Planning</li>
	<li>Design</li>
	<li>Development / Coding</li>
	<li>Testing</li>
	<li>Deployment</li>
</ol>
	</li>
</ol>


		


# Tech Stacks Used
1. Server Side: Spring (Spring Boot,Spring AOP, Spring Data JPA, Spring Profile), Swagger(For API documentation), Central Log, Custom Exception Handling
2. Client Side: HTML5, CSS3, BootStrap-4, Angular
3. Database: MySQL, Flyway(Data Migration Tool)
4. Deployment: Docker (Optional)
5. Testing Covered: Mockito(Unit Testing), Junit(Integration Testing)

# Enviroment Setup: (For Linux Users)

 Server Side Environment:
i.   Installing Java 8:
ii.  Installing Git:
iii. Installing Maven:
iv.  Installing MySQL:
v.   Installing Docker (if you want to deploy in container):

 Client Side Enviroment Setup: 
vi. Installing Angular

# a) Enviroment Setup: (For Windows Users)

 Server Side Environment:
i.   Installing Java 8:
ii.  Installing Git:
iii. Installing Maven:
iv.  Installing MySQL:
v.   Installing Docker (if you want to deploy in container):

 Client Side Enviroment Setup: 
vi. Installing Angular

# b) Quick Start:
<h4>For Linux Environment</h4>
<ul>
	<li>Open terminal and run the command: git clone https://github.com/shshetudev/User_Management_App.git</li>
	<h4><b>Backend Deployment:</b></h4>
	<li>To install Open JDK, run the command: sudo apt-get install openjdk-8-jdk</li>
	<li>Run the command : cd User_Management_App/Backend/user_management_app. Then run the command: mvn clean install . It will create the backend jar file with name: user_management_app.jar</li>
	<li>To run the jar file with flyway simply run the command: java -jar target/user_management_app.jar</li>
	<li>To run the jar file without flyway simply run the command: java -jar -Dspring.profiles.active=dev target/user_management_app.jar</li>
	<li>Open the web browser and run this command to see the API documentation and testing tool Swagger's interface: http://localhost:8181/swagger-ui.html#/</li>
	<h4><b>Frontend Deployment:</b></h4>
	<li>Run the command: cd Frontend/UserManagementApp/</li>
	<li>Install Angular CLI: npm install -g @angular/cli</li>
	<li>To run the frontend , run the command: npm start</li>
	<li>Go to the browser and run: http://localhost:4200/ . It will run the front end. Enjoy the Application.</li>
</ul>

# c. SDLC:

 # 1. Requirements :
 Create an APP that stores user data
- Data is composed of first name, last name, address (stree, city, state and zip)
- The app should create the following User types (Parent, Child) The child cannot have an address and Must belong to a parent
- App should have API to:
	Delete user data
	Create user data
	Update user data
  
  By Analyzing the business domain / requirements and aligning with real world scenarios, we discovered and developed the following functionalites for Creating, Updating and Deleting Users:
  
 <h2> a) Create User Data: </h2> 
 <ul>
	<li>A Child User must be created under a Parent User.</li>
	<li>A Child User is not a must to create a Parent User. But A parent user  can have a child user while user creation.</li>
	<li>First Name and User Type can not be empty for any type of user.</li>
</ul> 

 <h2> b) Update User Data: </h2> 
 <ul>
	<li>If a Parent User or Child User can update any field except User Type, that field will be updated without any complex logic implementation.</li>
	<li>If the User Type of Child User is changed to Parent User, the Child will be no longer under any Parent User.</li>
	<li>If the User Type of a Parent User is changed to Child User and if that Parent User has any Child User, those Child users User Type will be updated to Parent User. And this Parent User must be assigned to an existing Parent User.</li>
</ul>
  
 <h2> c) Delete User Data: </h2> 
 <ul>
	<li>If a Parent User is deleted , then all the Child Users User Type of that Parent User will be updated to Child User. And only that Parent User will be deleted.</li>
	<li>While a Child User is deleted, it will be deleted for storage and there will be no relation with it's existing parent user.</li>
</ul>
  
 # 2. Planning
 # 3. Design
 # 4. Development / Coding
 # 5. Testing:

	
<ul>
	<h4> a) Unit Testing on Service Layer: </h4>
	<li><b>injectedComponentsAreNotNull:</b> Injected Components are not null</li>
	<li><b>testNotSavingUserDueToNullFirstName: </b>User Saving failed due to first name being null</li>
	<li><b>testNotSavingUserDueToNullUserType: </b>User Saving failed due to user type being null</li>
	<li><b>testSaveParentUser: </b>Save Parent User</li>
	<li><b>testSaveChildUsers: </b>Save 2 Child Users under the Parent User</li>
	<li><b>testNotFindingValidParentUser: </b>Finding Invalid Parent User</li>
	<li><b>testFindingValidParentUser: </b>Finding Valid Parent User</li>
	<li><b>testNotFindingValidChildUser: </b>Finding Invalid Child User</li>
	<li><b>testFindingValidChildUser: </b>Finding Valid Parent User</li>
	<li><b>testNotFindingByFirstNameAndUserTypeDueToWrongFirstName: </b>Not Finding By First Name And UserType DueTo Wrong First Name</li>
	<li><b>testNotFindingByFirstNameAndUserTypeDueToWrongUserType: </b>Not Finding By First Name And UserType DueTo Wrong User Type</li>
	<li><b>testNotFindingById: </b>Not Finding User By Id due to wrong User Id</li>
	<li><b>testFindById: </b>Finding User By Id</li>
	<li><b>testNotFindingAllParentUsers: </b>Not Finding All Parent Users Due to Invalid UserType</li>
	<li><b>testFindingAllParentUsers: </b>Finding All Parent Users Due to Valid UserType</li>
	<li><b>testNotFindingAllChildUsers: </b>Not Finding All Child Users Due to Invalid UserType</li>
	<li><b>testFindingAllChildUsers: </b>Finding All Child Users Due to Valid UserType</li>
	<li><b>testFindingAllChildUsersOfAParentUser: </b>Finding All Child Users of a Parent User</li>
	<li><b>testFindingAll: </b>Finding All Users</li>
	<li><b>testUpdatingUser:</b>Update User's Last Name</li>
</ul>

	
<ul>
	<h4> b) Integration Testing on Repository Layer: </h4>
	<li><b>testNotSaveParentDueToNullFirstName: </b>Not Saving Parent User Due To Null First Name</li>
	<li><b>testSaveParent: </b>Saving Parent User</li>
	<li><b>testNotSaveChildDueToNoParentUser: </b>Not Saving Child User Due to No Valid Parent User</li>
	<li><b>testSaveChildUnderANonExistingParentUser: </b>Saving Child User Under ANon Existing Parent User</li>
	<li><b>testNotFindUserById: </b>Not Find User By Id</li>
	<li><b>testFindUserById: </b>Find User By Id</li>
	<li><b>testNotFindByIdAndUserType: </b>Not Find User By Id and UserType</li>
	<li><b>testFindByIdAndUserType: </b>Find User By Id and UserType</li>
	<li><b>testFindAllParentUsers: </b>Find All Parent Users</li>
	<li><b>testFindAllChildUsers: </b>Find All Child Users</li>
	<li><b>testFindAllUsers: </b>Find All Users</li>
	<li><b>testNotChangeUserTypeOfParentUserToChildHavingNoChildUserDueToNoParentUserExistingInDB: </b>Not Change User Type of Parent User To Child Having No ChildUser Due To No Parent User Existing in DB</li>
	<li><b>testNotDeleteParentUserHavingNoChildUserDueToInvalidUserId: </b>Not Delete Parent User Having No Child User Due To Invalid User Id</li>
	<li><b>testDeleteParentUserHavingNoChildUser: </b>Delete Parent User Having No Child User</li>
	<li><b>testNotDeleteChildUserDueToInvalidUserId: </b>Not Delete Child User Due To Invalid UserId</li>
	<li><b>testDeleteChildUser: </b>Delete Child User</li>
</ul>

 # 6. Deployment:
 <ul>
	<h2>To deploy in docker: </h2>
		<li>Run the command : cd User_Management_App/Backend/user_management_app</li>
		<li>Install docker compose by running the command on terminal: sudo apt install docker-compose </li>
		<li>Run the docker compose: sudo docker-compose up</li>
	</ul>
