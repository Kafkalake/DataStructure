package com.hooniegit.DataStructure.EDA.Reference.DCP;

import java.util.List;

/**
 * 이벤트 트리거 기반 데이터 수집 조건 (예: Step 변경 시 해당 시점의 데이터 전송)
 */
public record EventRequest(
        String triggerEventId,            // 이 이벤트가 발생할 때
        List<String> contextParamIds      // 이 변수들의 값을 함께 묶어서 보내라
) {}