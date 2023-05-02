package session;

import entities.User;

public class    UserSession extends User {

    private static UserSession instance;
    private User user;



    private UserSession(User user) {
        this.user = user;
    }

    public static UserSession getSession() {
        return instance;
    }

    public static void setSession(User user) {
        instance = new UserSession(user);
    }

    public User getUser() {
        return user;
    }

    public void clearSession() {
        instance = null;
    }

}
