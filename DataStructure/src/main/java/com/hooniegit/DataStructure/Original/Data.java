package com.hooniegit.DataStructure.Original;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import com.hooniegit.DataStructure.State;

@Getter
@Setter
@AllArgsConstructor
public class Data<T> {

    private int parameter;
    private int tool;

    private T value;

    private State state;
    private String step;
    private String condition;
    private boolean pmmode;

}
