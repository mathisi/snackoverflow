package dhbw.ase.snackoverflow.domain.entities;

import dhbw.ase.snackoverflow.domain.valueobjects.Metric;

public class Ingredient {
    private int id;
    private Metric metric;
    private String name;

    public Ingredient(int id, Metric metric, String name) {
        this.id = id;
        this.metric = metric;
        this.name = name;
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

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", metric=" + metric +
                ", name='" + name + '\'' +
                '}';
    }
}
