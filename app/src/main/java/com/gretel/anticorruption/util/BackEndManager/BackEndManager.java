package com.gretel.anticorruption.util.BackEndManager;

import com.gretel.anticorruption.model.Agent.Agent;
import com.gretel.anticorruption.model.Agent.Report;
import com.gretel.anticorruption.model.Agent.User;

public interface BackEndManager {

    /**
     * This method adds an user object to the database
     * @param a specifies the user to be uploaded
     * @param loginType specifies the medium user used to login.
     */
    void addUser(Agent a, String loginType);

    /**
     * This method is used to retrieve an user object from the database
     * @param loginType specifies the type user used to login
     * @param id specifies the unique id of the user in the login type
     */
    void getUser(String loginType, String id);

    /**
     * This method is used to edit the current user with a new user
     * @param user specifies the new user
     */
    void editUser(User user);

    /**
     * This method adds a new report to the database
     * @param report specifies the report object
     * @return reports the id assigned to the report by firebase
     */
    String addReport(Report report);

    /**
     * This method connects the newly added report to the database of the user
     * @param u specifies the user who created the report
     * @param reportID specifies the reportID of the user
     */
    void addReportToUser(User u, String reportID);

    /**
     * This method assigns a negative timestamp to every report
     * @param reportID specifies the reportID of the user
     */
    void addTimestamp(String reportID);

}
