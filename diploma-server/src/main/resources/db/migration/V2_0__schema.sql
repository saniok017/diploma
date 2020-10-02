-- START >>> table 'characters'
CREATE TABLE IF NOT EXISTS characters
(
    id             BIGSERIAL      NOT NULL PRIMARY KEY,
    char_name      VARCHAR(50)    NOT NULL,
    str            SMALLINT       NOT NULL,
    dex            SMALLINT       NOT NULL,
    con            SMALLINT       NOT NULL,
    int            SMALLINT       NOT NULL,
    wis            SMALLINT       NOT NULL,
    cha            SMALLINT       NOT NULL,
    saves_bits     INT            NOT NULL DEFAULT 0,
    background     VARCHAR(50)    NOT NULL,
    player_name    VARCHAR(50)    NOT NULL,
    race           VARCHAR(50)    NOT NULL,
    alignment      VARCHAR(30)    NOT NULL,
    exp_points     INT            NOT NULL DEFAULT 0,
    armor_class    SMALLINT       NOT NULL,
    skills_bits    INT            NOT NULL DEFAULT 0

    -- FIXME: add all required fields!!!
)
WITH (OIDS = FALSE);

ALTER TABLE characters OWNER to postgres;
-- END <<< table 'characters'

-- START >>> table 'character_classes'
CREATE TABLE IF NOT EXISTS character_classes
(
    id              BIGSERIAL      NOT NULL PRIMARY KEY,
    name            VARCHAR(30)    NOT NULL UNIQUE,
    level           SMALLINT       NOT NULL,
    character_id    BIGINT         NOT NULL,

    CONSTRAINT FK_character_classes_characters FOREIGN KEY (character_id) REFERENCES characters (id)
        ON UPDATE CASCADE
        ON DELETE SET NULL
)
WITH (OIDS = FALSE);

ALTER TABLE character_classes OWNER to postgres;
-- END <<< table 'character_classes'

-- START >>> table 'skills'
CREATE TABLE IF NOT EXISTS skills
(
    id         BIGSERIAL      NOT NULL PRIMARY KEY,
    name       VARCHAR(30)    NOT NULL UNIQUE,
    ability    VARCHAR(30)    NOT NULL
)
WITH (OIDS = FALSE);

ALTER TABLE skills OWNER to postgres;
-- END <<< table 'skills'

-- START >>> table 'character_skills'
CREATE TABLE IF NOT EXISTS character_skills
(
    character_id    BIGINT    NOT NULL,
    skill_id        BIGINT    NOT NULL,

    CONSTRAINT PK_character_skills PRIMARY KEY (character_id, skill_id),
    CONSTRAINT FK_character_skills_characters FOREIGN KEY (character_id) REFERENCES characters (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT FK_character_skills_skills FOREIGN KEY (skill_id) REFERENCES skills (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
)
WITH (OIDS = FALSE);

ALTER TABLE character_skills OWNER to postgres;
-- END <<< table 'character_skills'

