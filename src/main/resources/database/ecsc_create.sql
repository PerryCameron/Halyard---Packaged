drop database if exists ECSC_SQL;
create database if not exists ECSC_SQL;
use ECSC_SQL;

create table boat
(
    BOAT_ID          INTEGER NOT NULL auto_increment primary key,
    MANUFACTURER     varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
    MANUFACTURE_YEAR varchar(5),
    REGISTRATION_NUM varchar(30),
    MODEL            varchar(40),
    BOAT_NAME        varchar(30),
    SAIL_NUMBER      varchar(20),
    HAS_TRAILER      boolean,
    LENGTH           varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
    WEIGHT           varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
    KEEL             varchar(4),
    PHRF             INTEGER,
    DRAFT varchar(15) NULL,
    BEAM varchar(20) NULL,
    LWL varchar(20) NULL,
    AUX TINYINT(1) DEFAULT 0 NOT NULL
);

create table boat_memo
(
    BOAT_MEMO_ID INTEGER NOT NULL auto_increment primary key,
    BOAT_ID      INTEGER NOT NULL,
    MEMO_DATE    date    NOT NULL,
    MEMO         varchar(2000),
    foreign key (BOAT_ID) references boat (BOAT_ID)
);

CREATE TABLE boat_picture
(
    BOAT_PICTURE_ID   INTEGER NOT NULL auto_increment primary key,
    BOAT_ID           INTEGER NOT NULL,
    BOAT_PICTURE_DATE date    NOT NULL,
    PICTURE_PATH      varchar(2000),
    foreign key (BOAT_ID) references boat (BOAT_ID)
);

create table winter_storage
(
    WS_ID       INTEGER NOT NULL auto_increment primary key,
    BOAT_ID     INTEGER NOT NULL,
    FISCAL_YEAR INTEGER NOT NULL,
    unique (FISCAL_YEAR, BOAT_ID),
    foreign key (BOAT_ID) references boat (BOAT_ID)
);

create table membership
(
    MS_ID     int        NOT NULL auto_increment primary key,
    P_ID      int UNIQUE NOT NULL,
    JOIN_DATE date,
    MEM_TYPE  varchar(4),
    # ACTIVE_MEMBERSHIP boolean, # this may be redundant because of money
    ADDRESS   varchar(40), # each membership has the same address
    CITY      varchar(20),
    STATE     varchar(4),
    ZIP       varchar(15)
);

create table membership_id
(
    MID           INTEGER           NOT NULL auto_increment primary key,
    FISCAL_YEAR   INTEGER NOT NULL,
    MS_ID         INTEGER           NOT NULL,
    MEMBERSHIP_ID INTEGER,
    RENEW         boolean,
    MEM_TYPE      varchar(4),
    SELECTED      boolean,
    LATE_RENEW tinyint(1) NULL,
    foreign key (MS_ID) references membership (MS_ID),
    unique (FISCAL_YEAR, MS_ID),
    unique (FISCAL_YEAR, MEMBERSHIP_ID)

);

create table slip
(
    SLIP_ID      INTEGER               NOT NULL auto_increment primary key,
    MS_ID        INTEGER unique        NULL,
    SLIP_NUM     varchar(4) unique NOT NULL,
    SUBLEASED_TO INTEGER unique,
    ALT_TEXT     varchar(20),
    foreign key (MS_ID) references membership (MS_ID)
);

CREATE TABLE fee
(
    FEE_ID      INTEGER NOT NULL auto_increment primary key,
    FIELD_NAME  varchar(40),
    FIELD_VALUE DECIMAL(10, 2),
    FIELD_QTY   INTEGER,
    FEE_YEAR    INTEGER NOT NULL,
    Description varchar(40) NULL
);

