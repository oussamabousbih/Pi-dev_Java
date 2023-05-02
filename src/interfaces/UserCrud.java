package interfaces;
import entities.User;

import java.util.List;

public interface UserCrud {


    public boolean createUser(User u);

    public boolean updateUser(User u,String emailrech);

    public boolean deleteUser(String email);

    public User afficherUser(String email);

    public User login(String email, String password);

}
