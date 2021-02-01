package eu.bopet.jocadv.core.features;

import eu.bopet.jocadv.core.Geometry;
import eu.bopet.jocadv.core.constraints.Const;
import eu.bopet.jocadv.core.constraints.Constraint;
import eu.bopet.jocadv.core.geometries.datums.JoPlane;
import eu.bopet.jocadv.core.solver.Solver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Sketch extends Feature implements Geometry{
    private final Set<Geometry> geometries;
    private final Set<Constraint> constraints;
    private final Set<Geometry> references;
    private boolean inEdit;
    private JoPlane sketchPlane;
    private Solver solver;

    private Sketch() {
        super();
        this.geometries = new LinkedHashSet<>();
        this.constraints = new LinkedHashSet<>();
        this.references = new LinkedHashSet<>();
        this.inEdit = true;
        this.solver = new Solver(this);
    }

    public Sketch(final JoPlane plane) {
        this();
        sketchPlane = plane;
    }

    public void edit() {
        this.inEdit = true;
    }

    public void done() {
        this.inEdit = false;
    }

    public boolean isInEdit() {
        return inEdit;
    }

    public void addGeometry(Geometry geometry) {
        this.geometries.add(geometry);

    }

    public void addConstraint(Constraint constraint) {
        Constraint c = isNewConstraint(constraint);
        if (c == null) {
            this.constraints.add(constraint);
            for (Geometry geometry : constraint.getGeometries()) {
                if (!geometries.contains(geometry)) {
                    references.add(geometry);
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

    public void constrained() {
        for (Geometry geometry : geometries) {
            geometry.constrained();
        }
    }

    public void addReference(Geometry reference) {
        this.references.add(reference);
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
