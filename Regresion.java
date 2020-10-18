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
        int size = 100;
        double [][] Xmatrix = new double[size][size];
        for (int i = 0; i < size; i++){
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
        double[] betaArray = new double[3];
        Matrix X = new Matrix(this.XMatrix);
        Matrix Y = new Matrix(this.yMDistArr, this.yMDistArr.length);
        // calculate coefficients
        Matrix XT = X.transpose();
        Matrix beta = (XT.times(X)).inverse().times(XT).times(Y);
        beta.print(12, 7);

        // turn jama matrix to normal array
        double [][] tempMatrix  = beta.getArray();
        betaArray = tempMatrix[0];
        return betaArray;
    }
}
