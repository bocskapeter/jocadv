package eu.bopet.jocadv.core.features;

import eu.bopet.jocadv.core.Constraint;
import eu.bopet.jocadv.core.Geometry;
import eu.bopet.jocadv.core.constraints.Const;
import eu.bopet.jocadv.core.constraints.PointOnPlane;
import eu.bopet.jocadv.core.constraints.PointToPlaneDistance;
import eu.bopet.jocadv.core.geometries.Plane3D;
import eu.bopet.jocadv.core.solver.Solver;
import eu.bopet.jocadv.core.vector.Value;
import eu.bopet.jocadv.core.vector.Vector3D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Sketch implements Geometry, Feature{
    private final Set<Geometry> geometries;
    private final Set<Constraint> constraints;
    private final Set<Geometry> references;
    private boolean inEdit;
    private Plane3D sketchPlane;
    private Solver solver;

    private Sketch() {
        super();
        this.geometries = new LinkedHashSet<>();
        this.constraints = new LinkedHashSet<>();
        this.references = new LinkedHashSet<>();
        this.inEdit = true;
        this.solver = new Solver(this);
    }

    public Sketch(final Plane3D plane) {
        this();
        sketchPlane = plane;
        this.references.add(sketchPlane);
    }

    public void edit() {
        for (Geometry geometry : geometries) {
            geometry.setStatus(Value.FLEXIBLE);
        }
        for (Geometry reference : references) {
            reference.setStatus(Value.CONSTANT);
        }
        this.inEdit = true;
    }

    public void done() {
        for (Geometry geometry : geometries) {
            geometry.setStatus(Value.CONSTANT);
        }
        this.inEdit = false;
    }

    public boolean isInEdit() {
        return inEdit;
    }

    public void addGeometry(Geometry geometry) {
        this.geometries.add(geometry);
        geometry.setStatus(Value.FLEXIBLE);
        List<Vector3D> points = geometry.getPoints();
        for (Vector3D point : points) {
            Constraint c = new PointOnPlane(point, sketchPlane, Const.USER);
            addConstraint(c);
            int direction = sketchPlane.getN().getMainDirection();
            for (int i = 0; i < 3; i++) {
                if (i != direction) {
                    Plane3D plane = Plane3D.getMainPlane(i);
                    Value distance = new Value(Value.FLEXIBLE, plane.getDistanceToPoint(point));
                    Constraint dist = new PointToPlaneDistance(point, plane, distance, Const.SYSTEM);
                    addConstraint(dist);
                }
            }
        }
    }

    public void addConstraint(Constraint constraint) {
        Constraint c = isNewConstraint(constraint);
        if (c == null) {
            this.constraints.add(constraint);
            for (Geometry geometry : constraint.getGeometries()) {
                if (!geometries.contains(geometry)) {
                    references.add(geometry);
                    geometry.setStatus(Value.CONSTANT);
                }
            }
            if (constraint.getType() == Const.USER) {
                if (solver.solve()) {
                    constrained();
                } else {
                    restore();
                    constraints.remove(constraint);
                }
            }
        } else {

        }
    }

    private Constraint isNewConstraint(Constraint constraint) {
        for (Constraint c : constraints) {
            if (c.getClass().equals(constraint.getClass())) {
                List gOld = c.getGeometries();
                List gNew = constraint.getGeometries();
                if (new HashSet<>(gOld).equals(new HashSet(gNew))) return c;
            }
        }
        return null;
    }

    public void restore() {
        for (Geometry geometry : geometries) {
            geometry.restore();
        }
    }

    @Override
    public int getDOF() {
        return 0;
    }

    public void constrained() {
        for (Geometry geometry : geometries) {
            geometry.constrained();
        }
    }

    public void addReference(Geometry reference) {
        this.references.add(reference);
        reference.setStatus(Value.CONSTANT);
    }

    public Set<Geometry> getGeometries() {
        return geometries;
    }

    public Set<Constraint> getConstraints() {
        return constraints;
    }

    public Set<Geometry> getReferences() {
        return references;
    }

    @Override
    public List<Value> getValues() {
        return null;
    }

    public List<Vector3D> getPoints() {
        Set<Vector3D> result = new HashSet<>();
        for (Geometry g : geometries) {
            if (g instanceof Vector3D) {
                result.add((Vector3D) g);
            }
        }
        return (List<Vector3D>) result;
    }

    @Override
    public void setStatus(short status) {

    }

    @Override
    public String toString() {
        List<String> gS = new ArrayList<>();
        gS.add("\n");
        for (Geometry g : geometries) {
            gS.add(g.toString() + "\n");
        }
        List<String> cS = new ArrayList<>();
        for (Constraint c : constraints) {
            cS.add(c.toString() + "\n");
        }
        List<String> rS = new ArrayList<>();
        for (Geometry r : references) {
            rS.add(r.toString() + "\n");
        }
        String eS = inEdit ? "🔓" : "🔒";
        return "Sketch - " + eS + "\n - Geometries:\n" + gS + "\n - Constraints:\n" + cS + "\n - References:\n" + rS
                + "\n";
    }
}
