package eu.bopet.jocadv.core.constraints;

import java.util.List;

public interface Constraint {
    /**
     * @return function value - f(xn)
     */
    double getValue();

    /**
     * @param interval interval size
     * @return partial derivative values - f'(xn)
     */
    List<Double> getDerivatives(double interval);

}
