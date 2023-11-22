package com.example.myfirstapp;

import java.util.Scanner;

public class POStChecker {
    public boolean checkEligibility(double a67, double a48, double a22, double a31, double a37) {
        double gpa = (a67 + a48 + a22 + a31 + a37) / 5;
        int cMinusCount = 0;
        if (gpa < 2.5) {
            return false;
        }
        if (a48 < 3.0) {
            return false;
        }
        if (a67 >= 1.7) {
            cMinusCount++;
        }
        if (a22 >= 1.7) {
            cMinusCount++;
        }
        if (a37 >= 1.7) {
            cMinusCount++;
        }
        return (cMinusCount >= 2);
    }
}

