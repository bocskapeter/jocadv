package eu.bopet.jocadv.core;

import eu.bopet.jocadv.core.features.Sketch;
import eu.bopet.jocadv.core.geometries.Line3D;
import eu.bopet.jocadv.core.geometries.Plane3D;
import eu.bopet.jocadv.core.vector.Value;
import eu.bopet.jocadv.core.vector.Vector3D;

/**
 *
 * @author bocskapeter
 */
public class Main {

    public static void main(String[] args) {
        Sketch s = new Sketch(Plane3D.XY);
        System.out.println("Sketch: " + s.toString());
        Vector3D point = new Vector3D(Value.ONE,Value.ZERO,Value.ZERO);
        Line3D line = new Line3D(Vector3D.ORIGIN,point);
        s.addGeometry(line);
        System.out.println("Sketch: " + s.toString());
    }
}
