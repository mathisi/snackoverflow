package dhbw.ase.snackoverflow.adapters;

import dhbw.ase.snackoverflow.adapters.handlers.ShoppingListHandler;
import dhbw.ase.snackoverflow.adapters.handlers.ManageUserHandler;
import dhbw.ase.snackoverflow.adapters.handlers.RecipeHandler;
import dhbw.ase.snackoverflow.domain.entities.User;
import dhbw.ase.snackoverflow.domain.usecases.*;
import dhbw.ase.snackoverflow.domain.valueobjects.*;

import java.util.*;
import java.util.function.Supplier;

public class ConsoleAdapter {
    private final Scanner scanner;
    private final CreateUser createUser;
    private final LoginUser loginUser;
    private final LogoutUser logoutUser;

    private final ShoppingListHandler shoppingListHandler;
    private final ManageUserHandler manageUserHandler;
    private final RecipeHandler recipeHandler;

    public ConsoleAdapter(CreateUser createUser, ChangeUserName changeUserName, LoginUser loginUser,
            GetActiveUser getActiveUser, LogoutUser logoutUser, ChangeUserPassword changeUserPassword,
            AddItemToShoppingList addItemToShoppingList, RemoveItemFromShoppingList removeItemFromShoppingList,
            FindRecipe findRecipe, CreateRecipe createRecipe) {
        this.createUser = createUser;
        this.loginUser = loginUser;
        this.logoutUser = logoutUser;
        this.scanner = new Scanner(System.in);
        this.shoppingListHandler = new ShoppingListHandler(this.scanner, getActiveUser, addItemToShoppingList,
                removeItemFromShoppingList);
        this.manageUserHandler = new ManageUserHandler(this.scanner, getActiveUser, changeUserPassword, changeUserName);
        this.recipeHandler = new RecipeHandler(this.scanner, findRecipe, createRecipe, getActiveUser);
    }

    public void start() {
        Map<Integer, Supplier<Boolean>> menuActions = new HashMap<>();
        menuActions.put(1, () -> {
            createUser();
            return true;
        });
        menuActions.put(2, () -> {
            loginUser();
            return false;
        });
        menuActions.put(3, () -> false);

        boolean running = true;
        while (running) {
            try {
                printStartupMenu();
                int choice = getIntInput("Choose an option: ");

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

    public void startLoggedIn() {
        Map<Integer, Supplier<Boolean>> menuActions = new HashMap<>();
        menuActions.put(1, () -> {
            this.manageUserHandler.start();
            return true;
        });
        menuActions.put(2, () -> {
            this.shoppingListHandler.start();
            return true;
        });
        menuActions.put(3, () -> {
            this.recipeHandler.start();
            return true;
        });
        menuActions.put(10, () -> {
            logoutUser();
            start(); //
            return false;
        });
        boolean running = true;
        while (running) {
            try {
                printLoggedInMenu();
                int choice = getIntInput("Choose an option: ");
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

    private void logoutUser() {
        this.logoutUser.logout();
        System.out.println("Successfully logged out");
    }

    private void loginUser() {
        String email = getStringInput("Enter email: ");
        String password = getStringInput("Enter password: ");

        try {
            this.loginUser.login(new EmailAddress(email), password);
            System.out.println("Successfully logged in");
            this.startLoggedIn();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            this.start();
        }
    }

    private void createUser() {
        System.out.println("Create a new User");
        try {
            EmailAddress emailAddress = new EmailAddress(getStringInput("Enter email adress: "));
            String userName = getStringInput("Enter username: ");
            String password = getStringInput("Enter password: ");

            User newUser = new User.Builder()
                    .id(0)
                    .email(emailAddress)
                    .userName(userName)
                    .password(password)
                    .build();
            User createdUser = this.createUser.createUser(newUser);
            System.out.println("User created: " + createdUser.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void printStartupMenu() {
        System.out.println("\n - Welcome to Snackoverflow -");
        System.out.println("1. Create Account");
        System.out.println("2. Login with e-mail");
        System.out.println("3. Close Application");
    }

    private void printLoggedInMenu() {
        System.out.println("\n - Snackoverflow -");
        System.out.println("1. Manage User");
        System.out.println("2. Manage ShoppingList");
        System.out.println("3. Manage Recipes");
        System.out.println("10. Log out");
    }

    private int getIntInput(String output) {
        while (true) {
            try {
                System.out.print(output);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}