create table memo
(
    MEMO_ID   INTEGER         NOT NULL auto_increment primary key,
    MS_ID     INTEGER         NOT NULL,
    MEMO_DATE date        NOT NULL,
    MEMO      varchar(2000),
    MONEY_ID  INTEGER,
    CATEGORY  varchar(20) NOT NULL,
    foreign key (MS_ID) references membership (MS_ID)
);

create table person
(
    P_ID        INTEGER     NOT NULL auto_increment primary key,
    MS_ID       INTEGER, # attaches person to membership
    MEMBER_TYPE INTEGER, # 1 for primary 2 for secondary 3 for children
    F_NAME      varchar(20),
    L_NAME      varchar(20),
    BIRTHDAY    date,
    OCCUPATION  varchar(50),
    BUISNESS    varchar(50),
    IS_ACTIVE   boolean,
    PICTURE     blob,
    NICK_NAME   varchar(30) NULL,
    OLDMSID     INTEGER     NULL,
    foreign key (MS_ID) references membership (MS_ID)
);

create table email
(
    EMAIL_ID     INTEGER NOT NULL auto_increment primary key,
    P_ID         INTEGER NOT NULL,
    PRIMARY_USE  boolean,
    EMAIL        varchar(60),
    EMAIL_LISTED boolean,
    foreign key (P_ID) references person (P_ID)
);

create table phone
(
    PHONE_ID     INTEGER NOT NULL auto_increment primary key,
    P_ID         INTEGER NOT NULL,
    PHONE        varchar(30),
    PHONE_TYPE   varchar(30),
    PHONE_LISTED boolean,
    foreign key (P_ID) references person (P_ID)
);

create table boat_owner
(
    MS_ID   INTEGER NOT NULL,
    BOAT_ID INTEGER NOT NULL,
    foreign key (MS_ID) references membership (MS_ID),
    foreign key (BOAT_ID) references boat (BOAT_ID)
);

-- ALTER TABLE ECSC_SQL.boat_owner
-- DROP FOREIGN KEY boat_owner_ibfk_2;
-- #We want to change this to be a key but not prevent deletions, so make sure boat exists only for creation

create table deposit
(
    DEPOSIT_ID   INTEGER NOT NULL auto_increment primary key unique,
    DEPOSIT_DATE date    NOT NULL,
    FISCAL_YEAR  INTEGER NOT NULL,
    BATCH        INTEGER NOT NULL,
    unique (FISCAL_YEAR, BATCH)
);

create table money
(
    MONEY_ID               int                     NOT NULL auto_increment primary key unique,
    MS_ID                  int                     NOT NULL,
    FISCAL_YEAR            INTEGER                 NULL,
    BATCH                  INTEGER                 NULL,
    OFFICER_CREDIT         DECIMAL(10, 2)          NULL, #this is the dues and gets derived.
    EXTRA_KEY              INTEGER                 NULL,
    KAYAK_SHED_KEY         INTEGER                 NULL,
    SAIL_LOFT_KEY          INTEGER                 NULL,
    SAIL_SCHOOL_LOFT_KEY   INTEGER                 NULL,
    BEACH                  INTEGER                 NULL,
    WET_SLIP               DECIMAL(10, 2)          NULL,
    KAYAK_RACK             INTEGER                 NULL,
    KAYAK_SHED             INTEGER                 NULL,
    SAIL_LOFT              INTEGER                 NULL,
    SAIL_SCHOOL_LASER_LOFT INTEGER                 NULL,
    WINTER_STORAGE         INTEGER                 NULL,
    YSC_DONATION           DECIMAL(10, 2)          NULL,
    PAID                   DECIMAL(10, 2)          NULL,
    TOTAL                  DECIMAL(10, 2)          NULL,
    CREDIT                 DECIMAL(10, 2)          NULL,
    BALANCE                DECIMAL(10, 2)          NULL,
    DUES                   DECIMAL(10, 2)          NULL,
    COMMITED               boolean,
    CLOSED                 boolean,
    OTHER                  DECIMAL(10, 2)          NULL,
    INITIATION             DECIMAL(10, 2)          NULL,
    SUPPLEMENTAL           boolean,
    WORK_CREDIT            INT                     NULL,
    OTHER_CREDIT           DECIMAL(10, 2)          NULL,
    KAYAK_BEACH_RACK       DECIMAL(4, 0) DEFAULT 0 NULL,
    foreign key (MS_ID) references membership (MS_ID)
);

