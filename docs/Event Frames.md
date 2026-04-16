**AVEVA PI System(과거 OSIsoft PI)**은 전 세계 수많은 발전소, 정유공장, 제약 및 반도체 공장에서 사용하는 **산업용 시계열 데이터베이스(Historian)의 사실상 글로벌 표준**입니다.

이 PI System에서 제공하는 가장 강력하고 핵심적인 기능 중 하나가 바로 **'이벤트 프레임(Event Frames)'**입니다.

이해하기 쉽게 비유하자면, PI System에 끝없이 쌓이는 시계열 데이터가 **'24시간 돌아가는 CCTV 녹화본'**이라면, 이벤트 프레임은 특정 사건이 발생한 구간만 잘라낸 **'사건/사고 하이라이트 클립(Bookmark)'**이라고 할 수 있습니다.

### 1. 이벤트 프레임(Event Frames)이 왜 필요한가?

공장의 센서 데이터는 1년 365일 쉬지 않고 초 단위로 쏟아집니다(연속 데이터). 하지만 엔지니어들이 데이터를 분석할 때는 "2023년 5월 1일 14시부터 16시까지의 온도"를 궁금해하기보다는, **특정 맥락(Context)이 부여된 사건**을 궁금해합니다.

* "지난달에 **B펌프가 고장 났던(Down) 모든 시간대**의 진동 데이터를 모아줘."
* "오늘 생산한 **'배치(Batch) #105' 공정**에 걸린 시간과 평균 온도를 알려줘."
* "설비가 **'가열 스텝(Step 3)'**일 때 압력이 기준치를 넘은 적이 몇 번이야?"

연속된 시간 축 위에서 사용자가 원하는 의미 있는 구간(시작 시간과 종료 시간)을 자동으로 잘라내어 이름을 붙이고 메타데이터를 저장하는 기술이 바로 이벤트 프레임입니다.

### 2. 이벤트 프레임의 구성 요소 및 동작 방식

이벤트 프레임은 단순한 시간표가 아니라, 하나의 완전한 데이터 패키지입니다.

* **트리거 조건 (Trigger Condition):** 이벤트를 언제 시작하고 언제 종료할지 정의합니다.
    * *예:* 이전 답변에서 다룬 `state` 파라미터가 'PRD(생산)'에서 'DOWN(고장)'으로 바뀌는 순간 이벤트 시작, 다시 'PRD'로 돌아오면 이벤트 종료.
* **시간 정보 (Time Context):** 이벤트의 시작 시간(Start Time), 종료 시간(End Time), 그리고 지속 시간(Duration)을 기록합니다.
* **속성 (Attributes / Metadata):** 해당 이벤트 구간 동안의 핵심 요약 정보를 계산하여 저장합니다.
    * *예:* 고장 구간 동안의 '최고 온도', 생산 구간의 '제품 번호(Product ID)', '작업자 이름' 등.

### 3. 현장 활용 시나리오 (앞선 파라미터들과의 결합)

앞서 질문하셨던 `state`, `step`, `condition`, `pmmode` 파라미터들은 이 이벤트 프레임을 생성하는 완벽한 트리거(Trigger)가 됩니다.

1.  **배치(Batch) 및 레시피 분석:**
    * 설비의 `step` 파라미터가 변경될 때마다 이벤트 프레임을 생성합니다.
    * 엔지니어는 수백 번의 '가열 스텝' 이벤트 프레임만 검색한 뒤, 각 프레임에 기록된 '최고 온도' 속성을 엑셀로 한 번에 다운로드하여 공정의 산포를 분석할 수 있습니다.
2.  **설비 종합 효율(OEE) 및 가동 시간 분석:**
    * 설비의 `state`가 'DOWN'일 때를 묶어 '고장 이벤트(Downtime Event)'를 만듭니다.
    * 여기에 `condition` 알람 코드를 속성으로 추가하면, "이번 달에 '압력 저하 알람'으로 인해 멈춘 총 시간"을 클릭 몇 번으로 통계 낼 수 있습니다.
3.  **예방 정비(PM) 이력 관리:**
    * `pmmode`가 켜져 있는 구간을 '정비 이벤트'로 지정합니다. 이 프레임이 잡혀 있는 시간대의 센서 데이터는 불량 분석 모델에서 자동으로 제외하도록 필터링할 수 있습니다.

---

이벤트 프레임 추출 기능은 언뜻 보면 복잡한 분석 같지만, 시스템 내부를 들여다보면 컴퓨터 공학의 아주 고전적인 패턴인 **상태 머신(State Machine)**과 **선형 탐색(Linear Scan)**의 조합으로 이루어져 있습니다.

특히 방금 보신 시뮬레이터처럼 슬라이더를 움직일 때마다 '즉각적으로' 반응하려면, 이 로직을 어떻게 배치하고 최적화하느냐가 관건입니다. 실제 엔터프라이즈 환경 및 프론트엔드 UI에서 이를 어떻게 구현하는지 세 가지 관점으로 나누어 설명해 드립니다.

---

### 1. 로직의 핵심: 상태 머신 (State Machine) 패턴

이벤트 프레임은 본질적으로 시스템이 **'정상 상태(0)'**에 있는지, 아니면 **'이벤트 발생 상태(1)'**에 있는지를 추적하는 것입니다. 데이터 스트림이 들어올 때마다 임계치와 비교하여 상태 전이(State Transition)가 일어날 때만 액션을 취합니다.

Java를 예로 든다면, 데이터를 수신하는 워커 스레드나 인메모리 프로세서에 다음과 같은 로직이 들어갑니다.

