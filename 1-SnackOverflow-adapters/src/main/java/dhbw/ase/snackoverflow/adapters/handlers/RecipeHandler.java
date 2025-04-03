package dhbw.ase.snackoverflow.adapters.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Supplier;

import dhbw.ase.snackoverflow.domain.entities.Ingredient;
import dhbw.ase.snackoverflow.domain.entities.IngredientCategory;
import dhbw.ase.snackoverflow.domain.entities.ProcessStep;
import dhbw.ase.snackoverflow.domain.entities.Recipe;
import dhbw.ase.snackoverflow.domain.entities.User;
import dhbw.ase.snackoverflow.domain.exceptions.IngredientNotFoundException;
import dhbw.ase.snackoverflow.domain.usecases.CreateRecipe;
import dhbw.ase.snackoverflow.domain.usecases.FindRecipe;
import dhbw.ase.snackoverflow.domain.usecases.GetActiveUser;
import dhbw.ase.snackoverflow.domain.valueobjects.Metric;
import dhbw.ase.snackoverflow.domain.valueobjects.MetricFactory;

public class RecipeHandler {
    private final Scanner scanner;
    private final FindRecipe findRecipe;
    private final CreateRecipe createRecipe;
    private final GetActiveUser getActiveUser;

    public RecipeHandler(Scanner scanner, FindRecipe findRecipe, CreateRecipe createRecipe,
            GetActiveUser getActiveUser) {
        this.scanner = scanner;
        this.findRecipe = findRecipe;
        this.createRecipe = createRecipe;
        this.getActiveUser = getActiveUser;
    }

    public void start() {
        Map<Integer, Supplier<Boolean>> menuActions = new HashMap<>();
        menuActions.put(1, () -> {
            this.createRecipe();
            return true;
        });
        menuActions.put(2, () -> {
            this.findRecipe();
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

    private void findRecipe() {
        try {
            System.out.println("Search Recipes by:");
            System.out.println("1. Name");
            System.out.println("2. Ingredients");
            System.out.println("3. User");
            final int choice = getIntInput("Choose an option: ");

            List<Recipe> foundRecipes = new ArrayList<>();

            switch (choice) {
                case 1 -> {
                    String name = getStringInput("Enter recipe name: ");
                    foundRecipes = findRecipe.findByName(name);
                }
                case 2 -> {
                    List<Ingredient> ingredients = new ArrayList<>();
                    boolean addingIngredients = true;
                    while (addingIngredients) {
                        String ingredientName = getStringInput("Enter ingredient name: ");
                        ingredients.add(new Ingredient(0, null, ingredientName, null));
                        addingIngredients = getIntInput("Add another ingredient? (1 = yes, 0 = no): ") == 1;
                    }
                    foundRecipes = findRecipe.findByIngredients(ingredients);
                }
                case 3 -> {
                    int userId = getIntInput("Enter user ID: ");
                    User user = new User(userId, null, null, null);
                    foundRecipes = findRecipe.findByUser(user);
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
            if (foundRecipes.isEmpty()) {
                System.out.println("No recipes found.");
            } else {
                System.out.println("Found recipes:");
                for (int i = 0; i < foundRecipes.size(); i++) {
                    System.out.println((i + 1) + ". " + foundRecipes.get(i).getName());
                }

                int recipeChoice = getIntInput("Select a recipe to display (enter number): ");
                if (recipeChoice < 1 || recipeChoice > foundRecipes.size()) {
                    System.out.println("Invalid selection.");
                } else {
                    Recipe selectedRecipe = foundRecipes.get(recipeChoice - 1);
                    this.displayRecipe(selectedRecipe);
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    private void displayRecipe(Recipe selectedRecipe) {
        System.out.println("Recipe: " + selectedRecipe.getName());
        System.out.println("Ingredients:");
        for (Ingredient ingredient : selectedRecipe.getIngredients()) {
            System.out.println("- " + ingredient.getName() + ": "
                    + ingredient.getMetric().getAmount() + " "
                    + ingredient.getMetric().getUnit());
        }
        System.out.println("Process Steps:");
        for (ProcessStep step : selectedRecipe.getProcessSteps()) {
            System.out.println("- " + step.getDescription());
        }
    }

    private void createRecipe() {
        try {
            final User activeUser = this.getActiveUser();
            final String recipeName = getStringInput("Enter Recipe Name: ");
            final int numberOfPortions = getIntInput("Enter number of portions: ");
            final int cookTime = getIntInput("Enter total cook time in minutes: ");
            List<ProcessStep> processSteps = new ArrayList<>();
            boolean running = true;
            while (running) {
                final String stepDescription = getStringInput("Enter step description: ");
                List<Ingredient> ingredients = new ArrayList<>();
                boolean addingIngredients = true;

                while (addingIngredients) {
                    final String ingredientName = getStringInput("Enter ingredient name: ");
                    final String ingredientCategory = getStringInput("Enter ingredient category: ");
                    final String ingredientMetric = getStringInput("Enter ingredient metric: ");

                    IngredientCategory category;
                    try {
                        category = IngredientCategory.valueOf(ingredientCategory.toUpperCase());
                    } catch (IllegalArgumentException e) {
                        throw new IngredientNotFoundException("Invalid ingredient category: " + ingredientCategory);
                    }
                    // TODO: ingredient ID always 0?
                    ingredients.add(new Ingredient(0, MetricFactory.fromString(ingredientMetric), ingredientName,
                            category));
                    addingIngredients = getIntInput("Add another ingredient? (1 = yes, 0 = no): ") == 1;
                }

                ProcessStep processStep = new ProcessStep(stepDescription);
                processStep.setIngredients(ingredients);
                processSteps.add(processStep);

                running = getIntInput("Add another step? (1 = yes, 0 = no): ") == 1;
            }
            final Recipe newRecipe = new Recipe(0,
                    recipeName,
                    numberOfPortions,
                    cookTime,
                    processSteps,
                    activeUser);
            this.createRecipe.createRecipe(newRecipe);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private User getActiveUser() throws Exception {
        return this.getActiveUser.getActiveUser();
    }

    private void printMenu() {
        System.out.println("\n - Manage Recipes -");
        System.out.println("1. Create Recipe");
        System.out.println("2. Browse Recipes");
        System.out.println("10. Back");
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
