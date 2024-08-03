// init-mongo.js
db = connect("mongodb://localhost:27017/mydatabase");
db.createCollection("mycollection");
