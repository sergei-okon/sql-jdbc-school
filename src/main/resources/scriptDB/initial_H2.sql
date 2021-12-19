DROP TABLE IF EXISTS COURSES;
--
DROP TABLE IF EXISTS  GROUPS, STUDENTS, COURSES;

create table COURSES
(
    COURSES_ID INT auto_increment,
    NAME CHARACTER,
    DESCRIPTION CHARACTER,
    NEW_COLUMN INT,
    constraint COURSES_PK
        primary key (COURSES_ID)
);

--
create table groups
(
    group_id int auto_increment,
    name character not null,
    amount_students character
);
--
create table students
(
    students_id int auto_increment,
    first_name character not null,
    last_name character not null,
    group_id int,
    constraint STUDENTS_PK
        primary key (students_id),
    constraint STUDENTS_GROUPS_GROUP_ID_FK
        foreign key (group_id) references GROUPS (GROUP_ID)
);

--
create table students_courses
(
    id int auto_increment,
    student_id int not null,
    courses_id int not null,
    constraint STUDENTS_COURSES_PK
        primary key (id),
    constraint STUDENTS_COURSES_COURSES_COURSES_ID_FK
        foreign key (courses_id) references COURSES,
    constraint STUDENTS_COURSES_STUDENTS_STUDENTS_ID_FK
        foreign key (student_id) references STUDENTS
);

INSERT INTO public.courses(name, description)
VALUES
    ('Math', 'Mathemetic'),
    ('Acc', 'Accounting'),
    ('BM', 'Business management' ),
    ('ES', 'Entrepreneurial skills' ),
    ('Mrg', 'Marketing' ),
    ('PF', 'Personal finance' ),
    ('CP', 'Computer programming' ),
    ('GD', 'Graphic design' ),
    ('MT', 'Media technology' ),
    ('WD', 'Web design' );


INSERT INTO public.groups(name, AMOUNT_STUDENTS)
VALUES
    ('SR-01',10),
    ('DD-02',15),
    ('SA-03',25),
    ('RF-04',25),
    ('FG-05',28),
    ('QW-06',11),
    ('LK-14',18),
    ('AA-25',14),
    ('WW-41',17),
    ('ZZ-77',17);

INSERT INTO public.students("GROUP_ID", "FIRST_NAME", "LAST_NAME")
VALUES
    (1,'Callum', 'Mcfarland'),
    (1, 'Ashely', 'Blevins'),
    (1, 'Randall', 'Vang'),
    (1, 'Barry', 'Wilson'),
    (2, 'Alea', 'Knight'),
    (2, 'Keiko', 'Olsen'),
    (2, 'Aristotle', 'Atkinson'),
    (2, 'Alexandra', 'Stephenson'),
    (3, 'Lucius', 'Dotson'),
    (3, 'Harper', 'Kirby'),
    (3, 'Griffith', 'Buchanan'),
    (3, 'Todd', 'Silva'),
    (4, 'Cairo', 'Cruz'),
    (4, 'Rina', 'Stuart'),
    (4, 'Eagan', 'Pollard'),
    (5, 'Emmanuel', 'Mccray'),
    (5, 'Orlando', 'Holloway'),
    (5, 'Brody', 'Mcgee'),
    (5, 'Ezekiel', 'Pierce'),
    (6, 'Phillip', 'Mccormick'),
    (6, 'Allegra', 'Savage'),
    (6, 'Hyacinth', 'Rodgers'),
    (6, 'Suki', 'Ewing'),
    (7, 'Yeo', 'Forbes'),
    (7, 'Shaeleigh', 'Chaney'),
    (7, 'Colby', 'Hutchinson'),
    (8, 'Demetrius', 'Blackwell'),
    (8, 'Imani', 'Lee'),
    (8, 'MacKensie', 'Mccall'),
    (8, 'Camden', 'Guzman'),
    (8, 'Christian', 'Jones'),
    (9, 'Drake', 'Curry'),
    (9, 'Velma', 'Sherman'),
    (9, 'Jakeem', 'Mann'),
    (9, 'Reese', 'Rosa'),
    (9, 'Giacomo', 'Dixon'),
    (9, 'Fulton', 'Bryant'),
    (10, 'Alexander', 'Bowen'),
    (10, 'Jolene', 'Anderson');

insert into STUDENTS_COURSES (STUDENT_ID, COURSES_ID)
values (1, 2),
       (2, 4),
       (2, 1),
       (3, 5),
       (4, 9),
       (5, 10),
       (6, 5),
       (7, 7),
       (7, 8),
       (7, 1),
       (8, 6),
       (8, 7),
       (9, 7),
       (10, 1);
