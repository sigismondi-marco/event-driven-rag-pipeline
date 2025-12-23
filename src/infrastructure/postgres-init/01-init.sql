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
