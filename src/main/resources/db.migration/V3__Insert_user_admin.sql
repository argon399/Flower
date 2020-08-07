INSERT INTO usr (id, active, email, fio, password, username) VALUES (0, 1, 'admin@test.xyz', 'Admin', '$2a$08$VwGkzHg.5vt0TrPNInB6.O.pPANhQGhHs7/q6ow5Q3k99loBBCZ8K', 'u');

INSERT INTO user_role (id_user, id_role) VALUES (0, 0);
INSERT INTO user_role (id_user, id_role) VALUES (0, 1);