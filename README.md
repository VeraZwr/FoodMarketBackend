# Food-market
project refer to http://129.213.60.139/

## How to run

Need java, MySql8.0+

Firstly, need to change the configuration file `/src/main/resources/application.properties`. change the `spring.datasource.url` to the mysql database you have, maybe create a new database called foodmarket. Change the user name and password of your database.

The port I set is 8080.

Run this Spring boot project by clicking the `run` button in the IDE. (main class is soen487.foodmarket.FoodmarketApplication, IntelliJIDEA is highly recommended.)


## Project funtionalities：

### User module

* Sign up
* Log in
* Change password
* Modify profile

### Product module

* Seller: create a new food
* Seller: Mark a food as processed
* Seller: Modify attributes of a food
* Seller: View all the food that he posted
* Seller: increate/decreate the stock of a product
* Buyer: View all the food for sale
* Buyer: Put a food in cart
* Buyer: View the detail info of a food

### Order module

* Buyer: view all the orders he made
* Buyer: Create a new order
* Buyer: Pay an order
* Seller: view all the order to him/her
* Seller: Process an order

## API

### User Module

* Sign up：

```
POST: /user/new   
{
  "username": "abc003",
  "password": "ohoh123",
  "name": "test1",
  "email": "abc@gmail.com",
  "address": "124124 sdfka, QC",
  "tel": "1234567"
}

Sample Response:
{
    "status": "success",
    "data": {
        "id": 8,
        "username": "abc004",
        "name": "test1",
        "email": "abc@gmail.com",
        "tel": "1234567",
        "address": "124124 sdfka, QC"
    }
}

```

* Log in:
```
POST: /user/login
 x-www-form-urlencoded:
 username: abc004
 password: ohoh123

Sample response:
{
    "status": "success",
    "data": {
        "id": 8,
        "username": "abc004",
        "name": "test1",
        "email": "abc@gmail.com",
        "tel": "1234567",
        "address": "124124 sdfka, QC"
    }
}
```

* Change password:
```
POST: /user/pwdchange
{
  "username": "youareremoved",
  "password": "ohoh123",
  "newPassword": "ahah"
}

{
    "status": "success",
    "data": {
        "id": 1,
        "username": "youareremoved",
        "name": "Three Zhang",
        "email": "abc@gmail.com",
        "tel": "1234567",
        "address": "124124 sdfka, QC"
    }
}
```

* Modify profile:
```
POST: /user/infochange
{
  "id": 1,
  "username": "youareremoved",
  "password": "ohoh123",
  "name": "abc21316",
  "email": "abc@gmail.com",
  "address": "124124 sdfka, QC",
  "tel": "1234567"
}

{
    "status": "success",
    "data": {
        "id": 1,
        "username": "youareremoved",
        "name": "abc21316",
        "email": "abc@gmail.com",
        "tel": "1234567",
        "address": "124124 sdfka, QC"
    }
}
```

* new product / modify product
```
POST: /dish
PUT: /dish
{
	"productId": 0,
	"productName": "cake",
	"productPrice" : 8.98,
	"productStock": 5,
	"productDescription" : "cake!",
	"productImage": "http://baidu.com/jianbingguozi/1.png",
	"productStatus": 0,
	"category": 4,
  "productOwnerId": 1
}

{
    "status": "success",
    "data": {
        "productId": "1587665300050522524",
        "productName": "asdfwef",
        "productPrice": 15,
        "productStock": 5,
        "productDescription": "adfwef",
        "productImage": "http://baidu.com/jianbingguozi/1.png",
        "productStatus": 0,
        "category": 4,
        "productOwnerId": 2
    }
}
```

