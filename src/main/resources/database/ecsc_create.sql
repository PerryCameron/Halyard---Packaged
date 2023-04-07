drop database if exists ECSC_SQL;
create database if not exists ECSC_SQL;
use ECSC_SQL;

create table ECSC_SQL.boat
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
    WEIGHT           varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
    KEEL             varchar(4),
    PHRF             INTEGER,
    DRAFT varchar(15) NULL,
    BEAM varchar(20) NULL,
    LWL varchar(20) NULL,
    AUX TINYINT(1) DEFAULT 0 NOT NULL
);

create table ECSC_SQL.boat_memo
(
    BOAT_MEMO_ID INTEGER NOT NULL auto_increment primary key,
    BOAT_ID      INTEGER NOT NULL,
    MEMO_DATE    date    NOT NULL,
    MEMO         varchar(2000),
    foreign key (BOAT_ID) references boat (BOAT_ID)
);

CREATE TABLE ECSC_SQL.boat_picture
(
    BOAT_PICTURE_ID   INTEGER NOT NULL auto_increment primary key,
    BOAT_ID           INTEGER NOT NULL,
    BOAT_PICTURE_DATE date    NOT NULL,
    PICTURE_PATH      varchar(2000),
    foreign key (BOAT_ID) references boat (BOAT_ID)
);

create table ECSC_SQL.winter_storage
(
    WS_ID       INTEGER NOT NULL auto_increment primary key,
    BOAT_ID     INTEGER NOT NULL,
    FISCAL_YEAR INTEGER NOT NULL,
    unique (FISCAL_YEAR, BOAT_ID),
    foreign key (BOAT_ID) references boat (BOAT_ID)
);

create table ECSC_SQL.membership
(
    MS_ID     int        NOT NULL auto_increment primary key,
    P_ID      int UNIQUE NOT NULL,
    JOIN_DATE date,
    MEM_TYPE  varchar(4),
    ADDRESS   varchar(40), # each membership has the same address
    CITY      varchar(20),
    STATE     varchar(4),
    ZIP       varchar(15)
);

create table ECSC_SQL.membership_id
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

create table ECSC_SQL.slip
(
    SLIP_ID      INTEGER               NOT NULL auto_increment primary key,
    MS_ID        INTEGER unique        NULL,
    SLIP_NUM     varchar(4) unique NOT NULL,
    SUBLEASED_TO INTEGER unique,
    ALT_TEXT     varchar(20),
    foreign key (MS_ID) references membership (MS_ID)
);

CREATE TABLE ECSC_SQL.fee
(
    FEE_ID        INTEGER                    NOT NULL auto_increment primary key,
    FIELD_NAME    varchar(40),
    FIELD_VALUE   DECIMAL(10, 2),
    DB_INVOICE_ID INTEGER                    NOT NULL,
    FEE_YEAR      INTEGER                    NOT NULL,
    Description   varchar(40)                NULL,
    DEFAULT_FEE   boolean                    NOT NULL,
    foreign key (DB_INVOICE_ID) references db_invoice (ID)
);

create table ECSC_SQL.memo
(
    MEMO_ID    INTEGER     NOT NULL auto_increment primary key,
    MS_ID      INTEGER     NOT NULL,
    MEMO_DATE  date        NOT NULL,
    MEMO       varchar(2000),
    INVOICE_ID INTEGER,
    CATEGORY   varchar(20) NOT NULL,
    BOAT_ID    INTEGER,
    foreign key (MS_ID) references membership (MS_ID)
);

create table ECSC_SQL.person
(
    P_ID        INTEGER     NOT NULL auto_increment primary key,
    MS_ID       INTEGER, # attaches person to membership
    MEMBER_TYPE INTEGER, # 1 for primary 2 for secondary 3 for children
    F_NAME      varchar(20),
    L_NAME      varchar(20),
    BIRTHDAY    date,
    OCCUPATION  varchar(50),
    BUSINESS    varchar(50),
    IS_ACTIVE   boolean,
    PICTURE     blob,
    NICK_NAME   varchar(30) NULL,
    OLD_MSID     INTEGER     NULL,
    foreign key (MS_ID) references membership (MS_ID)
);

create table ECSC_SQL.email
(
    EMAIL_ID     INTEGER NOT NULL auto_increment primary key,
    P_ID         INTEGER NOT NULL,
    PRIMARY_USE  boolean,
    EMAIL        varchar(60),
    EMAIL_LISTED boolean,
    foreign key (P_ID) references person (P_ID)
);

