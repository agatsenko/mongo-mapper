////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// database helpers

function createDb(dbName, userName, userPwd) {
  let newDb = db.getSiblingDB(dbName);
  if (!newDb.getUser(userName)) {
    newDb.createUser({
      user: userName,
      pwd: userPwd,
      roles: [{role: 'readWrite', db: dbName}]
    });
  }
  return newDb;
}

function createCollection(db, collName, collOpts, additionalActionFunc) {
  let collNames = db.getCollectionNames();
  if (!collNames.includes(collName)) {
    db.createCollection(collName, collOpts);
    if (additionalActionFunc) {
      additionalActionFunc(db, collName);
    }
  }
}

function dropAllCollections(db) {
  let collNames = db.getCollectionNames();
  for (let i = 0; i < collNames.length; ++i) {
    db.getCollection(collNames[i]).drop();
  }
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// uuid helpers

function hexToBase64(hex) {
  var base64Digits = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
  var base64 = "";
  var group;
  for (var i = 0; i < 30; i += 6) {
      group = parseInt(hex.substr(i, 6), 16);
      base64 += base64Digits[(group >> 18) & 0x3f];
      base64 += base64Digits[(group >> 12) & 0x3f];
      base64 += base64Digits[(group >> 6) & 0x3f];
      base64 += base64Digits[group & 0x3f];
  }
  group = parseInt(hex.substr(30, 2), 16);
  base64 += base64Digits[(group >> 2) & 0x3f];
  base64 += base64Digits[(group << 4) & 0x3f];
  base64 += "==";
  return base64;
}

function base64ToHex(base64) {
  var base64Digits = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
  var hexDigits = "0123456789abcdef";
  var hex = "";
  for (var i = 0; i < 24; ) {
      var e1 = base64Digits.indexOf(base64[i++]);
      var e2 = base64Digits.indexOf(base64[i++]);
      var e3 = base64Digits.indexOf(base64[i++]);
      var e4 = base64Digits.indexOf(base64[i++]);
      var c1 = (e1 << 2) | (e2 >> 4);
      var c2 = ((e2 & 15) << 4) | (e3 >> 2);
      var c3 = ((e3 & 3) << 6) | e4;
      hex += hexDigits[c1 >> 4];
      hex += hexDigits[c1 & 15];
      if (e3 != 64) {
          hex += hexDigits[c2 >> 4];
          hex += hexDigits[c2 & 15];
      }
      if (e4 != 64) {
          hex += hexDigits[c3 >> 4];
          hex += hexDigits[c3 & 15];
      }
  }
  return hex;
}

function uuidToBinary(uuidStr) {
  var hex = uuidStr.replace(/[{}-]/g, ""); // remove extra characters
  var base64 = hexToBase64(hex);
  return new BinData(4, base64); // new subtype 4
}

BinData.prototype.toUuid = function () {
  var hex = base64ToHex(this.base64()); // don't use BinData's hex function because it has bugs in older versions of the shell
  var uuid = hex.substr(0, 8) + '-' + hex.substr(8, 4) + '-' + hex.substr(12, 4) + '-' + hex.substr(16, 4) + '-' + hex.substr(20, 12);
  return 'UUID("' + uuid + '")';
};

BinData.prototype.toHexUuid = function () {
  var hex = base64ToHex(this.base64()); // don't use BinData's hex function because it has bugs
  var uuid = hex.substr(0, 8) + '-' + hex.substr(8, 4) + '-' + hex.substr(12, 4) + '-' + hex.substr(16, 4) + '-' + hex.substr(20, 12);
  return 'HexData(' + this.subtype() + ', "' + uuid + '")';
};
