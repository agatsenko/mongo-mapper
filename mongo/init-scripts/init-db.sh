mongo -u $MONGO_INITDB_ROOT_USERNAME \
      -p $MONGO_INITDB_ROOT_PASSWORD \
      --eval "var TEST_DB_DBNAME='$MONGO_TEST_DB'; var TEST_DB_USERNAME='$MONGO_TEST_USERNAME'; var TEST_DB_PASSWORD='$MONGO_TEST_PASSWORD';" \
      --verbose \
      admin \
      /mongo-js-scripts/helpers.js \
      /mongo-js-scripts/create_test_db.js
