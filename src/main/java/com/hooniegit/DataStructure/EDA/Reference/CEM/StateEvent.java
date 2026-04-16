package com.hooniegit.DataStructure.EDA.Reference.CEM;

/**
 * 특정 노드에서 발생하는 이벤트 (예: Step Changed)
 */
public record StateEvent(
        String eventId,
        String eventName
) {}