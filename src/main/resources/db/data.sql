truncate table users cascade;
truncate table bio_data_roles cascade;
truncate table renters cascade;
truncate table landlords cascade;
TRUNCATE TABLE blacklisted_tokens CASCADE;
TRUNCATE TABLE addresses CASCADE;
TRUNCATE TABLE properties CASCADE;
TRUNCATE TABLE account_details CASCADE;
TRUNCATE TABLE apartments CASCADE;
TRUNCATE TABLE apartment_media_urls CASCADE;
TRUNCATE TABLE ratings CASCADE;


insert into users(id, first_name, last_name, email, password, date_registered)values
(100, 'Doe', 'John', 'johndoe@gmail.com', 'password', '2024-07-23T15:03:03.792009700'),
(101, 'Bull', 'Joe', 'bulljoe@gmail.com', 'password', '2024-07-23T15:03:03.792009700'),
(103, 'Sam', 'Cole', 'colesam@gmail.com', '$2a$10$seAKbpBsTn/xgAg7nbRKWuH1dnRvMlLloxMOjH00zMmTu3vLCtlee', '2024-07-23T15:03:03.792009700'),
(104, 'Palmer', 'James', 'jamespalmer@gmail.com', '$2a$10$seAKbpBsTn/xgAg7nbRKWuH1dnRvMlLloxMOjH00zMmTu3vLCtlee', '2024-07-23T15:03:03.792009700'),
(105, 'Theodore', 'Bagwell', 'bagwell@gmail.com', 'password', '2024-07-23T15:03:03.792009700'),
(106, 'john', 'Blakes', 'blakes@gmail.com', 'password', '2024-07-23T15:03:03.792009700');

insert into bio_data_roles(bio_data_id, roles)values
(100, 'RENTER'),
(101, 'RENTER'),
(103, 'RENTER'),
(104, 'LANDLORD'),
(105, 'LANDLORD'),
(106, 'LANDLORD');

insert into renters(bio_data_id, id, occupation)values
(100, 200, 'Software Engineering'),
(101, 201, 'Accountant'),
(103, 202, 'Civil Servant');

insert into landlords(bio_data_id, id) values
(104, 300),
(105, 301),
(106, 302);

INSERT INTO blacklisted_tokens (id, token, expires_at, blacklisted_at) VALUES
(600, 'eyJhbGciOiJSUzUxMiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJFYXN5UmVudEFwcCIsImlhdCI6MTcyMTkxMDU2NSwiZXhwIjoxNzIxOTk2OTY1LCJzdWIiOiJjb2xlc2FtQGdtYWlsLmNvbSIsInByaW5jaXBhbCI6ImNvbGVzYW1AZ21haWwuY29tIiwiY3JlZGVudGlhbHMiOiJbUFJPVEVDVEVEXSIsInJvbGVzIjpbIlJFTlRFUiJdfQ.E4ItFh9rCkBYn1DTrb1XnlP4_nIkC_rnHsGOtaQcj4AvVaoYSvd4ydiyn7HUG1dAbNqLol8cicGn7zPhsZyC2fgDsNWB25xMzXFXo7uVGnzeJZ8o1boknxyBwO_tfwll7m7lEN3QhRT0Yly7BHKDm9QNj8h-J1hSLWnok5tifLfJe5nEfvGls0ZUmFwLRAhTEvjREPPTamzglltNlI9FlK1XiorFav2193ZuDwhtd3y0vT6DgB_tMP1Ki5SwvfsAXi9_F456AH8k_aF1S-3pSX-MN50wdE-FUiipveHmkH3sdMXQgh9yXKEVFKcLWv2qCAMU66Fu330bI6VIZqV3pQ',
 '2024-07-24T14:02:27.425305100Z', '2024-07-13T14:02:27.434315200');

INSERT INTO addresses(id, area, lga, state, number, street)VALUES
(400, 'area', 'shomolu', 'LAGOS', 'number', 'street');

INSERT INTO properties(id, no_of_apartments, address_id, landlord_id, type, media_url)VALUES
(500, 50, 400, 300, 'HOSTEL', 'http://res.cloudinary.com/dvliop7es/image/upload/v1721901227/file.jpg');

INSERT INTO account_details(id, account_name, account_number, bank_name, landlord_id) VALUES
(700, 'account_name', '0123456789', 'bankName', 300);

INSERT INTO apartments(id, number, price, rent_type, type, is_available, property_id) VALUES
(800, 25, 1000.00, 'YEARLY', 'DOUBLE_SHARED_ROOM', true, 500);

INSERT INTO apartment_media_urls(apartment_id, media_urls) VALUES
(800, 'http://res.cloudinary.com/dvliop7es/image/upload/v1722372047/file.jpg'),
(800, 'http://res.cloudinary.com/dvliop7es/image/upload/v1722372046/file.jpg');

INSERT INTO ratings(id, comment, rating,review_date, property_id, reviewer_id)VALUES
(40, 'nice house', 5, '2024-07-13T14:02:27.434315200', 500, 104),
(50, 'bad house', 0, '2024-07-13T14:02:27.434315200', 500, 104)