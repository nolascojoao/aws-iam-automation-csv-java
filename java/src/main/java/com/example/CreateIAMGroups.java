package com.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import software.amazon.awssdk.services.iam.IamClient;
import software.amazon.awssdk.services.iam.model.AttachGroupPolicyRequest;
import software.amazon.awssdk.services.iam.model.CreateGroupRequest;
import software.amazon.awssdk.services.iam.model.CreateGroupResponse;
import software.amazon.awssdk.services.iam.model.IamException;

public class CreateIAMGroups {

    public List<String[]> readGroupsAndPoliciesFromCSV(String csvFilePath) {
        List<String[]> groupPolicyList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                if (values.length == 2) {
                    groupPolicyList.add(values);
                } else {
                    System.err.println("Skipping invalid line: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        }
        return groupPolicyList;
    }

    public void createIAMGroupsAndAttachPolicies(IamClient iamClient, List<String[]> groupPolicyList) {
        for (String[] groupPolicy : groupPolicyList) {
            String groupname = groupPolicy[0];
            String policyArn = groupPolicy[1];
            createIAMGroupAndAttachPolicy(iamClient, groupname, policyArn);
        }
    }

    public void createIAMGroupAndAttachPolicy(IamClient iamClient, String groupname, String policyArn) {
        try {
            CreateGroupRequest createGroupRequest = CreateGroupRequest.builder()
                    .groupName(groupname)
                    .build();
            CreateGroupResponse createGroupResponse = iamClient.createGroup(createGroupRequest);
            System.out.println("Successfully created group: " + createGroupResponse.group().groupName());
            attachPolicyToGroup(iamClient, groupname, policyArn);
        } catch (IamException e) {
            System.err.println("Failed to create group or attach policy: " + e.getMessage());
        }
    }

    public void attachPolicyToGroup(IamClient iamClient, String groupname, String policyArn) {
        try {
            AttachGroupPolicyRequest attachGroupPolicyRequest = AttachGroupPolicyRequest.builder()
                    .groupName(groupname)
                    .policyArn(policyArn)
                    .build();
            iamClient.attachGroupPolicy(attachGroupPolicyRequest);
            System.out.println("Successfully attached policy: " + policyArn + " to group: " + groupname);
        } catch (IamException e) {
            System.err.println("Failed to attach policy " + policyArn + " to group " + groupname + ": " + e.getMessage());
        }
    }
}
