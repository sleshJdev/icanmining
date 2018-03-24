FROM mysql
RUN echo "\
    CREATE DATABASE monominer; \
    CREATE DATABASE monominer_test; \
    CREATE USER '$MYSQL_USER'@'%' IDENTIFIED BY '$MYSQL_PASSWORD'; \
    CREATE USER '$MYSQL_USER'@'localhost' IDENTIFIED BY '$MYSQL_PASSWORD'; \
    GRANT ALL PRIVILEGES ON monominer.* TO '$MYSQL_USER'@'%'; \
    GRANT ALL PRIVILEGES ON monominer.* TO '$MYSQL_USER'@'localhost'; \
    GRANT ALL PRIVILEGES ON monominer_test.* TO '$MYSQL_USER'@'%'; \
    GRANT ALL PRIVILEGES ON monominer_test.* TO '$MYSQL_USER'@'localhost'; \
    FLUSH PRIVILEGES;" > /docker-entrypoint-initdb.d/db-init.sql