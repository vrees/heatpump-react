package de.vrees.heatpump.slaves.beckhoff.converter;

/**
 * Converter for analog input Values (4..20mA, -10V..+10V)
 * @author vrees
 *
 */
public class RawConverterAnalog16Bit {

    public static final int HIGH_VAL = 0x7FFF;

    public IoRange<Float> range;

    public RawConverterAnalog16Bit(IoRange<Float> range) {
        this.range = range;
    }

    public Float inputValueFromRaw(short rawVal) {
        return (range.getMax() - range.getMin()) * rawVal / HIGH_VAL + range.getMin();
    }

    public short outputToRaw(Float value) {
        if (value > range.getMax()) {
            value = range.getMax();
        }

        if (value < range.getMin()) {
            value = range.getMin();
        }

        value -= range.getMin();

        float rawVal = value / (range.getMax() - range.getMin()) * HIGH_VAL;

        return (short) rawVal;
    }

    @Override
    public String toString() {
        return "RawConverterAnalog16Bit [range=" + range + "]";
    }

}