create table payment
(
    PAY_ID       INTEGER        NOT NULL auto_increment primary key,
    MONEY_ID     INTEGER        NOT NULL,
    CHECKNUMBER  VARCHAR(20)    NULL,
    PAYMENT_TYPE varchar(4)     NOT NULL,
    PAYMENT_DATE date           NOT NULL,
    AMOUNT       DECIMAL(10, 2) NOT NULL,
    DEPOSIT_ID   INTEGER        NOT NULL,
    foreign key (DEPOSIT_ID) references deposit (DEPOSIT_ID),
    foreign key (MONEY_ID) references money (MONEY_ID)
);

# should attach to money_id, if put in early, just create money_id along with it
create table officer
(
    O_ID       INTEGER NOT NULL auto_increment primary key,
    P_ID       INTEGER NOT NULL,
    BOARD_YEAR INTEGER NULL,
    OFF_TYPE   varchar(20),
    OFF_YEAR   INTEGER NULL, # This maintains the record forever
    foreign key (P_ID) references person (P_ID),
    unique (P_ID, OFF_YEAR, OFF_TYPE)
);

create table defined_fee
(
    FISCAL_YEAR            INTEGER unique primary key,
    DUES_REGULAR           DECIMAL(10, 2) NULL,
    DUES_FAMILY            DECIMAL(10, 2) NULL,
    DUES_LAKE_ASSOCIATE    DECIMAL(10, 2) NULL,
    DUES_SOCIAL            DECIMAL(10, 2) NULL,
    INITIATION             DECIMAL(10, 2) NULL,
    WET_SLIP               DECIMAL(10, 2) NULL,
    BEACH                  DECIMAL(10, 2) NULL,
    WINTER_STORAGE         DECIMAL(10, 2) NULL,
    MAIN_GATE_KEY          DECIMAL(10, 2) NULL,
    SAIL_LOFT              DECIMAL(10, 2) NULL,
    SAIL_LOFT_KEY          DECIMAL(10, 2) NULL,
    SAIL_SCHOOL_LASER_LOFT DECIMAL(10, 2) NULL,
    SAIL_SCHOOL_LOFT_KEY   DECIMAL(10, 2) NULL,
    KAYAK_RACK             DECIMAL(10, 2) NULL,
    KAYAK_SHED             DECIMAL(10, 2) NULL,
    KAYAK_SHED_KEY         DECIMAL(10, 2) NULL,
    WORK_CREDIT            DECIMAL(10, 2) NULL,
    KAYAK_BEACH_RACK       DECIMAL(10, 2) NULL
);

CREATE TABLE stats
(
    STAT_ID            INTEGER NOT NULL PRIMARY KEY UNIQUE,
    FISCAL_YEAR        INTEGER NOT NULL,
    ACTIVE_MEMBERSHIPS INTEGER,
    NON_RENEW          INTEGER,
    RETURN_MEMBERS     INTEGER,
    NEW_MEMBERS        INTEGER,
    SECONDARY_MEMBERS  INTEGER,
    DEPENDANTS         INTEGER,
    NUMBER_OF_BOATS    INTEGER,
    FAMILY             INTEGER,
    REGULAR            INTEGER,
    SOCIAL             INTEGER,
    LAKEASSOCIATES     INTEGER,
    LIFEMEMBERS        INTEGER,
    RACEFELLOWS        INTEGER,
    STUDENT            INTEGER,
    DEPOSITS           DECIMAL(13, 2),
    INIATION           DECIMAL(13, 2)
);

