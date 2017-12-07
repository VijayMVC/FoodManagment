package main.model.units;

public interface IMeasureable {

    public double  getValueOf(IMeasureable measureable) throws NotConvertableException;

    public double  getFractionOfBasicUnit();
}
