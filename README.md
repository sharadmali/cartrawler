[![N|Solid](https://www.cartrawler.com/ct/wp-content/themes/cartrawler2017/assets/img/logo2017.png)](https://www.cartrawler.com/ct/)
# Car Utils 
Car Utils is a reusable library that contains functionlities to 
## Application Functionality

- Display cheapest of each car type
- Allow to pass filter by car type
- Sort by corporate (Corporate Cars are AVIS,BUDGET,ENTERPRISE,FIREFLY,HERTZ,SIXT,THRIFTY)
- Within each group sort low-to-high price

## User Guide
For this assignment, I have used a maven-based project with TestNG, Java, JSON Path library, extend report.
Folder structure

```sh
src/main/java:
    - CommonUtilityApplication.java
    - Base.java
    - ExcelUtility.java
	- Global.java
	- Report.java
src/test/java:
    - CarDetails_Test.java
    - Controller.java
```
 **CommonUtilityApplication Class**: Contains below application functions as requested in the assignment:
 - **getCheapestCarByType**: Return cheapest options of each car type· 
- **filterbyCarType**: Allow user to filter the car details by car type· 
- **sortbyCorporate**: Return the sorted data by corporates or venders· 
- **sortWithinGroupbyPrice**: Sort low-to-high price within each group by passing vendor name

 **Base Class**: Contains the BeforeTest, AfterTest, BeforeMethod, AfterMethod, DataProvider, and it's responsible for calling the methods for generating the Extend report and providing the test data to the test cases that are extended by test case class.
**Global Class**: Contains constants that will be used globally in the framework.
**Report Class**: It's responsible for generating the Extent report.
**ExcelUtility Class**: Reading the test cases from excel sheet (path: TestArtifacts/GroupControlFile.xlsx).

**CarDetails_Test Class**: It contains the actual test case to test the application functions.
**Controller Class**: It's responsible for reading the test cases from the excel sheet (Path:TestArtifacts/GroupControlFile.xlsx), generating the TestNG XML file at runtime, and running it. Using this class, we can execute the test cases from Jenkins.

## Test Execution
For executing the test case, we have two options:
1. Execute the testing.xml file (Have mentioned all the test cases here to be executed)
2. Execute the Controller Class (It reads test cases from the excel sheet, generate TestNG XML file at runtime, and run it)

Test execution report will be generated and found at below path:
```sh
CarTrawler/Reports/TestResultLog/Selenium_<TIMESTAMP>.html
```
## Improvements/Enhancements

1) What would you do to ensure a safe and controlled release process?
> In the release process lifecycle, the first essential step to ensure a safe release process is to have a Jenkins pipeline on the feature (local) branch based on a Version Control System (GitHub) that will run builds to run unit tests/ sonar cube build to ensure code quality before the Pull Request on the feature branch is peer-reviewed and approved to be merged with the main (master) branch. This step will also make sure that the code quality standards are met.
The next most important step as a Quality Assurance Engineer is to have an automation test suite ready with integration tests that will run on a pre-production environment with all other third-party systems along with the comprehensive QA tests automation (manual if required). Once the pre-production environment looks good, the deployment can be made to the production environment. At the same time runtime, risk mitigation plan is essential to roll back the build causing issues. 

2) Explain how you would release and publish versions of the library that teams can re-use
> As per the current design, the functionality I have implemented can be used by different teams as a maven dependency if they use a Java maven project.
With maven usage, we can create and publish the Maven package as a package or maven repository used by other maven java projects by adding as a dependency.
Also, to publish the latest version of the library, we can publish it as a new version of the library, and other projects that use the library can get the newest version once they update the package.

```sh
mvn compile
mvn package
mvn install
```
```sh
<dependency>
    <groupId>com.cartrawler</groupId>
    <artifactId>carutils</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency
```
>  Releasing new versions of the library for the teams to re-use is to release a new version of code with a maven update.mvn versions:use-latest-releases Another approach can be to use a JAR file and using as a dependency directly to the project.
To support this code to be re-used by diverse domains such as front-end code, teams using python, PHP, or any other programming language using enclosing these functions by publishing REST APIs would be one of the approaches that teams can use these functionalities irrespective of the technology used.  

3)  What tools are you using for this assessment? 
> I have used Maven for building the project and setting up the framework.TestNG is used for writing test cases. Also used Extend reports utility to generate stepwise test cases status reports. The tests I wrote cover the 98% code path of the implemented functionality. JSON Path is also used to quickly find the values from JSON nested structure without looping through each object.


## Tools/Libraries used

| Plugin | Version |
| ------ | ------ |
| Java | 1.8 |
| TestNG | 7.4.0 |
| Maven | 3.8.3 |
| Extend Report | 4.1.2 |
| Fillo | 1.18 |
| JSON Path | 2.6.0 |

