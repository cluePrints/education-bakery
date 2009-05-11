SET NAMES 'utf8';
SET CHARACTER SET utf8;
drop schema if exists bakery;
create schema bakery;
use bakery;
CREATE TABLE BAKERY.addresses (
       address_id INTEGER NOT NULL AUTO_INCREMENT
     , address_address CHAR(250)
     , address_active INT NOT NULL DEFAULT 1
     , PRIMARY KEY (address_id)
) DEFAULT CHARSET=utf8;

CREATE TABLE BAKERY.recips (
       recip_id INTEGER NOT NULL AUTO_INCREMENT
     , recip_name CHAR(100) NOT NULL DEFAULT '<unknown>'
     , recip_formula CHAR(250)
     , recip_active INT NOT NULL DEFAULT 1
     , recip_time INTEGER NOT NULL
     , PRIMARY KEY (recip_id)
) DEFAULT CHARSET=utf8;

CREATE TABLE BAKERY.env_parameters (
       id INTEGER NOT NULL AUTO_INCREMENT
     , current_datetime DATETIME
     , PRIMARY KEY (id)
) DEFAULT CHARSET=utf8;

CREATE TABLE BAKERY.devices (
       device_id INTEGER NOT NULL AUTO_INCREMENT
     , device_active INT NOT NULL DEFAULT 1
     , device_name CHAR(100) NOT NULL DEFAULT '<unknown>'
     , device_desc CHAR(250)
     , PRIMARY KEY (device_id)
) DEFAULT CHARSET=utf8;

CREATE TABLE BAKERY.units (
       unit_id INTEGER NOT NULL AUTO_INCREMENT
     , unit_name CHAR(100) NOT NULL DEFAULT '<unknown>'
     , unit_active INT NOT NULL DEFAULT 1
     , PRIMARY KEY (unit_id)
) DEFAULT CHARSET=utf8;

CREATE TABLE BAKERY.product_types (
       product_type_id INTEGER NOT NULL AUTO_INCREMENT
     , product_type_name CHAR(100) NOT NULL DEFAULT '<unknown>'
     , product_type_unit INTEGER NOT NULL
     , product_type_active INT NOT NULL DEFAULT 1
     , PRIMARY KEY (product_type_id)
     , INDEX (product_type_unit)
     , CONSTRAINT FK_product_types_1 FOREIGN KEY (product_type_unit)
                  REFERENCES BAKERY.units (unit_id)
) DEFAULT CHARSET=utf8;

CREATE TABLE BAKERY.contragents (
       contragent_id INTEGER NOT NULL AUTO_INCREMENT
     , contragent_name CHAR(100) NOT NULL DEFAULT '<unknown>'
     , contragent_active INT NOT NULL DEFAULT 1
     , contragent_address INTEGER NOT NULL
     , contragent_ischild INTEGER NOT NULL DEFAULT 0
     , PRIMARY KEY (contragent_id)
     , INDEX (contragent_address)
     , CONSTRAINT FK_contragents_1 FOREIGN KEY (contragent_address)
                  REFERENCES BAKERY.addresses (address_id)
) DEFAULT CHARSET=utf8;

CREATE TABLE BAKERY.accounts (
       account_id INTEGER NOT NULL
     , account_name CHAR(100) NOT NULL DEFAULT '<unknown>'
     , account_active INT NOT NULL DEFAULT 1
     , account_desc CHAR(250)
     , account_contragent INTEGER NOT NULL
     , PRIMARY KEY (account_id)
     , INDEX (account_contragent)
     , CONSTRAINT FK_accounts_1 FOREIGN KEY (account_contragent)
                  REFERENCES BAKERY.contragents (contragent_id)
) DEFAULT CHARSET=utf8;

