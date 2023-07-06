import java.util.ArrayList;
import java.util.List;

public class User extends ChatEntity implements MessageReceiver {
    private String password;
    private List<Group> groups;
    private List<Message> inbox;
    private Message newMessage;

    public User(String username, String password) {
        super(username);
        this.password = password;
        this.groups = new ArrayList<>();
        this.inbox = new ArrayList<>();
    }



    //instead of using getPassword it's better to verify it inside the class to prevent external access (information hiding)
    public boolean verifyPassword(String insertedPassword) {
        if (this.password.equals(insertedPassword))
            return true;
        else
            return false;
    }


    //sendMessage to User or Group
    public void sendMessage(MessageReceiver receiver, String message) {
        Message newMessage;
        if (receiver.getClass() == Group.class)
            newMessage = new Message(this, receiver, message, true);
        else
            newMessage = new Message(this, receiver, message);
        receiver.receiveMessage(this, newMessage);
    }


    @Override
    public void receiveMessage(ChatEntity sender, Message newMessage) {
        this.addToInbox(newMessage);
    }

    public void addToInbox(Message message) {
        inbox.add(message);
    }

    public void openInbox() {
        System.out.println("\n********************");
        System.out.println("Inbox for " + getName() + ":");
        if (!inbox.isEmpty()) {
            for (Message message : inbox) {
                System.out.println(message);
            }
        } else {
            System.out.println("Empty inbox");
        }
        System.out.println("********************\n");
        inbox.clear();    //to clear the inbox after opening
    }

    public void joinGroup(Group group) {
        if (!groups.contains(group)) {
            groups.add(group);
            group.addMember(this);
            this.newMessage = new Message(this, group, " joined the group", true);
            group.receiveMessage(group, newMessage);
        }
    }

    public void leaveGroup(Group group) {
        if (groups.contains(group)) {
            groups.remove(group);
            group.removeMember(this);
            this.newMessage = new Message(this, group, " left the group", true);
            group.receiveMessage(group, newMessage);
        }
    }


    @Override
    public void displayInfo() {
        System.out.println("User: " + getName());
        System.out.println("joined groups: ");
        for (Group group : groups)
            System.out.println(" - " + group);

    }
}
