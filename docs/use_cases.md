# Facility Management Use Cases

## Facility

### Add New Facility

Corresponds to _addNewFacility_

User requests new facility providing a name only. Details for facility are provided by _Add Facility Detail_

Returns:

```java
AddFacilityResult {
    String errorMessage;
    Facility facility;
}
```

Example error:

```json
{
  errorMessage: "The named facility already exists",
  facility: null
}
```



### Add Facility Detail

Corresponds to _addFacilityDetail_

User specifies the facility name, buildings associated, and rooms associated

The facility detail must be a json object that looks similar to this example: 


> This represents _FacilityDetail_ data, also known as _FacilityInformation_
```json

{
    description: "The Information Technology facility is responsible for tech support and upkeep of computer and phone networks throughout the organization",
    buildings: [
        {
            name: "b1",
            rooms: [
                {
                    room_num: 101,
                    capacity: 10
                },
                {
                    room_num: 102,
                    capacity: 10
                }
            ]
        },
         
        {
            name: "b2",
            rooms: [
                {
                    room_num: 101,
                    capacity: 10
                },
                {
                    room_num: 102,
                    capacity: 10
                }
            ]
        }
    ]
}
```

*Note* Application must ensure building names unique within faciltiy.


| attribute | description |
|---| -- | 
| description | Text description of facility to support _getFacilityInformation_
| buildings | Json Array of buildings in facility
| rooms | Json Array of rooms within building
| room_num | Number of room, unique to building
| capacity | Number of people room may hold to support _requestAvailableCapacity_




### List Facilities

Corresponds to _listFacilities_

Returns list of all facility names and ids

```json
[   
    {
        id: 1,
        name: "f1"
    }
]
```

### Remove Facility

Corresponds to _removeFacility_

Given the _facility_id_, removes facility and corresponding details.
Returns true if the facility was removed, false if the id was not present.

### Get Facility Information

Corresponds to _getFacilityInformation_

Given _facility_id_, Returns:

```java
GetFacilityDetailResult {
    String errorMessage;
    Facility facility;
}
```

Example error:

```json
{
  errorMessage: "The facility does not exist",
  facility: null
}
```


_FacilityDetail_.

### Request Available Capacity

Corresponds to _requestAvailableCapacity_

Given _room_id_ returns capacity of given room.

## Facility Use

All facility usage in the system refers to usage of _roomms_ within _buildings_ within a _facility_. Every room is uniquely identified by its _id_. It is also unqiely identified by its _RoomCompoundId_ 

```json
{
    facility_name: "F1",
    building_name: "B1",
    room_number: 101
}
``` 

All interaction with the _Facility Use_ usecases is with the unique room _id_'s. Client code can lookup room ids given _RoomCompoundId_.

### Use During Interval

Corresponds to _isInUseDuringInterval_

Queries system given _room_id_ and _timeRange_ to determine if room is in use at any point during that range

### Assign Facility/Room to Use

Corresponds to _assignFacilityToUse_

>Technically this is assigning rooms. All scheduling in system is for rooms. 

Given _room_id_ and _timeRange_. Assuming facility is not in use during that range (see also _inInUseDuringInterval_) records facility as in use during range

### Vacate Facility

Corresponds to _vacateFacility_

Other _Facility Use_ use cases refer to rooms. This is special. In this terrifying day of school, work shooting and grotesque carnage our system supports facility wide message system that can notify all room monitor systems to vacate. This api simply obtains all identification of all rooms and sends a message to each rooms monitors with that *vacate message*.

Given _facility_id_, sends message to each room in facility to vacate.


### List Inspections

Corresponds to _listInspections_

All facilities require regular inspections that do not affect the goings on of the rooms within facility. Our system only tracks completion of inspections, per facility.

Given a _facility_id_ and _timeRange_ returns all inspection results over the period. 

### Add Completed Inspection

Corresponds to _addInspectionResults_

Given the _facility_id_, _time_completed_, and _passed_ indicating whether it passed the inspection, saves the inspection results.

### List Actual Usage

Corresponds to _listActualUsage_

Given a _room_id_ and _timeRange_, returns list of _timeRanges_ where room was in use.

### Calculate Usage Rate

Corresponds to _calcUsageRate_

Given a _room_id_ and _timeRange_, returns a percentage of time the room has been in use (assumes facilities are running 24/7 nonstop)

## Facility Maintenance

### Facility Maintenance Request

Corresponds to _makeFacilityMaintRequest_

Given a _facility_id_, _maintenance_type_, _description_, optional _is_vacate_required_ (default is false), and optional _is_routine_ (default is true). Returns the _facility_maintenance_request_id_ for later scheduling.

Inserts record to `maintenance_request` and `facility_maintenance_request`

### Room Maintenance Request

Corresponds to _makeRoomMaintRequest_

Given a _room_id_, _maintenance_type_, _description_, and optional _is_vacate_required_. Returns the _room_maintenance_request_id_ for later scheduling.

Inserts record to `maintenance_request` and `room_maintenance_request`

### Schedule Room Maintenance

Corresponds to _scheduleRoomMaintenance_

#### Case - vacancy required for maintenance

Given a _room_maintenance_request_id_ and _timeRange_, verifies the time range is available in `room_reservation`.

If available, inserts record to `room_reservation` and `room_maintenance_schedule`.

Returns: Status success or failure

#### Case - users can remain during maintenance

Given a _room_maintenance_request_id_ and _timeRange_, insert record to `room_maintenance_schedule`.

### Schedule Facility-wide Maintenance

Corresponds to _scheduleFacilityMaintenance_

> *NOTE* All facility-wide maintenance assume no rooms are impacted.

Given a _maintenance_request_id_ and _timeRange_, insert record to `facility_maintenance_schedule`.

### Calculate Maintenance Cost For Facility

Corresponds to _calcMaintenanceCostForFacility_

Given a _facility_id_ and _timeRange_, calculates total cost of maintenance for facility.

### Calculate Problem Rate For Facility

> Every Maintenance Request Represents a Problem or Routine Expected Maintenance

Corresponds to _calcProblemRateForFacility_

Given a _facility_id_ and _timeRange_, calculate the number of events within both `room_reservation` and `facility_maintenance_schedule` tables where _is_routine_ is false.

### Calculate Down Time For Facility

Corresponds to _calcDownTimeForFacility_

Given a _facility_id_ and _timeRange_, calculate the total time filled by _maintenance_requests_ excluding requests where _is_vacate_ is false.

### List Maintenance Request

Corresponds to _listMaintRequests_

Given a _facility_id_ and _timeRange_, list all _maintenance_requests_ during the period.

### List Maintenance

Corresponds to _listMaintenance_

Given a _facility_id_ and _timeRange_, list all 



