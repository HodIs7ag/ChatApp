public class Main {
    public static void main(String[] args) {
        ChatApp chatApp = new ChatApp();
        AppUI appUI = new AppUI(chatApp);

        //just for testing
        User user1 = new User("user1","1234");
        chatApp.registerUser(user1);
        User user2 = new User("user2","1234");
        chatApp.registerUser(user2);

        appUI.run();
    }
}
