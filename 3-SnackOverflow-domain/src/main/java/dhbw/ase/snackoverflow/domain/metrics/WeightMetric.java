package dhbw.ase.snackoverflow.domain.metrics;

public class WeightMetric implements Metric {
    private double amount;
    private WeightUnit unit;

    public WeightMetric(double amount, WeightUnit unit) {
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
