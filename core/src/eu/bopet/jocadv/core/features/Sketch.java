package eu.bopet.jocadv.core.features;

import eu.bopet.jocadv.core.constraints.Constraint;
import eu.bopet.jocadv.core.geometries.datums.JoPlane;
import eu.bopet.jocadv.core.solver.Solver;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Sketch extends Feature {
    private final Set<Constraint> constraints;
    private boolean inEdit;
    private JoPlane sketchPlane;
    private Solver solver;

    private Sketch() {
        super();
        this.constraints = new LinkedHashSet<>();
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


    public void addConstraint(Constraint constraint) {
        Constraint c = isNewConstraint(constraint);

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

}
