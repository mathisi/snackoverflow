package dhbw.ase.snackoverflow.adapters.handlers;

import dhbw.ase.snackoverflow.adapters.utils.InputUtils;
import dhbw.ase.snackoverflow.domain.entities.User;
import dhbw.ase.snackoverflow.domain.usecases.ChangeUserName;
import dhbw.ase.snackoverflow.domain.usecases.ChangeUserPassword;
import dhbw.ase.snackoverflow.domain.usecases.GetActiveUser;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Supplier;

public class ManageUserHandler {
    private final Scanner scanner;
    private final GetActiveUser getActiveUser;
    private final ChangeUserPassword changeUserPassword;
    private final ChangeUserName changeUserName;
    public ManageUserHandler(Scanner scanner, GetActiveUser getActiveUser, ChangeUserPassword changeUserPassword, ChangeUserName changeUserName) {
        this.scanner = scanner;
        this.getActiveUser = getActiveUser;
        this.changeUserPassword = changeUserPassword;
        this.changeUserName = changeUserName;
    }

    public void start() {
        Map<Integer, Supplier<Boolean>> menuActions = new HashMap<>();
        menuActions.put(1, () -> {
            this.changeUserName();
            return true;
        });
        menuActions.put(2, () -> {
            this.changePassword();
            return true;
        });
        menuActions.put(10, () -> false);
        boolean running = true;
        while (running) {
            try {
                printMenu();
                int choice = InputUtils.getIntInput("Choose an option: ", scanner);

                if (menuActions.containsKey(choice)) {
                    running = menuActions.get(choice).get();
                } else {
                    System.out.println("Invalid option. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }
    private void printMenu() {
        System.out.println("\n - Manage User -");
        System.out.println("1. Change Username");
        System.out.println("2. Change Password");
        System.out.println("10. Back");
    }
    private void changePassword() {
        try {
            User activeUser = this.getActiveUser();
            System.out.println("Change password for: " + activeUser.getUserName());
            String newPassword = getStringInput("Enter new password: ");
            String newPasswordRepeat = getStringInput("Enter new password again: ");
            if(newPassword.equals(newPasswordRepeat)) {
                this.changeUserPassword.changePassword(activeUser.getId(), newPassword);
                System.out.println("Password successfully changed");
            }
            else {
                System.out.println("Passwords do not match");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    private void changeUserName() {
        try {
            User activeUser = this.getActiveUser();
            System.out.println("Change Username for: " + activeUser.getUserName());
            String newUserName = getStringInput("Enter new user name: ");
            User updatedUser = changeUserName.changeName(activeUser.getId(), newUserName);
            System.out.println("Updated user: " + updatedUser.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
    private User getActiveUser() throws Exception {
        return this.getActiveUser.getActiveUser();
    }
    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}
