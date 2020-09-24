create database multishare_db;

USE multishare_db;

CREATE TABLE user_info(
   user_info_id BIGINT NOT NULL UNIQUE AUTO_INCREMENT,
   record_valid_from_date DATETIME NOT NULL,
   record_valid_to_date DATETIME,
   PRIMARY KEY(user_info_id)
);

CREATE TABLE user_info_detail(
    user_info_detail_id BIGINT NOT NULL UNIQUE AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    name VARCHAR(150) NOT NULL,
    surname VARCHAR(150) NOT NULL,
    gender VARCHAR(100) NOT NULL,
    legalIdentityNumber VARCHAR(150) NOT NULL,
    record_valid_from_date DATETIME NOT NULL,
    record_valid_to_date DATETIME,
    user_info_id BIGINT NOT NULL,
    PRIMARY KEY(user_info_detail_id),
    FOREIGN KEY(user_info_id)
    REFERENCES user_info(user_info_id)
);

CREATE TABLE contact_info(
    contact_info_id BIGINT NOT NULL UNIQUE AUTO_INCREMENT,
    contact VARCHAR(100) NOT NULL UNIQUE,
    contact_type VARCHAR(50) NOT NULL,
    record_valid_from_date DATETIME NOT NULL,
    record_valid_to_date DATETIME,
    user_info_id BIGINT NOT NULL,
    PRIMARY KEY(contact_info_id),
    FOREIGN KEY(user_info_id)
    REFERENCES user_info(user_info_id)
);

CREATE TABLE login_info(
    login_info_id BIGINT NOT NULL UNIQUE AUTO_INCREMENT,
    password VARCHAR(15) NOT NULL,
    record_valid_from_date DATETIME NOT NULL,
    record_valid_to_date DATETIME,
    user_info_id BIGINT NOT NULL,
    PRIMARY KEY(loin_info_id),
    FOREIGN KEY(user_info_id)
    REFERENCES user_info(user_info_id)
);

CREATE TABLE friendship_info(
    friendship_info_id BIGINT NOT NULL UNIQUE AUTO_INCREMENT,
    src_friendship_info_id BIGINT NOT NULL,
    dest_friendship_info_id BIGINT NOT NULL,
    record_valid_from_date DATETIME NOT NULL,
    record_valid_to_date DATETIME,
    friendship_info_status VARCHAR(25) NOT NULL,
    PRIMARY KEY(friendship_info_id),
    FOREIGN KEY(src_friendship_info_id)
    REFERENCES user_info(user_info_id),
    FOREIGN KEY(dest_friendship_info_id)
    REFERENCES user_info(user_info_id)
);

CREATE TABLE post_info(
    post_info_id BIGINT NOT NULL UNIQUE AUTO_INCREMENT,
    record_valid_from_date DATETIME NOT NULL,
    record_valid_to_date DATETIME,
    user_info_id BIGINT NOT NULL,
    PRIMARY KEY(post_info_id),
    FOREIGN KEY(user_info_id)
    REFERENCES user_info(user_info_id)
);

CREATE TABLE post_info_detail(
    post_info_detail_id BIGINT NOT NULL UNIQUE AUTO_INCREMENT,
    title VARCHAR(50) NOT NULL,
    post_body VARCHAR(500) NOT NULL,
    record_valid_from_date DATETIME NOT NULL,
    record_valid_to_date DATETIME,
    post_info_id BIGINT NOT NULL,
    PRIMARY KEY(post_info_detail_id),
    FOREIGN KEY(post_info_id)
    REFERENCES post_info(post_info_id)
);

CREATE TABLE post_info_detail_resource(
    post_info_detail_resource_id BIGINT NOT NULL UNIQUE AUTO_INCREMENT,
    resource VARCHAR(350) NOT NULL,
    record_valid_from_date DATETIME NOT NULL,
    record_valid_to_date DATETIME,
    post_info_detail_id BIGINT NOT NULL,
    PRIMARY KEY(post_info_detail_resource_id),
    FOREIGN KEY(post_info_detail_id)
    REFERENCES post_info_detail(post_info_detail_id)
);

CREATE TABLE user_role_info(
    user_role_info_id BIGINT NOT NULL UNIQUE AUTO_INCREMENT,
    user_role VARCHAR(50) NOT NULL,
    record_valid_from_date DATETIME NOT NULL,
    record_valid_to_date DATETIME,
    user_info_id BIGINT NOT NULL,
    PRIMARY KEY (user_role_info_id),
    FOREIGN KEY(user_info_id)
    REFERENCES user_info(user_info_id)
);