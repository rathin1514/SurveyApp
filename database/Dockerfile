FROM alpine:latest

RUN apk add --no-cache bash gcc musl-dev make readline-dev zlib-dev openssl-dev curl

# Postgres version
ENV PG_VERSION=15.2
ENV PG_SOURCE=https://ftp.postgresql.org/pub/source/v${PG_VERSION}/postgresql-${PG_VERSION}.tar.bz2

RUN apk add --no-cache \
    bash \
    curl \
    tar \
    build-base \
    linux-headers \
    libxml2-dev \
    libxslt-dev \
    openssl-dev \
    readline-dev \
    zlib-dev \
    bison \
    flex \
    perl \
    python3 \
    icu-dev \
    gettext


RUN mkdir -p /usr/src/postgres && \
    curl -L -o /tmp/postgresql.tar.bz2 $PG_SOURCE && \
    tar -xjf /tmp/postgresql.tar.bz2 -C /usr/src/postgres --strip-components=1 && \
    rm /tmp/postgresql.tar.bz2

WORKDIR /usr/src/postgres

RUN ./configure --prefix=/usr/local/pgsql && \
    make && \
    make install

RUN adduser -D postgres && \
    mkdir -p /usr/local/pgsql/data && \
    chown -R postgres:postgres /usr/local/pgsql


ENV PATH="/usr/local/pgsql/bin:$PATH"
ENV PGDATA="/usr/local/pgsql/data"

USER postgres

# RUN initdb -D $PGDATA

COPY --chown=postgres:postgres init-db.sh /docker-entrypoint-initdb.d/init-db.sh
RUN chmod +x /docker-entrypoint-initdb.d/init-db.sh

EXPOSE 5432

# Start PostgreSQL server
CMD ["sh", "-c", "/docker-entrypoint-initdb.d/init-db.sh && exec postgres -D /usr/local/pgsql/data"]