```java
public class EventFrameProcessor {
    private double threshold;
    private EventFrame currentEvent = null;

    public EventFrameProcessor(double threshold) {
        this.threshold = threshold;
    }

    // 초당 수만 건씩 들어오는 데이터 스트림을 처리하는 메서드
    public void onDataReceived(long timestamp, double value) {
        if (value > threshold) {
            // [상태 전이: 0 -> 1] 임계치 초과 + 현재 진행 중인 이벤트가 없을 때
            if (currentEvent == null) {
                currentEvent = new EventFrame(timestamp); // 이벤트 시작
            }
            // 이벤트 진행 중: 최대 온도 등 속성값 업데이트
            currentEvent.updateMaxValue(value); 
            
        } else {
            // [상태 전이: 1 -> 0] 임계치 미만 + 진행 중이던 이벤트가 있을 때
            if (currentEvent != null) {
                currentEvent.setEndTime(timestamp); // 이벤트 종료
                saveToDatabase(currentEvent);       // 완성된 프레임을 DB나 큐로 전송
                currentEvent = null;                // 상태 초기화
            }
        }
    }
}
```

이 구조는 객체 생성(Allocation)을 최소화하면서도 `O(N)`의 시간 복잡도로 데이터를 훑고 지나가기 때문에, 아주 빠른 속도로 이벤트를 추출해 냅니다.

### 2. UI에서 '즉각적으로' 반응하는 원리 (In-Memory Processing)

사용자가 슬라이더를 드래그할 때마다 DB에 쿼리를 날리면 네트워크 지연 시간(Latency) 때문에 뚝뚝 끊기는 현상이 발생합니다. 시뮬레이터가 부드럽게 동작하는 이유는 **관심 구간의 Raw 데이터를 메모리에 올려두고 브라우저(또는 로컬 서버) 단에서 재계산**하기 때문입니다.

1. **캐싱:** 화면에 보여줄 1시간 치의 1Hz 온도 데이터(약 3,600개 배열)를 프론트엔드 메모리나 백엔드의 빠른 인메모리 캐시(Redis 등)에 올려둡니다.
2. **배치 재실행:** 사용자가 슬라이더를 움직여 임계치를 `70`에서 `75`로 변경하는 이벤트가 발생하면, 앞서 설명한 `onDataReceived` 로직을 3,600개의 캐시 배열에 대해 `for` 문으로 순식간에 다시 돌립니다.
3. **렌더링:** 배열을 한 번 순회(O(N))하며 새롭게 생성된 이벤트 프레임 목록(배열)을 즉시 UI 컴포넌트에 넘겨 화면을 다시 그립니다.

데이터가 수만 개 수준이라면 최신 CPU나 브라우저의 V8 엔진에서는 밀리초(ms) 단위로 처리가 끝나기 때문에 인간의 눈에는 '실시간'으로 반응하는 것처럼 보입니다.

### 3. 대용량 DB 환경에서의 추출 (T-SQL Gaps and Islands)

만약 "지난 1년 치" 데이터에 대해 새로운 임계치를 적용하여 이벤트 프레임을 뽑아내야 한다면 메모리에 다 올릴 수 없으므로 RDBMS의 쿼리 엔진을 활용해야 합니다.

이러한 패턴을 SQL에서는 전형적인 **'Gaps and Islands(연속과 단절)' 문제**라고 부릅니다. MSSQL(T-SQL)의 윈도우 함수(Window Function)를 사용하면 이를 효율적으로 구현할 수 있습니다.

```sql
-- MSSQL 환경에서 임계치(70)를 넘는 연속된 구간(Island)을 하나의 프레임으로 묶는 논리
WITH ThresholdFlag AS (
    SELECT 
        Timestamp, 
        Value,
        -- 임계치를 넘으면 1, 아니면 0 플래그 부여
        CASE WHEN Value > 70 THEN 1 ELSE 0 END AS IsOver
    FROM SensorData
),
StateChange AS (
    SELECT 
        Timestamp, 
        Value, 
        IsOver,
        -- 이전 행과 비교하여 상태가 변했는지 확인 (LAG 함수)
        CASE WHEN IsOver <> LAG(IsOver) OVER (ORDER BY Timestamp) 
             THEN 1 ELSE 0 END AS IsChanged
    FROM ThresholdFlag
),
EventGrouping AS (
    SELECT 
        Timestamp, 
        Value, 
        IsOver,
        -- 상태 변화를 누적합(SUM OVER)하여 동일한 이벤트 구간에 같은 GroupID 부여
        SUM(IsChanged) OVER (ORDER BY Timestamp) AS EventGroupID
    FROM StateChange
)
-- 최종 이벤트 프레임 집계
SELECT 
    MIN(Timestamp) AS StartTime,
    MAX(Timestamp) AS EndTime,
    DATEDIFF(SECOND, MIN(Timestamp), MAX(Timestamp)) AS DurationSeconds,
    MAX(Value) AS MaxValue
FROM EventGrouping
WHERE IsOver = 1 -- 임계치를 넘은 구간만 필터링
GROUP BY EventGroupID;
```

상용 SCADA나 PI System 같은 솔루션들은 이러한 로직을 내부 시계열 DB 엔진 깊숙한 곳에 C/C++ 레벨로 최적화하여 내장해 둡니다. 덕분에 엔지니어는 복잡한 코딩이나 쿼리 작성 없이 조건만 입력하면 시스템이 알아서 데이터를 평탄화하고 프레임을 잘라주게 됩니다.