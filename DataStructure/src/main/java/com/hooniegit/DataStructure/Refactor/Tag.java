package com.hooniegit.DataStructure.Refactor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Tag<T> {

    private int id;
    private T value;

}
