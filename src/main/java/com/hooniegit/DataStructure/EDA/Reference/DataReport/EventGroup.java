package com.hooniegit.DataStructure.EDA.Reference.DataReport;

import java.util.Map;

/**
 * 특정 이벤트 발생 시점의 스냅샷 데이터
 */
public record EventGroup(
        String eventId,
        long eventTimestamp,
        Map<String, Object> contextValues // 이벤트 발생 당시의 장비 State, Step, 센서값 등
) {}