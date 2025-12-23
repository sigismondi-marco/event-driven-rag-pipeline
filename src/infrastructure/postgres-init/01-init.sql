-- Create table for PositionEventEntity
-- Fields from @Embeddable Location and Payload are flattened into this table
CREATE TABLE IF NOT EXISTS public.position_events (
    -- Primary Key (corresponds to Long id in Java)
    id BIGSERIAL PRIMARY KEY,
    
    -- Base Entity Fields
    event_id VARCHAR(255),
    schema_version VARCHAR(50),
    timestamp TIMESTAMPTZ,
    device_id VARCHAR(100),
    device_type VARCHAR(100),

    -- Fields from @Embeddable Location
    type VARCHAR(50),
    longitude DOUBLE PRECISION,
    latitude DOUBLE PRECISION,
    altitude DOUBLE PRECISION,

    -- Fields from @Embeddable Payload (camelCase mapped to snake_case)
    acc_x DOUBLE PRECISION,
    acc_y DOUBLE PRECISION,
    acc_z DOUBLE PRECISION,
    acc_unit VARCHAR(20),
    engine_rpm INTEGER,
    speed_kmh DOUBLE PRECISION,
    fuel_level_percent INTEGER,
    status VARCHAR(50)
);

-- Essential for Debezium CDC: 
-- Ensures the log contains both old and new values for updates/deletes
ALTER TABLE public.position_events REPLICA IDENTITY FULL;

CREATE TABLE IF NOT EXISTS public.risk_zones (
    -- Unique identifier for the risk area (e.g., 'WEATHER-BOLOGNA-001')
    zone_id VARCHAR(50) PRIMARY KEY,
    
    -- Human readable name of the location
    zone_name VARCHAR(100) NOT NULL,
    
    -- Type of weather hazard: ICY_ROAD, HEAVY_RAIN, STRONG_WINDS, FLOODING, FOG
    hazard_type VARCHAR(50) NOT NULL,
    
    -- Risk severity level: LOW, MEDIUM, HIGH, CRITICAL
    severity_level VARCHAR(20) NOT NULL,
    
    -- Geographical center of the alert
    center_lat DOUBLE PRECISION NOT NULL,
    center_lon DOUBLE PRECISION NOT NULL,
    
    -- The area covered by the alert in meters
    radius_meters DOUBLE PRECISION NOT NULL,
    
    -- Recommended speed limit for this specific weather condition (km/h)
    suggested_speed_limit INT,
    
    -- Status to enable/disable the zone without deleting the row
    is_active BOOLEAN DEFAULT true,

    -- Expiration time for the weather alert
    expires_at TIMESTAMP
);

ALTER TABLE public.risk_zones REPLICA IDENTITY FULL;

-- Milano: Heavy Fog in the Po Valley
INSERT INTO risk_zones (zone_id, zone_name, hazard_type, severity_level, center_lat, center_lon, radius_meters, suggested_speed_limit, expires_at)
VALUES ('W-MIL-001', 'Milano Tangenziale Ovest', 'FOG', 'MEDIUM', 45.4642, 9.1900, 3000.0, 70, '2025-12-24T10:00:00Z');

-- Bologna: Icy Road on the Apennines section
INSERT INTO risk_zones (zone_id, zone_name, hazard_type, severity_level, center_lat, center_lon, radius_meters, suggested_speed_limit, expires_at)
VALUES ('W-BOL-002', 'Bologna A1 Panoramica', 'ICY_ROAD', 'CRITICAL', 44.4949, 11.3426, 5000.0, 40, '2025-12-24T06:00:00Z');

-- Roma: Flash Flooding risk due to heavy rain
INSERT INTO risk_zones (zone_id, zone_name, hazard_type, severity_level, center_lat, center_lon, radius_meters, suggested_speed_limit, expires_at)
VALUES ('W-ROM-003', 'Roma Grande Raccordo Anulare', 'FLOODING', 'HIGH', 41.8902, 12.4922, 2500.0, 50, '2025-12-23T23:59:00Z');

-- Firenze: Strong crosswinds on the viaducts
INSERT INTO risk_zones (zone_id, zone_name, hazard_type, severity_level, center_lat, center_lon, radius_meters, suggested_speed_limit, expires_at)
VALUES ('W-FIR-004', 'Firenze-Mare Viaduct', 'STRONG_WINDS', 'MEDIUM', 43.7696, 11.2558, 1200.0, 80, '2025-12-24T15:00:00Z');

-- Padova: Heavy Rain reducing visibility
INSERT INTO risk_zones (zone_id, zone_name, hazard_type, severity_level, center_lat, center_lon, radius_meters, suggested_speed_limit, expires_at)
VALUES ('W-PAD-005', 'Padova Industrial Zone', 'HEAVY_RAIN', 'LOW', 45.4064, 11.8768, 2000.0, 90, '2025-12-24T12:00:00Z');

-- Venezia: High Tide/Flooding on the access bridge
INSERT INTO risk_zones (zone_id, zone_name, hazard_type, severity_level, center_lat, center_lon, radius_meters, suggested_speed_limit, expires_at)
VALUES ('W-VEN-006', 'Ponte della Libertà Venice', 'FLOODING', 'HIGH', 45.4408, 12.3155, 1000.0, 40, '2025-12-23T21:00:00Z');

-- Napoli: Volcanic Ash (Simulation) or Heavy Storm
INSERT INTO risk_zones (zone_id, zone_name, hazard_type, severity_level, center_lat, center_lon, radius_meters, suggested_speed_limit, expires_at)
VALUES ('W-NAP-007', 'Napoli Tangenziale Est', 'HEAVY_RAIN', 'MEDIUM', 40.8518, 14.2681, 3500.0, 60, '2025-12-24T18:00:00Z');

-- Palermo: Extreme Heat affecting asphalt/tires
INSERT INTO risk_zones (zone_id, zone_name, hazard_type, severity_level, center_lat, center_lon, radius_meters, suggested_speed_limit, expires_at)
VALUES ('W-PAL-008', 'Palermo Highway Exit', 'EXTREME_HEAT', 'LOW', 38.1157, 13.3615, 1500.0, 100, '2025-12-24T14:00:00Z');

-- Taranto: Coastal Gale Winds
INSERT INTO risk_zones (zone_id, zone_name, hazard_type, severity_level, center_lat, center_lon, radius_meters, suggested_speed_limit, expires_at)
VALUES ('W-TAR-009', 'Taranto Port Access', 'STRONG_WINDS', 'HIGH', 40.4677, 17.2233, 800.0, 50, '2025-12-24T02:00:00Z');

-- Torino: Heavy Snowfall in the Alpine foothills
INSERT INTO risk_zones (zone_id, zone_name, hazard_type, severity_level, center_lat, center_lon, radius_meters, suggested_speed_limit, expires_at)
VALUES ('W-TOR-010', 'Torino North Bypass', 'SNOW', 'CRITICAL', 45.0703, 7.6869, 4000.0, 30, '2025-12-25T00:00:00Z');
