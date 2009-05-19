# 
# Оценка стоимости на потребленных ресурсов
#
#	Заменить id рецепта и дату!
#

SELECT sum(amount*price) FROM
	(SELECT sum(amounts) as amount, pt FROM
	  (SELECT rep_id, pt, if(usedev, device_parameters.device_parameter_best_value,1)*mul AS amounts  FROM
	    (SELECT
	      recipe_effect_part_id as rep_id,
	      recip_effect_product_type as pt,
	      recip_effect_part_multiplicator as mul,
	      recipe_effect_parts.device_parameter_id as dp_id,
	      not isnull(recipe_effect_parts.device_parameter_id) as usedev
	    FROM recip_effects
	    JOIN recipe_effect_parts
	    WHERE recip_effect_is_consumed=1
	    AND recip_effect_recip=1) as t
	  JOIN
	    device_parameters
	  GROUP BY rep_id) as a
	GROUP BY pt) as used_amount_table
JOIN
  (	SELECT max(price_list_item_price) as price, price_list_product_type as product FROM price_list_items
	  JOIN price_list_heads
	  JOIN contragents
	WHERE price_list_head_date>'2009-04-22'
	  AND price_list_item_active>0
	  AND price_list_head_active>0
	  AND contragent_ischild=0

	GROUP BY price_list_product_type) as highest_non_child_prices
WHERE
  used_amount_table.pt=highest_non_child_prices.product

# 
# Оценка стоимости на произведенных
#

SELECT sum(amount*price) FROM
	(SELECT sum(amounts) as amount, pt FROM
	  (SELECT rep_id, pt, if(usedev, device_parameters.device_parameter_best_value,1)*mul AS amounts  FROM
	    (SELECT
	      recipe_effect_part_id as rep_id,
	      recip_effect_product_type as pt,
	      recip_effect_part_multiplicator as mul,
	      recipe_effect_parts.device_parameter_id as dp_id,
	      not isnull(recipe_effect_parts.device_parameter_id) as usedev
	    FROM recip_effects
	    JOIN recipe_effect_parts
	    WHERE recip_effect_is_consumed=0
	    AND recip_effect_recip=1) as t
	  JOIN
	    device_parameters
	  GROUP BY rep_id) as a
	GROUP BY pt) as used_amount_table
JOIN
  (	SELECT min(price_list_item_price) as price, price_list_product_type as product FROM price_list_items
	  JOIN price_list_heads
	  JOIN contragents
	WHERE price_list_head_date>'2009-04-22'
	  AND price_list_item_active>0
	  AND price_list_head_active>0
	  AND contragent_ischild=1

	GROUP BY price_list_product_type) as highest_non_child_prices
WHERE
  used_amount_table.pt=highest_non_child_prices.product


#
#	Отчет по прибыльности рецептов
#

SELECT recip_id, sum(gain) FROM (
  SELECT recip_id, sum(amount*price) as gain FROM
    (SELECT recip_id, pt, sum(if(usedev, device_parameters.device_parameter_best_value,1)*mul) AS amount FROM
      (SELECT
        recip_effect_recip as recip_id,
        recipe_effect_part_id as rep_id,
        recip_effect_product_type as pt,
        if (recip_effect_part_multiplicator is null, 1, recip_effect_part_multiplicator) as mul,
        recipe_effect_parts.device_parameter_id as dp_id,
        recipe_effect_parts.device_parameter_id is not null as usedev
      FROM recip_effects
      LEFT OUTER JOIN recipe_effect_parts ON recipe_effect_parts.recip_effect_id=recip_effects.recip_effect_id
      WHERE recip_effect_is_consumed=1) as recEffParts
    LEFT OUTER JOIN device_parameters ON device_parameter_id=dp_id
    GROUP BY pt) usedAmountTable
  JOIN
    (SELECT max(price_list_item_price) as price, price_list_product_type as product FROM price_list_items
	    JOIN price_list_heads
	    JOIN contragents
  	WHERE price_list_head_date>:reportDate
	    AND price_list_item_active>0
	    AND price_list_head_active>0
  	  AND contragent_ischild=0
  	GROUP BY price_list_product_type) as highest_non_child_prices
  WHERE
    usedAmountTable.pt=highest_non_child_prices.product
  GROUP BY recip_id
UNION
  SELECT recip_id, -sum(amount*price) as gain FROM
    (SELECT recip_id, pt, sum(if(usedev, device_parameters.device_parameter_best_value,1)*mul) AS amount FROM
      (SELECT
        recip_effect_recip as recip_id,
        recipe_effect_part_id as rep_id,
        recip_effect_product_type as pt,
        if (recip_effect_part_multiplicator is null, 1, recip_effect_part_multiplicator) as mul,
        recipe_effect_parts.device_parameter_id as dp_id,
        recipe_effect_parts.device_parameter_id is not null as usedev
      FROM recip_effects
      LEFT OUTER JOIN recipe_effect_parts ON recipe_effect_parts.recip_effect_id=recip_effects.recip_effect_id
      WHERE recip_effect_is_consumed=0) as recEffParts
    LEFT OUTER JOIN device_parameters ON device_parameter_id=dp_id
    GROUP BY pt) usedAmountTable
  JOIN
    (SELECT min(price_list_item_price) as price, price_list_product_type as product FROM price_list_items
  	  JOIN price_list_heads
  	  JOIN contragents
  	WHERE price_list_head_date>:reportDate
  	  AND price_list_item_active>0
  	  AND price_list_head_active>0
  	  AND contragent_ischild=1
  	GROUP BY price_list_product_type) as highest_non_child_prices
  WHERE
    usedAmountTable.pt=highest_non_child_prices.product
  GROUP BY recip_id) as aaa
GROUP BY recip_id
