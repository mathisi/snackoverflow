package dhbw.ase.snackoverflow;

import dhbw.ase.snackoverflow.application.repositories.DefaultUserRepository;
import dhbw.ase.snackoverflow.application.usecases.DefaultAddItemToShoppingList;
import dhbw.ase.snackoverflow.application.usecases.DefaultChangeUserName;
import dhbw.ase.snackoverflow.domain.entities.Ingredient;
import dhbw.ase.snackoverflow.domain.entities.IngredientCategory;
import dhbw.ase.snackoverflow.domain.entities.ShoppingList;
import dhbw.ase.snackoverflow.domain.entities.User;
import dhbw.ase.snackoverflow.domain.valueobjects.EmailAddress;
import dhbw.ase.snackoverflow.domain.valueobjects.Metric;
import dhbw.ase.snackoverflow.domain.valueobjects.VolumeMetric;
import dhbw.ase.snackoverflow.domain.valueobjects.VolumeUnit;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;
import java.util.Optional;
public class DefaultAddItemToShoppingListTest {
    private DefaultUserRepository userRepository = Mockito.mock(DefaultUserRepository.class);
    private DefaultAddItemToShoppingList addItemToShoppingList = new DefaultAddItemToShoppingList(userRepository);

    private User user;
    private ShoppingList shoppingList;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {

        user = new User(1,new EmailAddress("test@mail.de"), "Test User", "pw");
    }

    @Test
    void addItemSuccessful() {
        Metric metric = new VolumeMetric(10, VolumeUnit.LITER);
        Ingredient ingredient = new Ingredient(0, metric, "Tomato", IngredientCategory.BEVERAGES);
        Mockito.when(userRepository.searchByID(1)).thenReturn(Optional.of(user));

        ShoppingList result = addItemToShoppingList.addItem(1, ingredient);

        assertNotNull(result);
        assertTrue(result.getIngredients().contains(ingredient));
        Mockito.verify(userRepository, Mockito.times(1)).searchByID(1);
    }

    @Test
    void addItemUserNotFoundException() {
        Metric metric = new VolumeMetric(10, VolumeUnit.LITER);
        Ingredient ingredient = new Ingredient(0, metric, "Tomato", IngredientCategory.BEVERAGES);
        Mockito.when(userRepository.searchByID(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                addItemToShoppingList.addItem(1, ingredient)
        );

        assertEquals("User not found", exception.getMessage());
        Mockito.verify(userRepository, Mockito.times(1)).searchByID(1);
    }
}
