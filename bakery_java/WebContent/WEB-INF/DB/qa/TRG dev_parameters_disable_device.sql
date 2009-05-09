# 
#	Когда некоторый аппарат, применяемый для изготовления продукции (Устройство, табл. devices) ломается,
#	мы не можем измерять некоторый из определяющих его работу производственных параметров (табл. device_parameters)
#	У этого параметра поле active=0, устройство не может быть больше использовано
# 

DELIMITER |
CREATE TRIGGER device_parameter_disables_device
  BEFORE UPDATE ON device_parameters FOR EACH ROW
  BEGIN
    UPDATE devices SET device_active=0 WHERE (NEW.device_parameter_active=0) AND devices.device_id=OLD.device_id;
  END;
|
DELIMITER;