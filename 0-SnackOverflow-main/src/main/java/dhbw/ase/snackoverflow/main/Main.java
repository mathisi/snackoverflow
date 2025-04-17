package dhbw.ase.snackoverflow.main;

import dhbw.ase.snackoverflow.adapters.ConsoleAdapter;
import dhbw.ase.snackoverflow.application.repositories.DefaultRecipeRepository;
import dhbw.ase.snackoverflow.application.repositories.DefaultUserRepository;
import dhbw.ase.snackoverflow.application.usecases.*;
import dhbw.ase.snackoverflow.domain.TestDomain;
import dhbw.ase.snackoverflow.domain.entities.Ingredient;
import dhbw.ase.snackoverflow.domain.entities.IngredientCategory;
import dhbw.ase.snackoverflow.domain.entities.ShoppingList;
import dhbw.ase.snackoverflow.domain.entities.User;
import dhbw.ase.snackoverflow.domain.valueobjects.EmailAddress;
import dhbw.ase.snackoverflow.domain.valueobjects.VolumeMetric;
import dhbw.ase.snackoverflow.domain.valueobjects.VolumeUnit;

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
        DefaultFindRecipe findRecipe = new DefaultFindRecipe(recipeRepository, userRepository);
        DefaultCreateRecipe createRecipe = new DefaultCreateRecipe(recipeRepository, userRepository);

        ConsoleAdapter consoleAdapter = new ConsoleAdapter(createUser, changeUserName, loginUser, getActiveUser,
                logoutUser, changeUserPassword, addItemToShoppingList, removeItemFromShoppingList, findRecipe,
                createRecipe);

        consoleAdapter.start();

    }
}
