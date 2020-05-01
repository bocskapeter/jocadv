import eu.bopet.jocadv.core.constraints.Const;
import eu.bopet.jocadv.core.constraints.PointToPlaneDistance;
import eu.bopet.jocadv.core.features.Sketch;
import eu.bopet.jocadv.core.geometries.Line3D;
import eu.bopet.jocadv.core.geometries.Plane3D;
import eu.bopet.jocadv.core.vector.Value;
import eu.bopet.jocadv.core.vector.Vector3D;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class SketchTest {

    @Test
    public void newSketch(){
        Sketch s = new Sketch(Plane3D.XY);
        assert s.getReferences().contains(Plane3D.XY);
        Random r = new Random();
        Vector3D point1 = new Vector3D(r.nextDouble(),r.nextDouble(),r.nextDouble());
        s.addGeometry(point1);
        Vector3D point2 = new Vector3D(5+r.nextDouble(),r.nextDouble(),r.nextDouble());
        s.addGeometry(point2);
        Line3D line = new Line3D(point1,point2);
        s.addGeometry(line);
        PointToPlaneDistance distance1 = new PointToPlaneDistance(point2,Plane3D.YZ,new Value(Value.USER_INPUT,5.0), Const.USER);
        s.addConstraint(distance1);
    }

}
