// 9 2 9 9 1 8 8 8 2 1 1
var fs  = require("fs");
fs.readFileSync(process.argv[2]).toString().split('\n').forEach(function (line) {
  if (line != "") {
    printResult(line.split(" "));
    //do something here
    //console.log(answer_line);
  }
});

// 3 3 9 1 6 5 8 1 5 3
function printResult(arr) {
  arr = arr.map(value => parseInt(value));
  var numbers = [];
  for (var k = 0; k < 10; k++) {
    numbers[k] = 0;
  }

  for (var value of arr) {
    numbers[value]++;
  }

  var minIndex = 0;
  for (var i = 1; i < numbers.length; i++) {
    if (numbers[i] === 1) {
      for (var j = 0; j < arr.length; j++) {
        if (i === arr[j]) {
          console.log(j + 1);
          return;
        }
      }
    }
  }
  console.log(minIndex);
}