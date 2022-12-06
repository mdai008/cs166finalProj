-- --browse stores: return the list of stores within 30 miles of user's location
-- --calculateDistance (double lat1, double long1, double lat2, double long2)
-- SELECT S.storeID, S.name
-- FROM Store S
-- WHERE calculateDistance(userLat = 20, userLong = 20, S.latitude, S.longitude) <= 30;

-- --return lat and long of current user 
-- SELECT U.latitude, U.longitude
-- FROM Users U
-- WHERE U.userID = 255;

----------------------------------------------------------------------------------
-- --browse products: given a storeID, return the list of products that store provides.
-- --return the products' name, number of units, and price per unit
-- SELECT P.productName, P.numberOfUnits, P.pricePerUnit
-- FROM Product P
-- WHERE P.storeID = 255;

----------------------------------------------------------------------------------
-- -- order product: users can order product from stores within 30 miles of their location.
-- -- given storeID, productName, and numUnits.
-- -- print out error messages when necessary, such as store out of range, product 
-- -- isnt supplied, or not enough units.
-- -- when query is executed successfully, insert order information into orders table.
-- -- products table is then updated with an after trigger.


-- SELECT * 
-- FROM Product P, (SELECT S.storeID
--                  FROM Store S
--                  WHERE calculateDistance(userLat = 20, userLong = 20, S.latitude, S.longitude) <= 30
--                     AND S.storeID = 255) 
--                 AS X
-- WHERE X.storeID = P.storeID AND P.productName = 'x' AND P.numberOfUnits >= 5;



----------------------------------------------------------------------------------
--update prod info: for managers (check if manager id matches store), given storeID, they can update product info.
--Product and ProductUpdates table will be updated with triggers.
--manager can view the last 5 rows of ProductUpdates.

SELECT *
FROM ProductUpdates P
WHERE P.updateNumber > (SELECT MAX(P1.updateNumber)
                        FROM ProductUpdates P1) - 5;




