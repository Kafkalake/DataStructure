package com.hooniegit.DataStructure.EDA.Simple;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 실측값과 수집 퀄리티를 기록하는 샘플 데이터 클래스입니다.
 * 퀄리티 정보는 DCQV 등 해당 기업/사업소 표준에 기인합니다.
 */
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Value {

    private int value;

    private double quality;

}
