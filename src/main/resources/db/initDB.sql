drop all objects;

create table USERS
(
    ID identity primary key,
    LOGIN VARCHAR(255)
        constraint USERS_LOGIN_UINDEX
        unique,
    PASSWORD VARCHAR(255),
    FULL_NAME VARCHAR(255),
    ROLE VARCHAR(255) NOT NULL DEFAULT 'USER'

);

create table ACCOUNTS
(
    ID IDENTITY primary key,
    USER_ID BIGINT(19),
    ACCOUNT BIGINT(19) not null auto_increment (123400000000,1),
    BALANCE DECIMAL,
    constraint ACCOUNTS_USERS_ID_FK
        foreign key (USER_ID) references USERS
);

create table CARDS
(
    ID identity primary key,
    NUMBER BIGINT(19) not null auto_increment(1000000000000000,1),
    ACCOUNT_ID BIGINT(19) not null,
    IS_ACTIVE BOOLEAN,
    constraint CARDS_ACCOUNTS_ID_FK
        foreign key (ACCOUNT_ID) references ACCOUNTS
);

--test user bean
insert into USERS (LOGIN, PASSWORD, FULL_NAME) values ('bean', 'YmVhbjE0ODg=','Mister Bean');

--test user hill
insert into USERS (LOGIN, PASSWORD, FULL_NAME) values ('hill', 'aGlsbGhpbGxoaWxs','Benny Hill');

--test account 1 for bean
insert into ACCOUNTS (BALANCE, user_id) values ('148.84', 1);

--test account 2 for bean
insert into ACCOUNTS (BALANCE, user_id) values ('100.00', 1);

--test account 1 for hill
insert into ACCOUNTS (BALANCE, user_id) values ('100.00', 2);

insert into CARDS (ACCOUNT_ID, IS_ACTIVE) values (1, true);



