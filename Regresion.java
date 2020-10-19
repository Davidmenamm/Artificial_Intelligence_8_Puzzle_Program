/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company;

// imports
import Jama.Matrix;

import java.util.Arrays;

/**
 *
 * @author dfellig
 */
public class Regresion {

    // atributes
    double [][] XMatrix;
    double [] yMDistArr;

    /**
     * Constructor
     * @param x1
     * @param x2
     * @param yMDistArr
     */
    public Regresion( double [] x1, double [] x2, double [] yMDistArr) {
        int numRows = 100;
        int numCols = 3;
        double [][] Xmatrix = new double[numRows][numCols];
        for (int i = 0; i < numRows; i++){
            double first = 1; // first column for heuristic is full of 1's
            double second = x1[i];
            double third = x2[i];
            Xmatrix[i][0] = first;
            Xmatrix[i][1] = second;
            Xmatrix[i][2] = third;
        }
        // assign values
        this.XMatrix = Xmatrix;
        this.yMDistArr = yMDistArr;
    }


    /**
     *  Get coefficients for learned heuristic
     *  Formula to get coefficients
     *  X matrix first col of 1's, second col of x1, third col of x2
     *  Coefficients = ((X.transpose * X)^-1)*X.transpose*) * Y_vector
     */
    public double [] getCoefficients(){
        // test printing
        double[][] array = {{1.,2.,3},{4.,5.,6.},{7.,8.,10.},{2.,11.,3.}};
        Matrix A = new Matrix(array);
//        System.out.println("A");
//        System.out.println(A);
//        A.print(12, 7);


        double[] betaArray = new double[3];
//        System.out.println("Matrix is!");
//        System.out.println(this.XMatrix.length);
//        for (int i=0; i < 100; i++){
////            System.out.println("First loop");
//            for (int j=0; i<3; i++){
////                System.out.println("Second loop");
////                System.out.println(this.XMatrix[i][j]);
//            }
//        }

        // test arrays
//        double[][] XArrayTest = {{1, 7, 560},
//                {1, 3, 220},
//                {1, 3, 340},
//                {1, 4, 80},
//                {1, 6, 150},
//                {1, 7, 330},
//                {1, 2, 110},
//                {1, 7, 210},
//                {1, 30, 1460},
//                {1, 5, 605},
//                {1, 16, 688},
//                {1, 10, 215},
//                {1, 4, 255},
//                {1, 6, 462},
//                {1, 9, 448},
//                {1, 10, 776},
//                {1, 6, 200},
//                {1, 7, 132},
//                {1, 3, 36},
//                {1, 17, 770},
//                {1, 10, 140},
//                {1, 26, 810},
//                {1, 9, 450},
//                {1, 8, 635},
//                {1, 4, 150}
//        };

//        double[] yArrayTest = {16.68,
//                11.50,
//                12.03,
//                14.88,
//                13.75,
//                18.11,
//                8.00,
//                17.83,
//                79.24,
//                21.50,
//                40.33,
//                21.00,
//                13.50,
//                19.75,
//                24.00,
//                29.00,
//                15.35,
//                19.00,
//                9.50,
//                35.10,
//                17.90,
//                52.32,
//                18.75,
//                19.83,
//                10.75};

        Matrix X = new Matrix(this.XMatrix);
//        Matrix X = new Matrix(XArrayTest);
        X.print(12, 7);
        Matrix Y = new Matrix(this.yMDistArr, this.yMDistArr.length);
//        Matrix Y = new Matrix(yArrayTest, yArrayTest.length);
        // calculate coefficients
        Matrix XT = X.transpose();
//        XT.print(12, 7);
        Matrix betaStep0 = (XT.times(X));
//        betaStep0.print(12, 7);
        Matrix betaStep1 = betaStep0.inverse();
//        betaStep1.print(12, 7);
        Matrix betaStep2 = betaStep1.times(XT);
//        betaStep2.print(12, 7);
//        Y.print(12, 7);
        Matrix betaStep3 = betaStep2.times(Y);
//        betaStep3.print(12, 7);

        // turn jama matrix to normal array
        double [][] tempMatrix  = betaStep3.getArray();
        betaArray[0] = tempMatrix[0][0];
        betaArray[1] = tempMatrix[1][0];
        betaArray[2] = tempMatrix[2][0];

//        System.out.println("beta matrix");
//        String betaMar = Arrays.toString(tempMatrix);
//        System.out.println(betaMar);
//
//        System.out.println("beta arraayyyy");
//        String betaArrStr = Arrays.toString(betaArray);
//        System.out.println(betaArrStr);
        return betaArray;
    }
}
