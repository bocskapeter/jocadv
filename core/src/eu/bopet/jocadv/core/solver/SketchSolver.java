package eu.bopet.jocadv.core.solver;

import org.apache.commons.math3.analysis.MultivariateMatrixFunction;
import org.apache.commons.math3.analysis.MultivariateVectorFunction;

public class SketchSolver {

    public MultivariateVectorFunction getModelFunction(){
        return point -> {
            System.out.println("point length: " + point.length);
            return new double[0];
        };
    }

    public MultivariateMatrixFunction getModelFunctionJacobian(){
        return point -> {
            System.out.println("point length: " + point.length);
            return new double[0][];
        };
    }
}