* List all Products by category
```
GET: /dish/all

{
    "status": "success",
    "data": [
        {
            "typeName": "Appetizers",
            "typeNumber": 1,
            "productVOList": [
                {
                    "productId": "1587229278721585704",
                    "productName": "Mongo Ice",
                    "productPrice": 5.98,
                    "productStock": 5,
                    "productDescription": "good!",
                    "productImage": "https://i.imgur.com/TTQSmAS.jpg"
                },
                {
                    "productId": "1587229942208638564",
                    "productName": "Jianbing Guozi",
                    "productPrice": 15.98,
                    "productStock": 7,
                    "productDescription": "Tianjin appetizer",
                    "productImage": "https://i.imgur.com/QmToZXc.jpg"
                }
            ]
        },
        {
            "typeName": "Home cooking",
            "typeNumber": 2,
            "productVOList": [
                {
                    "productId": "1587230314518968376",
                    "productName": "PiPi shrimp",
                    "productPrice": 15.00,
                    "productStock": 5,
                    "productDescription": "good!",
                    "productImage": "https://i.imgur.com/8YcdHpP.jpg"
                },
                {
                    "productId": "1587566017379326530",
                    "productName": "fired chicken",
                    "productPrice": 10.50,
                    "productStock": 2,
                    "productDescription": "chicken",
                    "productImage": "https://i.imgur.com/3IIeqZM.jpg"
                },
                {
                    "productId": "1587569998785691425",
                    "productName": "Samon",
                    "productPrice": 10.00,
                    "productStock": 8,
                    "productDescription": "sfwef",
                    "productImage": "https://i.imgur.com/hBLs1Zj.jpg"
                }
            ]
        },
        {
            "typeName": "Desserts",
            "typeNumber": 4,
            "productVOList": [
                {
                    "productId": "1587229964425454866",
                    "productName": "Rice congee",
                    "productPrice": 8.98,
                    "productStock": 8,
                    "productDescription": "cake!",
                    "productImage": "https://i.imgur.com/LgzjlLx.jpg"
                },
                {
                    "productId": "1587665300050522524",
                    "productName": "asdfwef",
                    "productPrice": 15.00,
                    "productStock": 5,
                    "productDescription": "adfwef",
                    "productImage": "http://baidu.com/jianbingguozi/1.png"
                }
            ]
        },
        {
            "typeName": "Main Food",
            "typeNumber": 6,
            "productVOList": [
                {
                    "productId": "1587430139013359155",
                    "productName": "burger",
                    "productPrice": 8.40,
                    "productStock": 9,
                    "productDescription": "home made beef burger",
                    "productImage": "https://i.imgur.com/pkCkHov.jpg"
                },
                {
                    "productId": "1587437153919703562",
                    "productName": "Yangzhou Rice",
                    "productPrice": 5.80,
                    "productStock": 10,
                    "productDescription": "rice",
                    "productImage": "https://i.imgur.com/k7cI0li.jpg"
                },
                {
                    "productId": "1587560939652531881",
                    "productName": "Pizza",
                    "productPrice": 12.00,
                    "productStock": 9,
                    "productDescription": "double topping pizza",
                    "productImage": "https://i.imgur.com/kZUmroB.jpg"
                }
            ]
        }
    ]
}
```

* List all products by seller
```
GET: /dish/listBySeller?ownerId=1

{
    "status": "success",
    "data": [
        {
            "productId": "1587229278721585704",
            "productName": "Mongo Ice",
            "productPrice": 5.98,
            "productStock": 5,
            "productDescription": "good!",
            "productImage": "https://i.imgur.com/TTQSmAS.jpg",
            "productStatus": 0,
            "category": 1,
            "productOwnerId": 1
        },
        {
            "productId": "1587229942208638564",
            "productName": "Jianbing Guozi",
            "productPrice": 15.98,
            "productStock": 7,
            "productDescription": "Tianjin appetizer",
            "productImage": "https://i.imgur.com/QmToZXc.jpg",
            "productStatus": 0,
            "category": 1,
            "productOwnerId": 1
        },
        {
            "productId": "1587229964425454866",
            "productName": "Rice congee",
            "productPrice": 8.98,
            "productStock": 8,
            "productDescription": "cake!",
            "productImage": "https://i.imgur.com/LgzjlLx.jpg",
            "productStatus": 0,
            "category": 4,
            "productOwnerId": 1
        },
        {
            "productId": "1587566017379326530",
            "productName": "fired chicken",
            "productPrice": 10.50,
            "productStock": 2,
            "productDescription": "chicken",
            "productImage": "https://i.imgur.com/3IIeqZM.jpg",
            "productStatus": 0,
            "category": 2,
            "productOwnerId": 1
        }
    ]
}
```

