

DROP TABLE IF EXISTS  groups, students,courses;



CREATE TABLE IF NOT EXISTS public.groups
(
    group_id integer NOT NULL,
    group_name character varying(100) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT groups_pkey PRIMARY KEY (group_id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.groups
    OWNER to postgres;
	
	
CREATE TABLE IF NOT EXISTS public.courses
(
    course_id integer NOT NULL,
    course_name character varying(20) COLLATE pg_catalog."default" NOT NULL,
    course_description character varying(100) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT courses_pkey PRIMARY KEY (course_id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.courses
    OWNER to postgres;
	

-- Table: public.students

CREATE TABLE IF NOT EXISTS public.students
(
    stude_id integer NOT NULL,
    "  group" integer NOT NULL,
    "  first_name" character varying(30) COLLATE pg_catalog."default" NOT NULL,
    "  last_name" character varying(30) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT students_pkey PRIMARY KEY (stude_id),
    CONSTRAINT group_id FOREIGN KEY ("  group")
        REFERENCES public.groups (group_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.students
    OWNER to postgres;
	
	
	
