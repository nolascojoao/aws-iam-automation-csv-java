# AWS IAM Automation with CSV and Java

## Objective
Automate AWS IAM tasks using AWS SDK for Java:
- Create Groups based on the `groups.csv`
- Attach Policies based on `groups.csv`
- Create Users based on the `users.csv` 
- Assign Users to Groups based on the `users.csv`

---

## Project Structure
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
---

## Prerequisites
- Java 1.8+
- Maven 3.9.9+
- AWS CLI

---

## CSV Files
Customize users and groups through CSV files:
- `users.csv`: Specifies the username, password and the group for each user.

<p align="center">
  <img src="https://github.com/user-attachments/assets/d98bbc22-f2e7-4b07-8878-4781661e6cc5" alt="image"/>
</p>

- `groups.csv` Defines the group name and associated policy ARN.

<p align="center">
  <img src="https://github.com/user-attachments/assets/c1493c45-ce3f-4b8e-89d6-00722ad59fb2" alt="image"/>
</p>

- Ensure the CSV files are properly comma-separated (,).

---

## Step-by-Step Execution
#### Using an IDE
1. Clone the repository
```bash
git clone git@github.com:nolascojoao/aws-iam-automation-csv-java.git
```
2. Open the project in Eclipse or any Java IDE.
3. Run `Main.java` to execute the program

#### Using Maven
1. Navigate to the project's root directory.
2. Build and execute the project:
```bash
mvn clean install
mvn exec:java -Dexec.mainClass="com.example.Main"
```

---

#### Console Output

<p align="center">
  <img src="https://github.com/user-attachments/assets/7b30943a-2ae2-4cf3-a6ff-a55badac17c9" alt="image"/>
</p>
