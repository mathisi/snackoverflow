package dhbw.ase.snackoverflow.adapters;

import dhbw.ase.snackoverflow.domain.entities.Ingredient;
import dhbw.ase.snackoverflow.domain.entities.IngredientCategory;
import dhbw.ase.snackoverflow.domain.entities.ShoppingList;
import dhbw.ase.snackoverflow.domain.entities.User;
import dhbw.ase.snackoverflow.domain.usecases.*;
import dhbw.ase.snackoverflow.domain.valueobjects.*;

import java.util.*;
import java.util.function.Supplier;

public class ConsoleAdapter {
    private final Scanner scanner;
    private final CreateUser createUser;
    private final ChangeUserName changeUserName;
    private final LoginUser loginUser;
    private final GetActiveUser getActiveUser;
    private final LogoutUser logoutUser;

    private final ChangeUserPassword changeUserPassword;

    private final AddItemToShoppingList addItemToShoppingList;
    public ConsoleAdapter(CreateUser createUser, ChangeUserName changeUserName, LoginUser loginUser, GetActiveUser getActiveUser, LogoutUser logoutUser, ChangeUserPassword changeUserPassword, AddItemToShoppingList addItemToShoppingList) {
        this.createUser = createUser;
        this.changeUserName = changeUserName;
        this.loginUser = loginUser;
        this.getActiveUser = getActiveUser;
        this.logoutUser = logoutUser;
        this.changeUserPassword = changeUserPassword;
        this.addItemToShoppingList = addItemToShoppingList;
        this.scanner = new Scanner(System.in);
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
        boolean running = true;
        while (running) {
            try {
                printLoggedInMenu();
                int choice = getIntInput("Choose an option: ");
                switch (choice) {
                    case 1:
                        this.changeUserName();
                        break;
                    case 2:
                        this.changePassword();
                        break;
                    case 3:
                        this.addItemToShoppingList();
                        break;
                    case 4:
                        this.printShoppingList();
                        break;
                    case 10:
                        running = false;
                        this.logoutUser();
                        this.start();
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }
    private void printShoppingList() {
        try {
            User activeUser = this.getActiveUser();
            ShoppingList shoppingList = activeUser.getShoppingList();
            List ingredientList = (ArrayList) shoppingList.getIngredients();
            for (int i = 0; i < ingredientList.size(); i++) {
                Ingredient ingredient = (Ingredient) ingredientList.get(i);
                System.out.println("Name: " + ingredient.getName() + ", Amount: " + ingredient.getMetric().getAmount() + ingredient.getMetric().getUnit() + ", Category: " + ingredient.getCategory().toString());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    private void addItemToShoppingList() {
        try {
            User activeUser = this.getActiveUser();

            // name
            String name = getStringInput("Enter ingredient name: ");

            double amount = getDoubleInput("Enter your amount: ");

            Metric metric = null;
            System.out.println("Which metric does your ingredient have?");
            System.out.println("1: " + VolumeUnit.MILLILITER);
            System.out.println("2: " + VolumeUnit.LITER);
            System.out.println("3: " + VolumeUnit.CUP);
            System.out.println("4: " + VolumeUnit.TEASPOON);
            System.out.println("5: " + VolumeUnit.TABLESPOON);
            System.out.println("6: " + WeightUnit.GRAM);
            System.out.println("7: " + WeightUnit.KILOGRAM);
            System.out.println("8: " + WeightUnit.POUND);
            System.out.println("9: " + WeightUnit.OUNCE);

            boolean selectedUnit = false;
            while(!selectedUnit) {
                int choice = getIntInput("Choose an option: ");
                switch (choice) {
                    case 1:
                        metric = new VolumeMetric(amount, VolumeUnit.MILLILITER);
                        selectedUnit = true;
                        break;
                    case 2:
                        metric = new VolumeMetric(amount, VolumeUnit.LITER);
                        selectedUnit = true;
                        break;
                    case 3:
                        metric = new VolumeMetric(amount, VolumeUnit.CUP);
                        selectedUnit = true;
                        break;
                    case 4:
                        metric = new VolumeMetric(amount, VolumeUnit.TEASPOON);
                        selectedUnit = true;
                        break;
                    case 5:
                        metric = new VolumeMetric(amount, VolumeUnit.TABLESPOON);
                        selectedUnit = true;
                        break;
                    case 6:
                        metric = new WeightMetric(amount, WeightUnit.GRAM);
                        selectedUnit = true;
                        break;
                    case 7:
                        metric = new WeightMetric(amount, WeightUnit.KILOGRAM);
                        selectedUnit = true;
                        break;
                    case 8:
                        metric = new WeightMetric(amount, WeightUnit.POUND);
                        selectedUnit = true;
                        break;
                    case 9:
                        metric = new WeightMetric(amount, WeightUnit.OUNCE);
                        selectedUnit = true;
                        break;
                    default:
                        System.out.println("Invalid option, try again");
                        break;
                }
            }

            System.out.println("Which category does your ingredient belong to?");
            System.out.println("1: " + IngredientCategory.FRUITS_VEGETABLES);
            System.out.println("2: " + IngredientCategory.DAIRY);
            System.out.println("3: " + IngredientCategory.MEAT_FISH);
            System.out.println("4: " + IngredientCategory.BAKERY);
            System.out.println("5: " + IngredientCategory.BEVERAGES);
            System.out.println("6: " + IngredientCategory.DRY_GOODS);
            System.out.println("7: " + IngredientCategory.FROZEN);
            System.out.println("8: " + IngredientCategory.SPICES_SAUCES);
            System.out.println("9: " + IngredientCategory.MISCELLANEOUS);
            IngredientCategory ingredientCategory = null;
            boolean selectedCategory = false;
            while(!selectedCategory) {
                int choice = getIntInput("Choose an option: ");
                switch (choice) {
                    case 1:
                        ingredientCategory = IngredientCategory.FRUITS_VEGETABLES;
                        selectedCategory = true;
                        break;
                    case 2:
                        ingredientCategory = IngredientCategory.DAIRY;
                        selectedCategory = true;
                        break;
                    case 3:
                        ingredientCategory = IngredientCategory.MEAT_FISH;
                        selectedCategory = true;
                        break;
                    case 4:
                        ingredientCategory = IngredientCategory.BAKERY;
                        selectedCategory = true;
                        break;
                    case 5:
                        ingredientCategory = IngredientCategory.BEVERAGES;
                        selectedCategory = true;
                        break;
                    case 6:
                        ingredientCategory = IngredientCategory.DRY_GOODS;
                        selectedCategory = true;
                        break;
                    case 7:
                        ingredientCategory = IngredientCategory.FROZEN;
                        selectedCategory = true;
                        break;
                    case 8:
                        ingredientCategory = IngredientCategory.SPICES_SAUCES;
                        selectedCategory = true;
                        break;
                    case 9:
                        ingredientCategory = IngredientCategory.MISCELLANEOUS;
                        selectedCategory = true;
                        break;
                    default:
                        System.out.println("Invalid option, try again");
                        break;
                }
            }

            Ingredient newIngredient = new Ingredient(0, metric, name, ingredientCategory);
            ShoppingList shoppingList = this.addItemToShoppingList.addItem(activeUser.getId(), newIngredient);
            System.out.println(shoppingList.toString());
            // category


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

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
    private void logoutUser() {
        this.logoutUser.logout();
        System.out.println("Successfully logged out");
    }
    private User getActiveUser() throws Exception {
        return this.getActiveUser.getActiveUser();
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
    private void createUser(){
        System.out.println("Create a new User");
        EmailAddress emailAddress = new EmailAddress(getStringInput("Enter email adress: "));
        String userName = getStringInput("Enter username: ");
        String password = getStringInput("Enter password: ");
        User newUser = new User(0, emailAddress, userName, password);
        User createdUser = this.createUser.createUser(newUser);
        System.out.println("User created: " + createdUser.toString());
    }
    private void printStartupMenu() {
        System.out.println("\n - Snackoverflow -");
        System.out.println("1. Create Account");
        System.out.println("2. Login with e-mail");
        System.out.println("3. Close Application");
    }
    private void printLoggedInMenu() {
        System.out.println("\n - Snackoverflow -");
        System.out.println("1. Change Username");
        System.out.println("2. Change Password");
        System.out.println("3. Add new ingredient to shopping list");
        System.out.println("4. Print shopping list");
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
    private double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
}
