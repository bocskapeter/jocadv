package eu.bopet.jocadv.core.constraints;

import java.util.List;

public interface Constraint {
    /**
     * @return function value - f(xn)
     */
    double getValue();

    /**
     * @return partial derivative values - f'(xn)
     */
    List<GeometryDerivatives> getDerivatives();

}
