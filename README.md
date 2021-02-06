# User Management Application
User Management Application is a simple crud application that manipulate user data based on specific requirements. This is developed by following SDLC (Software Devlopment Life Cycle). This documentation is sectioned according to the SDLC.  Hope you will enjoy!

# Sections:
a. Enviroment Setup
b. Quick Start
c. SDLC:
  1. Requirements Analysis
  2. Planning
  3. Design
  4. Development / Coding
  5. Testing
  6. Deployment

# Tech Stack Used
1. Server Side: Spring (Spring Boot,Spring AOP, Spring Data JPA, Spring Profile), Swagger(For API documentation), Central Log
2. Client Side: HTML5, CSS3, BootStrap-4, Angular
3. Database: MySQL, Flyway(Data Migration Tool)
4. Deployment: Docker (Optional)
5. Testing Covered: Mockito(Unit Testing), Junit(Integration Testing)

# a) Enviroment Setup: (For Linux Users)

 Server Side Environment:
i.   Installing Java 8:
ii.  Installing Git:
iii. Installing Maven:
iv.  Installing MySQL:
v.   Installing Docker (if you want to deploy in container):

 Client Side Enviroment Setup: 
vi. Installing Angular

# Enviroment Setup: (For Windows Users)

 Server Side Environment:
i.   Installing Java 8:
ii.  Installing Git:
iii. Installing Maven:
iv.  Installing MySQL:
v.   Installing Docker (if you want to deploy in container):

 Client Side Enviroment Setup: 
vi. Installing Angular

# b. Quick Start:

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
  
  # a) Create User Data:
  i.  A Child User must be created under a Parent User.
  ii. A Child User is not a must to create a Parent User. But A parent user  can have a child user while user creation.
  iii. First Name and User Type can not be empty for any type of user.

 # b) Update User Data:
  i. If a Parent User or Child User can update any field except User Type, that field will be updated without any complex logic implementation.
  ii. If the User Type of Child User is changed to Parent User, the Child will be no longer under any Parent User.
  iii. If the User Type of a Parent User is changed to Child User and if that Parent User has any Child User, those Child users User Type will be updated to Parent User. And this Parent User must be assigned to an existing Parent User.
  
 # c) Delete User Data:
  i. If a Parent User is deleted , then all the Child Users User Type of that Parent User will be updated to Child User. And only that Parent User will be deleted.
  ii. While a Child User is deleted, it will be deleted for storage and there will be no relation with it's existing parent user.
  
 # 2. Planning
 # 3. Design
 # 4. Development / Coding
 # 5. Testing
 # 6. Deployment
