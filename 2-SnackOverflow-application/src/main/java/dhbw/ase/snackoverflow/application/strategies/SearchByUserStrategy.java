package dhbw.ase.snackoverflow.application.strategies;

import dhbw.ase.snackoverflow.domain.recipes.Recipe;
import dhbw.ase.snackoverflow.domain.users.User;
import dhbw.ase.snackoverflow.domain.users.UserRepository;

import java.util.List;
import java.util.Optional;

public class SearchByUserStrategy implements RecipeSearchStrategy {

    private final UserRepository userRepository;

    public SearchByUserStrategy(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<Recipe> search(RecipeSearchContext context) {
        User user = context.getUser();
        Optional<User> foundUser = userRepository.searchByID(user.getId());
        if (!foundUser.isPresent()) {
            throw new IllegalArgumentException("User not found");
        }
        List<Recipe> userRecipes = foundUser.get().getRecipes();
        if (userRecipes.isEmpty()) {
            throw new IllegalArgumentException("No recipes found for user " + user);
        }
        return userRecipes;
    }
}
