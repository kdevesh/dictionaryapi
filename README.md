# **Dictionary API:**
This dictionary api has been created using JAVA and Play Framework and H2 database.


**The endpoints are:**

POST /save

GET  /search/:word

JUnit Test Cases are also included.
To see the coverage report go to folder coverage and open index.html

To run the test cases again comment the play.evolutions block in application.conf inside conf folder
play.evolutions{
  db.default.enabled = true
}

Requirements to run this project :
i) JAVA 8 or above
ii) sbt (build tool for play framework and for dependency management)
Optional if you want to import code in any IDE use Intellij (install scala plugin in Intellij)

How To Run the Application:

**METHOD 1:**

•	I have packaged the application and is present in the folder as zipped folder (dictionary-api-1.0-SNAPSHOT.zip)

•	Unzip the folder(dictionary-api-1.0-SNAPSHOT.zip) go inside bin folder you will see to scripts (bash and .bat) for linux and windows respectively.

•	Run the script based on your OS like below.

1.	dictionary-api.bat -Dplay.http.secret.key=ad31779d4ee49d5ad5162bf1429c32e2e9933f3b (**For Windows**)

2.	sh dictionary-api -Dplay.http.secret.key=ad31779d4ee49d5ad5162bf1429c32e2e9933f3b (**For Linux**)

•	After running the script with above parameters application will start serving at localhost:9000   

•	You can use localhost:9000/save endpoint to save the file

•	And localhost:9000/search/:word to search for word                                                                                 

**METHOD 2:**
Go inside the project folder:
Type sbt update then sbt compile and finally sbt run the application will start running at port 9000
