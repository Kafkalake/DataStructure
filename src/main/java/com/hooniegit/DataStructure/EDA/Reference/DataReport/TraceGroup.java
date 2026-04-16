package com.hooniegit.DataStructure.EDA.Reference.DataReport;

import java.util.Map;

/**
 * 동일한 타임스탬프에 찍힌 센서 데이터들의 집합
 */
public record TraceGroup(
        long sampleTimestamp,             // 센싱된 정확한 시간
        Map<String, Object> values        // Key: paramId, Value: 센서값
) {}
