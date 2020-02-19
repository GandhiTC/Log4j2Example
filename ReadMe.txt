Log4j2Example uses Log4j v2 to create custom HTML logs as well as editing logs asynchronously.

Logs are separated into 2 categories:
	- Trace, where everything is logged
	- Errors, where only the errors are logged
	
Sorts logs/directories by:
	- Group Id
	- Artifact Id
	- Year
	- Month
	- Date (2-digit day of month, 3-letter month, 4-digit year)

Outputs:
	- Trace logs to console
	- Trace logs to trace.log  & trace.html
	- Error logs to errors.log & errors.html

Custom HTML output
	- Removed text at top of file
	- Changed the Time column to match console output
	- Changed Thread column to show Thread ID instead of Thread Name



---------------------------------------------------------------------------------------------------------------------------------------
CustomHtmlLayout.java
---------------------------------------------------------------------------------------------------------------------------------------
This file edits the layout of the logs in HTML format.
Edit the "toSerializable" method to change the columns/values.



---------------------------------------------------------------------------------------------------------------------------------------
RunAsJavaAppTest.java
---------------------------------------------------------------------------------------------------------------------------------------
Run this file as a java application.
By default, it will run a headless instance of Chrome browser, 
this can be changed via "Program Arguments" in Run Configurations (see: Logging Asynchronously)



---------------------------------------------------------------------------------------------------------------------------------------
RunTestngXMLTest.java
---------------------------------------------------------------------------------------------------------------------------------------
To run this test, run the file "testng.xml" as a TestNG Suite.
By default, it will run a headless instance of Firefox browser, 
this can be changed via the "browserName" parameter in the "testng.xml" file



---------------------------------------------------------------------------------------------------------------------------------------
Browser Options
---------------------------------------------------------------------------------------------------------------------------------------
"firefox"
"firefox headless"
"chrome"
"chrome headless"
"ie"
"edge"

Place copies of your WebDriver exe files  in:  ./src/test/resources/drivers/



---------------------------------------------------------------------------------------------------------------------------------------
Logging Asynchronously
---------------------------------------------------------------------------------------------------------------------------------------
Right-click RunAsJavaAppTest.java -> Run As -> Run Configurations
	- In left pane menu
		- Select Java Application -> New Configuration
	- In main/right pane -> 
		- At the top, give it a name, something like:  RunAsJavaAppTest
		- Main tab
			- Project   :  Log4j2Example
			- Main class:  com.github.GandhiTC.java.Log4j2Example.tests.RunAsJavaAppTest
		- Arguments tab
			- Program arguments:  Select one from the Browser Options section
			- VM arguments     :  -Dlog4j2.contextSelector=org.apache.logging.log4j.core.async.AsyncLoggerContextSelector 
					      -DAsyncLogger.WaitStrategy=busyspin 
					      -Dlog4j2.enable.threadlocals=true 
					      -Dlog4j2.enable.direct.encoders=true


Right-click testng.xml -> Run As -> Run Configurations
In left pane menu
	- Select TestNG -> New Configuration
In main/right pane -> 
	- At the top, give it a name, something like:  Log4j2Example_testng.xml
	- Main tab
		- Project   :  Log4j2Example
		- Suite     :  click on "browse" -> select the "testng.xml" file in this project's directory.
	- Arguments tab
		- Program arguments:  Leave blank, change the "browserName" parameter in testng.xml file instead.
		- VM arguments     :  -Dlog4j2.contextSelector=org.apache.logging.log4j.core.async.AsyncLoggerContextSelector 
				      -DAsyncLogger.WaitStrategy=busyspin 
				      -Dlog4j2.enable.threadlocals=true 
				      -Dlog4j2.enable.direct.encoders=true


pom.xml
	- "argLine" property has been added to the "properties" section
	- LMAX Disruptor added to dependencies