* Mark a product as sold out
```
PUT: /dish/soldOut?productId=1587229942208638564

{
    "status": "success",
    "data": {
        "productId": "1587229942208638564",
        "productName": "Jianbing Guozi",
        "productPrice": 15.98,
        "productStock": 7,
        "productDescription": "Tianjin appetizer",
        "productImage": "https://i.imgur.com/QmToZXc.jpg",
        "productStatus": 1,
        "category": 1,
        "productOwnerId": 1
    }
}
```

* Mark a product as for sale

PUT /dish/forSale?productId=1587229942208638564
```
{
    "status": "success",
    "data": {
        "productId": "1587229942208638564",
        "productName": "Jianbing Guozi",
        "productPrice": 15.98,
        "productStock": 7,
        "productDescription": "Tianjin appetizer",
        "productImage": "https://i.imgur.com/QmToZXc.jpg",
        "productStatus": 0,
        "category": 1,
        "productOwnerId": 1
    }
}
```

### Order Module

* Create a new order
```
POST: /order
 x-www-form-urlencoded:
 buyerId: 1
 buyerName: zhangsan
 buyerPhone: 123456
 buyerAddress: new address
 items: [{productId:"1587229964425454866",quantity:2,unit:"piece"},{productId:"1587229942208638564",quantity:4,unit:"piece"}]
 {
    "status": "success",
    "data": {
        "orderId": "1587665742463647197",
        "buyerId": 1,
        "buyerName": "zhangsan",
        "buyerPhone": "123456",
        "buyerAddress": "new address",
        "orderAmount": 81.88,
        "orderStatus": 0,
        "payStatus": 0,
        "createTime": null,
        "updateTime": null,
        "orderItemVOList": [
            {
                "itemId": "1587665742465115534",
                "orderId": "1587665742463647197",
                "productId": "1587229964425454866",
                "productName": "Rice congee",
                "productPrice": 8.98,
                "productDescription": "cake!",
                "productImage": "https://i.imgur.com/LgzjlLx.jpg",
                "quantity": 2,
                "unit": "个"
            },
            {
                "itemId": "1587665742474687993",
                "orderId": "1587665742463647197",
                "productId": "1587229942208638564",
                "productName": "Jianbing Guozi",
                "productPrice": 15.98,
                "productDescription": "Tianjin appetizer",
                "productImage": "https://i.imgur.com/QmToZXc.jpg",
                "quantity": 4,
                "unit": "piece"
            }
        ]
    }
}
```

