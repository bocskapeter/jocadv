package eu.bopet.jocadv.core.constraints;

import eu.bopet.jocadv.core.Geometry;

import java.util.List;

public interface Constraint {

    List<Geometry> getGeometries();

    /**
     *
     * @return function value - f(xn)
     */
    double getValue();

    /**
     * @param interval interval size
     * @return partial derivative values - f'(xn)
     */
    List<Double> getDerivatives(double interval);

    /**
     *
     * @param type
     */
    void setType(short type);

    /**
     *
     * @return
     */
    short getType();
}
