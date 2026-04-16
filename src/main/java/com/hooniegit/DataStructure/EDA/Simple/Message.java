package com.hooniegit.DataStructure.EDA.Simple;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

/**
 * EDA(Equipment Data Acquisition) 통신 규격의 샘플 데이터 클래스입니다.
 * Header 내에 Publish 시점 데이터를 기록하고, Message 내에 실제 수집된 데이터를 보관합니다.
 * @param <T> 수집된 데이터 타입 정보
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Message <T> {

    private Map<String, Object> header;

    private T message;

}