CREATE TABLE BAKERY.price_list_heads (
       price_list_head_id INTEGER NOT NULL AUTO_INCREMENT
     , price_list_head_date DATETIME NOT NULL
     , price_list_head_comment CHAR(250)
     , price_list_head_contragent INTEGER NOT NULL
     , price_list_head_active INT NOT NULL DEFAULT 1
     , PRIMARY KEY (price_list_head_id)
     , INDEX (price_list_head_contragent)
     , CONSTRAINT FK_price_lists_1 FOREIGN KEY (price_list_head_contragent)
                  REFERENCES BAKERY.contragents (contragent_id)
) DEFAULT CHARSET=utf8;

CREATE TABLE BAKERY.recip_effects (
       recip_effect_id INTEGER NOT NULL AUTO_INCREMENT
     , recip_effect_product_type INTEGER NOT NULL
     , recip_effect_recip INTEGER NOT NULL
     , recip_effect_is_consumed INTEGER NOT NULL
     , PRIMARY KEY (recip_effect_id)
     , INDEX (recip_effect_product_type)
     , CONSTRAINT FK_recip_results_1 FOREIGN KEY (recip_effect_product_type)
                  REFERENCES BAKERY.product_types (product_type_id)
     , INDEX (recip_effect_recip)
     , CONSTRAINT FK_recip_effects_2 FOREIGN KEY (recip_effect_recip)
                  REFERENCES BAKERY.recips (recip_id)
) DEFAULT CHARSET=utf8;

CREATE TABLE BAKERY.product_warehouses (
       product_warehouse_id INTEGER NOT NULL AUTO_INCREMENT
     , product_warehouse_name CHAR(100) NOT NULL DEFAULT '<unknown>'
     , product_warehouse_address INTEGER NOT NULL
     , product_warehouse_owner INTEGER NOT NULL
     , product_warehouse_active CHAR(10)
     , PRIMARY KEY (product_warehouse_id)
     , INDEX (product_warehouse_address)
     , CONSTRAINT FK_product_warehouses_1 FOREIGN KEY (product_warehouse_address)
                  REFERENCES BAKERY.addresses (address_id)
     , INDEX (product_warehouse_owner)
     , CONSTRAINT FK_product_warehouses_2 FOREIGN KEY (product_warehouse_owner)
                  REFERENCES BAKERY.contragents (contragent_id)
) DEFAULT CHARSET=utf8;

CREATE TABLE BAKERY.device_parameters (
       device_parameter_id INTEGER NOT NULL AUTO_INCREMENT
     , device_parameter_name CHAR(100) NOT NULL DEFAULT '<unknown>'
     , device_parameter_active INT NOT NULL DEFAULT 1
     , device_parameter_unit INTEGER NOT NULL
     , device_id INTEGER NOT NULL
     , device_parameter_changable INT NOT NULL
     , device_parameter_minimize INT NOT NULL
     , device_parameter_best_value FLOAT
     , PRIMARY KEY (device_parameter_id)
     , INDEX (device_parameter_unit)
     , CONSTRAINT FK_device_parameters_1 FOREIGN KEY (device_parameter_unit)
                  REFERENCES BAKERY.units (unit_id)
     , INDEX (device_id)
     , CONSTRAINT FK_device_parameters_2 FOREIGN KEY (device_id)
                  REFERENCES BAKERY.devices (device_id)
) DEFAULT CHARSET=utf8;

CREATE TABLE BAKERY.orders (
       order_id INTEGER NOT NULL AUTO_INCREMENT
     , order_provider INTEGER NOT NULL
     , order_consumer INTEGER NOT NULL
     , order_creation_date DATETIME NOT NULL
     , order_active INTEGER NOT NULL DEFAULT 1
     , order_done_date DATETIME
     , PRIMARY KEY (order_id)
     , INDEX (order_consumer)
     , CONSTRAINT FK_orders_1 FOREIGN KEY (order_consumer)
                  REFERENCES BAKERY.contragents (contragent_id)
) DEFAULT CHARSET=utf8;

