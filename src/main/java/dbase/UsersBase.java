package dbase;

import model.User;

import java.util.ArrayList;
import java.util.List;

public class UsersBase {

    private static final UsersBase instance = new UsersBase();

    private final List<User> users;

    private UsersBase() {
        users = new ArrayList<>();

        User user1 = new User(1L, "Den", "1234");
        User user2 = new User(2L, "Peter", "5678");
        User user3 = new User(3L, "Asya", "4321");
        User user4 = new User(4L, "Sveta", "8765");
        User user5 = new User(5L, "Jimmy", "1111");

        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);
    }

    public static UsersBase getInstance() {
        return instance;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public List<User> getAllUsers() {
        return users;
    }
}

