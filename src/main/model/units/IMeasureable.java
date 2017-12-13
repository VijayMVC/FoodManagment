package main.model.units;

public interface IMeasureable {

    public double getValueIn(IMeasureable measureable) throws NotConvertableException;

    public double  getFractionOfBasicUnit();
}
