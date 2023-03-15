CREATE USER 'grnmaster'@'%' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON *.* TO 'grnmaster'@'%';
flush privileges;

CREATE TABLE member (
    no bigint NOT NULL AUTO_INCREMENT,
    member_id VARCHAR(45) NOT NULL,
    password VARCHAR(255) NOT NULL,
    nickname VARCHAR(100) NULL,
    phone VARCHAR(11) NULL,
    roles VARCHAR(100) NOT NULL,
    activated VARCHAR(1) NOT NULL DEFAULT 'Y',
    ins_mbr_id VARCHAR(100) DEFAULT 'server',
    upd_mbr_id VARCHAR(100) DEFAULT 'server',
    create_tm DATETIME DEFAULT now(),
    update_tm DATETIME DEFAULT now(),
    PRIMARY KEY (no),
    UNIQUE INDEX mbr_email_UNIQUE (member_id ASC) VISIBLE)
ENGINE = InnoDB charset = utf8mb4;
