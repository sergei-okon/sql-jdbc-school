DROP TABLE IF EXISTS COURSES;
--
DROP TABLE IF EXISTS  GROUPS, STUDENTS, COURSES;

create table COURSES
(
    COURSE_ID   INT auto_increment,
    NAME        CLOB,
    DESCRIPTION CLOB,
    NEW_COLUMN  INT,
    constraint COURSES_PK
        primary key (COURSE_ID)
);

--
create table groups
(
    group_id        int auto_increment,
    name            CLOB not null,
    amount_students int,
    constraint GROUPS_PK
        primary key (group_id)
);
--
create table STUDENTS
(
    student_id int auto_increment,
    first_name CLOB not null,
    last_name  CLOB not null,
    group_id   int,
    constraint STUDENTS_PK
        primary key (student_id),
    constraint STUDENTS_GROUPS_GROUP_ID_FK
        foreign key (group_id) references GROUPS (GROUP_ID)
);

--
create table students_courses
(
    id         int auto_increment,
    student_id int not null,
    courses_id  int not null,
    constraint STUDENTS_COURSES_PK
        primary key (id),
    constraint STUDENTS_COURSES_COURSES_COURSES_ID_FK
        foreign key (courses_id) references COURSES,
    constraint STUDENTS_COURSES_STUDENTS_STUDENTS_ID_FK
        foreign key (student_id) references STUDENTS
);
