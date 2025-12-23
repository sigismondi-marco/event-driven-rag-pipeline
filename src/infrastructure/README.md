# Infrastructure Setup (Podman)

This folder contains the necessary configuration to run the local development environment for the **Event driven RAG pipeline** project, including **PostgreSQL**, **Redpanda** (Kafka), and **Debezium**.

## 1. Prerequisites
- **Podman** and **podman-compose** installed on Fedora.
- **jq** (optional, for formatted JSON output): `sudo dnf install jq`.

## 2. Start the Infrastructure
Run the following command to pull and start the containers in detached mode:

```bash
podman-compose up -d

```
## 3. Register Debezium Connector

Once the containers are healthy, register the PostgreSQL connector to start Change Data Capture (CDC):

```bash
curl -i -X POST -H "Content-Type: application/json" \
     --data @register-postgres.json \
     http://localhost:8083/connectors

```

## 4. Ingesting Events (API Testing)

To test the full data flow (App -> Database -> Debezium -> Kafka), send a sample event to your Quarkus application using the file located in `src/main/resources/event.json`:

```bash
curl -X POST http://localhost:8080/events \
     -H "Content-Type: application/json" \
     --data @../src/main/resources/event.json

```

### API Documentation: `/events`

* **Method:** `POST`
* **Endpoint:** `http://localhost:8080/events`
* **Payload:** `PositionEvent` JSON object.
* **Behavior:** Persists data to the `position_events` table.

### 5. Verify Data

### Check Risk Zones (Reference Data)

To verify that the weather risk zones are correctly configured in the database:

```bash
podman exec -it postgres psql -U quarkus -d events -c "SELECT zone_name, hazard_type, severity_level FROM risk_zones;"

```

### Check PostgreSQL Records (Live Events)

To verify that the position events have been correctly saved:

```bash
podman exec -it postgres psql -U quarkus -d events -c "SELECT * FROM position_events;"

```

### Check Kafka Messages (Redpanda)

Monitor the CDC events being streamed to the Kafka topics:

* **Events Topic:** `podman exec -it redpanda rpk topic consume cdc.public.position_events --from-start`
* **Risk Zones Topic:** `podman exec -it redpanda rpk topic consume cdc.public.risk_zones --from-start`


## 6. Monitoring & Troubleshooting

### Check Connector Status

```bash
curl -s http://localhost:8083/connectors/position-connector/status | jq

```

### View Logs

* **Debezium:** `podman logs -f debezium`
* **Redpanda:** `podman logs -f redpanda`
* **Postgres:** `podman logs -f postgres`

### Stop Infrastructure

```bash
podman-compose down

```
