FROM mysql
RUN echo "\
    CREATE DATABASE icanmining; \
    CREATE DATABASE icanmining_test; \
    CREATE USER '$MYSQL_USER'@'%' IDENTIFIED BY '$MYSQL_PASSWORD'; \
    CREATE USER '$MYSQL_USER'@'localhost' IDENTIFIED BY '$MYSQL_PASSWORD'; \
    GRANT ALL PRIVILEGES ON icanmining.* TO '$MYSQL_USER'@'%'; \
    GRANT ALL PRIVILEGES ON icanmining.* TO '$MYSQL_USER'@'localhost'; \
    GRANT ALL PRIVILEGES ON icanmining_test.* TO '$MYSQL_USER'@'%'; \
    GRANT ALL PRIVILEGES ON icanmining_test.* TO '$MYSQL_USER'@'localhost'; \
    FLUSH PRIVILEGES;" > /docker-entrypoint-initdb.d/db-init.sql