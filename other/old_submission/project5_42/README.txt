--- CS122B | Group 42: Project 5 README ---

***** IMPORTANT: Please rename "fabflix_webapp.war" to "fabflix.war" before deploying or else it will not work correctly. *****

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
	
	* Please note that because tail follows more than one file, updates to the log files are slow.
		-> You can get a realtime update of the files by just CTRL+C, then re-running the command. It will reload the file and show the latest updates.
		-> I do not know why it behaves like this. Thank you for your understanding.

-> For Original instance, you can view the configuration of Apache2 load balancer at the following file:
	/etc/apache2/sites-available/000-default.conf
	
--- Instance Credentials --

-> For ALL THREE instances:
	-> SSH Credentials
		ID: ubuntu
		PW: (The included private key .pem inside the ZIP)
	
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
		-> Mainly, fabflix_webapp.war\WEB-INF\classes\general_helpers\DatabaseHelper.java, in the constructor function, and in openConnection()/getDataSource()!
			-> getDataSource(), if given true, returns the pool for the Master DB for writes. Otherwise it returns the localhost DB pool for reads. 
				-> Pretty simple.

--- Notes Related To Performance Measurement in Task 3 ---

	Report can be found at http://35.167.240.46:8080/fabflix/jsp/jmeter_report.jsp, as well as included in the .zip in jmeter_report.html
	
	The python script "GetTimeAvgs.py" located in "python-script" opens the log file "log.txt" and appends the averages to 
	"log_avgs.txt". It also empties "log.txt". The script must be placed in the same directory as "log.txt". 
	To run the script, simply type the following command:
											
											python GetTimeAvgs.py "Comment"
	
	where "Comment" is the comment associated with the time averages. The script appends two lines to "log_avgs.txt". 
	The first is "Comment:" and the second is the averages (TS avg: ## TJ avg: ##).
	
	The script is used after running queries in the search servelet ("/fabflix/movielist?..."). The search servlet appends the
	values of TS and TJ of each query to "log.txt". Running the script produces the averages and resets log.txt by emptying the
	file.
	