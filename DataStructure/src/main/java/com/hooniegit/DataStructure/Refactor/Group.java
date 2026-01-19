package com.hooniegit.DataStructure.Refactor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Group<T> {

    private Tag<T>[] group;
    private String timestamp;

}
