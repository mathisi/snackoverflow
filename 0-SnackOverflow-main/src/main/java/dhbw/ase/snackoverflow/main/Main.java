package dhbw.ase.snackoverflow.main;

import dhbw.ase.snackoverflow.adapters.ConsoleAdapter;
import dhbw.ase.snackoverflow.application.recipes.DefaultRecipeRepository;
import dhbw.ase.snackoverflow.application.recipes.usecases.DefaultCreateRecipe;
import dhbw.ase.snackoverflow.application.shoppinglists.usecases.DefaultAddItemToShoppingList;
import dhbw.ase.snackoverflow.application.shoppinglists.usecases.DefaultRemoveItemFromShoppingList;
import dhbw.ase.snackoverflow.application.users.DefaultUserRepository;
import dhbw.ase.snackoverflow.application.recipes.strategies.RecipeFinder;
import dhbw.ase.snackoverflow.application.recipes.strategies.SearchByNameStrategy;
import dhbw.ase.snackoverflow.application.users.usecases.*;

public class Main {
    public static void main(String[] args) {

        DefaultUserRepository userRepository = new DefaultUserRepository();
        DefaultRecipeRepository recipeRepository = new DefaultRecipeRepository();

        DefaultCreateUser createUser = new DefaultCreateUser(userRepository);
        DefaultChangeUserName changeUserName = new DefaultChangeUserName(userRepository);
        DefaultLoginUser loginUser = new DefaultLoginUser(userRepository);
        DefaultGetActiveUser getActiveUser = new DefaultGetActiveUser(userRepository);
        DefaultLogoutUser logoutUser = new DefaultLogoutUser(userRepository);
        DefaultChangeUserPassword changeUserPassword = new DefaultChangeUserPassword(userRepository);
        DefaultAddItemToShoppingList addItemToShoppingList = new DefaultAddItemToShoppingList(userRepository);
        DefaultRemoveItemFromShoppingList removeItemFromShoppingList = new DefaultRemoveItemFromShoppingList(
                userRepository);

        DefaultCreateRecipe createRecipe = new DefaultCreateRecipe(recipeRepository, userRepository);

        RecipeFinder recipeFinder = new RecipeFinder(new SearchByNameStrategy(recipeRepository), recipeRepository, userRepository);

        ConsoleAdapter consoleAdapter = new ConsoleAdapter(createUser, changeUserName, loginUser, getActiveUser,
                logoutUser, changeUserPassword, addItemToShoppingList, removeItemFromShoppingList,
                createRecipe, recipeFinder);

        consoleAdapter.start();

    }
}
