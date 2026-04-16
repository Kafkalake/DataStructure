package com.hooniegit.DataStructure.EDA.Simple;

import lombok.Getter;

/**
 * 설비의 종합 상태를 나타내는 열거형 클래스입니다.
 * 설비 전체의 현재 가동 여부 및 생산 가용성을 나타내는 가장 큰 범주의 파라미터입니다.
 */
@Getter
public enum State {

    PRD(-1, "PRODUCTION"),
    SBY(0, "STANDBY"),
    ENG(1, "ENGINEERING"),
    DOWN(2, "DOWN");

    int value;
    String expression;

    State(int value, String expression) {
        this.value = value;
        this.expression = expression;
    }

}
