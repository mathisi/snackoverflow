package dhbw.ase.snackoverflow.domain.entities;

import dhbw.ase.snackoverflow.domain.valueobjects.EmailAddress;

import java.util.List;

public class User {

    private final int id;
    private EmailAddress email;
    private String userName;
    private String password;
    private List<String> recipes;
    // string to be replaced by recipe java


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

    public List<String> getRecipes() {
        return recipes;
    }

   public void addRecipe(String recipe) {
        this.recipes.add(recipe);
   }
}