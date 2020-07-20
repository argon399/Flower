CREATE TABLE backlog_issue (
    id_workflow   NUMBER NOT NULL,
    id_issue      NUMBER NOT NULL
);

ALTER TABLE backlog_issue ADD CONSTRAINT backlog_issue_pk PRIMARY KEY ( id_workflow,
                                                                        id_issue );

CREATE TABLE issue (
    id             NUMBER NOT NULL,
    name           VARCHAR2(255 BYTE),
    description    VARCHAR2(4000 BYTE),
    date_created   DATE,
    id_parent      NUMBER,
    id_reporter    NUMBER,
    id_executor    NUMBER,
    id_priority    NUMBER,
    id_type        NUMBER,
    id_status      NUMBER
);

ALTER TABLE issue ADD CONSTRAINT issue_pk PRIMARY KEY ( id );

CREATE TABLE list_issue_priority (
    id         NUMBER NOT NULL,
    priority   VARCHAR2(20 BYTE) NOT NULL
);

ALTER TABLE list_issue_priority ADD CONSTRAINT list_issue_priority_pk PRIMARY KEY ( id );

CREATE TABLE list_issue_status (
    id       NUMBER NOT NULL,
    status   VARCHAR2(20 BYTE) NOT NULL
);

ALTER TABLE list_issue_status ADD CONSTRAINT list_issue_status_pk PRIMARY KEY ( id );

CREATE TABLE list_issue_type (
    id     NUMBER NOT NULL,
    type   VARCHAR2(20 BYTE) NOT NULL
);

ALTER TABLE list_issue_type ADD CONSTRAINT list_issue_type_pk PRIMARY KEY ( id );

CREATE TABLE list_team_role (
    id     NUMBER NOT NULL,
    role   VARCHAR2(20 BYTE) NOT NULL
);

ALTER TABLE list_team_role ADD CONSTRAINT list_team_role_pk PRIMARY KEY ( id );

CREATE TABLE list_user_role (
    id            NUMBER NOT NULL,
    role          VARCHAR2(20 BYTE) NOT NULL,
    description   VARCHAR2(1000 BYTE)
);

ALTER TABLE list_user_role ADD CONSTRAINT list_user_role_pk PRIMARY KEY ( id );

CREATE TABLE notification (
    id       NUMBER NOT NULL,
    id_user   NUMBER NOT NULL,
    msg      VARCHAR2(1000 BYTE)
);

ALTER TABLE notification ADD CONSTRAINT notification_pk PRIMARY KEY ( id );

CREATE TABLE sprint (
    id           NUMBER NOT NULL,
    label        VARCHAR2(255 BYTE),
    date_start   DATE,
    date_end     DATE
);

ALTER TABLE sprint ADD CONSTRAINT sprint_pk PRIMARY KEY ( id );

CREATE TABLE sprint_issue (
    id_sprint   NUMBER NOT NULL,
    id_issue    NUMBER NOT NULL
);

ALTER TABLE sprint_issue ADD CONSTRAINT sprint_issue_pk PRIMARY KEY ( id_issue,
                                                                      id_sprint );

CREATE TABLE team (
    id          NUMBER NOT NULL,
    name        VARCHAR2(255 BYTE),
    id_leader   NUMBER
);

ALTER TABLE team ADD CONSTRAINT team_pk PRIMARY KEY ( id );

CREATE TABLE team_member (
    id_team        NUMBER NOT NULL,
    id_member      NUMBER NOT NULL,
    id_team_role   NUMBER NOT NULL
);

ALTER TABLE team_member ADD CONSTRAINT team_member_pk PRIMARY KEY ( id_team,
                                                                    id_member );

CREATE TABLE user_role (
    id_user   NUMBER NOT NULL,
    id_role   NUMBER NOT NULL
);

ALTER TABLE user_role ADD CONSTRAINT user_role_pk PRIMARY KEY ( id_user,
                                                                id_role );

CREATE TABLE workflow_sprint (
    id_workflow   NUMBER NOT NULL,
    id_sprint     NUMBER NOT NULL
);

ALTER TABLE workflow_sprint ADD CONSTRAINT workflow_sprint_pk PRIMARY KEY ( id_workflow,
                                                                            id_sprint );

CREATE TABLE usr (
    id                NUMBER NOT NULL,
    username          VARCHAR2(255 BYTE),
    fio               VARCHAR2(255 BYTE),
    email             VARCHAR2(255 BYTE),
    password          VARCHAR2(255 BYTE),
    active            NUMBER,
    activation_code   VARCHAR2(10 BYTE)
);

