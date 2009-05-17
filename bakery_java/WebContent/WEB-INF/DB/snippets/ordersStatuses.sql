#
#	Получение заказов и статусов.
#	1 если оплачен, 0 - нет
#
SELECT DISTINCT
  order_id,
  if(sum(if(money_move_date is null,0,1))<sum(1),false,true) as payed
FROM orders
  LEFT JOIN money_moves ON order_id=money_move_order
WHERE order_active=1
GROUP BY order_id 

#
#	Получение статуса производства по заказу
#	1 - Готов к отгрузке, 0 - не готов
#
SELECT order_id, sum(1)=sum(produced) as readyForShipping FROM (
  SELECT
      order_id,
      money_move_product,
      sum(if(product_move_source is null and product_move_destination is not null and product_move_date is not null, 1,-1))=0 as produced
    FROM orders
      LEFT JOIN money_moves ON order_id=money_move_order
      LEFT JOIN product_moves ON product_move_money_move=money_move_id
    WHERE order_active=1
  GROUP BY
    order_id, money_move_product) as prodState
GROUP BY order_id

#
#	Получение статуса отгружености по заказу
#	1 - отгружен, 0 - не отгружен
#
SELECT order_id, sum(move_shipped)=sum(1) as orderShipped FROM (
  SELECT
      order_id,
      money_move_product,
      product_move_id,
      if(product_move_source is not null and product_move_destination is not null and product_move_date is not null, 1,0) as move_shipped
    FROM orders
      LEFT JOIN money_moves ON order_id=money_move_order
      LEFT JOIN product_moves ON product_move_money_move=money_move_id
    WHERE order_active=1 AND product_move_source is not null) as t
GROUP BY order_id