const DIST_TO_BUILDING = 6;

var fs = require("fs");
fs.readFileSync(process.argv[2]).toString().split('\n').forEach(function(line) {
  if (line !== "") {
    var parts = line.split(' ');
    for (var i = 0; i < parts.length; i++) {
      parts[i] = parseInt(parts[i]);
    }

    var bats = parts.slice(3);
    var batsCount = parts[2];

    var minDistance = parts[1];
    var wireLen = parts[0];

    console.log(getMaximumAdditionalBatsOnWire(wireLen, minDistance, batsCount, bats));
  }
});

function getMaximumAdditionalBatsOnWire(wireLen, minDistance, batsCount, bats) {
  var newWireLen = wireLen - (DIST_TO_BUILDING * 2);
  if (newWireLen <= 0) {
    return 0;
  }
  if (bats.length === 0) {
    var sectionsCount = Math.floor(newWireLen / minDistance);
    return sectionsCount + 1;
  }

  var result = 0;
  var prevSectionStart = 0;
  var count;
  var sectionLen;
  for (var i = 0; i < bats.length; i++) {
    sectionLen = bats[i] - prevSectionStart - DIST_TO_BUILDING;
    prevSectionStart = bats[i] - DIST_TO_BUILDING;
    count = Math.floor(sectionLen / minDistance);
    if (i === 0) {
      result += count;
    } else {
      result += (count - 1)
    }
  }
  sectionLen = newWireLen - (bats[batsCount - 1] - DIST_TO_BUILDING);
  count = Math.floor(sectionLen / minDistance);
  result += count;

  return result;
}