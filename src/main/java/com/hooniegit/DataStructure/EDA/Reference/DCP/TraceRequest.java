package com.hooniegit.DataStructure.EDA.Reference.DCP;

import java.util.List;

/**
 * 주기적(Trace) 데이터 수집 조건 (예: 100ms 마다 온도/압력 전송)
 */
public record TraceRequest(
        String traceId,
        long intervalMs,                  // 수집 주기 (밀리초)
        long groupSize,                   // 몇 번 수집하고 한 번에 보낼 것인가 (버퍼링)
        List<String> targetParamIds       // 수집할 센서 ID 목록
) {}