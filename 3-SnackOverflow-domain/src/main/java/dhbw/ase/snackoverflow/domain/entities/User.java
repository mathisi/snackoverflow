package dhbw.ase.snackoverflow.domain.entities;

import dhbw.ase.snackoverflow.domain.valueobjects.EmailAddress;

import java.util.ArrayList;
import java.util.List;

public class User {

    private final int id;
    private EmailAddress email;
    private String userName;
    private String password;
    private List<Recipe> recipes;
    private ShoppingList shoppingList;


    private User(Builder builder) {
        this.id = builder.id;
        this.email = builder.email;
        this.userName = builder.userName;
        this.password = builder.password;
        this.recipes = builder.recipes != null ? builder.recipes : new ArrayList<>();
        this.shoppingList = builder.shoppingList != null ? builder.shoppingList : new ShoppingList(0);
    }

    public int getId() {
        return id;
    }

    public EmailAddress getEmail() {
        return email;
    }

    public void setEmail(EmailAddress email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public ShoppingList getShoppingList() {
        return shoppingList;
    }

    public void addRecipe(Recipe recipe) {
        this.recipes.add(recipe);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email=" + email +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", recipes=" + recipes +
                ", shoppingList='" + shoppingList + '\'' +
                '}';
    }

    public static class Builder {
        private int id;
        private EmailAddress email;
        private String userName;
        private String password;
        private List<Recipe> recipes;
        private ShoppingList shoppingList;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder email(EmailAddress email) {
            this.email = email;
            return this;
        }

        public Builder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder recipes(List<Recipe> recipes) {
            this.recipes = recipes;
            return this;
        }

        public Builder shoppingList(ShoppingList shoppingList) {
            this.shoppingList = shoppingList;
            return this;
        }

        public User build() {

            return new User(this);
        }
    }
}
