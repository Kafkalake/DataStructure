package com.hooniegit.DataStructure.EDA.Reference.CEM;

/**
 * 특정 노드에서 측정 가능한 단일 센서/상태값 (E125 규격)
 */
public record DataVariable(
        String paramId,      // 예: "GasFlow_Ar"
        String paramName,
        DataType dataType,
        String unit          // 예: "sccm", "Celsius"
) {}
