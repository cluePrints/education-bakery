
#
#
#
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