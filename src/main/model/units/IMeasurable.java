package main.model.units;
/**
 * Provides functionality to keep proper value while changing measures.
 */
public interface IMeasurable {
    /**
    * Give old-new measure ratio
    *
    * @param measurable new measure
    * @return new-old ratio
    */
     double getValueIn(IMeasurable measurable) throws NotConvertibleException;

    /**
    * get measure - basic measure ratio
    *
    * @return measure-basic measure ratio
    */
     double  getFractionOfBasicUnit();
}
