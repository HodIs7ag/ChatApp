import java.util.InputMismatchException;
import java.util.Scanner;

public class AppUI {
    private final Scanner scanner;
    private final ChatApp chatApp;

    public AppUI(ChatApp chatApp) {
        this.scanner = new Scanner(System.in);
        this.chatApp = chatApp;
    }

    public void run() {
        System.out.println("Welcome to the Chat App!");

        while (true) {
            System.out.println("\n********************************************************");
            System.out.println("Choose an option:");
            System.out.println("1. Login");
            System.out.println("2. Create a new user");
            System.out.println("3. Exit");
            System.out.print("Enter your choice (1, 2, or 3): ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1:
                        User user = loginUser();
                        if (user != null) {
                            performChatAppOperations(user);
                        }
                        break;
                    case 2:
                        createUser();
                        break;
                    case 3:
                        System.out.println("Exiting the program.");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please choose a number (1-3).");
                scanner.nextLine(); // Consume the invalid input
            }
        }
    }

    private User loginUser() {
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();
        User user = chatApp.loginUser(username, password);
        if (user == null) {
            System.out.println("Invalid username or password.");
        }
        return user;
    }

    private void createUser() {
        System.out.print("Enter a username: ");
        String username = scanner.nextLine();
        System.out.print("Enter a password: ");
        String password = scanner.nextLine();
        User user = new User(username, password);
        chatApp.registerUser(user);
        System.out.println("User created successfully.");
    }

    private void performChatAppOperations(User user) {
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("\n********************************************************");
            System.out.println("Choose an option:");
            System.out.println("1. Send a message");
            System.out.println("2. Display registered users");
            System.out.println("3. Groups");
            System.out.println("4. Open Inbox");
            System.out.println("5. DisplayInfo");
            System.out.println("6. Logout");
            System.out.print("Enter your choice (1-5): ");


            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
                System.out.println("********************************************************\n");
                switch (choice) {
                    case 1:
                        sendMessageToUser(user);
                        break;
                    case 2:
                        chatApp.displayUsers();
                        break;
                    case 3:
                        groupOperations(user);
                        break;
                    case 4:
                        user.openInbox();
                        break;
                    case 5:
                        user.displayInfo();
                        break;
                    case 6:
                        System.out.println("Logging out...");
                        loggedIn = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please choose a number (1-5).");
                scanner.nextLine(); // Consume the invalid input
            }
        }
    }

    private void sendMessageToUser(User user) {
        System.out.print("Enter the recipient's username: ");
        String recipientName = scanner.nextLine();
        User recipient = chatApp.getUserByUsername(recipientName);
        if (recipient == null) {
            System.out.println("User not found.");
            return;
        }

        System.out.println("Enter your messages (enter '/back' to exit):");
        while (true) {
            String message = scanner.nextLine();
            if (message.equals("/back")) {
                break;
            }
            user.sendMessage(recipient, message);
        }

    }

    private void groupOperations(User user) {
        boolean inGroupMenu = true;
        while (inGroupMenu) {
            System.out.println("\n********************************************************");
            System.out.println("Group Operations:");
            System.out.println("1. Create a group");
            System.out.println("2. Join a group");
            System.out.println("3. Leave a group");
            System.out.println("4. Display groups Info");
            System.out.println("5. Send a message to a group");
            System.out.println("6. Back to Main Menu");
            System.out.print("Enter your choice (1-6): ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
                System.out.println("********************************************************\n");
                switch (choice) {
                    case 1:
                        System.out.print("Enter the group name: ");
                        String groupName = scanner.nextLine();
                        chatApp.createGroup(groupName, user);
                        break;
                    case 2:
                        System.out.print("Enter the group name to join: ");
                        String joinGroupName = scanner.nextLine();
                        Group joinGroup = chatApp.getGroupByName(joinGroupName);
                        if (joinGroup == null) {
                            System.out.println("Group not found.");
                        } else {
                            user.joinGroup(joinGroup);
                        }
                        break;
                    case 3:
                        System.out.print("Enter the group name to leave: ");
                        String leaveGroupName = scanner.nextLine();
                        Group leaveGroup = chatApp.getGroupByName(leaveGroupName);
                        if (leaveGroup == null) {
                            System.out.println("Group not found.");
                        } else {
                            user.leaveGroup(leaveGroup);
                        }
                        break;
                    case 4:
                        chatApp.displayGroups();
                        break;
                    case 5:
                        System.out.print("Enter the group name: ");
                        String groupNameToMessage = scanner.nextLine();
                        System.out.println("********************************************************\n");
                        Group messageGroup = chatApp.getGroupByName(groupNameToMessage);
                        if (messageGroup == null) {
                            System.out.println("Group not found.");
                        } else {
                            System.out.println("Enter your messages (enter '/back' to exit):");
                            while (true) {
                                String message = scanner.nextLine();
                                if (message.equals("/back")) {
                                    break;
                                }
                                user.sendMessage(messageGroup, message);
                            }
                        }
                        break;
                    case 6:
                        System.out.println("Returning to the main menu.");
                        inGroupMenu = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please choose a number (1-6).");
                scanner.nextLine(); // Consume the invalid input
            }
        }
    }

}
