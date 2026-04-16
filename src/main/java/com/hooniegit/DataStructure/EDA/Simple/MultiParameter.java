package com.hooniegit.DataStructure.EDA.Simple;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 설비 단위의 샘플 데이터 클래스입니다.
 * 고주파수 시계열 데이터를 대상으로, 해당 데이터의 수집 상황에 대한 컨텍스트 부여 목적으로 사용됩니다.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MultiParameter extends Parameter {

    private int toolId;

    private State state; // 설비 종합 상태: PRD, SBY, ENG, DOWN, ..
    private String condition; // 물리/논리적 조건 및 상태: Normal, Warning, Fault, Interlocked, Material Present, ..
    private String step; // 공정 진행 단계: 진공, 가스 주입, 가열, Plasma 방전, 냉각, ..

}
