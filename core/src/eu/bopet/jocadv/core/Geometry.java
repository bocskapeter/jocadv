package eu.bopet.jocadv.core;

import eu.bopet.jocadv.core.vector.Value;
import eu.bopet.jocadv.core.vector.Vector3D;
import java.util.List;

public interface Geometry {

    /**
     *
     * @return vector of values describing the geometry
     */
    List<Value> getValues();

    List<Vector3D> getPoints();

    void setStatus(short status);

    void constrained();

    void restore();

    int getDOF();

}
