package dhbw.ase.snackoverflow.adapters.handlers;

import dhbw.ase.snackoverflow.domain.entities.Ingredient;
import dhbw.ase.snackoverflow.domain.entities.IngredientCategory;
import dhbw.ase.snackoverflow.domain.entities.ShoppingList;
import dhbw.ase.snackoverflow.domain.entities.User;
import dhbw.ase.snackoverflow.domain.usecases.AddItemToShoppingList;
import dhbw.ase.snackoverflow.domain.usecases.GetActiveUser;
import dhbw.ase.snackoverflow.domain.usecases.RemoveItemFromShoppingList;
import dhbw.ase.snackoverflow.domain.valueobjects.*;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class ShoppingListHandler {


    private final Scanner scanner;
    private final GetActiveUser getActiveUser;
    private final AddItemToShoppingList addItemToShoppingList;
    private final RemoveItemFromShoppingList removeItemFromShoppingList;
    public ShoppingListHandler(Scanner scanner,GetActiveUser getActiveUser, AddItemToShoppingList addItemToShoppingList, RemoveItemFromShoppingList removeItemFromShoppingList) {
        this.scanner = scanner;
        this.getActiveUser = getActiveUser;
        this.addItemToShoppingList = addItemToShoppingList;
        this.removeItemFromShoppingList = removeItemFromShoppingList;
    }

    public void start() {
        Map<Integer, Supplier<Boolean>> menuActions = new HashMap<>();
        menuActions.put(1, () -> {
            this.addItem();
            return true;
        });
        menuActions.put(2, () -> {
            this.removeItem();
            return true;
        });
        menuActions.put(3, () -> {
            this.printShoppingList();
            return true;
        });
        menuActions.put(10, () -> false);
        boolean running = true;
        while (running) {
            try {
                printMenu();
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
    private void printMenu() {
        System.out.println("\n - Manage ShoppingList -");
        System.out.println("1. Add Item");
        System.out.println("2. Remove Item");
        System.out.println("3. Print");
        System.out.println("10. Back");
    }
    private void removeItem() {
        try {
            User activeUser = this.getActiveUser();
            System.out.println("\n - Remove Item -");
            if(this.printShoppingList()) {
                String name = getStringInput("Enter ingredient name to remove: ");

                this.removeItemFromShoppingList.removeItem(activeUser.getId(), name);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
    public boolean printShoppingList() {
        try {
            User activeUser = this.getActiveUser();
            System.out.println("\n - Shopping List for: " + activeUser.getUserName() + " -");
            ShoppingList shoppingList = activeUser.getShoppingList();
            List ingredientList = shoppingList.getIngredients();
            if(ingredientList.size() == 0) {
                System.out.println("There are no items on your shopping list!");
                return false;
            }
            else {
                for (Object o : ingredientList) {
                    Ingredient ingredient = (Ingredient) o;
                    System.out.println("Name: " + ingredient.getName() + ", Amount: " + ingredient.getMetric().getAmount() + ingredient.getMetric().getUnit() + ", Category: " + ingredient.getCategory().toString());
                }
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    public void addItem() {
        try {
            User activeUser = this.getActiveUser();

            System.out.println("\n - Add Item -");

            String name = getStringInput("Enter ingredient name: ");

            double amount = getDoubleInput("Enter your amount: ");

            Metric metric = this.getMetricInput(amount);

            IngredientCategory ingredientCategory = this.getIngredientCategoryInput();

            Ingredient newIngredient = new Ingredient(0, metric, name, ingredientCategory);
            ShoppingList shoppingList = this.addItemToShoppingList.addItem(activeUser.getId(), newIngredient);
            System.out.println(shoppingList.toString());


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }
    private IngredientCategory getIngredientCategoryInput() {
        // Create a map of option numbers to ingredient categories
        Map<Integer, IngredientCategory> categoryMap = new LinkedHashMap<>(); // LinkedHashMap preserves insertion order

        categoryMap.put(1, IngredientCategory.FRUITS_VEGETABLES);
        categoryMap.put(2, IngredientCategory.DAIRY);
        categoryMap.put(3, IngredientCategory.MEAT_FISH);
        categoryMap.put(4, IngredientCategory.BAKERY);
        categoryMap.put(5, IngredientCategory.BEVERAGES);
        categoryMap.put(6, IngredientCategory.DRY_GOODS);
        categoryMap.put(7, IngredientCategory.FROZEN);
        categoryMap.put(8, IngredientCategory.SPICES_SAUCES);
        categoryMap.put(9, IngredientCategory.MISCELLANEOUS);

        // Display options
        System.out.println("Which category does your ingredient belong to?");
        categoryMap.forEach((key, category) -> System.out.println(key + ": " + category));

        // Get user selection
        while (true) {
            int choice = getIntInput("Choose an option: ");

            if (categoryMap.containsKey(choice)) {
                return categoryMap.get(choice);
            } else {
                System.out.println("Invalid option, try again");
            }
        }
    }
    private Metric getMetricInput(double amount) {

        Map<Integer, Object> unitMap = new LinkedHashMap<>();
        Map<Integer, BiFunction<Double, Object, Metric>> metricConstructorMap = new HashMap<>();

        unitMap.put(1, VolumeUnit.MILLILITER);
        unitMap.put(2, VolumeUnit.LITER);
        unitMap.put(3, VolumeUnit.CUP);
        unitMap.put(4, VolumeUnit.TEASPOON);
        unitMap.put(5, VolumeUnit.TABLESPOON);

        unitMap.put(6, WeightUnit.GRAM);
        unitMap.put(7, WeightUnit.KILOGRAM);
        unitMap.put(8, WeightUnit.POUND);
        unitMap.put(9, WeightUnit.OUNCE);

        for (int i = 1; i <= 5; i++) {
            metricConstructorMap.put(i, (amt, unit) -> new VolumeMetric(amt, (VolumeUnit) unit));
        }
        for (int i = 6; i <= 9; i++) {
            metricConstructorMap.put(i, (amt, unit) -> new WeightMetric(amt, (WeightUnit) unit));
        }

        System.out.println("Which metric does your ingredient have?");
        unitMap.forEach((key, unit) -> System.out.println(key + ": " + unit));

        while (true) {
            int choice = getIntInput("Choose an option: ");

            if (unitMap.containsKey(choice)) {
                Object selectedUnit = unitMap.get(choice);
                BiFunction<Double, Object, Metric> constructor = metricConstructorMap.get(choice);
                return constructor.apply(amount, selectedUnit);
            } else {
                System.out.println("Invalid option, try again");
            }
        }
    }
    private User getActiveUser() throws Exception {
        return this.getActiveUser.getActiveUser();
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
