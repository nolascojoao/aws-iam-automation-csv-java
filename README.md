# AWS IAM Automation with CSV and Java

### Objective
The goal of this project is to automate the following tasks in AWS IAM using the AWS SDK for Java:
- Create Groups based on the groups.csv 
- Attach Policies to the Groups
- Create Users based on the users.csv 
- Assign Users to Groups based on the input from users.csv

### Project Structure
```bash
src/
├── com/
│   └── example/
│       ├── CreateIAMGroups.java
│       ├── CreateIAMUsers.java    
│       └── Main.java
├── resources/
│   ├── logback.xml
│   └── csv/
│       ├── groups.csv
│       └── users.csv
```
### Prerequisites
- Java 1.8+
- Maven 3.9.9+
- AWS CLI installed and configured with an authenticated AWS user

### CSV Files
The CSV files allow for easy customization of users and groups

- `users.csv` defines the **username**, **password** and the **group** to which the user will be assigned

<p align="center">
  <img src="https://github.com/user-attachments/assets/d98bbc22-f2e7-4b07-8878-4781661e6cc5" alt="image"/>
</p>

- `groups.csv` defines the **group name** and the associated **policy ARN** that will be attached to the group

<p align="center">
  <img src="https://github.com/user-attachments/assets/c1493c45-ce3f-4b8e-89d6-00722ad59fb2" alt="image"/>
</p>

**Ensure that the CSV files are properly separated by commas (,)**

### Step-by-Step Execution
#### Using an IDE
1. Clone the repository
```bash
git clone https://github.com/your-repository/aws-iam-automation-csv-java.git
```
2. Open the project in Eclipse or any Java IDE of your choice
3. Run the `Main.java` class from the IDE to execute the program

#### Using Maven
1. Navigate to the project's root folder in your terminal
2. Build the project and execute the program:
```bash
mvn clean install
mvn exec:java -Dexec.mainClass="com.example.Main"
```
#### Console Output

<p align="center">
  <img src="https://github.com/user-attachments/assets/7b30943a-2ae2-4cf3-a6ff-a55badac17c9" alt="image"/>
</p>
