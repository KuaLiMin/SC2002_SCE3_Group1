package foms.tools;

import java.util.HashMap;
import java.util.Map;

public class Authenticator {
    private final Map<String, String> userStore = new HashMap<>();

    public Authenticator() {
        userStore.put("admin", "adminPass");
        userStore.put("user", "userPass");
    }

    // @param username the username
    // @param password the password
    // @return true if the user is authenticated, false otherwise
     
    public boolean authenticate(String username, String password) {
        String storedPassword = userStore.get(username);
        return storedPassword != null && storedPassword.equals(password);
    }
}
