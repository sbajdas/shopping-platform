INSERT INTO PRODUCTS (uuid, name, price) VALUES ('6ab39712-734b-49fc-a335-9b438c3c31c8', 'Skateboard', 20.0);
INSERT INTO PRODUCTS (uuid, name, price)
VALUES ('b254eb3e-47c3-452e-8517-5373e8cc09ec', 'Fish', 1.25);
INSERT INTO PRODUCTS (uuid, name, price)
VALUES ('a81a4a43-1a12-4c78-9c1f-31d67f37859e', 'Chips', 12.0);

INSERT INTO DISCOUNTS (id, quantity, amount_discount, percent_discount)
VALUES (nextval('mysequence'), 10, 10.0, 0);
INSERT INTO DISCOUNTS (id, quantity, amount_discount, percent_discount)
VALUES (nextval('mysequence'), 10, 0, 0.1);
