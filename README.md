# GENERIC-TEAM
ICS 372 GROUP PROJECTS

# AS DISCUSSED 
* Eclipse IDE Will be used by everyone for consistency
* No Graphical User Interface(GUI)
* The lastest Java JDK (https://www.oracle.com/technetwork/java/javase/downloads/jdk13-downloads-5672538.html) will be used
* Discord will be used for communication

# LAUNCH IN ECLIPSE
* File > Open Projects from File System... > Directory >
* Choose your directory of your project

# DEV WORKFLOW
* Fork your own repository
* Clone your fork
* add an upstream to the main repo so you can pull changes from the main repo to your fork (git remote add upstream https://github.com/Bossmanswift/GENERIC-TEAM.git)
* Commit to your fork
* Go to the main repository (https://github.com/Bossmanswift/GENERIC-TEAM)
* Click on Pull Request Tab
* Click on the "New pull request"
* Click on "compare across forks"
* Select your changes from your fork into the main branch

# GET CHANGES FROM MAIN REPO INTO YOUR FORK
* since you added an upstream to main repo (run: git pull upstream master)

# ADD WEBHOOK TO YOUR REPOSITORY SO ALL MEMBERS ARE NOTIFIED WHEN NEW COMMITS ARE PUSHED
* github repository > Settings tab > Webhooks > Add Webhooks
* Payload URL: the-URL-is-found-inside-readme-channel
* Content Type: application/json
* Select: Send me Everything
* Click Add webhook

# EXPLAINATION OF HOW JSON WORKS IN JAVA
* https://www.geeksforgeeks.org/parse-json-java/
# UNIT TESTING AAA FORMAT LINK
* http://wiki.c2.com/?ArrangeActAssert
# INFORMATION REGARDING SECOND ITERATION
* All commits/pull requests for this iteration will go to the master2 branch
* GUI will be used. I believe we all have Java 8. Java FX8 will be used in building the GUI
* We will be adding XML parsing to the software
* We will be adding a name attribute to Warehouse class
* Although we already have good exception handling mechanisms in our software, we would need rigorous testing of those mechanisms 
  for this iteration, especially for I/0 operations
* Coding conventions wiki/document will be created
* JUnit testing will be incorporated into the software
* Class and sequence diagrams should be generated as we progress. This facilitates a clear 
  understanding of where we are and where we are going
* Communication via discord is crucial to this team project so please try to be active
