var fs = require("fs");
fs.readFileSync(process.argv[2]).toString().split('\n').forEach(function(line) {
  if (line !== "") {

    var parts = line.split(';');
    var list1 = parts[0].split(',').map(function(value) {
      return parseInt(value, 10);
    });
    var list2 = parts[1].split(',').map(function(value) {
      return parseInt(value, 10);
    });
    console.log(getIntersection(list1, list2));
  }
});

function getIntersection(list1, list2) {
  var len1 = list1.length;
  var len2 = list2.length;

  var result = [];
  var p1 = 0;
  var p2 = 0;
  while (true) {

    if (list1[p1] > list2[p2]) {
      // go to === or
      while (list2[p2] < list1[p1] && p2 < len2) {
        p2++
      }
    } else if (list1[p1] < list2[p2]) {
      while (list1[p1] < list2[p2] && p1 < len1) {
        p1++
      }
    }

    if (list1[p1] === list2[p2] && list1[p1] !== result[result.length - 1]) {
      result.push(list1[p1]);
      p1++;
      p2++;
    } else if (list1[p1] === list2[p2]) {
      p1++;
      p2++;
    }
    if (p1 === len1 || p2 === len2) {
      break;
    }
  }

  return result.join(',');
}