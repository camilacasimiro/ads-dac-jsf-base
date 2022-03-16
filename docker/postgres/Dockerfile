FROM postgres
ENV POSTGRES_DB atividade01
ENV POSTGRES_USER jheycami
ENV POSTGRES_PASSWORD 333

COPY create.sql /docker-entrypoint-initdb.d/
COPY insert.sql /docker-entrypoint-initdb.d/