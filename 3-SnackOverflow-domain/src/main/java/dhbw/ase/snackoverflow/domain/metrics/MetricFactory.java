package dhbw.ase.snackoverflow.domain.metrics;

public class MetricFactory {
    public static Metric fromString(String metric) {
        String[] parts = metric.split(" ");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid metric format. Expected format: '<amount> <unit>'");
        }

        double amount;
        try {
            amount = Double.parseDouble(parts[0]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid amount: " + parts[0]);
        }

        String unit = parts[1].toUpperCase();

        for (WeightUnit weightUnit : WeightUnit.values()) {
            if (weightUnit.name().equals(unit)) {
                return new WeightMetric(amount, weightUnit);
            }
        }

        for (VolumeUnit volumeUnit : VolumeUnit.values()) {
            if (volumeUnit.name().equals(unit)) {
                return new VolumeMetric(amount, volumeUnit);
            }
        }

        throw new IllegalArgumentException("Unknown unit: " + unit);
    }
}
