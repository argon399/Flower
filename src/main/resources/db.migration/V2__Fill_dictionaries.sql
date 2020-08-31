INSERT INTO list_user_role (id, description, role) VALUES (0, 'Default role', 'USER');
INSERT INTO list_user_role (id, description, role) VALUES (1, 'Admin role', 'ADMIN');

INSERT INTO list_team_role (id, role) VALUES (0, 'DEVELOPER');
INSERT INTO list_team_role (id, role) VALUES (1, 'LEADER');

INSERT INTO list_issue_priority (id, priority) VALUES (0, 'LOW');
INSERT INTO list_issue_priority (id, priority) VALUES (1, 'MIDDLE');
INSERT INTO list_issue_priority (id, priority) VALUES (2, 'HIGH');

INSERT INTO list_issue_type (id, type) VALUES (0, 'EPIC');
INSERT INTO list_issue_type (id, type) VALUES (1, 'STORY');
INSERT INTO list_issue_type (id, type) VALUES (2, 'TASK');
INSERT INTO list_issue_type (id, type) VALUES (3, 'BUG');

INSERT INTO list_issue_status (id, status) VALUES (0, 'OPEN');
INSERT INTO list_issue_status (id, status) VALUES (1, 'IN_PROGRESS');
INSERT INTO list_issue_status (id, status) VALUES (2, 'REVIEW');
INSERT INTO list_issue_status (id, status) VALUES (3, 'TEST');
INSERT INTO list_issue_status (id, status) VALUES (4, 'RESOLVED');
INSERT INTO list_issue_status (id, status) VALUES (5, 'REOPEN');
INSERT INTO list_issue_status (id, status) VALUES (6, 'CLOSED');