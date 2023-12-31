drop database if exists ECSC_SQL;
create database if not exists ECSC_SQL;
use ECSC_SQL;

create table boat
(
    BOAT_ID          int auto_increment
        primary key,
    MANUFACTURER     varchar(40)          null,
    MANUFACTURE_YEAR varchar(5)           null,
    REGISTRATION_NUM varchar(30)          null,
    MODEL            varchar(40)          null,
    BOAT_NAME        varchar(30)          null,
    SAIL_NUMBER      varchar(20)          null,
    HAS_TRAILER      tinyint(1)           null,
    LENGTH           varchar(20)          null,
    WEIGHT           varchar(20)          null,
    KEEL             varchar(4)           null,
    PHRF             int                  null,
    DRAFT            varchar(20)          null,
    BEAM             varchar(20)          null,
    LWL              varchar(20)          null,
    AUX              tinyint(1) default 0 not null
)
    collate = utf8mb4_unicode_ci;

create table memo
(
    MEMO_ID    int auto_increment
        primary key,
    MS_ID      int           not null,
    MEMO_DATE  date          not null,
    MEMO       varchar(2000) null,
    INVOICE_ID int           null,
    CATEGORY   varchar(20)   not null,
    boat_id    int           null
)
    collate = utf8mb4_unicode_ci;


create table boat_photos
(
    ID            int auto_increment
        primary key,
    BOAT_ID       int                  not null,
    upload_date   datetime             null,
    filename      varchar(200)         not null,
    file_number   int                  not null,
    default_image tinyint(1) default 0 null,
    constraint boat_photos_ibfk_1
        foreign key (BOAT_ID) references boat (BOAT_ID)
)
    collate = utf8mb4_unicode_ci;

create index BOAT_ID
    on boat_photos (BOAT_ID);

create table winter_storage
(
    WS_ID       int auto_increment
        primary key,
    BOAT_ID     int not null,
    FISCAL_YEAR int not null,
    constraint FISCAL_YEAR
        unique (FISCAL_YEAR, BOAT_ID),
    constraint winter_storage_ibfk_1
        foreign key (BOAT_ID) references boat (BOAT_ID)
)
    collate = utf8mb4_unicode_ci;


create table membership
(
    MS_ID     int auto_increment
        primary key,
    P_ID      int         not null,
    JOIN_DATE date        null,
    MEM_TYPE  varchar(4)  null,
    ADDRESS   varchar(40) null,
    CITY      varchar(20) null,
    STATE     varchar(4)  null,
    ZIP       varchar(15) null,
    constraint P_ID
        unique (P_ID)
)
    collate = utf8mb4_unicode_ci;

create table membership_id
(
    MID           int auto_increment
        primary key,
    FISCAL_YEAR   int        not null,
    MS_ID         int        not null,
    MEMBERSHIP_ID int        null,
    RENEW         tinyint(1) null,
    MEM_TYPE      varchar(4) null,
    SELECTED      tinyint(1) null,
    LATE_RENEW    tinyint(1) null,
    constraint FISCAL_YEAR
        unique (FISCAL_YEAR, MS_ID),
    constraint FISCAL_YEAR_2
        unique (FISCAL_YEAR, MEMBERSHIP_ID),
    constraint membership_id_ibfk_1
        foreign key (MS_ID) references membership (MS_ID)
)
    collate = utf8mb4_unicode_ci;

create index MS_ID
    on membership_id (MS_ID);

create table slip
(
    SLIP_ID      int auto_increment
        primary key,
    MS_ID        int         null,
    SLIP_NUM     varchar(4)  not null,
    SUBLEASED_TO int         null,
    ALT_TEXT     varchar(20) null,
    constraint MS_ID
        unique (MS_ID),
    constraint SLIP_NUM
        unique (SLIP_NUM),
    constraint SUBLEASED_TO
        unique (SUBLEASED_TO),
    constraint slip_ibfk_1
        foreign key (MS_ID) references membership (MS_ID)
)
    collate = utf8mb4_unicode_ci;

create table fee
(
    FEE_ID        int auto_increment
        primary key,
    FIELD_NAME    varchar(40)          null,
    FIELD_VALUE   decimal(10, 2)       null,
    DB_INVOICE_ID int                  not null,
    FEE_YEAR      int                  not null,
    Description   varchar(40)          null,
    DEFAULT_FEE   tinyint(1) default 0 not null
)
    collate = utf8mb4_unicode_ci;

