# Content-Management-System
Simple Content Management System, where user can manage content of specific data types.


# Simple CMS
This project will create a simple Content management system, where user can manage content of the following types.
1. Lists with metadata information.
2. Documents with metadata.

Application with authentication and authorization. 
More specifically

## Authentication and authorization
The application will have three predefined/fixed user roles. Administrator, Designer and Contributor.
Contributor will be able to add content to existing lists and upload Documents.
Designer will be able to create new List definitions or Document Libraries (and anything contributor can do).
Administrator will be able to do everything Designer can, plus user management.

### Project setup
1. Use spring initalizer. 
2. Use Gradle/java settings

Add the following:
1. lombok
2. spring web, 
3. rest repositories,
4. spring security, 
5. spring data jpa, 
6. posgresql driver


### User management (administration role)
Configure database to connect with a postgresql database.
CRUD interface of users.
Configure Spring Security to use users from database. 

### List and Library Definition (designer)
A ContentDefinition entity will have the following:
1. Type (list|Library)
2. Name
3. List<Metadata> metadata

Metatada entity will have: 
1. Label
2. Description
3. Type  [Text, Number, List Name]

Designers will have CRUD interface to create metadata entities and ContentDefinition entities. Each new ContentDefinition will be a new List or Library where contributor will be able to add data content.


### List and Library contribution (contributor)
CRUD Rest interface now will allow users to add/remove/update list content or libary documents. Documents will be uploaded to the file sytem.

### Angular project initialization
Add docker to gradle. Docker compose should run now postgres and web application via docker.
Add a second angular hellow project, via node, when angular runs you should be able to access the hello page.
Add a gradle multimodule project, configure angular build via gradle add everything in docker.
Add in docker compose apache 2 and configure angular and web app to go through apache/port 80.
