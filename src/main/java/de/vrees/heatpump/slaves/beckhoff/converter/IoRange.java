package de.vrees.heatpump.slaves.beckhoff.converter;

public class IoRange<T> {

    public T getMin() {
        return min;
    }

    public T getMax() {
        return max;
    }

    private T min;

    private T max;

    public IoRange(T min, T max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public String toString() {
        return "IoRange [min=" + min + ", max=" + max + "]";
    }
}
