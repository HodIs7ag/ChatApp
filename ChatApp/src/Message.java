public class Message {
    private User sender;
    private MessageReceiver receiver;
    private String content;
    private boolean isGroup;

    //constructor for private messages
    public Message(User sender, MessageReceiver receiver, String content) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
    }

    //constructor for group messages
    public Message(User sender, MessageReceiver receiver, String content, boolean isGroup) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.isGroup = isGroup;
    }



    public User getSender() {
        return sender;
    }

    public MessageReceiver getReceiver() {
        return receiver;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString(){
        if (isGroup)
            return "["+getReceiver().getName()+"]" + "["+ getSender().getName() + "]: " + getContent(); //Message from group
        else
            return "["+ getSender().getName() + "]: " + getContent();  //Private message

    }

}
