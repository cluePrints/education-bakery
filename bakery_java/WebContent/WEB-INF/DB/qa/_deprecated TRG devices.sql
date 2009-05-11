DELIMITER |
DROP TRIGGER after_upd_devices|
CREATE TRIGGER after_upd_devices
  AFTER UPDATE ON devices FOR EACH ROW
  BEGIN
	#
	# 	Вырубая устройство, сразу меняем его параметры в то же состояние
	#	
	UPDATE device_parameters SET device_parameter_active=NEW.device_active
    WHERE
      device_id=NEW.device_id;
  END;
|
DELIMITER;