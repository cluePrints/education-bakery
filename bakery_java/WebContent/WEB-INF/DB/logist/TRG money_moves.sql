#
# 	ƒвигаютс€ только положительные количества денег
#	

DELIMITER |
DROP TRIGGER IF EXISTS  before_upd_money_moves|
CREATE TRIGGER before_upd_money_moves
  BEFORE UPDATE ON money_moves FOR EACH ROW
  BEGIN
  	IF NEW.money_move_amount<=0 THEN
  		SET NEW.money_move_amount=1;
    END IF;
  END;
|
DELIMITER;

DELIMITER |
DROP TRIGGER IF EXISTS before_ins_money_moves|
CREATE TRIGGER before_ins_money_moves
  BEFORE INSERT ON money_moves FOR EACH ROW
  BEGIN
  	IF NEW.money_move_amount<=0 THEN
  		SET NEW.money_move_amount=1;
    END IF;
  END;
|
DELIMITER;