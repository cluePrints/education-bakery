# 
#	Вырубание параметров вырубает устройство и зависимые рецепты
#

DELIMITER |
DROP TRIGGER IF EXISTS  on_upd_device_parameters|
CREATE TRIGGER on_upd_device_parameters
BEFORE UPDATE ON device_parameters FOR EACH ROW
  BEGIN
    UPDATE devices SET device_active=0
	    WHERE (NEW.device_parameter_active=0) AND devices.device_id=OLD.device_id AND devices.device_active=1;

  	UPDATE recips SET recip_active=0
    	WHERE (NEW.device_parameter_active=0) AND recips.recip_id IN (SELECT recip_id FROM recip_parameters WHERE device_parameter_id=2);
  END;
|
DELIMITER;

DELIMITER |
DROP TRIGGER IF EXISTS  before_ins_device_parameters|
CREATE TRIGGER before_ins_device_parameters
  BEFORE INSERT ON device_parameters FOR EACH ROW
  BEGIN
    UPDATE devices SET device_active=0 WHERE (NEW.device_parameter_active=0) AND devices.device_id=NEW.device_id AND devices.device_active=1;
  END;
|
DELIMITER;