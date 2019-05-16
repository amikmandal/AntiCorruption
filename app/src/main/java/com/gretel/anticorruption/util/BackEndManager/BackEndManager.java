package com.gretel.anticorruption.util.BackEndManager;

import com.gretel.anticorruption.model.Agent.Agent;
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

}
