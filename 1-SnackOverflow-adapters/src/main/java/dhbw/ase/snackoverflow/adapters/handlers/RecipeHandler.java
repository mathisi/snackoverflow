package dhbw.ase.snackoverflow.adapters.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import dhbw.ase.snackoverflow.adapters.utils.InputUtils;
import dhbw.ase.snackoverflow.application.recipes.strategies.*;
import dhbw.ase.snackoverflow.domain.ingredients.Ingredient;
import dhbw.ase.snackoverflow.domain.ingredients.IngredientCategory;
import dhbw.ase.snackoverflow.domain.recipes.ProcessStep;
import dhbw.ase.snackoverflow.domain.recipes.Recipe;
import dhbw.ase.snackoverflow.domain.users.User;
import dhbw.ase.snackoverflow.domain.recipes.RecipeRepository;
import dhbw.ase.snackoverflow.domain.users.UserRepository;
import dhbw.ase.snackoverflow.domain.recipes.usecases.CreateRecipe;
import dhbw.ase.snackoverflow.domain.users.usecases.GetActiveUser;
import dhbw.ase.snackoverflow.domain.metrics.Metric;
import dhbw.ase.snackoverflow.domain.metrics.MetricUnit;
import dhbw.ase.snackoverflow.domain.metrics.VolumeMetric;
import dhbw.ase.snackoverflow.domain.metrics.VolumeUnit;
import dhbw.ase.snackoverflow.domain.metrics.WeightMetric;
import dhbw.ase.snackoverflow.domain.metrics.WeightUnit;

public class RecipeHandler {
    private final Scanner scanner;
    private final RecipeFinder recipeFinder;
    private final CreateRecipe createRecipe;
    private final GetActiveUser getActiveUser;

    public RecipeHandler(Scanner scanner, RecipeFinder recipeFinder, CreateRecipe createRecipe,
            GetActiveUser getActiveUser) {
        this.scanner = scanner;
        this.recipeFinder = recipeFinder;
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

    private void findRecipe() {
        try {
            System.out.println("Search Recipes by:");
            System.out.println("1. Name");
            System.out.println("2. Ingredients");
            System.out.println("3. User");
            final int choice = InputUtils.getIntInput("Choose an option: ", scanner);

            List<Recipe> foundRecipes = new ArrayList<>();

            switch (choice) {
                case 1 -> {
                    String name = getStringInput("Enter recipe name: ");
                    RecipeRepository recipeRepository = recipeFinder.getRecipeRepository();
                    recipeFinder.setStrategy(new SearchByNameStrategy(recipeRepository));
                    foundRecipes = recipeFinder.find(new RecipeSearchContext().name(name));
                }
                case 2 -> {
                    List<Ingredient> ingredients = new ArrayList<>();
                    boolean addingIngredients = true;
                    while (addingIngredients) {
                        String ingredientName = getStringInput("Enter ingredient name: ");
                        ingredients.add(new Ingredient(0, null, ingredientName, null));
                        addingIngredients = InputUtils.getIntInput("Add another ingredient? (1 = yes, 0 = no): ", scanner) == 1;
                    }
                    RecipeRepository recipeRepository = recipeFinder.getRecipeRepository();
                    recipeFinder.setStrategy(new SearchByIngredientsStrategy(recipeRepository));
                    foundRecipes = recipeFinder.find(new RecipeSearchContext().ingredients(ingredients));
                }
                case 3 -> {
                    int userId = InputUtils.getIntInput("Enter user ID: ", scanner);
                    User user = new User.Builder().id(0).build();
                    UserRepository userRepository = recipeFinder.getUserRepository();
                    recipeFinder.setStrategy(new SearchByUserStrategy(userRepository));
                    foundRecipes = recipeFinder.find(new RecipeSearchContext().user(user));
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

                int recipeChoice = InputUtils.getIntInput("Select a recipe to display (enter number): ", scanner);
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
            final int numberOfPortions = InputUtils.getIntInput("Enter number of portions: ", scanner);
            final int cookTime = InputUtils.getIntInput("Enter total cook time in minutes: ", scanner);
            List<ProcessStep> processSteps = new ArrayList<>();
            boolean running = true;
            while (running) {
                final String stepDescription = getStringInput("Enter step description: ");
                List<Ingredient> ingredients = new ArrayList<>();
                boolean addingIngredients = true;

                while (addingIngredients) {
                    final String ingredientName = getStringInput("Enter ingredient name: ");
                    final IngredientCategory category = this.getIngredientCategoryInput();
                    final double amount = InputUtils.getIntInput("Enter amount: ", scanner);
                    final Metric ingredientMetric = this.getMetricInput(amount);

                    // TODO: ingredient ID always 0?
                    ingredients.add(new Ingredient(0, ingredientMetric, ingredientName,
                            category));
                    addingIngredients = InputUtils.getIntInput("Add another ingredient? (1 = yes, 0 = no): ", scanner) == 1;
                }

                ProcessStep processStep = new ProcessStep(stepDescription);
                processStep.setIngredients(ingredients);
                processSteps.add(processStep);

                running = InputUtils.getIntInput("Add another step? (1 = yes, 0 = no): ", scanner) == 1;
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


    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
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
            int choice = InputUtils.getIntInput("Choose an option: ", scanner);

            if (categoryMap.containsKey(choice)) {
                return categoryMap.get(choice);
            } else {
                System.out.println("Invalid option, try again");
            }
        }
    }

    private Metric getMetricInput(double amount) {

        Map<Integer, MetricUnit> unitMap = new LinkedHashMap<>();
        Map<Integer, BiFunction<Double, Object, Metric>> metricConstructorMap = new HashMap<>();

        unitMap.put(1, VolumeUnit.MILLILITER);
        unitMap.put(2, VolumeUnit.LITER);
        unitMap.put(3, VolumeUnit.CUP);
        unitMap.put(4, VolumeUnit.TEASPOON);
        unitMap.put(5, VolumeUnit.TABLESPOON);
        unitMap.put(6, VolumeUnit.PIECE);

        unitMap.put(7, WeightUnit.GRAM);
        unitMap.put(8, WeightUnit.KILOGRAM);
        unitMap.put(9, WeightUnit.POUND);
        unitMap.put(10, WeightUnit.OUNCE);

        for (int i = 1; i <= 6; i++) {
            metricConstructorMap.put(i, (amt, unit) -> new VolumeMetric(amt, (VolumeUnit) unit));
        }
        for (int i = 7; i <= 10; i++) {
            metricConstructorMap.put(i, (amt, unit) -> new WeightMetric(amt, (WeightUnit) unit));
        }

        System.out.println("Which metric does your ingredient have?");
        unitMap.forEach((key, unit) -> System.out.println(key + ": " + unit.getDisplayName()));

        while (true) {
            int choice = InputUtils.getIntInput("Choose an option: ", scanner);

            if (unitMap.containsKey(choice)) {
                Object selectedUnit = unitMap.get(choice);
                BiFunction<Double, Object, Metric> constructor = metricConstructorMap.get(choice);
                return constructor.apply(amount, selectedUnit);
            } else {
                System.out.println("Invalid option, try again");
            }
        }
    }
}
