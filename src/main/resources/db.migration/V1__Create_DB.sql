CREATE TABLE backlog_issue (
    id_project   INT NOT NULL,
    id_issue      INT NOT NULL
);

ALTER TABLE backlog_issue ADD CONSTRAINT backlog_issue_pk PRIMARY KEY ( id_project,
                                                                        id_issue );

CREATE TABLE issue (
    id             INT NOT NULL,
    name           VARCHAR(255),
    description    VARCHAR(4000),
    date_created   DATE,
    id_parent      INT,
    id_reporter    INT,
    id_executor    INT,
    id_priority    INT,
    id_type        INT,
    id_status      INT
);

ALTER TABLE issue ADD CONSTRAINT issue_pk PRIMARY KEY ( id );

CREATE TABLE list_issue_priority (
    id         INT NOT NULL,
    priority   VARCHAR(20) NOT NULL
);

ALTER TABLE list_issue_priority ADD CONSTRAINT list_issue_priority_pk PRIMARY KEY ( id );

CREATE TABLE list_issue_status (
    id       INT NOT NULL,
    status   VARCHAR(20) NOT NULL
);

ALTER TABLE list_issue_status ADD CONSTRAINT list_issue_status_pk PRIMARY KEY ( id );

CREATE TABLE list_issue_type (
    id     INT NOT NULL,
    type   VARCHAR(20) NOT NULL
);

ALTER TABLE list_issue_type ADD CONSTRAINT list_issue_type_pk PRIMARY KEY ( id );

CREATE TABLE list_team_role (
    id     INT NOT NULL,
    role   VARCHAR(20) NOT NULL
);

ALTER TABLE list_team_role ADD CONSTRAINT list_team_role_pk PRIMARY KEY ( id );

CREATE TABLE list_user_role (
    id            INT NOT NULL,
    role          VARCHAR(20) NOT NULL,
    description   VARCHAR(1000)
);

ALTER TABLE list_user_role ADD CONSTRAINT list_user_role_pk PRIMARY KEY ( id );

CREATE TABLE sprint (
    id           INT NOT NULL,
    label        VARCHAR(255),
    date_start   DATE,
    date_end     DATE
);

ALTER TABLE sprint ADD CONSTRAINT sprint_pk PRIMARY KEY ( id );

CREATE TABLE sprint_issue (
    id_sprint   INT NOT NULL,
    id_issue    INT NOT NULL
);

ALTER TABLE sprint_issue ADD CONSTRAINT sprint_issue_pk PRIMARY KEY ( id_issue,
                                                                      id_sprint );

CREATE TABLE team (
    id          INT NOT NULL,
    name        VARCHAR(255),
    id_leader   INT
);

ALTER TABLE team ADD CONSTRAINT team_pk PRIMARY KEY ( id );

CREATE TABLE team_member (
    id_team        INT NOT NULL,
    id_member      INT NOT NULL,
    id_team_role   INT NOT NULL
);

ALTER TABLE team_member ADD CONSTRAINT team_member_pk PRIMARY KEY ( id_team,
                                                                    id_member );

CREATE TABLE user_role (
    id_user   INT NOT NULL,
    id_role   INT NOT NULL
);

ALTER TABLE user_role ADD CONSTRAINT user_role_pk PRIMARY KEY ( id_user,
                                                                id_role );

CREATE TABLE project_sprint (
    id_project   INT NOT NULL,
    id_sprint     INT NOT NULL
);

ALTER TABLE project_sprint ADD CONSTRAINT project_sprint_pk PRIMARY KEY ( id_project,
                                                                          id_sprint );

CREATE TABLE usr (
    id                INT NOT NULL,
    username          VARCHAR(255),
    fio               VARCHAR(255),
    email             VARCHAR(255),
    password          VARCHAR(255),
    active            INT,
    activation_code   VARCHAR(100),
    new_password      VARCHAR(255),
    password_code     VARCHAR(100)
);

ALTER TABLE usr ADD CONSTRAINT usr_pk PRIMARY KEY ( id );

CREATE TABLE project (
    id            INT NOT NULL,
    id_team       INT,
    name          VARCHAR(255),
    description   VARCHAR(4000),
    id_owner      INT
);

ALTER TABLE project ADD CONSTRAINT project_pk PRIMARY KEY ( id );

ALTER TABLE backlog_issue
    ADD CONSTRAINT backlog_issue_issue_fk FOREIGN KEY ( id_issue )
        REFERENCES issue ( id )
            ON DELETE CASCADE;

ALTER TABLE backlog_issue
    ADD CONSTRAINT backlog_issue_project_fk FOREIGN KEY ( id_project )
        REFERENCES project ( id )
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

ALTER TABLE sprint_issue
    ADD CONSTRAINT sprint_issue_issue_fk FOREIGN KEY ( id_issue )
        REFERENCES issue ( id )
            ON DELETE CASCADE;

ALTER TABLE sprint_issue
    ADD CONSTRAINT sprint_issue_sprint_fk FOREIGN KEY ( id_sprint )
        REFERENCES sprint ( id )
            ON DELETE CASCADE;

ALTER TABLE project_sprint
    ADD CONSTRAINT project_sprint_project_fk FOREIGN KEY ( id_project )
        REFERENCES project ( id )
            ON DELETE CASCADE;

ALTER TABLE project_sprint
    ADD CONSTRAINT project_sprint_sprint_fk FOREIGN KEY ( id_sprint )
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

ALTER TABLE project
    ADD CONSTRAINT project_team_fk FOREIGN KEY ( id_team )
        REFERENCES team ( id );

ALTER TABLE project
    ADD CONSTRAINT project_usr_fk FOREIGN KEY ( id_owner )
        REFERENCES usr ( id );