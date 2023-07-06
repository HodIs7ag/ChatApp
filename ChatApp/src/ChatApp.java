import java.util.ArrayList;
import java.util.List;

public class ChatApp {
    private List<User> users;
    private List<Group> groups;

    public ChatApp() {
        this.users = new ArrayList<>();
        this.groups = new ArrayList<>();
    }

    public void registerUser(User user) {
        if (!users.contains(user)) {
            users.add(user);
            System.out.println("Registered user: " + user.getName());
        }
    }



    public void createGroup(String name, User user) {
        Group group = new Group(name);
        groups.add(group);
        group.addMember(user);
        System.out.println("Created group: " + name);
    }

    public void displayUsers() {
        System.out.println("Registered Users:");
        for (User user : users) {
            System.out.println(user.getName());
        }
    }

    public void displayGroups() {
        System.out.println("Created Groups:");
        for (Group group : groups) {
            group.displayInfo();
        }
    }

    public Group getGroupByName(String name) {
        for (Group group : groups) {
            if (group.getName().equals(name)) {
                return group;
            }
        }
        return null;
    }

    public User loginUser(String username, String password) {
        for (User user : users) {
            //verify username and password
            if (user.getName().equals(username) && user.verifyPassword(password)) {
                System.out.println("User logged in: " + username);
                return user;
            }
        }
        return null;
    }

    public User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getName().equals(username)) {
                return user;
            }
        }
        return null;
    }
}
