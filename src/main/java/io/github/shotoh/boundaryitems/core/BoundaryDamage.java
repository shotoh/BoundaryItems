package io.github.shotoh.boundaryitems.core;

public class BoundaryDamage {
    private double damage;
    private double additive;

    public BoundaryDamage(double damage) {
        this(damage, 1);
    }

    public BoundaryDamage(double damage, double additive) {
        this.damage = damage;
        this.additive = additive;
    }

    public double getDamage() {
        return damage;
    }
    public void setDamage(double damage) {
        this.damage = damage;
    }

    public void addDamage(double damage) {
        this.damage += damage;
    }

    public double getAdditive() {
        return additive;
    }

    public void addAdditive(double additive) {
        this.additive += additive;
    }

    public double calculate() {
        return Math.max(damage * additive, 1);
    }
}
