package com.hooniegit.DataStructure.EDA.Reference.DataReport;

import java.util.List;

/**
 * EDA 통신의 실질적인 데이터 운반체 (Data Report)
 * 초당 수십~수백 번 생성되므로 객체가 무거우면 안 됩니다.
 */
public record EdaDataReport(
        String reportId,            // UUID 등
        String dcpId,               // 어떤 수집 계획(DCP)에 의해 생성된 리포트인지
        long generatedTimestamp,    // 리포트 생성 시간 (Epoch Nanos)
        List<TraceGroup> traces,    // 주기적 수집 데이터 묶음
        List<EventGroup> events     // 이벤트 수집 데이터 묶음
) {}
