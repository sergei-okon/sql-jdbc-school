INSERT INTO public.courses(
	course_id, course_name, course_description)
	VALUES
	(1, 'Math', 'Mathemetic'),
	(2, 'Acc', 'Accounting'),
	(3, 'BM', 'Business management' ),
	(4, 'ES', 'Entrepreneurial skills' ),
	(5, 'Mrg', 'Marketing' ),
	(6, 'PF', 'Personal finance' ),
	(7, 'CP', 'Computer programming' ),
	(8, 'GD', 'Graphic design' ),
	(9, 'MT', 'Media technology' ),
	(10, 'WD', 'Web design' );


INSERT INTO public.groups(
	group_id, group_name)
	VALUES
 	(1,'SR-01'),
 	(2,'DD-02'),
 	(3,'SA-03'),
	(4,'RF-04'),
 	(5,'FG-04'),
 	(6,'QW-04'),
 	(7,'LK-04'),
 	(8,'AA-04'),
	(9,'WW-04'),
	(10, 'ZZ-04');


 INSERT INTO public.students(
	stude_id, "  group", "  first_name", "  last_name")
	VALUES
	(1, 1,'Callum', 'Mcfarland'),
	(2,1, 'Ashely', 'Blevins'),
	(3,1, 'Randall', 'Vang'),
	(4,1, 'Barry', 'Wilson'),
	(5,2, 'Alea', 'Knight'),
	(6,2, 'Keiko', 'Olsen'),
	(7,2, 'Aristotle', 'Atkinson'),
	(8,2, 'Alexandra', 'Stephenson'),
	(9,3, 'Lucius', 'Dotson'),
	(10,3, 'Harper', 'Kirby'),
	(11,3, 'Griffith', 'Buchanan'),
	(12,3, 'Todd', 'Silva'),
	(13,4, 'Cairo', 'Cruz'),
	(14,4, 'Rina', 'Stuart'),
	(15,4, 'Eagan', 'Pollard'),
	(16,5, 'Emmanuel', 'Mccray'),
	(17,5, 'Orlando', 'Holloway'),
	(18,5, 'Brody', 'Mcgee'),
	(19,5, 'Ezekiel', 'Pierce'),
	(20,6, 'Phillip', 'Mccormick'),
	(21,6, 'Allegra', 'Savage'),
	(22,6, 'Hyacinth', 'Rodgers'),
	(23,6, 'Suki', 'Ewing'),
	(24,7, 'Yeo', 'Forbes'),
	(25,7, 'Shaeleigh', 'Chaney'),
	(26,7, 'Colby', 'Hutchinson'),
	(27,8, 'Demetrius', 'Blackwell'),
	(28,8, 'Imani', 'Lee'),
	(29,8, 'MacKensie', 'Mccall'),
	(30,8, 'Camden', 'Guzman'),
	(31,8, 'Christian', 'Jones'),
	(32,9, 'Drake', 'Curry'),
	(33,9, 'Velma', 'Sherman'),
	(34,9, 'Jakeem', 'Mann'),
	(35,9, 'Reese', 'Rosa'),
	(36,9, 'Giacomo', 'Dixon'),
	(37,9, 'Fulton', 'Bryant'),
	(38,10, 'Alexander', 'Bowen'),
	(39,10, 'Jolene', 'Anderson');