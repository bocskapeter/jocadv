package eu.bopet.jocadv.core.solver;

import eu.bopet.jocadv.core.constraints.Constraint;
import eu.bopet.jocadv.core.features.Sketch;
import eu.bopet.jocadv.core.vector.Value;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Solver {

    private static final int MAX_ITERATIONS = 300;
    private static final double INTERVAL = 0.0000001;
    private static final double ALPHA = 0.1;
    private static Random r = new Random();

    Sketch sketch;
    Value[] xn;
    int size;
    double[][] inverseBk;
    double[] dFxk;
    double[] pk;
    Map<Value, List<Constraint>> map;
    double[] alphaK;
    double[] sk;
    double[] dFxk1;
    double[] yk;

    public Solver(Sketch sketch) {
        this.sketch = sketch;
    }

    public boolean solve() {
        xn = getXn().toArray(new Value[0]);
        size = xn.length;
        inverseBk = getIdentity();
        map = getMap();

        for (int i = 0; i < 3; i++) {
            dFxk = getDFxK();
            pk = getPk();
            alphaK = getAlphaK();
            sk = getSk();
            updateXn();
            System.out.println("Sketch: " + i+" ... " + sketch.toString()+" DOF: " + sketch.getPoints().size()*3);
            dFxk1 = getDFxK();
            yk = getYk();
            inverseBk = updateInverseBk();
        }
        return true;
    }

    private double[][] updateInverseBk() {
        double[][] result = new double[size][size];
        double skTyk = getSkTYk();
        System.out.println("skTyk: " + skTyk);
        double[] ykTInverseBk = getYkTInverseBk();
        double ykTInverseBkYk = getYkTInverseBkYk(ykTInverseBk);
        double[][] skSkT = getSkSkT();
        double[][] first = getFirst(skTyk, ykTInverseBkYk, skSkT);
        double[][] inverseBkYkSkT = getInverseBkYkSkT();
        double[][] skYkTInverseBk = getSkYkTInverseBk();
        double[][] second = getSecond(skTyk, inverseBkYkSkT, skYkTInverseBk);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                result[i][j] = inverseBk[i][j] - first[i][j] + second[i][j];
            }
        }
        return result;
    }

    private double[][] getSecond(double skTyk, double[][] inverseBkYkSkT, double[][] skYkTInverseBk) {
        double[][] result = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                result[i][j] = (inverseBkYkSkT[i][j] + skYkTInverseBk[i][j]) / skTyk;
            }
        }
        return result;
    }

    private double[][] getSkYkTInverseBk() {
        double[][] result = new double[size][size];
        double[][] skYkT = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                skYkT[i][j] = sk[i] * yk[j];
            }
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                result[i][j] = 0.0;
                for (int k = 0; k < size; k++) {
                    result[i][j] = result[i][j] + skYkT[i][k] * inverseBk[k][j];
                }
            }
        }
        return result;
    }

    private double[][] getInverseBkYkSkT() {
        double[][] result = new double[size][size];
        double[] inverseBkYk = new double[size];
        for (int i = 0; i < size; i++) {
            inverseBkYk[i] = 0.0;
            for (int j = 0; j < size; j++) {
                inverseBkYk[i] = inverseBkYk[i] + inverseBk[i][j] * yk[j];
            }
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                result[i][j] = inverseBkYk[i] * sk[j];
            }
        }
        return result;
    }

    private double[][] getFirst(double skTyk, double ykTInverseBkYk, double[][] skSkT) {
        double[][] result = new double[size][size];
        double numerator = skTyk + ykTInverseBkYk;
        double denominator = skTyk * skTyk;
        double fraction = numerator / denominator;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                result[i][j] = skSkT[i][j] * fraction;
            }
        }
        return result;
    }

    private double[][] getSkSkT() {
        double[][] result = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                result[i][j] = sk[i] * sk[j];
            }
        }
        return result;
    }

    private double getYkTInverseBkYk(double[] ykTInverseBk) {
        double result = 0.0;
        for (int i = 0; i < size; i++) {
            result = result + ykTInverseBk[i] * yk[i];
        }
        return result;
    }

    private double[] getYkTInverseBk() {
        double[] result = new double[size];
        for (int i = 0; i < size; i++) {
            result[i] = 0;
            for (int j = 0; j < size; j++) {
                result[i] = result[i] + yk[j] * inverseBk[i][j];
            }
        }
        return result;
    }

    private double getSkTYk() {
        double result = 0.0;
        for (int i = 0; i < size; i++) {
            result = result + sk[i] * yk[i];
        }
        return result;
    }

    private double[] getYk() {
        double[] result = new double[size];
        for (int i = 0; i < size; i++) {
            result[i] = dFxk1[i] - dFxk[i];
        }
        return result;
    }

    private void updateXn() {
        for (int i = 0; i < size; i++) {
            xn[i].setValue(xn[i].getValue() + sk[i]);
        }
    }

    private double[] getSk() {
        double[] result = new double[size];
        for (int i = 0; i < size; i++) {
            result[i] = alphaK[i] * pk[i];
        }
        return result;
    }

    private double[] getAlphaK() {
        double[] result = new double[size];
        for (int i = 0; i < size; i++) {
            result[i] = ALPHA;
        }
        return result;
    }

    private double[] getPk() {
        double[] result = new double[size];
        for (int i = 0; i < size; i++) {
            result[i] = 0.0;
            for (int j = 0; j < size; j++) {
                result[i] = result[i] + inverseBk[i][j] * dFxk[i];
            }
        }
        return result;
    }

    private double[] getDFxK() {
        double[] result = new double[size];
        for (int i = 0; i < size; i++) {
            result[i] = getSumOfDFxn(i);
        }
        return result;
    }

    private double getSumOfDFxn(int i) {
        double result = 0.0;
        int index;
        List<Constraint> allFxn = map.get(xn[i]);
        for (Constraint c : allFxn) {
            List<Double> dFx = c.getDerivatives(INTERVAL);
            index = c.getArguments().indexOf(xn[i]);
            result = result + dFx.get(index);
        }
        return result;
    }

    private double[][] getIdentity() {
        double[][] identity = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == j) {
                    identity[i][j] = 1.0;
                } else {
                    identity[i][j] = 0.0;
                }
            }
        }
        return identity;
    }

    private Map<Value, List<Constraint>> getMap() {
        Map<Value, List<Constraint>> result = new LinkedHashMap<>();
        for (Value v : xn) {
            List<Constraint> list = new ArrayList<>();
            for (Constraint c : sketch.getConstraints()) {
                if (c.getArguments().contains(c) && !list.contains(c)) {
                    list.add(c);
                }
            }
            result.put(v, list);
        }
        return result;
    }

    private List<Value> getXn() {
        List<Value> xn = new ArrayList<>();
        for (Constraint constraint : sketch.getConstraints()) {
            for (int i = 0; i < constraint.getArguments().size(); i++) {
                Value argument = constraint.getArguments().get(i);
                if (argument.getStatus() == Value.FLEXIBLE) {
                    if (!xn.contains(argument)) {
                        xn.add(argument);
                    }
                }
            }
        }
        return xn;
    }


    public static boolean solve(Sketch sketch) {
        return constraintSolver(sketch);
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