* List orders by buyer
```
GET /listByBuyer?buyerId=2

{
    "status": "success",
    "data": [
        {
            "orderId": "1587554992341539825",
            "buyerId": 2,
            "buyerName": "Jingchao",
            "buyerPhone": "4389217000",
            "buyerAddress": "1225 Rue Mackay, Unit 12",
            "orderAmount": 21.96,
            "orderStatus": 0,
            "payStatus": 1,
            "createTime": "2020-04-22T11:29:52.000+0000",
            "updateTime": "2020-04-22T11:30:09.000+0000",
            "orderItemVOList": [
                {
                    "itemId": "1587554992352893447",
                    "orderId": "1587554992341539825",
                    "productId": "1587229278721585704",
                    "productName": "Mongo Ice",
                    "productPrice": 5.98,
                    "productDescription": "good!",
                    "productImage": "https://i.imgur.com/TTQSmAS.jpg",
                    "quantity": 1,
                    "unit": "piece"
                },
                {
                    "itemId": "1587554992370688839",
                    "orderId": "1587554992341539825",
                    "productId": "1587229942208638564",
                    "productName": "Jianbing Guozi",
                    "productPrice": 15.98,
                    "productDescription": "Tianjin appetizer",
                    "productImage": "https://i.imgur.com/QmToZXc.jpg",
                    "quantity": 1,
                    "unit": "piece"
                }
            ]
        },
        {
            "orderId": "1587566315441481748",
            "buyerId": 2,
            "buyerName": "Jingchao",
            "buyerPhone": "4389217000",
            "buyerAddress": "1225 Rue Mackay, Unit 12",
            "orderAmount": 21.00,
            "orderStatus": 1,
            "payStatus": 1,
            "createTime": "2020-04-22T14:38:35.000+0000",
            "updateTime": "2020-04-22T14:52:27.000+0000",
            "orderItemVOList": [
                {
                    "itemId": "1587566315444980037",
                    "orderId": "1587566315441481748",
                    "productId": "1587566017379326530",
                    "productName": "fired chicken",
                    "productPrice": 10.50,
                    "productDescription": "chicken",
                    "productImage": "https://i.imgur.com/3IIeqZM.jpg",
                    "quantity": 2,
                    "unit": "piece"
                }
            ]
        },
        {
            "orderId": "1587570061807619356",
            "buyerId": 2,
            "buyerName": "Jingchao",
            "buyerPhone": "4389217000",
            "buyerAddress": "1225 Rue Mackay, Unit 12",
            "orderAmount": 20.00,
            "orderStatus": 1,
            "payStatus": 1,
            "createTime": "2020-04-22T15:41:01.000+0000",
            "updateTime": "2020-04-22T15:41:51.000+0000",
            "orderItemVOList": [
                {
                    "itemId": "1587570061808725354",
                    "orderId": "1587570061807619356",
                    "productId": "1587569998785691425",
                    "productName": "Samon",
                    "productPrice": 10.00,
                    "productDescription": "sfwef",
                    "productImage": "https://i.imgur.com/hBLs1Zj.jpg",
                    "quantity": 2,
                    "unit": "piece"
                }
            ]
        }
    ]
}

```

* Pay an order

```
PUT: order/pay?orderId=1587246126434750576

{
    "status": "success",
    "data": {
        "orderId": "1587246126434750576",
        "buyerId": 1,
        "buyerName": "zhangsan",
        "buyerPhone": "123456",
        "buyerAddress": "new address",
        "orderAmount": 81.88,
        "orderStatus": 0,
        "payStatus": 1,
        "createTime": "2020-04-18T21:42:06.000+0000",
        "updateTime": "2020-04-23T18:18:48.000+0000"
    }
}

```
* finish an order

```
PUT /order/finish?orderId=1587246126434750576

{
    "status": "success",
    "data": {
        "orderId": "1587246126434750576",
        "buyerId": 1,
        "buyerName": "zhangsan",
        "buyerPhone": "123456",
        "buyerAddress": "new address",
        "orderAmount": 81.88,
        "orderStatus": 1,
        "payStatus": 1,
        "createTime": "2020-04-18T21:42:06.000+0000",
        "updateTime": "2020-04-23T18:18:58.000+0000"
    }
}
```

* list orders by owner
```
GET /order/listByOwner?ownerId=1

{
    "status": "success",
    "data": [
        {
            "orderId": "1587561029084125141",
            "buyerId": 4,
            "buyerName": "test2",
            "buyerPhone": "1234567",
            "buyerAddress": "sdkjfl, Montreal, QC",
            "orderAmount": 12.00,
            "orderStatus": 1,
            "payStatus": 1,
            "createTime": "2020-04-22T13:10:29.000+0000",
            "updateTime": "2020-04-22T13:12:15.000+0000",
            "orderItemVOList": [
                {
                    "itemId": "1587561029084711248",
                    "orderId": "1587561029084125141",
                    "productId": "1587560939652531881",
                    "productName": "Pizza",
                    "productPrice": 12.00,
                    "productDescription": "double topping pizza",
                    "productImage": "https://i.imgur.com/kZUmroB.jpg",
                    "quantity": 1,
                    "unit": "piece"
                }
            ]
        }
    ]
}
```

