package dhbw.ase.snackoverflow.domain.valueobjects;

public enum VolumeUnit implements MetricUnit {
    MILLILITER("ml"),
    LITER("l"),
    CUP("cup"),
    TABLESPOON("tbsp"),
    TEASPOON("tsp"),
    PIECE("piece");

    private final String displayName;

    VolumeUnit(String displayName) {
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
