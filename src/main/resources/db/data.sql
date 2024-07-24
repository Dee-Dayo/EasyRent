truncate table users cascade;
truncate table renters cascade;
truncate table landlords cascade;

insert into users(id, first_name, last_name, email, password, date_registered)values
(100, 'Doe', 'John', 'johndoe@gmail.com', 'password', '2024-07-23T15:03:03.792009700'),
(101, 'Bull', 'Joe', 'bulljoe@gmail.com', 'password', '2024-07-23T15:03:03.792009700'),
(103, 'Sam', 'Cole', 'colesam@gmail.com', 'password', '2024-07-23T15:03:03.792009700'),
(104, 'Palmer', 'James', 'jamespalmer@gmail.com', 'password', '2024-07-23T15:03:03.792009700'),
(105, 'Theodore', 'Bagwell', 'bagwell@gmail.com', 'password', '2024-07-23T15:03:03.792009700'),
(106, 'john', 'Blakes', 'blakes@gmail.com', 'password', '2024-07-23T15:03:03.792009700');

insert into renters(bio_data_id, id, occupation)values
(100, 200, 'Software Engineering'),
(101, 201, 'Accountant'),
(103, 202, 'Civil Servant');