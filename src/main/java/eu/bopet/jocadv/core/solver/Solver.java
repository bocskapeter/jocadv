package eu.bopet.jocadv.core.solver;

import eu.bopet.jocadv.core.Constraint;
import eu.bopet.jocadv.core.features.Sketch;
import eu.bopet.jocadv.core.vector.Value;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Solver {

    private static final int MAX_ITERATIONS = 300;
    private static Random r = new Random();

    private Solver() {
    }

    public static boolean solve(Sketch sketch) {
        return BFGSSolver(sketch);
    }

    private static boolean BFGSSolver(Sketch sketch) {
        List<Value> xn = new ArrayList<>();
        List<Double> fxn = new ArrayList<>();
        List<Constraint> constraints = new ArrayList<>(sketch.getConstraints());
        for (Constraint constraint : constraints) {
            for (int i = 0; i < constraint.getArguments().size(); i++) {
                if (constraint.getArguments().get(i).getStatus() == Value.FLEXIBLE) {
                    xn.add(constraint.getArguments().get(i));
                }
            }
            fxn.add(constraint.getValue());
        }


        System.out.println(" Not converged in " + MAX_ITERATIONS + " steps.");
        return false;
    }


    private static boolean constraintSolver(Sketch sketch) {
        List<Value> xn = new ArrayList<>();
        List<Double> fxn = new ArrayList<>();
        Set<Constraint> constraints = sketch.getConstraints();
        for (Constraint constraint : constraints) {
            for (int i = 0; i < constraint.getArguments().size(); i++) {
                if (constraint.getArguments().get(i).getStatus() == Value.FLEXIBLE) {
                    xn.add(constraint.getArguments().get(i));
                }
            }
            fxn.add(constraint.getValue());
        }

        double[] xnp1 = new double[xn.size()];
        for (int i = 0; i < xn.size(); i++) {
            xnp1[i] = xn.get(i).getValue();
        }

        int step = 0;
        double fpx = 0.0;
        double error = 0.0;
        double newError = 10.0;
        double intervall = Value.TOLERANCE;
        while (step < MAX_ITERATIONS) {
            for (int i = 0; i < xn.size(); i++) {
                for (Constraint constraint : constraints) {
                    if (constraint.getArguments().contains(xn.get(i))) {
                        fpx = constraint.getDerivatives(intervall).get(constraint.getArguments().indexOf(xn.get(i)));
                        if (fpx > Value.TOLERANCE) {
                            xnp1[i] = xn.get(i).getValue() - (constraint.getValue() / fpx);
                        }
                    }
                }
            }
            error = 0.0;
            for (Constraint constraint : constraints) {
                error = error + Math.abs(constraint.getValue());
            }
            for (int i = 0; i < xn.size(); i++) {
                xn.get(i).setValue(xnp1[i]);
            }
            if (error < Value.TOLERANCE) {
                System.out.println(" Converged in " + step + " step(s).");
                return true;
            } else if (Math.abs(newError) - Math.abs(error) < Value.TOLERANCE) {
                intervall = intervall / 3.0;
                if (intervall < Value.TOLERANCE) {
                    intervall = 0.0000000001;
                }
                for (int i = 0; i < xn.size(); i++) {
                    xn.get(i).setValue(xn.get(i).getValue() + 0.001 * (r.nextDouble() - 0.5));
                }
            }
            newError = error;
            step++;
        }
        System.out.println(" Not converged in " + MAX_ITERATIONS + " steps.");
        return false;
    }
}
