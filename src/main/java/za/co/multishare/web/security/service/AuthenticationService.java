package za.co.multishare.web.security.service;

import java.util.Map;

public interface AuthenticationService {

    /**
     * Performs the authentication of the user by using the username and
     * pasword then returns the users information and the token
     * @param username - user's username which will be an email address
     * @param password - user's logging password
     * @return a map with users informationa and token
     */
    Map<String, String> authenticate(String username, String password);
}