create table memo
(
    MEMO_ID    int auto_increment
        primary key,
    MS_ID      int           not null,
    MEMO_DATE  date          not null,
    MEMO       varchar(2000) null,
    INVOICE_ID int           null,
    CATEGORY   varchar(20)   not null,
    boat_id    int           null
)
    collate = utf8mb4_unicode_ci;

create index MS_ID
    on memo (MS_ID);

create table person
(
    P_ID        int auto_increment
        primary key,
    MS_ID       int         null,
    MEMBER_TYPE int         null,
    F_NAME      varchar(20) null,
    L_NAME      varchar(20) null,
    BIRTHDAY    date        null,
    OCCUPATION  varchar(50) null,
    BUSINESS    varchar(50) null,
    IS_ACTIVE   tinyint(1)  null,
    PICTURE     blob        null,
    NICK_NAME   varchar(30) null,
    OLD_MSID    int         null,
    constraint person_ibfk_1
        foreign key (MS_ID) references membership (MS_ID)
)
    collate = utf8mb4_unicode_ci;

create index MS_ID
    on person (MS_ID);

create table email
(
    EMAIL_ID     int auto_increment
        primary key,
    P_ID         int         not null,
    PRIMARY_USE  tinyint(1)  null,
    EMAIL        varchar(60) null,
    EMAIL_LISTED tinyint(1)  null,
    constraint email_ibfk_1
        foreign key (P_ID) references person (P_ID)
)
    collate = utf8mb4_unicode_ci;

create index P_ID
    on email (P_ID);

create table phone
(
    PHONE_ID     int auto_increment
        primary key,
    P_ID         int         not null,
    PHONE        varchar(30) null,
    PHONE_TYPE   varchar(30) null,
    PHONE_LISTED tinyint(1)  null,
    constraint phone_ibfk_1
        foreign key (P_ID) references person (P_ID)
)
    collate = utf8mb4_unicode_ci;

create index P_ID
    on phone (P_ID);

create table boat_owner
(
    MS_ID   int not null,
    BOAT_ID int not null,
    constraint boat_owner_ibfk_1
        foreign key (MS_ID) references membership (MS_ID),
    constraint boat_owner_ibfk_2
        foreign key (BOAT_ID) references boat (BOAT_ID)
)
    collate = utf8mb4_unicode_ci;

create index BOAT_ID
    on boat_owner (BOAT_ID);

create index MS_ID
    on boat_owner (MS_ID);

create table deposit
(
    DEPOSIT_ID   int auto_increment
        primary key,
    DEPOSIT_DATE date not null,
    FISCAL_YEAR  int  not null,
    BATCH        int  not null,
    constraint DEPOSIT_ID
        unique (DEPOSIT_ID),
    constraint FISCAL_YEAR
        unique (FISCAL_YEAR, BATCH)
)
    collate = utf8mb4_unicode_ci;

create table invoice
(
    ID           int auto_increment
        primary key,
    MS_ID        int                         not null,
    FISCAL_YEAR  int                         null,
    PAID         decimal(10, 2)              null,
    TOTAL        decimal(10, 2)              null,
    CREDIT       decimal(10, 2)              null,
    BALANCE      decimal(10, 2)              null,
    BATCH        int                         null,
    COMMITTED    tinyint(1)                  null,
    CLOSED       tinyint(1)                  null,
    SUPPLEMENTAL tinyint(1)                  null,
    MAX_CREDIT   decimal(10, 2) default 0.00 not null,
    constraint ID
        unique (ID),
    constraint invoice_ibfk_1
        foreign key (MS_ID) references membership (MS_ID)
)
    collate = utf8mb4_unicode_ci;

create index MS_ID
    on invoice (MS_ID);

create table invoice_item
(
    ID          int auto_increment
        primary key,
    INVOICE_ID  int            not null,
    MS_ID       int            not null,
    FISCAL_YEAR int            null,
    FIELD_NAME  varchar(50)    not null,
    IS_CREDIT   tinyint(1)     not null,
    VALUE       decimal(10, 2) null,
    QTY         int            null,
    constraint ID
        unique (ID),
    constraint invoice_item_ibfk_1
        foreign key (INVOICE_ID) references invoice (ID),
    constraint invoice_item_ibfk_2
        foreign key (MS_ID) references membership (MS_ID)
)
    collate = utf8mb4_unicode_ci;

