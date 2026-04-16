package com.hooniegit.DataStructure.EDA.Reference.DCP;

import java.util.List;

/**
 * 설비에 어떤 데이터를 어떻게 수집할지 지시하는 수집 계획서 (SEMI E134)
 */
public record DataCollectionPlan(
        String dcpId,             // 구독 ID
        String clientId,          // 데이터를 수신할 호스트 ID
        DcpState state,           // ACTIVE, INACTIVE
        List<TraceRequest> traceRequests,
        List<EventRequest> eventRequests
) {}