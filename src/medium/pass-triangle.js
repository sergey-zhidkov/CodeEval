var fs = require("fs");
var arr = [];
fs.readFileSync(process.argv[2]).toString().split('\n').forEach(function(line) {
  line = line.trim();
  var numbers = line.split(' ');
  arr.push(stringToIntArr(numbers));
});
console.log(findMax(arr));

function findMax() {
  var level = 0;
  var rootIndex = 0;
  var root = arr[level][rootIndex];
  return root + getMax(arr, level + 1, rootIndex);
}

function getMax(arr, level, rootIndex) {
  if (level !== arr.length - 1) {
    var left = getMax(arr, level + 1, rootIndex) + arr[level][rootIndex];
    var right = getMax(arr, level + 1, rootIndex + 1) + arr[level][rootIndex];
    return Math.max(left, right);
  }
  return Math.max(arr[level][rootIndex], arr[level][rootIndex + 1]);
}

function stringToIntArr(arr) {
  var result = new Array(arr.length);
  for (var i = 0; i < arr.length; i++) {
    result[i] = parseInt(arr[i], 10);
  }
  return result;
}
