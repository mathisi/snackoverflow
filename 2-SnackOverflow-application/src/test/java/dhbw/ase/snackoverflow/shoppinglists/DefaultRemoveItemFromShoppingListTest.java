package dhbw.ase.snackoverflow.shoppinglists;

import dhbw.ase.snackoverflow.application.users.DefaultUserRepository;
import dhbw.ase.snackoverflow.application.shoppinglists.usecases.DefaultRemoveItemFromShoppingList;
import dhbw.ase.snackoverflow.domain.ingredients.Ingredient;
import dhbw.ase.snackoverflow.domain.ingredients.IngredientCategory;
import dhbw.ase.snackoverflow.domain.shoppingllists.ShoppingList;
import dhbw.ase.snackoverflow.domain.users.User;
import dhbw.ase.snackoverflow.domain.ingredients.IngredientNotFoundException;
import dhbw.ase.snackoverflow.domain.users.UserNotFoundException;
import dhbw.ase.snackoverflow.domain.users.EmailAddress;
import dhbw.ase.snackoverflow.domain.metrics.Metric;
import dhbw.ase.snackoverflow.domain.metrics.VolumeMetric;
import dhbw.ase.snackoverflow.domain.metrics.VolumeUnit;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;
import java.util.*;
public class DefaultRemoveItemFromShoppingListTest {

    private DefaultUserRepository userRepository = Mockito.mock(DefaultUserRepository.class);

    private DefaultRemoveItemFromShoppingList removeItemFromShoppingList = new DefaultRemoveItemFromShoppingList(userRepository);

    private User user;
    private ShoppingList shoppingList;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        user = new User.Builder().id(1).email(new EmailAddress("test@mail.de")).userName("Test User").password("pw").build();
    }

    @Test
    void removeItemSuccessful() {

        Metric metric = new VolumeMetric(10, VolumeUnit.LITER);
        String ingredientName = "Tomato";
        Ingredient ingredient = new Ingredient(0, metric, ingredientName, IngredientCategory.BEVERAGES);

        ShoppingList shoppingList = user.getShoppingList();
        List<Ingredient> ingredients = new ArrayList<>(shoppingList.getIngredients());
        ingredients.add(ingredient);
        shoppingList.setIngredients(ingredients);

        Mockito.when(userRepository.searchByID(1)).thenReturn(Optional.of(user));

        ShoppingList result = removeItemFromShoppingList.removeItem(1, ingredientName);

        assertNotNull(result);
        assertFalse(result.getIngredients().stream()
                        .anyMatch(ing -> ing.getName().equals(ingredientName)),
                "Ingredient should have been removed from the shopping list.");
    }

    @Test
    void removeItemUserNotFound() {
        Mockito.when(userRepository.searchByID(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            removeItemFromShoppingList.removeItem(1, "Ingredient");
        });
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void removeItemIngredientNotFound() {

        Mockito.when(userRepository.searchByID(1)).thenReturn(Optional.of(user));

        ShoppingList shoppingList = user.getShoppingList();
        shoppingList.setIngredients(new ArrayList<>()); // Ensuring empty list

        Exception exception = assertThrows(IngredientNotFoundException.class, () -> {
            removeItemFromShoppingList.removeItem(1, "NonExistingIngredient");
        });
        assertEquals("Ingredient NonExistingIngredient not found!", exception.getMessage());
    }
}