CREATE TABLE BAKERY.price_list_items (
       price_list_item_id INTEGER NOT NULL AUTO_INCREMENT
     , price_list_item_price FLOAT
     , price_list_item_head INTEGER NOT NULL
     , price_list_item_active INT NOT NULL DEFAULT 1
     , price_list_product_type INTEGER NOT NULL
     , PRIMARY KEY (price_list_item_id)
     , INDEX (price_list_item_head)
     , CONSTRAINT FK_price_list_items_1 FOREIGN KEY (price_list_item_head)
                  REFERENCES BAKERY.price_list_heads (price_list_head_id)
     , INDEX (price_list_product_type)
     , CONSTRAINT FK_price_list_items_2 FOREIGN KEY (price_list_product_type)
                  REFERENCES BAKERY.product_types (product_type_id)
) DEFAULT CHARSET=utf8;

CREATE TABLE BAKERY.plans (
       plan_id INTEGER NOT NULL AUTO_INCREMENT
     , order_id INTEGER NOT NULL
     , start_date DATETIME
     , recip_id INTEGER NOT NULL
     , PRIMARY KEY (plan_id)
     , INDEX (order_id)
     , CONSTRAINT FK_plans_1 FOREIGN KEY (order_id)
                  REFERENCES BAKERY.orders (order_id)
     , INDEX (recip_id)
     , CONSTRAINT FK_plans_2 FOREIGN KEY (recip_id)
                  REFERENCES BAKERY.recips (recip_id)
     , INDEX (plan_id)
     , CONSTRAINT FK_plans_3 FOREIGN KEY (plan_id)
                  REFERENCES BAKERY.plans (plan_id)
) DEFAULT CHARSET=utf8;

CREATE TABLE BAKERY.recip_parameters (
       recip_id INTEGER NOT NULL
     , device_parameter_id INTEGER NOT NULL
     , INDEX (recip_id)
     , CONSTRAINT FK_recip_parameters_1 FOREIGN KEY (recip_id)
                  REFERENCES BAKERY.recips (recip_id)
     , INDEX (device_parameter_id)
     , CONSTRAINT FK_recip_parameters_2 FOREIGN KEY (device_parameter_id)
                  REFERENCES BAKERY.device_parameters (device_parameter_id)
) DEFAULT CHARSET=utf8;

CREATE TABLE BAKERY.measures (
       measure_id INTEGER NOT NULL AUTO_INCREMENT
     , measure_value FLOAT NOT NULL
     , measure_device_parameter INTEGER NOT NULL
     , measure_time DATETIME NOT NULL
     , measure_active INTEGER
     , PRIMARY KEY (measure_id)
     , INDEX (measure_device_parameter)
     , CONSTRAINT FK_measures_1 FOREIGN KEY (measure_device_parameter)
                  REFERENCES BAKERY.device_parameters (device_parameter_id)
) DEFAULT CHARSET=utf8;

CREATE TABLE BAKERY.recipe_effect_parts (
       recipe_effect_part_id INTEGER NOT NULL AUTO_INCREMENT
     , recip_effect_id INTEGER NOT NULL
     , recip_effect_part_multiplicator FLOAT NOT NULL
     , device_parameter_id INTEGER
     , PRIMARY KEY (recipe_effect_part_id)
     , INDEX (recip_effect_id)
     , CONSTRAINT FK_recipe_effect_parts_1 FOREIGN KEY (recip_effect_id)
                  REFERENCES BAKERY.recip_effects (recip_effect_id)
     , INDEX (device_parameter_id)
     , CONSTRAINT FK_recipe_effect_parts_2 FOREIGN KEY (device_parameter_id)
                  REFERENCES BAKERY.device_parameters (device_parameter_id)
) DEFAULT CHARSET=utf8;

