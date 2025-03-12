package dhbw.ase.snackoverflow.domain.valueobjects;

public enum WeightUnit {
    GRAM("g"),
    KILOGRAM("kg"),
    OUNCE("oz"),
    POUND("lb");

    private final String displayName;

    WeightUnit(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
