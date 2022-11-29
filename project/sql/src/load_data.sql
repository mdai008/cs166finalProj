COPY Users
FROM '/home/user/project/data/users.csv'
WITH DELIMITER ',' CSV HEADER;
ALTER SEQUENCE users_userID_seq RESTART 101;

COPY Store
FROM '/home/user/project/data/stores.csv'
WITH DELIMITER ',' CSV HEADER;

COPY Product
FROM '/home/user/project/data/products.csv'
WITH DELIMITER ',' CSV HEADER;

COPY Warehouse
FROM '/home/user/project/data/warehouse.csv'
WITH DELIMITER ',' CSV HEADER;

COPY Orders
FROM '/home/user/project/data/orders.csv'
WITH DELIMITER ',' CSV HEADER;
ALTER SEQUENCE orders_orderNumber_seq RESTART 501;


COPY ProductSupplyRequests
FROM '/home/user/project/data/productSupplyRequests.csv'
WITH DELIMITER ',' CSV HEADER;
ALTER SEQUENCE productsupplyrequests_requestNumber_seq RESTART 11;

COPY ProductUpdates
FROM '/home/user/project/data/productUpdates.csv'
WITH DELIMITER ',' CSV HEADER;
ALTER SEQUENCE productupdates_updateNumber_seq RESTART 51;