CREATE TABLE BAKERY.money_moves (
       money_move_id INTEGER NOT NULL AUTO_INCREMENT
     , money_move_desc CHAR(250)
     , money_move_amount FLOAT
     , money_move_date DATETIME
     , money_move_destination INTEGER NOT NULL
     , money_move_source INTEGER NOT NULL
     , money_move_order INTEGER NOT NULL
     , money_move_product INTEGER NOT NULL
     , PRIMARY KEY (money_move_id)
     , INDEX (money_move_destination)
     , CONSTRAINT FK_money_moves_1 FOREIGN KEY (money_move_destination)
                  REFERENCES BAKERY.accounts (account_id)
     , INDEX (money_move_order)
     , CONSTRAINT FK_money_moves_2 FOREIGN KEY (money_move_order)
                  REFERENCES BAKERY.orders (order_id)
     , INDEX (money_move_product)
     , CONSTRAINT FK_money_moves_3 FOREIGN KEY (money_move_product)
                  REFERENCES BAKERY.price_list_items (price_list_item_id)
) DEFAULT CHARSET=utf8;

CREATE TABLE BAKERY.product_moves (
       product_move_id INT NOT NULL AUTO_INCREMENT
     , product_move_source INTEGER NOT NULL
     , product_move_destination INTEGER NOT NULL
     , product_move_date DATETIME
     , product_move_money_move INTEGER
     , PRIMARY KEY (product_move_id)
     , INDEX (product_move_source)
     , CONSTRAINT FK_product_moves_2 FOREIGN KEY (product_move_source)
                  REFERENCES BAKERY.product_warehouses (product_warehouse_id)
     , INDEX (product_move_source)
     , CONSTRAINT FK_product_moves_3 FOREIGN KEY (product_move_source)
                  REFERENCES BAKERY.product_warehouses (product_warehouse_id)
     , INDEX (product_move_source)
     , CONSTRAINT FK_product_moves_4 FOREIGN KEY (product_move_source)
                  REFERENCES BAKERY.product_warehouses (product_warehouse_id)
     , INDEX (product_move_money_move)
     , CONSTRAINT FK_product_moves_5 FOREIGN KEY (product_move_money_move)
                  REFERENCES BAKERY.money_moves (money_move_id)
) DEFAULT CHARSET=utf8;

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
  BEFORE INSERT ON price_list_items FOR EACH ROW
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
DROP TRIGGER IF EXISTS  on_upd_device_parameters|
CREATE TRIGGER on_upd_device_parameters
BEFORE UPDATE ON device_parameters FOR EACH ROW
  BEGIN
    UPDATE devices SET device_active=0
	    WHERE (NEW.device_parameter_active=0) AND devices.device_id=OLD.device_id AND devices.device_active=1;

  	UPDATE recips SET recip_active=0
    	WHERE (NEW.device_parameter_active=0) AND recips.recip_id IN (SELECT recip_id FROM recip_parameters WHERE device_parameter_id=2);
  END;
|
DELIMITER;

DELIMITER |
DROP TRIGGER IF EXISTS  before_ins_device_parameters|
CREATE TRIGGER before_ins_device_parameters
  BEFORE INSERT ON device_parameters FOR EACH ROW
  BEGIN
    UPDATE devices SET device_active=0 WHERE (NEW.device_parameter_active=0) AND devices.device_id=NEW.device_id AND devices.device_active=1;
  END;
|
DELIMITER;

#
# 	–ецепт может быть активным только если у него есть параметры, от которых он зависит
#	

DELIMITER |
DROP TRIGGER IF EXISTS before_upd_measures|
CREATE TRIGGER before_upd_measures
  BEFORE UPDATE ON measures FOR EACH ROW
  BEGIN
  	DECLARE mean FLOAT default null;
	DECLARE diff FLOAT default null;

	# получаем среднее за две недели
  	SELECT sum(measure_value)/sum(1) INTO mean FROM measures
	WHERE measure_time
    	BETWEEN ADDDATE(NEW.measure_time, -14)
      	AND NEW.measure_time
  	AND measure_device_parameter=1
	AND measure_active=1
	AND measure_id<>NEW.measure_id;
	
	IF mean is not null THEN
		SELECT sqrt(sum(power(mean-measure_value, 2))) INTO diff FROM measures
		WHERE measure_time
		    	BETWEEN ADDDATE(NEW.measure_time, -14)
		      	AND NEW.measure_time
		  	AND measure_device_parameter=1
			AND measure_active=1;
		IF abs(mean-NEW.measure_value)> diff THEN
			SET NEW.measure_active=0;
		END IF;
	END IF;	
  END;
