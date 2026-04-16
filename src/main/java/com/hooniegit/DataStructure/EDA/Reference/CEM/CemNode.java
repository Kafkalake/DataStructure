package com.hooniegit.DataStructure.EDA.Reference.CEM;

import java.util.ArrayList;
import java.util.List;

public class CemNode {
    private final String nodeId;        // 예: "EQP01-CHM-A"
    private final NodeType type;        // 예: SUBSYSTEM
    private final String description;

    // 하위 트리 구조
    private final List<CemNode> children = new ArrayList<>();

    // 이 노드에 매달려 있는 센서/변수 및 이벤트 목록
    private final List<DataVariable> parameters = new ArrayList<>();
    private final List<StateEvent> events = new ArrayList<>();

    public CemNode(String nodeId, NodeType type, String description) {
        this.nodeId = nodeId;
        this.type = type;
        this.description = description;
    }

    public void addChild(CemNode child) { this.children.add(child); }
    public void addParameter(DataVariable var) { this.parameters.add(var); }
    public void addEvent(StateEvent event) { this.events.add(event); }

    // Getters...
}
