// mycalc.aidl
package com.example.master.testaidl;

// Declare any non-default types here with import statements

interface MyCalc
{
    int Add(in int a, in int b);
    int Mul(in int a, in int b);
    int Sub(in int a, in int b);
    int Div(in int a, in int b);
}
