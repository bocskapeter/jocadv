package eu.bopet.jocadv.core;

import eu.bopet.jocadv.core.vector.Value;
import eu.bopet.jocadv.core.vector.JoVector;
import java.util.List;

public interface Geometry {

    /**
     *
     * @return vector of values describing the geometry
     */
    List<Value> getValues();

    List<JoVector> getPoints();

    void setStatus(short status);

    void constrained();

    void restore();

    int getDOF();

}
