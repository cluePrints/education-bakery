#
# 	Рецепт может зависеть от параметров только одного устройства
#	

DELIMITER |
DROP TRIGGER IF EXISTS before_upd_recip_parameters|
CREATE TRIGGER before_upd_recip_parameters
  BEFORE UPDATE ON recip_parameters FOR EACH ROW
  BEGIN
  	DECLARE p INTEGER default null;
  	SELECT device_parameter_id INTO p FROM device_parameters
	WHERE device_parameter_id=1 AND device_parameters.device_id IN
		(SELECT device_parameters.device_id FROM recip_parameters
  			JOIN device_parameters WHERE device_parameters.device_parameter_id=recip_parameters.device_parameter_id);
  	IF p=0 THEN
    	SET NEW.recip_id=null;
  		SET NEW.device_parameter_id=null;
    END IF;
  END;
|
DELIMITER;

DELIMITER |
DROP TRIGGER IF EXISTS before_ins_recip_parameters|
CREATE TRIGGER before_ins_recip_parameters
  BEFORE INSERT ON recip_parameters FOR EACH ROW
  BEGIN
  	DECLARE p INTEGER default null;
  	SELECT device_parameter_id INTO p FROM device_parameters
	WHERE device_parameter_id=1 AND device_parameters.device_id IN
		(SELECT device_parameters.device_id FROM recip_parameters
  			JOIN device_parameters WHERE device_parameters.device_parameter_id=recip_parameters.device_parameter_id);
  	IF p=0 THEN
    	SET NEW.recip_id=null;
  		SET NEW.device_parameter_id=null;
    END IF;
  END;
|
DELIMITER;