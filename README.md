# CSC471 Prj5

LOAD AS A MAVEN PROJECT! The dependencies are super necessary!

The necessary sql scripts are in the .sql file in the root directory. 

The password for the DB is "test" and if you need to change that you can find application.properties in src/main/resources/application.properties where the DB name is also conveniently located, "csc471." THIS IS A MAVEN PROJECT MAKE SURE YOU LOAD ALL THE DEPENDENCIES AND THEN BUILD

Built with Java 17
# Known Errors
If an employee is assigned to a department and then deleted, that department's employee count does not go down

Should be a simple fix of finding all the departments the employee is in with a filter and then decrement those employee counts.
Will be fixed later.

Please let me know if you find any other errors!