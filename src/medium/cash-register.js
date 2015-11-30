var coins = [0.01, 0.05, 0.1, 0.25, 0.5, 1.0, 2.0, 5.0, 10.0, 20.0, 50.0, 100.0];
var names = ['PENNY', 'NICKEL', 'DIME', 'QUARTER', 'HALF DOLLAR', 'ONE', 'TWO', 'FIVE', 'TEN', 'TWENTY', 'FIFTY', 'ONE HUNDRED'];

var fs = require("fs");
fs.readFileSync(process.argv[2]).toString().split('\n').forEach(function(line) {
    if (line !== "") {
        var parts = line.split(';');
        var price = parseFloat(parts[0]);
        var cash = parseFloat(parts[1]);

        if (price == cash) {
            console.log('ZERO');
        } else if (cash < price) {
            console.log('ERROR');
        } else {
            console.log(drawer(price, cash))
        }
    }
});

function drawer(price, cash) {
    var result = [];

    var diff = cash - price;

    while (Math.round(diff * 100) / 100 > 0.00001) {
        var pos = getNextCoinPos(diff);
        var coin = getCoinByPos(pos);
        var name = getCoinNameByPos(pos);

        var num = Math.floor(diff / coin);
        diff = Math.round((diff - num * coin) * 100) / 100;
        result.push([name, num]);
        //result.push(name);
    }

    return joinResult(result);
}

function joinResult(array) {
    var result = '';
    for (var i = 0; i < array.length; i++) {
        var coin = array[i];
        for (var j = 1; j <= coin[1]; j++) {
            result += coin[0];
            result += ',';
        }
    }
    return result.substr(0, result.length - 1);
}

function getNextCoinPos(diff) {
    var i;
    for (i = 0; i < coins.length; i++) {
        if (coins[i] > diff) {
            break;
        }
    }
    return i - 1;
}

function getCoinByPos(pos) {
    return coins[pos];
}

function getCoinNameByPos(pos) {
    return names[pos];
}
