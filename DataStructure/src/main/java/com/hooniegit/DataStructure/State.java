package com.hooniegit.DataStructure;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum State {

    UNKNOWN(-1, "UNKNOWN"),
    STOPPED(0, "STOPPED"),
    RUNNING(1, "RUNNING"),
    PROBLEM(2, "PROBLEM");

    int value;
    String expression;

}
