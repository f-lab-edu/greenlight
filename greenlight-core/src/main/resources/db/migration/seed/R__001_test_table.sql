INSERT INTO users (user_id, username, password, nickname, activated) VALUES ('admin', '관리', '$2a$10$AauittRfwYuSDFjgS/q.ye6ROb9pMQk6tdyRXEm0ImljJ0dYZFL6.', '최고관리자', 1);

INSERT INTO authority (authority_name) values ('ROLE_USER');
INSERT INTO authority (authority_name) values ('ROLE_ADMIN');

INSERT INTO user_authority (user_id, authority_name) values ('admin', 'ROLE_USER');
INSERT INTO user_authority (user_id, authority_name) values ('admin', 'ROLE_ADMIN');
