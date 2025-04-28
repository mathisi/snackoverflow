package dhbw.ase.snackoverflow.domain.ingredients;

import dhbw.ase.snackoverflow.domain.metrics.Metric;

public class Ingredient {
    private int id;
    private Metric metric;
    private String name;
    private IngredientCategory category;

    public Ingredient(int id, Metric metric, String name, IngredientCategory category) {
        this.id = id;
        this.metric = metric;
        this.name = name;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Metric getMetric() {
        return metric;
    }

    public void setMetric(Metric metric) {
        this.metric = metric;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IngredientCategory getCategory() {
        return category;
    }

    public void setCategory(IngredientCategory category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", metric=" + metric +
                ", name='" + name + '\'' +
                '}';
    }
}
