package dhbw.ase.snackoverflow.domain.ingredients;

public enum IngredientCategory {
    FRUITS_VEGETABLES("Fruits & Vegetables"),
    DAIRY("Dairy"),
    MEAT_FISH("Meat & Fish"),
    BAKERY("Bakery"),
    BEVERAGES("Beverages"),
    DRY_GOODS("Dry Goods"),
    FROZEN("Frozen"),
    SPICES_SAUCES("Spices & Sauces"),
    MISCELLANEOUS("Miscellaneous");

    private final String displayName;

    IngredientCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
