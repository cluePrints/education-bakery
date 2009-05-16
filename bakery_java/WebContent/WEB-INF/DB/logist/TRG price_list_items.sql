#
# 	    
#	
#
#	        
#
DELIMITER |
DROP TRIGGER IF EXISTS before_upd_price_list_items|
CREATE TRIGGER before_upd_price_list_items
  BEFORE UPDATE ON price_list_items FOR EACH ROW
  BEGIN
	DECLARE c INTEGER DEFAULT null;
  	IF NEW.price_list_item_price<=0 THEN
  		SET NEW.price_list_item_price=NULL;
    END IF;

	SELECT sum(1) into c FROM price_list_items
		WHERE price_list_item_active>0
  		and price_list_product_type=NEW.price_list_product_type
  		and price_list_item_head=NEW.price_list_item_head;
	IF c=1 THEN
		SET NEW.price_list_item_active=0;
	END IF;
  END;
|
DELIMITER;

DELIMITER |
DROP TRIGGER IF EXISTS before_ins_price_list_items|
CREATE TRIGGER before_ins_price_list_items
  DECLARE c INTEGER DEFAULT null;
  BEFORE INSERT ON price_list_items FOR EACH ROW
  BEGIN
  	IF NEW.price_list_item_price<=0 THEN
  		SET NEW.price_list_item_price=NULL;
    END IF;

	SELECT sum(1) into c FROM price_list_items
		WHERE price_list_item_active>0
  		and price_list_product_type=NEW.price_list_product_type
  		and price_list_item_head=NEW.price_list_item_head;
	IF c=1 THEN
		SET NEW.price_list_item_active=0;
	END IF;
  END;
|
DELIMITER;

DELIMITER |
DROP TRIGGER IF EXISTS before_upd_price_list_head|
CREATE TRIGGER before_upd_price_list_head
  BEFORE UPDATE ON price_list_heads FOR EACH ROW
  BEGIN
    IF(NEW.price_list_head_active = 0 AND OLD.price_list_head_active = 1 )THEN
    UPDATE price_list_items SET price_list_item_active = 0 WHERE price_list_item_head = OLD.price_list_head_id;
  END IF;

  END;
|
DELIMITER;