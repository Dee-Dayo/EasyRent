truncate table users cascade;
truncate table bio_data_roles cascade;
truncate table renters cascade;
truncate table landlords cascade;
TRUNCATE TABLE blacklisted_tokens CASCADE;
TRUNCATE TABLE addresses CASCADE;
TRUNCATE TABLE properties CASCADE;
TRUNCATE TABLE properties_reviews CASCADE;
TRUNCATE TABLE account_details CASCADE;
TRUNCATE TABLE apartments CASCADE;
TRUNCATE TABLE apartment_media_urls CASCADE;
TRUNCATE TABLE reviews CASCADE;
TRUNCATE TABLE users_reviews CASCADE;
TRUNCATE TABLE agent_details CASCADE;
TRUNCATE TABLE rents CASCADE;


insert into users(id, first_name, last_name, email, password, date_registered)values
(100, 'Doe', 'John', 'darhyor2050@gmail.com', 'password', '2024-07-23T15:03:03.792009700'),
(101, 'Bull', 'Joe', 'bulljoe@gmail.com', 'password', '2024-07-23T15:03:03.792009700'),
(103, 'Sam', 'Cole', 'colesam@gmail.com', '$2a$10$seAKbpBsTn/xgAg7nbRKWuH1dnRvMlLloxMOjH00zMmTu3vLCtlee', '2024-07-23T15:03:03.792009700'),
(109, 'Sam', 'Cole', 'renter@email.com', '$2a$10$seAKbpBsTn/xgAg7nbRKWuH1dnRvMlLloxMOjH00zMmTu3vLCtlee', '2024-07-23T15:03:03.792009700'),
(104, 'Palmer', 'James', 'jamespalmer@gmail.com', '$2a$10$seAKbpBsTn/xgAg7nbRKWuH1dnRvMlLloxMOjH00zMmTu3vLCtlee', '2024-07-23T15:03:03.792009700'),
(105, 'Theodore', 'Bagwell', 'bagwell@gmail.com', 'password', '2024-07-23T15:03:03.792009700'),
(106, 'john', 'Blakes', 'blakes@gmail.com', 'password', '2024-07-23T15:03:03.792009700'),
(107, 'Ashley', 'Cole', 'renter@gmail.com', '$2a$10$seAKbpBsTn/xgAg7nbRKWuH1dnRvMlLloxMOjH00zMmTu3vLCtlee', '2024-07-23T15:03:03.792009700');

insert into bio_data_roles(bio_data_id, roles)values
(100, 'RENTER'),
(101, 'RENTER'),
(103, 'RENTER'),
(109, 'RENTER'),
(104, 'LANDLORD'),
(105, 'LANDLORD'),
(106, 'LANDLORD'),
(107,'RENTER');


insert into landlords(bio_data_id, id) values
(100, 300),
(105, 301),
(104, 302);

INSERT INTO blacklisted_tokens (id, token, expires_at, blacklisted_at) VALUES
(600, 'eyJhbGciOiJSUzUxMiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJFYXN5UmVudEFwcCIsImlhdCI6MTcyMTkxMDU2NSwiZXhwIjoxNzIxOTk2OTY1LCJzdWIiOiJjb2xlc2FtQGdtYWlsLmNvbSIsInByaW5jaXBhbCI6ImNvbGVzYW1AZ21haWwuY29tIiwiY3JlZGVudGlhbHMiOiJbUFJPVEVDVEVEXSIsInJvbGVzIjpbIlJFTlRFUiJdfQ.E4ItFh9rCkBYn1DTrb1XnlP4_nIkC_rnHsGOtaQcj4AvVaoYSvd4ydiyn7HUG1dAbNqLol8cicGn7zPhsZyC2fgDsNWB25xMzXFXo7uVGnzeJZ8o1boknxyBwO_tfwll7m7lEN3QhRT0Yly7BHKDm9QNj8h-J1hSLWnok5tifLfJe5nEfvGls0ZUmFwLRAhTEvjREPPTamzglltNlI9FlK1XiorFav2193ZuDwhtd3y0vT6DgB_tMP1Ki5SwvfsAXi9_F456AH8k_aF1S-3pSX-MN50wdE-FUiipveHmkH3sdMXQgh9yXKEVFKcLWv2qCAMU66Fu330bI6VIZqV3pQ',
 '2024-07-24T14:02:27.425305100Z', '2024-07-13T14:02:27.434315200');

INSERT INTO addresses(id, area, lga, state, number, street)VALUES
(400, 'area', 'Shomolu', 'LAGOS', 'number', 'street'),
(401, 'area', 'Yaba', 'LAGOS', 'number', 'street'),
(402, 'area', 'Palmgrove', 'LAGOS', 'number', 'street'),
(403, 'area', 'Sabo', 'LAGOS', 'number', 'street'),
(404, 'area', 'Mushin', 'LAGOS', 'number', 'street'),
(405, 'area', 'Ikotun', 'LAGOS', 'number', 'street'),
(407, 'area', 'lga', 'LAGOS', 'number', 'street'),
(408, 'area', 'lga', 'LAGOS', 'number', 'street');


INSERT INTO agent_details(id, name, phone_number)VALUES
(9, 'Timi', '09022234556'),
(10, 'Dayo', '09022334556'),
(87, 'Ayo', '09022334556'),
(12, 'Ayo', '09022334556'),
(76, 'Femi', '09022334556');

