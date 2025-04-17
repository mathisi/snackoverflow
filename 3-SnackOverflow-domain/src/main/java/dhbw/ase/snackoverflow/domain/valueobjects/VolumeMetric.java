package dhbw.ase.snackoverflow.domain.valueobjects;

public class VolumeMetric implements Metric {
    private double amount;
    private VolumeUnit unit;

    public VolumeMetric(double amount, VolumeUnit unit) {
        this.amount = amount;
        this.unit = unit;
    }

    @Override
    public double getAmount() {
        return amount;
    }

    @Override
    public String getUnit() {
        return unit.getDisplayName();
    }
}
