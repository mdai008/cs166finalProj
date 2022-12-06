-- hash and btree indexes
CREATE INDEX userIDIndex
ON Users
USING HASH
(userID);

CREATE INDEX storeIDIndex
ON Store
USING Hash
(storeID);

CREATE INDEX ProductIndex
ON Product
USING Hash
(storeID);



