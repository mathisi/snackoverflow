package dhbw.ase.snackoverflow;

import dhbw.ase.snackoverflow.application.repositories.DefaultUserRepository;
import dhbw.ase.snackoverflow.application.usecases.DefaultRemoveItemFromShoppingList;
import dhbw.ase.snackoverflow.domain.entities.Ingredient;
import dhbw.ase.snackoverflow.domain.entities.IngredientCategory;
import dhbw.ase.snackoverflow.domain.entities.ShoppingList;
import dhbw.ase.snackoverflow.domain.entities.User;
import dhbw.ase.snackoverflow.domain.exceptions.IngredientNotFoundException;
import dhbw.ase.snackoverflow.domain.exceptions.UserNotFoundException;
import dhbw.ase.snackoverflow.domain.valueobjects.EmailAddress;
import dhbw.ase.snackoverflow.domain.valueobjects.Metric;
import dhbw.ase.snackoverflow.domain.valueobjects.VolumeMetric;
import dhbw.ase.snackoverflow.domain.valueobjects.VolumeUnit;
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

        user = new User(1, new EmailAddress("test@mail.de"), "Test User", "pw");
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