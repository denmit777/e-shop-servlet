package service.impl;

import dbase.UsersBase;
import model.User;
import service.UserService;

import java.util.List;
import java.util.NoSuchElementException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class.getName());

    private static final String FIELD_IS_EMPTY = "Login or password shouldn't be empty";
    private static final String INVALID_FIELD = "Login or password shouldn't be less than 4 symbols";
    private static final String USER_IS_PRESENT = "This user is already present";

    @Override
    public User save(String login, String password) {
        User user = new User();

        user.setId(getAll().size() + 1L);
        user.setLogin(login);
        user.setPassword(password);

        UsersBase base = UsersBase.getInstance();
        base.addUser(user);

        LOGGER.info("New user : " + user);

        return user;
    }

    @Override
    public User getById(Long id) {
        return getAll().stream()
                .filter(user -> id.equals(user.getId()))
                .findAny()
                .orElseThrow((() -> new NoSuchElementException(String.format("User with id %s not found", id))));
    }

    @Override
    public User getByLoginAndPassword(String login, String password) {
        return getAll().stream()
                .filter(user -> login.equals(user.getLogin())
                        && password.equals(user.getPassword()))
                .findAny()
                .orElse(new User("Unknown"));
    }

    @Override
    public List<User> getAll() {
        UsersBase base = UsersBase.getInstance();

        return base.getAllUsers();
    }

    @Override
    public boolean isInvalidUser(String login, String password) {
        return login.length() < 4 || password.length() < 4 || isUserPresent(login, password);
    }

    @Override
    public String invalidUser(String login, String password) {
        if (login.isEmpty() || password.isEmpty()) {
            LOGGER.error(FIELD_IS_EMPTY);

            return FIELD_IS_EMPTY;
        }

        if (isUserPresent(login, password)) {
            LOGGER.error(USER_IS_PRESENT);

            return USER_IS_PRESENT;
        }

        LOGGER.error(INVALID_FIELD);

        return INVALID_FIELD;
    }

    private boolean isUserPresent(String login, String password) {
        User user = getByLoginAndPassword(login, password);

        return user.getLogin().equals(login) && user.getPassword().equals(password);
    }
}