create table ECSC_SQL.phone
(
    PHONE_ID     INTEGER NOT NULL auto_increment primary key,
    P_ID         INTEGER NOT NULL,
    PHONE        varchar(30),
    PHONE_TYPE   varchar(30),
    PHONE_LISTED boolean,
    foreign key (P_ID) references person (P_ID)
);

create table ECSC_SQL.boat_owner
(
    MS_ID   INTEGER NOT NULL,
    BOAT_ID INTEGER NOT NULL,
    foreign key (MS_ID) references membership (MS_ID),
    foreign key (BOAT_ID) references boat (BOAT_ID)
);

-- ALTER TABLE ECSC_SQL.boat_owner
-- DROP FOREIGN KEY boat_owner_;
-- #We want to change this to be a key but not prevent deletions, so make sure boat exists only for creation

create table ECSC_SQL.deposit
(
    DEPOSIT_ID   INTEGER NOT NULL auto_increment primary key unique,
    DEPOSIT_DATE date    NOT NULL,
    FISCAL_YEAR  INTEGER NOT NULL,
    BATCH        INTEGER NOT NULL,
    unique (FISCAL_YEAR, BATCH)
);

create table ECSC_SQL.invoice
(
    ID           INTEGER                     NOT NULL auto_increment primary key unique,
    MS_ID        INTEGER                     NOT NULL,
    FISCAL_YEAR  INTEGER                     NULL,
    PAID         DECIMAL(10, 2)              NULL,
    TOTAL        DECIMAL(10, 2)              NULL,
    CREDIT       DECIMAL(10, 2)              NULL,
    BALANCE      DECIMAL(10, 2)              NULL,
    BATCH        INTEGER                     NULL,
    COMMITTED    boolean,
    CLOSED       boolean,
    SUPPLEMENTAL boolean,
    MAX_CREDIT   decimal(10, 2) default 0.00 not null,
    foreign key (MS_ID) references membership (MS_ID)
);

create table ECSC_SQL.invoice_item
(
    ID          INTEGER        NOT NULL auto_increment primary key unique,
    INVOICE_ID  INTEGER        NOT NULL,
    MS_ID       INTEGER        NOT NULL,
    FISCAL_YEAR INTEGER        NULL,
    FIELD_NAME   varchar(50)    NOT NULL,
    IS_CREDIT   boolean        NOT NULL,
    VALUE       DECIMAL(10, 2) NULL,
    QTY         INTEGER        NULL,
    foreign key (INVOICE_ID) references invoice(ID),
    foreign key (MS_ID) references membership (MS_ID)
);

create table ECSC_SQL.payment
(
    PAY_ID       INTEGER        NOT NULL auto_increment primary key,
    INVOICE_ID     INTEGER        NOT NULL,
    CHECK_NUMBER  VARCHAR(20)    NULL,
    PAYMENT_TYPE varchar(4)     NOT NULL,
    PAYMENT_DATE date           NOT NULL,
    AMOUNT       DECIMAL(10, 2) NOT NULL,
    DEPOSIT_ID   INTEGER        NOT NULL,
    foreign key (DEPOSIT_ID) references deposit (DEPOSIT_ID),
    foreign key (INVOICE_ID) references invoice (ID)
);

# should attach to invoice_id, if put in early, just create invoice_id along with it
create table ECSC_SQL.officer
(
    O_ID       INTEGER NOT NULL auto_increment primary key,
    P_ID       INTEGER NOT NULL,
    BOARD_YEAR INTEGER NULL,
    OFF_TYPE   varchar(20),
    OFF_YEAR   INTEGER NULL, # This maintains the record forever
    foreign key (P_ID) references person (P_ID),
    unique (P_ID, OFF_YEAR, OFF_TYPE)
);

CREATE TABLE ECSC_SQL.stats
(
    STAT_ID            INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
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
    LAKE_ASSOCIATES    INTEGER,
    LIFE_MEMBERS       INTEGER,
    RACE_FELLOWS       INTEGER,
    STUDENT            INTEGER,
    DEPOSITS           DECIMAL(13, 2),
    INITIATION         DECIMAL(13, 2)
);

CREATE TABLE ECSC_SQL.awards
(
    AWARD_ID   int         NOT NULL auto_increment primary key,
    P_ID       int         NOT NULL,
    AWARD_YEAR varchar(10) NOT NULL,
    AWARD_TYPE varchar(10) NOT NULL,
    foreign key (P_ID) references person (P_ID)
);

