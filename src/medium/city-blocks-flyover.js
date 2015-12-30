var fs = require("fs");
fs.readFileSync(process.argv[2]).toString().split('\n').forEach(function(line) {
  if (line !== "") {
    var parts = line.split(' ');
    var streets = parts[0].substring(1, parts[0].length - 1).split(",");
    var avenues = parts[1].substring(1, parts[1].length - 1).split(",");

    streets = stringToIntArray(streets);
    avenues = stringToIntArray(avenues);

    console.log(getFliedBlocksNumber(streets, avenues));
  }
});

function getFliedBlocksNumber(streets, avenues) {
  var result = 0;
  var lastStreet = streets[streets.length - 1];
  var lastAvenue = avenues[avenues.length - 1];
  var angle = lastAvenue / lastStreet;
  var leftY = 0;
  var rightY;
  var leftX = 0;
  var rightX;
  var dX;
  for (var i = 1; i < streets.length; i++) {
    rightX = streets[i];
    dX = rightX - leftX;
    rightY = leftY + dX * angle;
    result += getCrossedBlocksNumber(avenues, leftY, rightY);

    leftY = rightY;
    leftX = rightX;
  }

  return result;
}

function getCrossedBlocksNumber(avenues, bottom, top) {
  bottom = Math.floor(bottom);
  top = Math.ceil(top);
  var bottomAvenueIndex;
  var topAvenueIndex;
  for (var i = 0; i < avenues.length; i++) {
    if (avenues[i] > bottom) {
      bottomAvenueIndex = i - 1;
      break;
    }
  }

  for (var j = bottomAvenueIndex + 1; j < avenues.length; j++) {
    if (avenues[j] > top) {
      if (avenues[j - 1] < top) {
        topAvenueIndex = j;
      } else {
        topAvenueIndex = j - 1;
      }
      break;
    }
  }
  if (j === avenues.length) {
    topAvenueIndex = j - 1;
  }

  return topAvenueIndex - bottomAvenueIndex;
}

function stringToIntArray(arr) {
  for (var i = 0; i < arr.length; i++) {
    arr[i] = parseInt(arr[i], 10);
  }

  return arr;
}