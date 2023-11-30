CREATE USER 'pawa_task_app'@'%' IDENTIFIED BY 'pawa_task_app';
CREATE USER 'keycloak'@'%' IDENTIFIED BY 'keycloak';

CREATE DATABASE task;
CREATE DATABASE keycloak;


GRANT ALL PRIVILEGES ON task.* TO 'pawa_task_app'@'%';
GRANT ALL PRIVILEGES ON keycloak.* TO 'keycloak'@'%';