CREATE TABLE awards
(
    AWARD_ID   int         NOT NULL auto_increment primary key,
    P_ID       int         NOT NULL,
    AWARD_YEAR varchar(10) NOT NULL,
    AWARD_TYPE varchar(10) NOT NULL,
    foreign key (P_ID) references person (P_ID)
);

-- # one-to-one relation with money
create table work_credit
(
    MONEY_ID int NOT NULL primary key unique,
    MS_ID    int NOT NULL,
    RACING   INTEGER NULL,
    HARBOR   INTEGER NULL,
    SOCIAL   INTEGER NULL,
    OTHER    INTEGER NULL,
    foreign key (MONEY_ID) references money (MONEY_ID) on DELETE no action on UPDATE no action
);

-- #one-to-one relation with membership
create table waitlist
(
    MS_ID          int NOT NULL primary key unique,
    SLIPWAIT       boolean,
    KAYAKRACKWAIT  boolean,
    SHEDWAIT       boolean,
    WANTSUBLEASE   boolean,
    WANTRELEASE    boolean,
    WANTSLIPCHANGE boolean,
    foreign key (MS_ID) references membership (MS_ID) on DELETE no action on UPDATE no action
);

CREATE TABLE id_change
(
    CHANGE_ID int NOT NULL auto_increment primary key,
    ID_YEAR   int NOT NULL unique,
    CHANGED   boolean
);

-- this is the api key for jotform
CREATE TABLE api_key
(
    API_ID int         NOT NULL auto_increment primary key,
    NAME   varchar(50) NOT NULL unique,
    APIKEY varchar(50) NOT NULL unique,
    ts     TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- this stores a hash for each membership
create table form_msid_hash
(
    HASH_ID int           NOT NULL auto_increment primary key,
    HASH    BIGINT unique NOT NULL,
    MS_ID   int           NOT NULL,
    foreign key (MS_ID) references membership (MS_ID) on DELETE no action on UPDATE no action
);

-- This one row table holds email credentials to send email
CREATE TABLE ECSC_SQL.form_email_auth
(
    HOST      varchar(100),
    PORT      int,
    USER      varchar(100) primary key unique,
    PASS      varchar(100),
    PROTOCOL  varchar(20),
    SMTP_AUTH boolean,
    TTLS      boolean,
    DEBUG     boolean
);

-- Table to record everytime a request for a hash is made
CREATE TABLE ECSC_SQL.form_hash_request
(
    FORM_HASH_ID int       NOT NULL auto_increment primary key unique,
    REQ_DATE     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRI_MEM      varchar(20),
    LINK         varchar(120),
    MSID         int       NOT NULL,
    MAILED_TO    varchar(120)
);

-- Table to record everytime a form request is made
CREATE TABLE ECSC_SQL.form_request
(
    FORM_ID  int       NOT NULL auto_increment primary key unique,
    REQ_DATE TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRI_MEM  varchar(20),
    MSID     int       NOT NULL,
    SUCCESS  boolean
);

-- This one row table holds settings for program
CREATE TABLE ECSC_SQL.form_settings
(
    form_id  varchar(60)  not null primary key,
    PORT     int,
    LINK     varchar(200),
    form_url varchar(255) not null
);

-- user authentication database
CREATE TABLE ECSC_SQL.users
(
    id       INT                                 NOT NULL primary key,
    username varchar(50) COLLATE UTF8_GENERAL_CI not null,
    password varchar(50) COLLATE UTF8_GENERAL_CI not null,
    enabled  boolean                             not null
);

CREATE TABLE ECSC_SQL.board_positions
(
    id LONG NOT NULL primary key,
    position varchar(50) unique not null,
    identifier varchar(5) unique not null,
    order INTEGER not null,
    is_officer boolean not null,
    is_chair boolean not null,
    is_assistant_chair boolean not null
);



