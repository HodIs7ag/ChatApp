public abstract class ChatEntity {
    protected String name;

    public ChatEntity(String name) {
        this.name = name;
    }


    public abstract void displayInfo();

    public String getName() {
        return name;
    }
}

