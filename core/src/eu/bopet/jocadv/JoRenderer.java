package eu.bopet.jocadv;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import eu.bopet.jocadv.core.features.Feature;
import eu.bopet.jocadv.core.geometries.JoLine;
import eu.bopet.jocadv.core.geometries.datums.JoAxis;
import eu.bopet.jocadv.core.geometries.datums.JoCoordinateSystem;
import eu.bopet.jocadv.core.geometries.datums.JoPlane;
import eu.bopet.jocadv.core.geometries.datums.JoPoint;

import java.util.ArrayList;
import java.util.List;

public class JoRenderer {
    List<Feature> features;
    JoCADv joCADv;
    List<ModelInstance> modelInstances;
    ModelBuilder modelBuilder;
    MeshPartBuilder meshPartBuilder;
    Camera camera;

    public JoRenderer(JoCADv joCADv, List<Feature> features, OrthographicCamera cam) {
        this.features = features;
        this.joCADv = joCADv;
        modelInstances = new ArrayList<>();
        modelBuilder = new ModelBuilder();
        camera = cam;
    }

    public List<ModelInstance> getModelInstances() {
        return modelInstances;
    }

    public void renderFeatures() {
        for (Feature feature : features) {
            if (feature instanceof JoPoint) {
                modelInstances.add(renderPoint((JoPoint) feature));
            }
            if (feature instanceof JoAxis) {
                modelInstances.add(renderAxis((JoAxis) feature, Color.CORAL));
            }
            if (feature instanceof JoCoordinateSystem) {
                JoCoordinateSystem coordinateSystem = (JoCoordinateSystem) feature;
                modelInstances.add(renderPoint(coordinateSystem.getOrigin()));
                modelInstances.add(renderAxis(coordinateSystem.getxAxis(), Color.RED));
                modelInstances.add(renderAxis(coordinateSystem.getyAxis(), Color.GREEN));
                modelInstances.add(renderAxis(coordinateSystem.getzAxis(), Color.BLUE));
                modelInstances.add(renderPlane(coordinateSystem.getXyPlane()));
                modelInstances.add(renderPlane(coordinateSystem.getXzPlane()));
                modelInstances.add(renderPlane(coordinateSystem.getYzPlane()));
            }
            if (feature instanceof JoLine) {
                JoLine joLine = (JoLine) feature;
                modelInstances.add(renderLine(joLine.getP1().getVector().getVector3(),
                        joLine.getP2().getVector().getVector3(), Color.BROWN));
            }
            if (feature instanceof JoPlane) {
                modelInstances.add(renderPlane((JoPlane) feature));
            }
        }
    }

    private ModelInstance renderLine(Vector3 from, Vector3 to, Color color) {
        modelBuilder.begin();
        meshPartBuilder = modelBuilder.part("line", 1, 3, new Material());
        meshPartBuilder.setColor(color);
        meshPartBuilder.line(from, to);
        Model line = modelBuilder.end();
        return new ModelInstance(line);
    }


    private ModelInstance renderPlane(JoPlane joPlane) {
        Vector3 p1 = joPlane.getP1().getVector3();
        Vector3 p2 = joPlane.getP2().getVector3();
        Vector3 p3 = joPlane.getP3().getVector3();
        Vector3 p4 = joPlane.getP4().getVector3();
        Vector3 normal = joPlane.getNormal().getVector3();
        modelBuilder.begin();
        meshPartBuilder = modelBuilder.part("plane", 1, 3, new Material());
        meshPartBuilder.setColor(Color.BROWN);
        meshPartBuilder.rect(p1, p3, p4, p2, normal);
        Model plane = modelBuilder.end();

        return new ModelInstance(plane);
    }

    private ModelInstance renderAxis(JoAxis joAxis, Color color) {
        return renderLine(joAxis.getP1().getVector3(), joAxis.getP2().getVector3(), color);
    }

    private ModelInstance renderPoint(JoPoint joPoint) {
        Vector3 from = joPoint.getVector().getVector3();
        float r = 0.3f;//joCADv.getZoomFactor()*100000000;
        Vector3 v1 = from.cpy().add(Vector3.X.scl(r));
        Vector3 v2 = from.cpy().add(Vector3.Y.scl(r));
        Vector3 v3 = from.cpy().add(Vector3.Z.scl(r));
        Vector3 v4 = from.cpy().add(Vector3.X.scl(-r));
        Vector3 v5 = from.cpy().add(Vector3.Y.scl(-r));
        Vector3 v6 = from.cpy().add(Vector3.Z.scl(-r));
        modelBuilder.begin();
        meshPartBuilder = modelBuilder.part("point", 1, 3, new Material());
        meshPartBuilder.setColor(Color.BROWN);
        meshPartBuilder.triangle(v1,v2,v3);
        meshPartBuilder.triangle(v2,v3,v4);
        meshPartBuilder.triangle(v4,v5,v6);
        meshPartBuilder.triangle(v5,v6,v1);
        Model point = modelBuilder.end();
        return new ModelInstance(point);
    }
}
