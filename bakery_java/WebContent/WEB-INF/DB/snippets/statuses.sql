#
#
#	Returns all orders with their statuses
#
#
SELECT
    order_id,
   sum(1)=sum(move_payed) as order_payed,
   sum(1)=sum(move_produced) as order_produced,
   sum(1)=sum(move_shipped) as order_shipped,
   sum(1)=sum(move_done) as order_done
  FROM
  (SELECT *, move_payed=1 and move_produced=1 and move_shipped=1 as move_done FROM
    (SELECT
       order_id,
       money_move_id,
       money_move_product,
       sum(if(money_move_date is not null, 1,0))=sum(1) as move_payed,
       sum(if(product_move_source is null and product_move_destination is not null,1,-1))=0 as move_produced,
       sum(if(product_move_source is not null and product_move_destination is not null and money_move_date is not null,1,0)) as move_shipped
    FROM
      (SELECT * FROM orders
        JOIN money_moves ON money_move_order=order_id) as t
    LEFT JOIN product_moves ON product_move_money_move=money_move_id
    GROUP BY order_id, money_move_product) as tt
  ) as ttt
GROUP by order_id
