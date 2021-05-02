package eu.bopet.jocadv;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import eu.bopet.jocadv.core.features.Edge;
import eu.bopet.jocadv.core.features.Face;
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
                renderPoint((JoPoint) feature);
            }
            if (feature instanceof JoAxis) {
                modelInstances.add(renderAxis((JoAxis) feature));
            }
            if (feature instanceof JoCoordinateSystem) {
                JoCoordinateSystem coordinateSystem = (JoCoordinateSystem) feature;
                renderPoint(coordinateSystem.getOrigin());
                modelInstances.add(renderAxis(coordinateSystem.getXAxis()));
                modelInstances.add(renderAxis(coordinateSystem.getYAxis()));
                modelInstances.add(renderAxis(coordinateSystem.getZAxis()));
                modelInstances.add(renderPlane(coordinateSystem.getXyPlane()));
                modelInstances.add(renderPlane(coordinateSystem.getXzPlane()));
                modelInstances.add(renderPlane(coordinateSystem.getYzPlane()));
            }
            if (feature instanceof JoLine) {
                JoLine joLine = (JoLine) feature;
                modelInstances.add(renderLine(joLine.getP1().getVector().getVector3(),
                        joLine.getP2().getVector().getVector3(), JoColors.LINE));
            }
            if (feature instanceof JoPlane) {
                modelInstances.add(renderPlane((JoPlane) feature));
            }
            if (feature instanceof Face) {
                for (Edge edge: ((Face)feature).getEdges()){
                    modelInstances.add(renderLine(edge.getPointA().getVector().getVector3(),
                            edge.getPointB().getVector().getVector3(), JoColors.EDGE));
                }
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
        String name = joPlane.getName();
        if (name == null) {
            name = "plane";
        }
        Material material = new Material();

        modelBuilder.begin();
        meshPartBuilder = modelBuilder.part(name, GL20.GL_TRUE, 3, material);
        if (joPlane.isSelected()) {
            meshPartBuilder.setColor(JoColors.PLANE_SELECTED);
        } else {
            meshPartBuilder.setColor(JoColors.PLANE);
        }
        meshPartBuilder.rect(p1, p2, p4, p3, normal);

/*        ColorAttribute colorAttribute = ColorAttribute.createDiffuse(JoColors.PLANE_FACE);
        material = new Material(colorAttribute);
        material.set(new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA));
        meshPartBuilder = modelBuilder.part(name, GL20.GL_TRIANGLES,
                VertexAttributes.Usage.Position
                , material);
        meshPartBuilder.rect(p1, p3, p4, p2, normal);*/

        Model plane = modelBuilder.end();
        return new ModelInstance(plane);
    }

    private ModelInstance renderAxis(JoAxis joAxis) {
        Color color;
        if (joAxis.isSelected()) {
            color = JoColors.AXIS_SELECTED;
        } else {
            color = JoColors.AXIS;
        }
        return renderLine(joAxis.getP1().getVector3(), joAxis.getP2().getVector3(), color);
    }

    private void renderPoint(JoPoint joPoint) {
        joCADv.addPoint(joPoint);
    }
}
