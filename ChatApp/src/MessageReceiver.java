public interface MessageReceiver {
    void receiveMessage(ChatEntity sender, Message message);
    String getName();
}

// it's interface because we want to access only receiveMessage method and getName
// the interface is implemented in both User and group classes overriden to handle distributing the message