package com.fms.model;

public class FacilityMaintenanceRequest {

    public FacilityMaintenanceRequest(int id, int maintenanceRequestId, int facilityId) {
        this.id = id;
        this.maintenanceRequestId = maintenanceRequestId;
        this.facilityId = facilityId;
    }

    public int getId() {
        return id;
    }

    public int getMaintenanceRequestId() {
        return maintenanceRequestId;
    }

    public int getFacilityId() {
        return facilityId;
    }

    private int id;
    private int maintenanceRequestId;
    private int facilityId;
}