-- #one-to-one relation with membership
create table ECSC_SQL.wait_list
(
    MS_ID          int NOT NULL primary key unique,
    SLIP_WAIT       boolean,
    KAYAK_RACK_WAIT  boolean,
    SHED_WAIT       boolean,
    WANT_SUBLEASE   boolean,
    WANT_RELEASE    boolean,
    WANT_SLIP_CHANGE boolean,
    foreign key (MS_ID) references membership (MS_ID) on DELETE no action on UPDATE no action
);

CREATE TABLE ECSC_SQL.id_change
(
    CHANGE_ID int NOT NULL auto_increment primary key,
    ID_YEAR   int NOT NULL unique,
    CHANGED   boolean
);

-- this is the api key for jotform
CREATE TABLE ECSC_SQL.api_key
(
    API_ID int         NOT NULL auto_increment primary key,
    NAME   varchar(50) NOT NULL unique,
    APIKEY varchar(50) NOT NULL unique,
    ts     TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- this stores a hash for each membership
create table ECSC_SQL.form_msid_hash
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
    PRI_MEM  varchar(120),
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
    id INTEGER NOT NULL primary key,
    position varchar(50) unique not null,
    identifier varchar(5) unique not null,
    list_order INTEGER not null,
    is_officer boolean not null,
    is_chair boolean not null,
    is_assistant_chair boolean not null
);

CREATE TABLE ECSC_SQL.db_updates
(
    ID                INTEGER  NOT NULL primary key,
    SQL_CREATION_DATE DATETIME NULL,
    IS_CLOSED         boolean  NOT NULL,
    DB_SIZE           DOUBLE   NULL
);

CREATE TABLE ECSC_SQL.db_table_changes
(
    id            INTEGER   NOT NULL primary key,
    db_updates_id INTEGER   NOT NULL,
    table_changed varchar(50),
    table_insert  INTEGER   NOT NULL,
    table_delete  INTEGER   NOT NULL,
    table_update  INTEGER   NOT NULL,
    change_date   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    changed_by    varchar(100),
    foreign key (db_updates_id) references db_updates (ID)
);

CREATE Table ECSC_SQL.db_invoice
(
    id             INTEGER           NOT NULL primary key,
    FISCAL_YEAR    INTEGER           NOT NULL,
    FIELD_NAME     varchar(30)       NOT NULL,
    widget_type    varchar(30)       NOT NULL,
    width          double,
    invoice_order  INTEGER           NOT NULL,
    multiplied     boolean           NOT NULL,
    price_editable boolean           NOT NULL,
    is_credit      boolean           NOT NULL,
    max_qty        INTEGER           NOT NULL,
    auto_populate  boolean default 0 not null,
    is_itemized    boolean default 0 not null
);

create table ECSC_SQL.db_boat
(
    ID           INTEGER     NOT NULL auto_increment primary key,
    name         varchar(30) NOT NULL,
    control_type varchar(30) NOT NULL,
    data_type    varchar(30) NOT NULL,
    field_name   varchar(30) NOT NULL,
    list_order        INTEGER NOT NULL
);

create table ECSC_SQL.db_membership_list
(
    ID           INTEGER     NOT NULL auto_increment primary key,
    name         varchar(30) NOT NULL,
    pojo_name varchar(30) NOT NULL,
    data_type    varchar(30) NOT NULL,
    field_name   varchar(30) NOT NULL,
    list_order        INTEGER NOT NULL
);

create table ECSC_SQL.db_membership_list_selection
(
    ID            INTEGER      NOT NULL auto_increment primary key,
    LABEL         varchar(40)  NOT NULL,
    SQL_QUERY     varchar(2000),
    LIST_ORDER    INTEGER      NOT NULL,
    LIST          INTEGER      NOT NULL
);

create table ECSC_SQL.boat_photos
(
    ID            INTEGER      NOT NULL auto_increment primary key,
    BOAT_ID       INTEGER      NOT NULL,
    upload_date   DATETIME     NULL,
    filename      varchar(200) NOT NULL,
    file_number   INTEGER      NOT NULL,
    default_image boolean default 0,
    foreign key (BOAT_ID) references boat (BOAT_ID)
);

create table ECSC_SQL.boat_selection
(
    ID            INTEGER      NOT NULL auto_increment primary key,
    LABEL         varchar(40)  NOT NULL,
    SQL_QUERY     varchar(2000),
    LIST_ORDER    INTEGER      NOT NULL
)


