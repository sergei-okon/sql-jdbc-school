DROP TABLE IF EXISTS public.students_courses;
--
DROP TABLE IF EXISTS  groups, students,courses;
--
CREATE TABLE IF NOT EXISTS public.groups
(
    group_id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    name character varying(100) COLLATE pg_catalog."default" NOT NULL,
    amount_students integer,
    CONSTRAINT groups_pkey PRIMARY KEY (group_id)
)

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.groups
    OWNER to postgres;

--
CREATE TABLE IF NOT EXISTS public.courses
(
    course_id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    name character varying(20) COLLATE pg_catalog."default" NOT NULL,
    description character varying(100) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT courses_pkey PRIMARY KEY (course_id)
)

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.courses
    OWNER to postgres;
--
CREATE TABLE IF NOT EXISTS public.students
(
    student_id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    group_id integer,
    first_name character varying(30) COLLATE pg_catalog."default" NOT NULL,
    last_name character varying(30) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT students_pkey PRIMARY KEY (student_id),
    CONSTRAINT group_id FOREIGN KEY (group_id)
        REFERENCES public.groups (group_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.students
    OWNER to postgres;
--
CREATE TABLE IF NOT EXISTS public.students_courses
(
    id integer NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    student_id integer NOT NULL,
    courses_id integer NOT NULL,
    CONSTRAINT students_courses_pkey PRIMARY KEY (id),
    CONSTRAINT courses_id FOREIGN KEY (courses_id)
        REFERENCES public.courses (course_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT student_id FOREIGN KEY (student_id)
        REFERENCES public.students (student_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
)

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.students_courses
    OWNER to postgres;

