import java.util.ArrayList;
import java.util.List;

public class Group extends ChatEntity implements MessageReceiver {
    private List<User> members;
    //private String name;

    public Group(String name) {
        super(name);
        this.name = name;
        this.members = new ArrayList<>();
    }


    @Override
    public void receiveMessage(ChatEntity sender, Message message) {
        for (User user : members){
            if (!user.equals(sender)){
                user.addToInbox(message);
            }
        }
    }

    public void addMember(User member) {
        if (!members.contains(member)) {
            members.add(member);
            System.out.println("[" + getName() + "]: Added member: " + member.getName());
        }
    }

    public void removeMember(User member) {
        if (members.contains(member)) {
            members.remove(member);
            System.out.println("[" + getName() + "]: Removed member: " + member.getName());
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void displayInfo() {
        System.out.println("Group: " + getName());
        System.out.println("Members: ");
        for (User member : members){
            System.out.println("   - "+ member);
        }
        //TODO: print the memebers
    }
}
