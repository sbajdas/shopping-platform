# shopping-platform

Simple REST service based on Spring Boot and Hibernate, with following functionalities:

* Products manipulation
* Discounts manipulation
* Total price calculation

## Launching the service

To start the service one should either start ShoppingApplication.main() via IDE or run in terminal:

```
mvn clean install
java -jar target/shopping-0.0.1-SNAPSHOT.jar
```

Sample products and discounts are preloaded in the database, please check `src/main/resources/data.sql`
### Product endpoint

```[GET] http://localhost:8080/product/``` - list of available products

```[GET] http://localhost:8080/product/{uuid}``` - show product by uuid

```[DELETE] http://localhost:8080/product/{uuid}``` - delete product by uuid

```[POST] http://localhost:8080/product/``` - add new product.  
Request body:  
``{
"name": "Best product",  
"price": 20.10
}
``

### Discount endpoint

```[GET] http://localhost:8080/discount/``` - list of available discounts

```[GET] http://localhost:8080/discount/{id}``` - show discount by id

```[DELETE] http://localhost:8080/discount/{id}``` - delete discount by id

```[POST] http://localhost:8080/discount/``` - add new discount.
It can have either percentageDiscount (dicount by percents) or amountDiscount (absolute discount in USD)
Request body:  
``{
"quantity": 100,
"percentDiscount": 0.10,
"amountDiscount": 20 //only one of these two can be non-zero //
}
``

### Total price calculation endpoint

```[POST] http://localhost:8080/price```  
Request body:  
``{
"uuid": "a81a4a43-1a12-4c78-9c1f-31d67f37859e",
"quantity": 10
}``