INSERT INTO properties(id, no_of_apartments, address_id, landlord_id, type, media_url, agent_details_id)VALUES
(2, 9, 407, 300, 'HOSTEL', 'http://res.cloudinary.com/dljjrwkky/image/upload/v1723148119/file_uwt8z1.jpg',12),
(500, 9, 400, 300, 'HOSTEL', 'http://res.cloudinary.com/dljjrwkky/image/upload/v1723148119/file_uwt8z1.jpg',12),
(501, 3, 401, 300, 'DUPLEX', 'http://res.cloudinary.com/dljjrwkky/image/upload/v1723150403/file_v5traw.jpg', 12),
(502, 4, 402, 300, 'BUNGALOW', 'http://res.cloudinary.com/dljjrwkky/image/upload/v1723150486/file_ibvzwj.jpg', 76),
(503, 2, 403, 300, 'CONDO', 'http://res.cloudinary.com/dljjrwkky/image/upload/v1723150799/file_jktfbh.jpg', 87),
(504, 7, 404, 300, 'BUNGALOW', 'http://res.cloudinary.com/dljjrwkky/image/upload/v1723150840/file_voez6b.jpg', 12),
(505, 10, 405, 300, 'HOSTEL', 'http://res.cloudinary.com/dljjrwkky/image/upload/v1723148119/file_uwt8z1.jpg', 76),
(506, 9, 408, 300, 'HOSTEL', 'http://res.cloudinary.com/dljjrwkky/image/upload/v1723148119/file_uwt8z1.jpg',12);


INSERT INTO account_details(id, account_name, account_number, bank_name, landlord_id) VALUES
(700, 'account_name', '0123456789', 'bankName', 300);

INSERT INTO apartments(id, number, price, rent_type, type, is_available, property_id) VALUES
(800, 25, 1000.00, 'MONTHLY', 'ONE_ROOM', true, 500),
(801, 26, 5000.00, 'MONTHLY', 'ONE_ROOM', true, 500),
(802, 27, 34000.00, 'MONTHLY', 'ONE_ROOM', true, 502),
(803, 28, 16900.00, 'MONTHLY', 'THREE_BEDROOM_FLAT', true, 500),
(804, 29, 10500.00, 'HALF_YEARLY', 'ROOM_AND_PARLOUR', true, 500),
(805, 29, 10500.00, 'HALF_YEARLY', 'STUDIO', true, 500),
(806, 29, 10500.00, 'HALF_YEARLY', 'QUAD_SHARED_ROOM', true, 500),
(807, 29, 10500.00, 'HALF_YEARLY', 'BOYS_QUARTERS', true, 500),
(808, 29, 10500.00, 'HALF_YEARLY', 'BOYS_QUARTERS', true, 500);

insert into rents(id, price, apartment_id,payment_option, rent_type)values
(100,5000, 801, 'PAYSTACK', 'MONTHLY'),
(101,5000, 800, 'PAYSTACK', 'MONTHLY'),
(102,5000, 802, 'PAYSTACK', 'MONTHLY'),
(103, 5000, 803,'PAYSTACK', 'MONTHLY');

insert into renters(bio_data_id, id, occupation, rent_id)values
(100, 200, 'Software Engineering', 100),
(101, 201, 'Accountant', 101),
(103, 202, 'Civil Servant', 102),
(107, 203, 'Civil Servant', 103);




INSERT INTO apartment_media_urls(apartment_id, media_urls) VALUES
(800, 'https://res.cloudinary.com/dvliop7es/image/upload/v1722372047/file.jpg'),
(800, 'https://res.cloudinary.com/dvliop7es/image/upload/v1722372046/file.jpg');

INSERT INTO reviews(id, comment, rating,review_date, property_id, reviewer_id)VALUES
(40, 'nice house', 5, '2024-07-13T14:02:27.434315200', 500, 104),
(41, 'bad house', 1, '2024-07-13T14:02:27.434315200', 500, 104),
(42, 'bad house', 4, '2024-07-13T14:02:27.434315200', 500, 104),
(43, 'bad house', 1, '2024-07-13T14:02:27.434315200', 500, 104),
(45, 'bad house', 3, '2024-07-13T14:02:27.434315200', 501, 104),
(46, 'bad house', 3, '2024-07-13T14:02:27.434315200', 501, 104),
(47, 'bad house', 1, '2024-07-13T14:02:27.434315200', 502, 104),
(48, 'bad house', 1, '2024-07-13T14:02:27.434315200', 502, 104),
(49, 'bad house', 2, '2024-07-13T14:02:27.434315200', 502, 104),
(50, 'bad house', 0, '2024-07-13T14:02:27.434315200', 502, 104),
(51, 'bad house', 5, '2024-07-13T14:02:27.434315200', 502, 104),
(52, 'bad house', 4, '2024-07-13T14:02:27.434315200', 502, 104),
(53, 'bad house', 4, '2024-07-13T14:02:27.434315200', 502, 104),
(54, 'bad house', 1, '2024-07-13T14:02:27.434315200', 502, 104),
(55, 'bad house', 1, '2024-07-13T14:02:27.434315200', 502, 104),
(56, 'bad house', 2, '2024-07-13T14:02:27.434315200', 502, 104),
(57, 'bad house', 2, '2024-07-13T14:02:27.434315200', 502, 104);

INSERT INTO users_reviews(bio_data_id, reviews_id)VALUES
(104, 40),
(104, 41),
(104, 42),
(104, 43),
(104, 54),
(104, 45),
(104, 46),
(104, 47),
(104, 48),
(104, 49),
(104, 50);

INSERT INTO properties_reviews(property_id, reviews_id) VALUES
(500, 40),
(500, 41),
(500, 42),
(501, 43),
(502, 45),
(502, 46),
(502, 47),
(502, 48),
(503, 49),
(503, 50),
(503, 51),
(504, 52),
(504, 53),
(505, 54),
(505, 55),
(505, 56),
(505, 57);

