create database QiFeiDatabase;
use  QiFeiDatabase;

DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` INT AUTO_INCREMENT COMMENT '管理员id',
  `name` VARCHAR(30)  NOT NULL COMMENT '管理员登陆名称',
  `password` VARCHAR(20)  NOT NULL COMMENT '管理员登陆密码',
  `image` VARCHAR(250)  DEFAULT 'https://tse2-mm.cn.bing.net/th/id/OIP-C.hsnwZAr2R2xUr97ScoSjrgAAAA?w=199&h=199&c=7&r=0&o=5&dpr=2&pid=1.7' COMMENT '头像',
  `contact` VARCHAR(11)  NOT NULL COMMENT '管理员联系方式',
  PRIMARY KEY (`id`) 
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8  ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `supplier`;
	CREATE TABLE `supplier`  (
	  `id` INT  AUTO_INCREMENT COMMENT '供应商id',
	  `name` VARCHAR(30) NOT NULL COMMENT '供应商登陆名称',
	  `password` VARCHAR(20)  NOT NULL COMMENT '供应商登陆密码',
	 `image` VARCHAR(250)  DEFAULT 'https://tse2-mm.cn.bing.net/th/id/OIP-C.hsnwZAr2R2xUr97ScoSjrgAAAA?w=199&h=199&c=7&r=0&o=5&dpr=2&pid=1.7' COMMENT '头像',
	  `contact` VARCHAR(11)  NOT NULL COMMENT '供应商联系方式',
	  `admin_id` INT NOT NULL COMMENT '管理员外键',
	  PRIMARY KEY (`id`),
	  FOREIGN KEY (admin_id) REFERENCES admin(id) 
	) ENGINE = InnoDB AUTO_INCREMENT = 1000 CHARACTER SET = utf8  ROW_FORMAT = Dynamic;
    
DROP TABLE IF EXISTS `purchaser`;
CREATE TABLE `purchaser`  (
  `id` INT  AUTO_INCREMENT COMMENT '采购员id',
  `name` VARCHAR(30) NOT NULL COMMENT '采购员登陆名称',
  `password` VARCHAR(20)  NOT NULL COMMENT '采购员登陆密码',
    `image` VARCHAR(250)  DEFAULT 'https://tse2-mm.cn.bing.net/th/id/OIP-C.hsnwZAr2R2xUr97ScoSjrgAAAA?w=199&h=199&c=7&r=0&o=5&dpr=2&pid=1.7' COMMENT '头像',
  `contact` VARCHAR(11)  NOT NULL COMMENT '采购员联系方式',
  `admin_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (admin_id) REFERENCES admin(id) 
) ENGINE = InnoDB AUTO_INCREMENT = 1000 CHARACTER SET = utf8  ROW_FORMAT = Dynamic;


DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`  (
  `id` BIGINT AUTO_INCREMENT COMMENT '商品表主键id',
  `name` VARCHAR(200)  NOT NULL  COMMENT '商品名',
  `image` VARCHAR(250) DEFAULT '' COMMENT '商品主图',
  `price` DOUBLE(10,2) NOT NULL DEFAULT 0 COMMENT '商品价格',
  `number` INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '商品库存数量',
  `supplier_id` int NOT NULL COMMENT '供应商编号',
  PRIMARY KEY (`id`) 
) ENGINE = InnoDB AUTO_INCREMENT = 10000 CHARACTER SET = utf8 ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`  (
  `id` INT AUTO_INCREMENT COMMENT '订单表主键id',
  `create_time` VARCHAR(20)  NOT NULL  COMMENT '创建时间',
  `finish_time` VARCHAR(20) NOT NULL COMMENT '完成时间',
  `status` VARCHAR(20) NOT NULL COMMENT '订单状态',
  `number`  INT UNSIGNED NOT NULL DEFAULT 1 COMMENT '购买数量',
  `inventory` INT UNSIGNED NOT NULL COMMENT "库存",
  `product_id` BIGINT NOT NULL COMMENT '货品id',
  `purchaser_id` INT NOT NULL COMMENT '采购员id',
  PRIMARY KEY (`id`),
  FOREIGN KEY (product_id) REFERENCES product(id),
  FOREIGN KEY (purchaser_id) REFERENCES purchaser(id)
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 ROW_FORMAT = Dynamic;


DROP TABLE IF EXISTS `record`;
CREATE TABLE `record`  (
  `id` INT AUTO_INCREMENT COMMENT '记录表主键id',
  `type` VARCHAR(20) NOT NULL COMMENT '订单类型',
  `op_time` VARCHAR(50) NOT NULL COMMENT '操作时间',
  `number`  INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '出库数量',
  `inventory` int,
  `order_id` INT COMMENT '订单id',
  `product_id` BIGINT  COMMENT 'id',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 ROW_FORMAT = Dynamic;