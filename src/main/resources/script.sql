drop
database if exists db_recruiting_system;

create
database db_recruiting_system;

use
db_recruiting_system;

drop table if exists role;

create table role
(
    id   bigint auto_increment primary key,
    name varchar(100) not null
);

drop table if exists user;

create table user
(
    id       bigint auto_increment primary key,
    login    varchar(100) not null,
    password varchar(100) not null,
    email    varchar(100) not null
);

drop table if exists resume;

create table resume
(
    id                 bigint auto_increment primary key,
    fio                varchar(100) not null,
    sex                varchar(50)  not null,
    date_of_birth      date         not null,
    profession         varchar(100) not null,
    post               varchar(50)  not null,
    work_experience    int          not null,
    last_work          varchar(100) not null,
    type_of_employment varchar(100) not null,
    salary             int          not null,
    time_of_employment int          not null
);

drop table if exists user_role_links;

create table user_role_links
(
    user_id bigint not null,
    role_id bigint not null,
    foreign key (user_id) references user (id) on update cascade on delete cascade,
    foreign key (role_id) references role (id) on update cascade on delete cascade
);

drop table if exists user_resume_links;

create table user_resume_links
(
    user_id   bigint not null,
    resume_id bigint not null,
    foreign key (user_id) references user (id) on update cascade on delete cascade,
    foreign key (resume_id) references resume (id) on update cascade on delete cascade
);

drop table if exists recruiter_resume_links;

create table recruiter_resume_links
(
    user_id   bigint not null,
    resume_id bigint not null,
    foreign key (user_id) references user (id) on update cascade on delete cascade,
    foreign key (resume_id) references resume (id) on update cascade on delete cascade
);

drop table if exists recruiter_resume_to_send_links;

create table recruiter_resume_to_send_links
(
    user_id   bigint not null,
    resume_id bigint not null,
    foreign key (user_id) references user (id) on update cascade on delete cascade,
    foreign key (resume_id) references resume (id) on update cascade on delete cascade
);

drop table if exists report;

create table report
(
    id        bigint auto_increment primary key,
    user_id   bigint       not null,
    resume_id bigint       not null,
    message   varchar(200) not null,
    foreign key (user_id) references user (id) on update cascade on delete cascade,
    foreign key (resume_id) references resume (id) on update cascade on delete cascade
);

drop table if exists key_skill;

create table key_skill
(
    id   bigint auto_increment primary key,
    name varchar(200) not null
);

drop table if exists vacancy;

create table vacancy
(
    id                 bigint auto_increment primary key,
    profession         varchar(100) not null,
    post               varchar(50)  not null,
    work_experience    int          not null,
    salary             int          not null,
    time_of_employment int          not null
);

drop table if exists user_vacancy_links;

create table user_vacancy_links
(
    user_id    bigint not null,
    vacancy_id bigint not null,
    foreign key (user_id) references user (id) on update cascade on delete cascade,
    foreign key (vacancy_id) references vacancy (id) on update cascade on delete cascade
);

drop table if exists resume_skill_links;

create table resume_skill_links
(
    key_skill_id bigint not null,
    resume_id    bigint not null,
    foreign key (key_skill_id) references key_skill (id) on update cascade on delete cascade,
    foreign key (resume_id) references resume (id) on update cascade on delete cascade
);

drop table if exists vacancy_skill_links;

create table vacancy_skill_links
(
    key_skill_id bigint not null,
    vacancy_id   bigint not null,
    foreign key (key_skill_id) references key_skill (id) on update cascade on delete cascade,
    foreign key (vacancy_id) references vacancy (id) on update cascade on delete cascade
);

drop table if exists user_respond_links;

create table user_respond_links
(
    user_id    bigint not null,
    vacancy_id bigint not null,
    foreign key (user_id) references user (id) on update cascade on delete cascade,
    foreign key (vacancy_id) references vacancy (id) on update cascade on delete cascade
);


-- only for dev: password is 123456
insert into user(login, password, email)
values ('login1', '$2a$10$ya5CtEj.6MOcs/iXfc.8buQnfOcJVrKr/ReAaNsyZrywJ2h1Gb6KS', 'email1@mail.ru'),
       ('login2', '$2a$10$Q8debAEzmMbfqDabNa3T..MrI/6Smlkc4fI7mTX2Hk3eMtgC4dcqW', 'email2@mail.ru'),
       ('login3', '$2a$10$MPx5uLyhoH/flIZvfJ0GqO2L7fva7/j7GMu/uWk7UIJTSlQC.ctnS', 'email3@mail.ru');

insert into role(name)
values ('USER'),
       ('RECRUITER');

insert into user_role_links(user_id, role_id)
values (1, 1),
       (2, 2),
       (3, 1);