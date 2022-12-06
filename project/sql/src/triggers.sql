CREATE OR REPLACE LANGUAGE plpgsql;

-- --create sequence
-- DROP SEQUENCE IF EXISTS userIDseq;
-- CREATE SEQUENCE userIDseq START WITH 101;

--define before trigger function for setting orders timestamp
CREATE OR REPLACE FUNCTION setTimestamp()
RETURNS "trigger" AS
$BODY$
BEGIN
    NEW.orderTime = DATE_TRUNC('second', CURRENT_TIMESTAMP::timestamp); 
    RETURN NEW; 


END;
$BODY$
LANGUAGE plpgsql VOLATILE;


--create setTimestamp trigger
DROP TRIGGER IF EXISTS timestampTrigger ON Orders;

CREATE TRIGGER timestampTrigger
BEFORE INSERT
ON Orders
FOR EACH ROW
EXECUTE PROCEDURE setTimestamp();


--define after trigger function for updating products
CREATE OR REPLACE FUNCTION updateProduct()
RETURNS "trigger" AS
$BODY$
BEGIN
    UPDATE Product SET numberOfUnits = numberOfUnits - NEW.unitsOrdered 
    WHERE storeID = NEW.storeID AND productName = NEW.productName;
     
    RETURN NULL; 


END;
$BODY$
LANGUAGE plpgsql VOLATILE;


--create updateProduct trigger
DROP TRIGGER IF EXISTS updateProductTrigger ON Orders;

CREATE TRIGGER updateProductTrigger
AFTER INSERT
ON Orders
FOR EACH ROW
EXECUTE PROCEDURE updateProduct();



