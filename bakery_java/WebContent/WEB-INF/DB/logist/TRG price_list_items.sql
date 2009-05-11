#
# 	Цены должны быть положительными числами
#	

DELIMITER |
DROP TRIGGER IF EXISTS before_upd_price_list_items|
CREATE TRIGGER before_upd_price_list_items
  BEFORE UPDATE ON price_list_items FOR EACH ROW
  BEGIN
  	IF NEW.price_list_item_price<=0 THEN
  		SET NEW.price_list_item_price=NULL;
    END IF;
  END;
|
DELIMITER;

DELIMITER |
DROP TRIGGER IF EXISTS before_ins_price_list_items|
CREATE TRIGGER before_ins_price_list_items
  BEFORE INSERT ON price_list_items FOR EACH ROW
  BEGIN
  	IF NEW.price_list_item_price<=0 THEN
  		SET NEW.price_list_item_price=NULL;
    END IF;
  END;
|
DELIMITER;