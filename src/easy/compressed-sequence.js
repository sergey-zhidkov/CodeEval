var fs = require("fs");
fs.readFileSync(process.argv[2]).toString().split('\n').forEach(function(line) {
  if (line !== "") {
    line = line.trim();
    console.log(getCompressedSequence(line.split(' ')));
  }
});

function getCompressedSequence(arr) {
  if (arr.length === 1) {
    return 1 + ' ' + arr[0];
  }
  var result = [];

  var i = 1;
  var prev = arr[0];
  var num = 1;
  while (i < arr.length) {
    if (arr[i] == prev) {
      num++;
      i++;
    } else {
      result.push(num);
      result.push(prev);
      prev = arr[i];
      i++;
      num = 1;
    }
  }
  result.push(num);
  result.push(prev);

  return result.join(' ');
}