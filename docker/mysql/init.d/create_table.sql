CREATE USER 'grnmaster'@'%' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON *.* TO 'grnmaster'@'%';
flush privileges;

CREATE TABLE test_table (
    test_name varchar(25) not null,
    test_datetime datetime default NOW() not null
);
