CREATE TABLE IF NOT EXISTS `greenlight`.`member` (
 `no` INT NOT NULL AUTO_INCREMENT,
 `member_id` VARCHAR(45) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `nickname` VARCHAR(100) NULL,
    `phone` VARCHAR(11) NULL,
    `roles` VARCHAR(100) NOT NULL,
    `activated` TINYINT NOT NULL DEFAULT 1,
    `ins_mbr_id` VARCHAR(100) NOT NULL DEFAULT 'computer',
    `upd_mbr_id` VARCHAR(100) NOT NULL DEFAULT 'computer',
    `create_tm` DATETIME NOT NULL DEFAULT now(),
    `update_tm` DATETIME NOT NULL DEFAULT now(),
    PRIMARY KEY (`no`),
    UNIQUE INDEX `mbr_email_UNIQUE` (`member_id` ASC) VISIBLE)
    ENGINE = InnoDB  charset = utf8mb4;
