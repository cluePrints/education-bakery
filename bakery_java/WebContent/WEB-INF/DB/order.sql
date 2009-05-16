DELIMITER $$

DROP PROCEDURE IF EXISTS `bakery`.`secondRule` $$
CREATE PROCEDURE BAKERY.secondRule (IN orderId INTEGER)
BEGIN
   DECLARE moneyMoveDest INTEGER ;
   DECLARE moneyMoveSource INTEGER ;
   DECLARE moneyMoveAmount FLOAT;
   DECLARE moneyMoveProduct INTEGER ;
   DECLARE moneyMoveId INTEGER ;


   DECLARE orderProvider  INTEGER;
   DECLARE orderConsumer INTEGER ;
   DECLARE newOrderId INTEGER ;


    SELECT money_move_destination INTO moneyMoveDest FROM money_moves WHERE money_move_date AND money_move_order = orderId;
    SELECT money_move_source INTO moneyMoveSource FROM money_moves WHERE money_move_date AND money_move_order = orderId;
    SELECT money_move_amount INTO moneyMoveAmount FROM money_moves WHERE money_move_date AND money_move_order = orderId;
    SELECT money_move_product INTO moneyMoveProduct FROM money_moves WHERE money_move_date AND money_move_order = orderId;
    SELECT money_move_id INTO moneyMoveId FROM money_moves WHERE money_move_date AND money_move_order = orderId;

    SELECT order_provider INTO orderProvider FROM orders WHERE order_id =orderId;
    SELECT order_consumer INTO orderConsumer FROM orders WHERE order_id =orderId;

    DELETE FROM product_moves WHERE product_move_money_move = moneyMoveId;
    DELETE FROM money_moves WHERE money_move_id = moneyMoveId;



    INSERT INTO money_moves(money_move_id,money_move_desc,money_move_amount,money_move_date,money_move_destination,money_move_source,money_move_order,money_move_product)
    VALUE(moneyMoveId,"return",moneyMoveAmount,now(),moneyMoveSource,moneyMoveDest,orderId,moneyMoveProduct);



END $$

DELIMITER ;


DELIMITER |
DROP TRIGGER IF EXISTS  before_update_order|
CREATE TRIGGER before_update_order
  BEFORE UPDATE ON orders FOR EACH ROW
  BEGIN
  	IF (NEW.order_active<=0) AND (OLD.order_active)>0 THEN
        	CALL secondRule(OLD.order_id);
    END IF;
  END;
|
DELIMITER;