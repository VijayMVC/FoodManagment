package main.model.units;

public interface IMeasurable {

    public double getValueIn(IMeasurable measureable) throws NotConvertableException;

    public double  getFractionOfBasicUnit();
}
