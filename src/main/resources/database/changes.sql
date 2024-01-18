ALTER TABLE db_invoice MODIFY COLUMN id int NOT NULL AUTO_INCREMENT;

# need to move this to production
create table jotform_settings
(
    jot_id          bigint auto_increment primary key,
    form_number     bigint                                not null,
    answer_number   int                                   not null,
    answer_location varchar(100)                          null,
    answer_text     varchar(255)                          not null,
    answer_type     varchar(100)                          null,
    answer_name     varchar(25)                           null,
    answer_order    int                                   null,
    group_order     int                                   null,
    sublabels       text                                  null,
    data_type       varchar(50)                           null,
    updated_at      timestamp default current_timestamp() not null on update current_timestamp()
);