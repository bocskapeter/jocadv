/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.bopet.jocadv;

import eu.bopet.jocadv.core.features.Sketch;
import eu.bopet.jocadv.core.geometries.datums.JoPlane;

/**
 *
 * @author bocskapeter
 */
public class Main {

    public static void main(String[] args) {
        Sketch s = new Sketch(JoPlane.XY);
        System.out.println("Sketch: " + s.toString());
    }

}
