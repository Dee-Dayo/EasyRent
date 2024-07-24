truncate table users cascade;
truncate table renters cascade;
truncate table landlords cascade;
TRUNCATE TABLE blacklisted_tokens CASCADE;


insert into users(id, first_name, last_name, email, password, date_registered, role)values
(100, 'Doe', 'John', 'johndoe@gmail.com', 'password', '2024-07-23T15:03:03.792009700', 'RENTER'),
(101, 'Bull', 'Joe', 'bulljoe@gmail.com', 'password', '2024-07-23T15:03:03.792009700', 'RENTER'),
(103, 'Sam', 'Cole', 'colesam@gmail.com', '$2a$10$seAKbpBsTn/xgAg7nbRKWuH1dnRvMlLloxMOjH00zMmTu3vLCtlee', '2024-07-23T15:03:03.792009700', 'RENTER'),
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

INSERT INTO blacklisted_tokens (id, token, expires_at, blacklisted_at) values
(600, 'eyJhbGciOiJSUzUxMiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJqd3QtcHJvamVjdCIsImlhdCI6MTcyMTgzMzY0MSwiZXhwIjoxNzIxOTIwMDQxLCJzdWIiOiJbVXNlcm5hbWU9Y29sZXNhbUBnbWFpbC5jb20sIFBhc3N3b3JkPVtQUk9URUNURURdLCBFbmFibGVkPXRydWUsIEFjY291bnROb25FeHBpcmVkPXRydWUsIENyZWRlbnRpYWxzTm9uRXhwaXJlZD10cnVlLCBBY2NvdW50Tm9uTG9ja2VkPXRydWUsIEdyYW50ZWQgQXV0aG9yaXRpZXM9W11dIiwicHJpbmNpcGFsIjoiW1VzZXJuYW1lPWNvbGVzYW1AZ21haWwuY29tLCBQYXNzd29yZD1bUFJPVEVDVEVEXSwgRW5hYmxlZD10cnVlLCBBY2NvdW50Tm9uRXhwaXJlZD10cnVlLCBDcmVkZW50aWFsc05vbkV4cGlyZWQ9dHJ1ZSwgQWNjb3VudE5vbkxvY2tlZD10cnVlLCBHcmFudGVkIEF1dGhvcml0aWVzPVtdXSIsImNyZWRlbnRpYWxzIjoiW1BST1RFQ1RFRF0iLCJyb2xlcyI6W119.FLRQp22a_IWbeDh345Qo6hL3JLOVnxlne45IbrlFxEsrV-c2-Ba2ag03CjkPtXehGKnoNvlUzKZIwWKHmb4VHFcXaj6XWG3G-IrsBZeJ6Fo2QwSx-1_363sCG7AH8qEI7ldDfmnbzENV30vgmm806rL8oCYpk_Sh3KmykfcZenJ_VCqy1MFYRXC_YyEVPxCqAWwWe51FSyadcdbXzYUnpYV5A6nwMLy6j0jKrM21Zcb2Kb1q-o0C841l154NVuh6LtcAf_9lmP85bs8ljomYArLa5D4k6DNCfr4ZAEk9iU3N8jcCH_BhuNeicPaHRQIuPaYxwk6hh7Aa6_60kWVdaQ',
 '2024-07-24T14:02:27.425305100Z', '2024-07-13T14:02:27.434315200');