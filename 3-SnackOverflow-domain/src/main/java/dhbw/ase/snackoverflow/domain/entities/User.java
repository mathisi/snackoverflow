package dhbw.ase.snackoverflow.domain.entities;

import dhbw.ase.snackoverflow.domain.valueobjects.EmailAddress;

import java.util.List;

public class User {

    private final int id;
    private EmailAddress email;
    private String userName;
    private String password;
    private List<Recipe> recipes;
    // string to be replaced by recipe java

    private String shoppingList;
    // string to be replaced by shopping list class

    public User(int id, EmailAddress email, String userName, String password) {
        this.id = id;
        this.email = email;
        this.userName = userName;
        this.password = password;
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
    public String getShoppingList() {
        return shoppingList;
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

    public void addRecipe(Recipe recipe) {
        this.recipes.add(recipe);
   }
}