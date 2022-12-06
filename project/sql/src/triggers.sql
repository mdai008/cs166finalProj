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


