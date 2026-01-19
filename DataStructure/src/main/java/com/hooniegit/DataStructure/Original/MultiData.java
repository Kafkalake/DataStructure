package com.hooniegit.DataStructure.Original;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MultiData<T> {

    private Map<String, String> header;
    private List<Data<T>> body;

}