ALTER TABLE usr ADD CONSTRAINT usr_pk PRIMARY KEY ( id );

CREATE TABLE workflow (
    id            NUMBER NOT NULL,
    id_team       NUMBER,
    name          VARCHAR2(255 BYTE),
    description   VARCHAR2(4000 BYTE),
    id_admin      NUMBER
);

ALTER TABLE workflow ADD CONSTRAINT workflow_pk PRIMARY KEY ( id );

ALTER TABLE backlog_issue
    ADD CONSTRAINT backlog_issue_issue_fk FOREIGN KEY ( id_issue )
        REFERENCES issue ( id )
            ON DELETE CASCADE;

ALTER TABLE backlog_issue
    ADD CONSTRAINT backlog_issue_workflow_fk FOREIGN KEY ( id_workflow )
        REFERENCES workflow ( id )
            ON DELETE CASCADE;

ALTER TABLE issue
    ADD CONSTRAINT issue_executor_usr_fk FOREIGN KEY ( id_executor )
        REFERENCES usr ( id );

ALTER TABLE issue
    ADD CONSTRAINT issue_issue_fk FOREIGN KEY ( id_parent )
        REFERENCES issue ( id );

ALTER TABLE issue
    ADD CONSTRAINT issue_list_issue_priority_fk FOREIGN KEY ( id_priority )
        REFERENCES list_issue_priority ( id );

ALTER TABLE issue
    ADD CONSTRAINT issue_list_issue_status_fk FOREIGN KEY ( id_status )
        REFERENCES list_issue_status ( id );

ALTER TABLE issue
    ADD CONSTRAINT issue_list_issue_type_fk FOREIGN KEY ( id_type )
        REFERENCES list_issue_type ( id );

ALTER TABLE issue
    ADD CONSTRAINT issue_reporter_usr_fk FOREIGN KEY ( id_reporter )
        REFERENCES usr ( id );

ALTER TABLE notification
    ADD CONSTRAINT notification_usr_fk FOREIGN KEY ( id_usr )
        REFERENCES usr ( id )
            ON DELETE CASCADE;

ALTER TABLE sprint_issue
    ADD CONSTRAINT sprint_issue_issue_fk FOREIGN KEY ( id_issue )
        REFERENCES issue ( id )
            ON DELETE CASCADE;

ALTER TABLE sprint_issue
    ADD CONSTRAINT sprint_issue_sprint_fk FOREIGN KEY ( id_sprint )
        REFERENCES sprint ( id )
            ON DELETE CASCADE;

ALTER TABLE workflow_sprint
    ADD CONSTRAINT workflow_sprint_workflow_fk FOREIGN KEY ( id_workflow )
        REFERENCES workflow ( id )
            ON DELETE CASCADE;

ALTER TABLE workflow_sprint
    ADD CONSTRAINT workflow_sprint_sprint_fk FOREIGN KEY ( id_sprint )
        REFERENCES sprint ( id )
            ON DELETE CASCADE;

ALTER TABLE team_member
    ADD CONSTRAINT team_member_list_team_role_fk FOREIGN KEY ( id_team_role )
        REFERENCES list_team_role ( id );

ALTER TABLE team_member
    ADD CONSTRAINT team_member_team_fk FOREIGN KEY ( id_team )
        REFERENCES team ( id )
            ON DELETE CASCADE;

ALTER TABLE team_member
    ADD CONSTRAINT team_member_usr_fk FOREIGN KEY ( id_member )
        REFERENCES usr ( id )
            ON DELETE CASCADE;

ALTER TABLE team
    ADD CONSTRAINT team_usr_fk FOREIGN KEY ( id_leader )
        REFERENCES usr ( id );

ALTER TABLE user_role
    ADD CONSTRAINT user_role_list_user_role_fk FOREIGN KEY ( id_role )
        REFERENCES list_user_role ( id )
            ON DELETE CASCADE;

ALTER TABLE user_role
    ADD CONSTRAINT user_role_usr_fk FOREIGN KEY ( id_user )
        REFERENCES usr ( id )
            ON DELETE CASCADE;

ALTER TABLE workflow
    ADD CONSTRAINT workflow_team_fk FOREIGN KEY ( id_team )
        REFERENCES team ( id );

ALTER TABLE workflow
    ADD CONSTRAINT workflow_usr_fk FOREIGN KEY ( id_admin )
        REFERENCES usr ( id );