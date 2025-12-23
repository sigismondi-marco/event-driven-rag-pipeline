package com.github.sigismondi.marco.streams.model;

import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PositionEvent(
    @JsonProperty("metadata") Metadata metadata,
    @JsonProperty("location") Location location,
    @JsonProperty("payload") Payload payload
) {
    public record Metadata(
        @JsonProperty("event_id") String eventId,
        @JsonProperty("schema_version") String schemaVersion,
        @JsonProperty("timestamp") Instant timestamp,
        @JsonProperty("device_id") String deviceId,
        @JsonProperty("device_type") String deviceType
    ) {}

    public record Location(
        @JsonProperty("type") String type,
        @JsonProperty("coordinates") List<Double> coordinates,
        @JsonProperty("altitude") Double altitude
    ) {}

    public record Payload(
        @JsonProperty("accelerometer") Accelerometer accelerometer,
        @JsonProperty("engine_stats") EngineStats engineStats,
        @JsonProperty("status") String status
    ) {}

    public record Accelerometer(
        @JsonProperty("x") Double x,
        @JsonProperty("y") Double y,
        @JsonProperty("z") Double z,
        @JsonProperty("unit") String unit
    ) {}

    public record EngineStats(
        @JsonProperty("rpm") Integer rpm,
        @JsonProperty("speed_kmh") Double speedKmh,
        @JsonProperty("fuel_level_percent") Integer fuelLevelPercent
    ) {}
}
