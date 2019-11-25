--------------------------数据库创建SQL--------------------------

------- create user ----
CREATE USER 'bank'@'%' IDENTIFIED BY 'bank@12345';

------- create database ----
create database bank_db;
create database bank_db default charset UTF8mb4 collate utf8_general_ci;

GRANT all privileges ON bank_db.* TO 'bank'@'%';

FLUSH PRIVILEGES;


