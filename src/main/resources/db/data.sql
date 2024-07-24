truncate table users cascade;
truncate table renters cascade;
truncate table landlords cascade;

insert into users(id, first_name, last_name, email, password, date_registered, role)values
(100, 'Doe', 'John', 'johndoe@gmail.com', 'password', '2024-07-23T15:03:03.792009700', 'RENTER'),
(101, 'Bull', 'Joe', 'bulljoe@gmail.com', 'password', '2024-07-23T15:03:03.792009700', 'RENTER'),
(103, 'Sam', 'Cole', 'colesam@gmail.com', 'password', '2024-07-23T15:03:03.792009700', 'RENTER'),
(104, 'Palmer', 'James', 'jamespalmer@gmail.com', 'password', '2024-07-23T15:03:03.792009700', 'LANDLORD'),
(105, 'Theodore', 'Bagwell', 'bagwell@gmail.com', 'password', '2024-07-23T15:03:03.792009700', 'LANDLORD'),
(106, 'john', 'Blakes', 'blakes@gmail.com', 'password', '2024-07-23T15:03:03.792009700', 'LANDLORD');

insert into renters(bio_data_id, id, occupation)values
(100, 200, 'Software Engineering'),
(101, 201, 'Accountant'),
(103, 202, 'Civil Servant');

insert into landlords(bio_data_id, id) values
(104, 300),
(105, 301),
(106, 302);