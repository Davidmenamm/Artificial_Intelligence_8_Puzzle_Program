/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package regression;

import Jama.Matrix;

/**
 *
 * @author dfellig
 */
public class Main {

    public static void main(String[] args) {
        double[] BetaArray = new double[3];
        double[][] XArray = {{1, 7, 560},
        {1, 3, 220},
        {1, 3, 340},
        {1, 4, 80},
        {1, 6, 150},
        {1, 7, 330},
        {1, 2, 110},
        {1, 7, 210},
        {1, 30, 1460},
        {1, 5, 605},
        {1, 16, 688},
        {1, 10, 215},
        {1, 4, 255},
        {1, 6, 462},
        {1, 9, 448},
        {1, 10, 776},
        {1, 6, 200},
        {1, 7, 132},
        {1, 3, 36},
        {1, 17, 770},
        {1, 10, 140},
        {1, 26, 810},
        {1, 9, 450},
        {1, 8, 635},
        {1, 4, 150}
        };

        double[] yArray = {16.68,
            11.50,
            12.03,
            14.88,
            13.75,
            18.11,
            8.00,
            17.83,
            79.24,
            21.50,
            40.33,
            21.00,
            13.50,
            19.75,
            24.00,
            29.00,
            15.35,
            19.00,
            9.50,
            35.10,
            17.90,
            52.32,
            18.75,
            19.83,
            10.75};
        Matrix X = new Matrix(XArray);
        Matrix Y = new Matrix(yArray, yArray.length);
        Matrix XT = X.transpose();

        Matrix BETA = (XT.times(X)).inverse().times(XT).times(Y);
        BETA.print(12, 7);

    }
}
