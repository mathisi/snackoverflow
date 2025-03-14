package dhbw.ase.snackoverflow.domain.valueobjects;

public enum VolumeUnit {
    MILLILITER("ml"),
    LITER("l"),
    CUP("cup"),
    TABLESPOON("tbsp"),
    TEASPOON("tsp");

    private final String displayName;

    VolumeUnit(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
