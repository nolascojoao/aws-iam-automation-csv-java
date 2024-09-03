package com.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import software.amazon.awssdk.services.iam.IamClient;
import software.amazon.awssdk.services.iam.model.AddUserToGroupRequest;
import software.amazon.awssdk.services.iam.model.CreateLoginProfileRequest;
import software.amazon.awssdk.services.iam.model.CreateLoginProfileResponse;
import software.amazon.awssdk.services.iam.model.CreateUserRequest;
import software.amazon.awssdk.services.iam.model.CreateUserResponse;
import software.amazon.awssdk.services.iam.model.IamException;

public class CreateIAMUsers {

    public List<String[]> readUsersAndGroupsFromCSV(String csvFilePath) {
        List<String[]> userDetailsList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                if (values.length == 3) {
                    userDetailsList.add(values);
                } else {
                    System.err.println("Skipping invalid line: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        }
        return userDetailsList;
    }

    public void createIAMUsers(IamClient iamClient, List<String[]> userDetailsList) {
        for (String[] userDetails : userDetailsList) {
            String username = userDetails[0];
            String password = userDetails[1];
            String groupName = userDetails[2];
            createIAMUserWithPassword(iamClient, username, password, groupName);
        }
    }

    public void createIAMUserWithPassword(IamClient iamClient, String username, String password, String groupName) {
        try {
            CreateUserRequest createUserRequest = CreateUserRequest.builder()
                    .userName(username)
                    .build();
            CreateUserResponse createUserResponse = iamClient.createUser(createUserRequest);
            System.out.println("Successfully created user: " + createUserResponse.user().userName());
            setIAMUserPassword(iamClient, username, password);
            addUserToGroup(iamClient, username, groupName);
        } catch (IamException e) {
            System.err.println("Failed to create user " + username + ": " + e.getMessage());
        }
    }

    public void setIAMUserPassword(IamClient iamClient, String username, String password) {
        try {
            CreateLoginProfileRequest createLoginProfileRequest = CreateLoginProfileRequest.builder()
                    .userName(username)
                    .password(password)
                    .passwordResetRequired(true)  // true if you want the user to reset the password on first login
                    .build();
            CreateLoginProfileResponse createLoginProfileResponse = iamClient.createLoginProfile(createLoginProfileRequest);
            System.out.println("Successfully set password for user: " + createLoginProfileResponse.loginProfile().userName());
        } catch (IamException e) {
            System.err.println("Failed to set password for user " + username + ": " + e.getMessage());
        }
    }

    public void addUserToGroup(IamClient iamClient, String username, String groupName) {
        try {
            AddUserToGroupRequest addUserToGroupRequest = AddUserToGroupRequest.builder()
                    .userName(username)
                    .groupName(groupName)
                    .build();
            iamClient.addUserToGroup(addUserToGroupRequest);
            System.out.println("Successfully added user: " + username + " to group: " + groupName);
        } catch (IamException e) {
            System.err.println("Failed to add user " + username + " to group " + groupName + ": " + e.getMessage());
        }
    }
}
