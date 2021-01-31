package eu.bopet.jocadv.core.solver;

import eu.bopet.jocadv.core.features.Sketch;

import java.util.Random;

public class Solver {

    private static final int MAX_ITERATIONS = 300;
    private static Random r = new Random();

    Sketch sketch;


    public Solver(Sketch sketch) {
        this.sketch = sketch;
    }

    public boolean solve() {

        return true;
    }



    public static boolean solve(Sketch sketch) {
        return constraintSolver(sketch);
    }

    private static boolean constraintSolver(Sketch sketch) {

        return false;
    }
}
