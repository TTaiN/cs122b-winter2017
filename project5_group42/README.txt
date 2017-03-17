--- CS122B | Group 42: Project 5 README ---

-> Original AWS Instance (#1): http://35.167.240.46/fabflix
	-> This instance is the load balancer.
		Public IP: 35.167.240.46
	
-> Master AWS Instance (#2): 
	-> This instance is the master MySQL host.
		Public IP: 52.39.247.33
		Private IP: 172.31.25.145

-> Slave AWS Instance (#3):
	-> This instance is the slave MySQL host.
		Public IP: 52.36.0.76
		Private IP: 172.31.24.100
		
--- Linux Information --

-> For Master/Slave instances, you can tail the request logs of tomcat by running the following command:
	tail -f /opt/tomcat/logs/*.txt

-> For Original instance, you can view the configuration of Apache2 load balancer at the following file:
	/etc/apache2/sites-available/000-default.conf
	
--- Instance Credentials --

-> For ALL THREE instances:
	-> SSH Credentials
		ID: ubuntu
		PW: (The included private key .pem inside the ZIP)
		
		ID: classta
		PW: cs122bgroup42
	
	-> Tomcat Manager Information
		ID: tomcat (OR) classta
		PW: cs122bgroup42
		
	-> MySQL Credentials
		ID: root
		PW: cs122b
		
		ID: cs122b
		PW: cs122bgroup42

--- Additional Information ---

-> All MySQL writes in the /fabflix application is sent to the master, which then updates the slave.
	-> There are two Resources provided in the META-INF/context.xml file, one for localhost (name="jdbc/MovieDB") and one for the master DB (name="jdbc/MovieDBMaster").
	-> The implementation for write requests being sent to master only (as well as connection pooling) can be seen in the .java source files.
		-> Mainly, fabflix_webapp.war\WEB-INF\classes\general_helpers\DatabaseHelper.java, in the constructor function, openConnection() and getDataSource()!
			-> getDataSource(), if given true, returns the pool for the Master DB for writes. Otherwise it returns the localhost DB pool for reads. 
				-> Pretty simple.
	