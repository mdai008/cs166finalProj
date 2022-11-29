-- CREATE OR REPLACE LANGUAGE plpgsql;

-- --create sequence
-- DROP SEQUENCE IF EXISTS userIDseq;
-- CREATE SEQUENCE userIDseq START WITH 101;

-- --define trigger function
-- CREATE OR REPLACE FUNCTION fillUserID()
-- RETURNS "trigger" AS
-- $BODY$
-- BEGIN
--     NEW.userID = nextval('userIDseq'); 
--     RETURN NEW; 


-- END;
-- $BODY$
-- LANGUAGE plpgsql VOLATILE;


-- --create trigger
-- DROP TRIGGER IF EXISTS userIDTrigger ON Users;

-- CREATE TRIGGER userIDTrigger
-- BEFORE INSERT
-- ON Users
-- FOR EACH ROW
-- EXECUTE PROCEDURE fillUserID();


