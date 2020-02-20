conn = new Mongo();
db = conn.getDB("myretail");
db.productprice.drop();
db.createCollection("productprice");

db.productprice.insert( { _id: 13860425, value: 15.00, currencyCode: "USD" } );
db.productprice.insert( { _id: 13860424, value: 15.00, currencyCode: "USD" } );
db.productprice.insert( { _id: 13860423, value: 15.00, currencyCode: "USD" } );
db.productprice.insert( { _id: 13860421, value: 15.00, currencyCode: "USD" } );
db.productprice.insert( { _id: 13860420, value: 15.00, currencyCode: "USD" } );
db.productprice.insert( { _id: 13860419, value: 15.00, currencyCode: "USD" } );
db.productprice.insert( { _id: 13860418, value: 15.00, currencyCode: "USD" } );



