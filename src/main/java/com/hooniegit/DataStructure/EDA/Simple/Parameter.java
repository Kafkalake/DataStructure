package com.hooniegit.DataStructure.EDA.Simple;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 개별 센서 단위의 샘플 데이터 클래스입니다.
 */
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Parameter {

    protected int id;

    protected boolean pmmode; // Preventive Maintenance Mode (예방 정비 모드): Calibration, Time Based PM, Condition Based PM, ..

    protected Value value;

}