|
DELIMITER;

DELIMITER |
DROP TRIGGER IF EXISTS before_ins_measures|
CREATE TRIGGER before_ins_measures
  BEFORE INSERT ON measures FOR EACH ROW
  BEGIN
  	DECLARE mean FLOAT default null;
	DECLARE diff FLOAT default null;

	# получаем среднее за две недели
  	SELECT sum(measure_value)/sum(1) INTO mean FROM measures
	WHERE measure_time
    	BETWEEN ADDDATE(NEW.measure_time, -14)
      	AND NEW.measure_time
  	AND measure_device_parameter=1
	AND measure_active=1;
	
	IF mean is not null THEN
		SELECT sqrt(sum(power(mean-measure_value, 2))) INTO diff FROM measures
		WHERE measure_time
		    	BETWEEN ADDDATE(NEW.measure_time, -14)
		      	AND NEW.measure_time
		  	AND measure_device_parameter=1
			AND measure_active=1;
		IF abs(mean-NEW.measure_value)> diff THEN
			SET NEW.measure_active=0;
		END IF;
	END IF;	
  END;
|
DELIMITER;

#
# 	–ецепт может зависеть от параметров только одного устройства
#	

DELIMITER |
DROP TRIGGER IF EXISTS before_upd_recip_parameters|
CREATE TRIGGER before_upd_recip_parameters
  BEFORE UPDATE ON recip_parameters FOR EACH ROW
  BEGIN
  	DECLARE p INTEGER default 0;
  	SELECT device_parameter_id INTO p FROM device_parameters
	  WHERE device_parameter_id IN (SELECT device_parameter_id FROM recip_parameters WHERE recip_id=NEW.recip_id)
	  AND device_parameter_id=NEW.device_parameter_id;
  	IF p>0 THEN
    	SET NEW.recip_id=null;
  		SET NEW.device_parameter_id=null;
    END IF;
  END;
|
DELIMITER;

DELIMITER |
DROP TRIGGER IF EXISTS before_ins_recip_parameters|
CREATE TRIGGER before_ins_recip_parameters
  BEFORE INSERT ON recip_parameters FOR EACH ROW
  BEGIN
  	DECLARE p INTEGER default null;
  	DECLARE p INTEGER default 0;
  	SELECT device_parameter_id INTO p FROM device_parameters
	  WHERE device_parameter_id IN (SELECT device_parameter_id FROM recip_parameters WHERE recip_id=NEW.recip_id)
	  AND device_parameter_id=NEW.device_parameter_id;
  	IF p>0 THEN
    	SET NEW.recip_id=null;
  		SET NEW.device_parameter_id=null;
    END IF;
  END;
|
DELIMITER;

#
# 	–ецепт может быть активным только если у него есть параметры, от которых он зависит
#	

DELIMITER |
DROP TRIGGER IF EXISTS before_upd_recipes|
CREATE TRIGGER before_upd_recipes
  BEFORE UPDATE ON recips FOR EACH ROW
  BEGIN
  	DECLARE p INTEGER default 0;
  	SELECT recip_id INTO p FROM recip_parameters WHERE recip_parameters.recip_id=NEW.recip_id GROUP BY recip_id;
  	IF p=0 THEN
     SET NEW.recip_active=0;
    END IF;
  END;
|
DELIMITER;

DELIMITER |
DROP TRIGGER IF EXISTS before_ins_recipes|
CREATE TRIGGER before_ins_recipes
  BEFORE INSERT ON recips FOR EACH ROW
  BEGIN
  	DECLARE p INTEGER default 0;
  	SELECT recip_id INTO p FROM recip_parameters WHERE recip_parameters.recip_id=NEW.recip_id GROUP BY recip_id;
  	IF p=0 THEN
     SET NEW.recip_active=0;
    END IF;
  END;
|
DELIMITER;