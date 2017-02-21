Note: Due to the way Eclipse exports .WAR, all sources (*.java files) can be found with the .class files, in WEB-INF/classes.

-> Please change .war filename to Fabflix.war.

AWS Tomcat User Credentials:
	*URL: https://35.167.240.46:8443/
	*USER: tomcat OR classta
	*PASSWORD: cs122bgroup42

AWS Database User Credentials:
	*(IP - 35.167.240.46) 
	*(User - cs122b)
	*(Password - cs122bgroup42) 
	*(DB - moviedb)
	-> Note: testuser,testpass also exists if the above does not work.
	
Fabflix Employee Login Information:
	-> insert into employees values ('classta@email.edu', 'classta', 'TA CS122B');

How to compile & run Fabflix web project:
	(Note: We assume you are using Eclipse.) How to compile java sources: place all .java and package folders into /src folder for Java resources. Ensure that you add
	JDBC jar to your Java build path, since some classes require it. Ensure that you include all web dependencies.
	
	To link the WebContent, place all contents inside the WebContent folder into the web root folder for your project. If the name of your web root folder is WebContent, simply replace WebContent folder with the one provided.
	
	The database credentials are: (IP - 35.167.240.46) (User - cs122b) (Password - cs122bgroup42) (DB - moviedb).
	
	To create the .war, after following above instructions export the classes and sources as a .war.
	
	To deploy the .war, login to Tomcat Application Manager, select the .war provided rename it to "Fabflix.war" and deploy it.
	
	-> Note: It is easiest to just deploy the .war we submitted. The sources can be found in WEB-INF/src.
	
How to compile & run XML parser:
	See README.txt in XML parsing zip we submitted.