






#添加销售量
UPDATE 
ls_spu INNER JOIN (
SELECT num,spu_id FROM ls_sku t1 , ls_order_sku t2 WHERE t1.`id` = t2.`sku_id` AND t2.`order_id` = 434  
) t3 ON ls_spu.`id` = t3.spu_id   

SET sales = sales + num


#1.1清除销售量
UPDATE ls_spu SET sales = 0

#1.2根据订单同步销售量
UPDATE 
ls_spu INNER JOIN (

SELECT SUM(num) AS num,spu_id FROM ls_sku t1 , ls_order_sku t2 WHERE t1.`id` = t2.`sku_id` AND t2.`order_id` IN 

(SELECT id FROM ls_order WHERE STATUS = 4) GROUP BY spu_id

) t3 ON ls_spu.`id` = t3.spu_id

SET sales = sales + num