truncate table users cascade;
truncate table bio_data_roles cascade;
truncate table renters cascade;
truncate table landlords cascade;
TRUNCATE TABLE blacklisted_tokens CASCADE;
TRUNCATE TABLE addresses CASCADE;
TRUNCATE TABLE properties CASCADE;


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
(600, 'eyJhbGciOiJSUzUxMiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJFYXN5UmVudEFwcCIsImlhdCI6MTcyMTgzNTkwMSwiZXhwIjoxNzIxOTIyMzAxLCJzdWIiOiJbVXNlcm5hbWU9Y29sZXNhbUBnbWFpbC5jb20sIFBhc3N3b3JkPVtQUk9URUNURURdLCBFbmFibGVkPXRydWUsIEFjY291bnROb25FeHBpcmVkPXRydWUsIENyZWRlbnRpYWxzTm9uRXhwaXJlZD10cnVlLCBBY2NvdW50Tm9uTG9ja2VkPXRydWUsIEdyYW50ZWQgQXV0aG9yaXRpZXM9W1JFTlRFUl1dIiwicHJpbmNpcGFsIjoiW1VzZXJuYW1lPWNvbGVzYW1AZ21haWwuY29tLCBQYXNzd29yZD1bUFJPVEVDVEVEXSwgRW5hYmxlZD10cnVlLCBBY2NvdW50Tm9uRXhwaXJlZD10cnVlLCBDcmVkZW50aWFsc05vbkV4cGlyZWQ9dHJ1ZSwgQWNjb3VudE5vbkxvY2tlZD10cnVlLCBHcmFudGVkIEF1dGhvcml0aWVzPVtSRU5URVJdXSIsImNyZWRlbnRpYWxzIjoiW1BST1RFQ1RFRF0iLCJyb2xlcyI6WyJSRU5URVIiXX0.KPeayLBkPl_EZ2PvR-rddNr-uo3G0Sa21aGeLE84NqrFxGLNKyeBuPn0xGRjJS2amVp9rwR7e7ijs0lW6c-XBBPmq5o4NnKK16wey9fd8GBIm4k_MtUUq5aHAOpJsPGwYwlfJG3ecLO8Ov6OZDnxsduUNhTBw5m3-HNCjdDBWK8gkaRlzDh7WCX-1cPs8aeMz6imwPiAEGUcgPQJ8bthZCF_ZtkBNLY6snO8oS93mbDWOpEQ8KS9RkuzELX5pXy9xo1y2mEyICe_mLqhTeiX7aPK9u4Tie4elhhk7jsuNzmxX6KHHbxu49PdeNMRXF0cFYf4f79sRmpENtwP0hDNwQ',
 '2024-07-24T14:02:27.425305100Z', '2024-07-13T14:02:27.434315200');

INSERT INTO addresses(id, area, lga, state, number, street)VALUES
(400, 'area', 'shomolu', 'LAGOS', 'number', 'street');

INSERT INTO properties(id, no_of_apartments, address_id, landlord_id, type, media_url)VALUES
(500, 50, 400, 300, 'HOSTEL', 'http://res.cloudinary.com/dvliop7es/image/upload/v1721901227/file.jpg');