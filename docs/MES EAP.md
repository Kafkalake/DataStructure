**EAP(Equipment Automation Program / Equipment Application Programming)**는 공장의 메인 두뇌인 **MES(생산관리시스템)**와 수백, 수천 대의 물리적 **설비(Equipment)** 사이에서 완벽한 **'통역사'이자 '현장 반장' 역할**을 수행하는 핵심 미들웨어 소프트웨어입니다.

MES가 공장 전체의 생산 계획을 짜고 지시를 내리는 '본사'라면, 개별 장비들은 영어나 불어처럼 서로 다른 언어(SECS/GEM, OPC UA, PLC 등)를 쓰는 '외국인 노동자'들과 같습니다. 본사가 이 수많은 작업자에게 일일이 다가가 지시를 내리고 보고를 받기에는 시스템이 너무 무겁고 느립니다. 그래서 중간에 빠르고 똑똑한 EAP 미들웨어를 두어 통신과 제어를 전담하게 합니다.

EAP 미들웨어가 수행하는 4가지 핵심 기능과 특징을 정리해 드립니다.

### 1. EAP의 4대 핵심 기능

* **프로토콜 번역 (Protocol Translation)**
    * **MES의 언어:** HTTP API, JSON, Message Queue(Kafka, RabbitMQ), RDBMS(Oracle, MSSQL) 쿼리 등 표준 IT 기술을 사용합니다.
    * **설비의 언어:** SECS/GEM, OPC UA, Modbus, TCP/IP 소켓 통신 등 산업용 OT(운영 기술) 표준을 사용합니다.
    * **EAP의 역할:** MES가 "A장비, 레시피 1번으로 웨이퍼 가동해"라고 API를 쏘면, EAP가 이를 `S2F41(Host Command)`라는 SECS/GEM 규격으로 번역해서 설비에 전달합니다. 반대로 설비가 보내는 알람(`S5F1`)을 받아서 MES의 DB에 Insert(정제하여 저장)합니다.

* **현장 비즈니스 시나리오 제어 (Track-In / Track-Out)**
    * 제품(Lot/Batch)이 설비에 투입될 때(Track-In)부터 완료되어 나올 때(Track-Out)까지의 세부 시퀀스를 제어합니다.
    * "자재 바코드 스캔 -> MES에 유효성 검사 요청 -> 통과 시 설비에 레시피 다운로드 -> 설비 가동 명령 -> 종료 후 실적 보고"로 이어지는 자동화 물결을 EAP가 스크립트나 룰 엔진(Rule Engine)으로 처리합니다.

* **데이터 파싱 및 평탄화 (Data Parsing & Flattening)**
    * 설비는 공정 중 발생하는 수천 개의 센서 데이터를 하나의 긴 메시지 묶음으로 던집니다.
    * EAP는 이 복잡한 메시지를 뜯어내어(Parsing), 관계형 DB나 데이터 웨어하우스에 넣기 좋게 평탄화(`[시간, 장비ID, 스텝, 온도, 압력]`)하여 초고속으로 적재합니다.

* **안전 및 인터락 제어 (Interlock)**
    * 작업자가 엉뚱한 자재를 넣었거나, 설비의 온도가 갑자기 떨어졌을 때 MES까지 데이터가 갔다가 판단해서 돌아오면 이미 불량이 발생한 후일 수 있습니다.
    * EAP는 현장에 가장 가까이 있기 때문에, 이상 징후를 감지하면 즉시 설비에 '정지(Abort)' 명령을 내리는 인터락 권한을 가집니다.

### 2. 왜 MES가 직접 설비와 통신하지 않을까?

* **부하 분산 (Load Balancing):** 공장에는 수천 대의 장비가 초당 수만 건의 이벤트를 쏟아냅니다. MES 서버 하나가 이 TCP/IP 소켓들을 모두 쥐고 있으면 시스템이 뻗어버립니다. EAP 서버를 장비 10대~50대당 1대씩 분산 배치하여 부하를 나눕니다.
* **유지보수의 독립성:** 장비가 교체되거나 통신 규격이 바뀌어도 MES 코드를 수정할 필요 없이 해당 장비를 담당하는 EAP의 통신 드라이버만 수정하면 됩니다.

1. 공정 시작: 
- [MES] -(JSON)- [EAP] -(SECS/GEM)- [EQUIPMENT] -(SECS/GEM)- [EAP] -(SQL)- [MES]

2. 데이터 수집:
- [EQUIPMENT] -(SECS/GEM)- [EAP] -(SQL)- [MES]

3. 이상 감지
- [EQUIPMENT] -(SECS/GEM)- [EAP] -(SECS/GEM)- [EQUIPMENT] & [EAP] -(JSON)- [MES]