#!/bin/sh

if [ ! -f "$PGDATA/PG_VERSION" ]; then
    initdb -D "$PGDATA"
    echo "listen_addresses = '*'" >> "$PGDATA/postgresql.conf"
    echo "host all all all md5" >> "$PGDATA/pg_hba.conf"

    pg_ctl -D "$PGDATA" -w start

    psql -v ON_ERROR_STOP=1 --username=postgres <<EOSQL
ALTER USER postgres PASSWORD 'password';
CREATE DATABASE pollify;
EOSQL

    pg_ctl -D "$PGDATA" -m fast -w stop
fi

exec postgres -D "$PGDATA"
