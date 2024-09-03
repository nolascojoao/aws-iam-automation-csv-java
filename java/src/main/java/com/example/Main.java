package com.example;

import java.util.List;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.iam.IamClient;

public class Main {
    public static void main(String[] args) {

        IamClient iamClient = IamClient.builder()
                .region(Region.AWS_GLOBAL) // IAM is a global service
                .build();

        CreateIAMGroups iamManager = new CreateIAMGroups();
        CreateIAMUsers createIAMUsers = new CreateIAMUsers();

        String groupsCsvFilePath = "src/main/resources/groups.csv";
        String usersCsvFilePath = "src/main/resources/users.csv";

        List<String[]> groupPolicyList = iamManager.readGroupsAndPoliciesFromCSV(groupsCsvFilePath);
        iamManager.createIAMGroupsAndAttachPolicies(iamClient, groupPolicyList);

        List<String[]> userDetailsList = createIAMUsers.readUsersAndGroupsFromCSV(usersCsvFilePath);
        createIAMUsers.createIAMUsers(iamClient, userDetailsList);

        iamClient.close();
    }
}
