#
# 	Рецепт может быть активным только если у него есть параметры, от которых он зависит
#	

DELIMITER |
DROP TRIGGER IF EXISTS before_upd_recipes|
CREATE TRIGGER before_upd_recipes
  BEFORE UPDATE ON recips FOR EACH ROW
  BEGIN
  	DECLARE p INTEGER default 0;
  	SELECT recip_id INTO p FROM recip_parameters WHERE recip_parameters.recip_id=1 GROUP BY recip_id;
  	IF p=0 THEN
     SET NEW.recip_active=0;
    END IF;
  END;
|
DELIMITER;

DELIMITER |
DROP TRIGGER IF EXISTS before_ins_recipes|
CREATE TRIGGER before_ins_recipes
  BEFORE INSERT ON recips FOR EACH ROW
  BEGIN
  	DECLARE p INTEGER default 0;
  	SELECT recip_id INTO p FROM recip_parameters WHERE recip_parameters.recip_id=1 GROUP BY recip_id;
  	IF p=0 THEN
     SET NEW.recip_active=0;
    END IF;
  END;
|
DELIMITER;