create index INVOICE_ID
    on invoice_item (INVOICE_ID);

create index MS_ID
    on invoice_item (MS_ID);

create table payment
(
    PAY_ID       int auto_increment
        primary key,
    INVOICE_ID   int            not null,
    CHECK_NUMBER varchar(20)    null,
    PAYMENT_TYPE varchar(4)     not null,
    PAYMENT_DATE date           not null,
    AMOUNT       decimal(10, 2) not null,
    DEPOSIT_ID   int            not null,
    constraint payment_ibfk_1
        foreign key (DEPOSIT_ID) references deposit (DEPOSIT_ID),
    constraint payment_ibfk_2
        foreign key (INVOICE_ID) references invoice (ID)
)
    collate = utf8mb4_unicode_ci;

create index DEPOSIT_ID
    on payment (DEPOSIT_ID);

create index MONEY_ID
    on payment (INVOICE_ID);

# should attach to invoice_id, if put in early, just create invoice_id along with it
create table officer
(
    O_ID       int auto_increment
        primary key,
    P_ID       int         not null,
    BOARD_YEAR int         null,
    OFF_TYPE   varchar(20) null,
    OFF_YEAR   int         null,
    constraint P_ID
        unique (P_ID, OFF_YEAR, OFF_TYPE),
    constraint officer_ibfk_1
        foreign key (P_ID) references person (P_ID)
)
    collate = utf8mb4_unicode_ci;

create table stats
(
    STAT_ID            int auto_increment
        primary key,
    FISCAL_YEAR        int            not null,
    ACTIVE_MEMBERSHIPS int            null,
    NON_RENEW          int            null,
    RETURN_MEMBERS     int            null,
    NEW_MEMBERS        int            null,
    SECONDARY_MEMBERS  int            null,
    DEPENDANTS         int            null,
    NUMBER_OF_BOATS    int            null,
    FAMILY             int            null,
    REGULAR            int            null,
    SOCIAL             int            null,
    LAKE_ASSOCIATES    int            null,
    LIFE_MEMBERS       int            null,
    RACE_FELLOWS       int            null,
    STUDENT            int            null,
    DEPOSITS           decimal(13, 2) null,
    INITIATION         decimal(13, 2) null
)
    collate = utf8mb4_unicode_ci;

create table awards
(
    AWARD_ID   int auto_increment
        primary key,
    P_ID       int         not null,
    AWARD_YEAR varchar(10) not null,
    AWARD_TYPE varchar(10) not null,
    constraint awards_ibfk_1
        foreign key (P_ID) references person (P_ID)
)
    collate = utf8mb4_unicode_ci;

create index P_ID
    on awards (P_ID);

-- #one-to-one relation with membership
create table wait_list
(
    MS_ID            int        not null
        primary key,
    SLIP_WAIT        tinyint(1) null,
    KAYAK_RACK_WAIT  tinyint(1) null,
    SHED_WAIT        tinyint(1) null,
    WANT_SUBLEASE    tinyint(1) null,
    WANT_RELEASE     tinyint(1) null,
    WANT_SLIP_CHANGE tinyint(1) null,
    constraint MS_ID
        unique (MS_ID),
    constraint wait_list_ibfk_1
        foreign key (MS_ID) references membership (MS_ID)
)
    collate = utf8mb4_unicode_ci;

create table id_change
(
    CHANGE_ID int auto_increment
        primary key,
    ID_YEAR   int        not null,
    CHANGED   tinyint(1) null,
    constraint ID_YEAR
        unique (ID_YEAR)
)
    collate = utf8mb4_unicode_ci;

-- this is the api key for jotform
create table api_key
(
    API_ID int auto_increment
        primary key,
    NAME   varchar(50)                           not null,
    APIKEY varchar(50)                           not null,
    ts     timestamp default current_timestamp() null on update current_timestamp(),
    constraint APIKEY
        unique (APIKEY),
    constraint NAME
        unique (NAME)
)
    collate = utf8mb4_unicode_ci;

-- this stores a hash for each membership
create table form_msid_hash
(
    HASH_ID int auto_increment
        primary key,
    HASH    bigint not null,
    MS_ID   int    not null,
    constraint HASH
        unique (HASH),
    constraint form_msid_hash_ibfk_1
        foreign key (MS_ID) references membership (MS_ID)
)
    collate = utf8mb4_unicode_ci;

create index MS_ID
    on form_msid_hash (MS_ID);

