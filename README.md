# Java + Vue.js Shop Simulator

A simple client-server application simulating a shop with a REST API (Java Spark) and web interface (Vue.js).  
**Database:** MongoDB (manual setup required).



## Requirements
1. Java 17+
2. Maven 3.8+
3. Node.js 16+ (for frontend)
4. MongoDB 6.0+


## Installation and Run

### Backend (Java)
1. Clone the repository
2. Set up MongoDB:
   - Create a database named `Shop`
   - Create a collection called `products`
   - Document structure:

     ```json
     {
       "name": "Product Name",
       "price": 99.99
     }
     ```

3. Update the MongoDB connection string in the `MongoDBConnector` class (field `CONNECTION_STRING`)

4. Run the server (`server.java`):

The server will start at `http://localhost:4567`.

### Frontend (Vue.js)
1. Navigate to the frontend directory (ZooShopVue/)
2. Install dependencies:
   ```bash
   npm install

3. Run the client:

```bash
npm run dev
```

The application will be available at `http://localhost:5173`.


## API Endpoints

| Method | URL       | Description          |
| ------ | --------- | -------------------- |
| GET    | /products | Get list of products |
| POST   | /add      | Add a new product    |
| POST   | /purchase | Purchase a product   |


## Project Structure

```
src/
├── main/
│   ├── java/ [backend]
│   │   ├── controller/    # ApiController (Spark routes)
│   │   ├── service/       # ShopService (business logic)
│   │   ├── repository/    # ProductRepository (MongoDB access)
│   │   ├── db/            # MongoDBConnector
│   │   └── model/         # Product model
ZooShopVue/ [frontend]
├── zooshop-vue/ 
│   ├── ...
│   ├── src/
│   │   ├── app.vue
```



## Possible Issues

1. **CORS errors**: Ensure the headers are properly set in `ApiController`:

   ```java
   before((request, response) -> {
       response.header("Access-Control-Allow-Origin", "*");
   });
   ```

2. **MongoDB errors**: Check the following:

   * MongoDB (`mongod`) is running
   * Connection string is correct
   * Collection `products` exists in `Shop` database

3. **Maven dependency issues**: Update versions in `pom.xml` if necessary.


## Technologies
* Backend: Java 17, Spark, MongoDB
* Frontend: Vue.js 3, Axios
* Tools: Maven, Postman


To add sample data, use the following Postman request:
`POST http://localhost:4567/add` with body:

```json
   {
   "name": "Sample Product",
   "price": 100.0
   }
```
