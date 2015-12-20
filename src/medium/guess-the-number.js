var fs = require("fs");
var arr = [];
fs.readFileSync(process.argv[2]).toString().split('\n').forEach(function(line) {
    var parts = line.split(' ');
    var top = parts.shift();
    parts.pop();
    console.log(guessTheNumber(parts, 0, parseInt(top, 10)));
});

function guessTheNumber(hints, low, top) {
    var guess = Math.ceil((top - low) / 2) + low;
    if (hints.length === 0) {
        return guess;
    }
    var hint = hints.shift();
    if (hint === 'Lower') {
        return guessTheNumber(hints, low, guess -1);
    } else if (hint === 'Higher'){
        return guessTheNumber(hints, guess + 1, top);
    }
    debugger;
}