-- This one row table holds email credentials to send email
create table form_email_auth
(
    HOST      varchar(100) null,
    PORT      int          null,
    USER      varchar(100) not null
        primary key,
    PASS      varchar(100) null,
    PROTOCOL  varchar(20)  null,
    SMTP_AUTH tinyint(1)   null,
    TTLS      tinyint(1)   null,
    DEBUG     tinyint(1)   null,
    id        int          not null,
    email     varchar(255) null,
    constraint USER
        unique (USER)
)
    collate = utf8mb4_unicode_ci;

-- Table to record everytime a request for a hash is made
create table form_hash_request
(
    FORM_HASH_ID int auto_increment
        primary key,
    REQ_DATE     timestamp default current_timestamp() not null,
    PRI_MEM      varchar(120)                          null,
    LINK         varchar(120)                          null,
    MSID         int                                   not null,
    MAILED_TO    varchar(120)                          null,
    constraint FORM_HASH_ID
        unique (FORM_HASH_ID)
)
    collate = utf8mb4_unicode_ci;
-- Table to record everytime a form request is made
create table form_request
(
    FORM_ID  int auto_increment
        primary key,
    REQ_DATE timestamp default current_timestamp() not null,
    PRI_MEM  varchar(50)                           null,
    MSID     int                                   not null,
    SUCCESS  tinyint(1)                            null,
    constraint FORM_ID
        unique (FORM_ID)
)
    collate = utf8mb4_unicode_ci;
-- This one row table holds settings for program
create table form_settings
(
    form_id       varchar(60)  not null
        primary key,
    PORT          int          null,
    LINK          varchar(200) null,
    form_url      varchar(255) not null,
    selected_year int          null
)
    collate = utf8mb4_unicode_ci;

-- user authentication database
create table users
(
    user_id  int auto_increment
        primary key,
    username varchar(50)  not null,
    password varchar(200) not null,
    p_id     int          not null,
    constraint username
        unique (username)
);

create table authorities
(
    id        int auto_increment
        primary key,
    username  varchar(50) not null,
    authority varchar(50) not null,
    constraint authorities_ibfk_1
        foreign key (username) references users (username)
);

create index username
    on authorities (username);

create table board_positions
(
    id                 int         not null
        primary key,
    position           varchar(50) not null,
    identifier         varchar(5)  not null,
    list_order         int         not null,
    is_officer         tinyint(1)  not null,
    is_chair           tinyint(1)  not null,
    is_assistant_chair tinyint(1)  not null,
    constraint identifier
        unique (identifier),
    constraint position
        unique (position)
)
    collate = utf8mb4_unicode_ci;

create table db_updates
(
    ID                int        not null
        primary key,
    SQL_CREATION_DATE datetime   null,
    IS_CLOSED         tinyint(1) not null,
    DB_SIZE           double     null
)
    collate = utf8mb4_unicode_ci;

create table db_table_changes
(
    id            int                                   not null
        primary key,
    db_updates_id int                                   not null,
    table_changed varchar(50)                           null,
    table_insert  int                                   not null,
    table_delete  int                                   not null,
    table_update  int                                   not null,
    change_date   timestamp default current_timestamp() not null,
    changed_by    varchar(100)                          null,
    constraint db_table_changes_ibfk_1
        foreign key (db_updates_id) references db_updates (ID)
)
    collate = utf8mb4_unicode_ci;



create table db_boat_list_radio_selection
(
    ID          int auto_increment
        primary key,
    LABEL       varchar(40)          not null,
    METHOD_NAME varchar(2000)        null,
    LIST_ORDER  int                  null,
    LIST        int        default 1 null,
    SELECTED    tinyint(1) default 0 null
)
    collate = utf8mb4_unicode_ci;

# added 12/15/2023
create table app_settings
(
    setting_key   varchar(255)                          not null
        primary key,
    setting_value varchar(255)                          not null,
    description   text                                  null,
    data_type     varchar(50)                           null,
    updated_at    timestamp default current_timestamp() not null on update current_timestamp(),
    group_name    varchar(25)                           null
);

# setting_key: A unique identifier for each setting.
# setting_value: The value of the setting.
# description: A description of what the setting is used for.
# data_type: Optional, to specify what type of data the setting holds (e.g., 'string', 'integer', 'boolean'). This can be useful for parsing the value correctly in your application.
# updated_at: A timestamp to track when the setting was last updated.


