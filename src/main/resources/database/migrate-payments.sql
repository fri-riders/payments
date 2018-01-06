CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

INSERT INTO payments (uuid, user_id, booking_id, is_paypal_payment, amount, payment_date, transaction_id) VALUES (uuid_generate_v4(), '1bd484ed-b5ab-4f88-b57e-3c167d53ecb2', 1, TRUE, 256.920, TIMESTAMP '2018-01-01 12:00:00', 'iofjsal2309alasjdh');
INSERT INTO payments (uuid, user_id, booking_id, is_paypal_payment, amount, payment_date, transaction_id) VALUES (uuid_generate_v4(), '1bd484ed-b5ab-4f88-b57e-3c167d53ecb2', 2, TRUE, 156.920, TIMESTAMP '2018-01-02 12:00:00', 'vksvbcncnmzbvzuei3');
INSERT INTO payments (uuid, user_id, booking_id, is_paypal_payment, amount, payment_date, transaction_id) VALUES (uuid_generate_v4(), '1b9aec27-b80c-4baa-8467-2d0a89c5640c', 3, TRUE, 456.920, TIMESTAMP '2018-01-03 12:00:00', 'dkcb1odoisa139ajsn');