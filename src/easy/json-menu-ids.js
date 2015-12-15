var fs = require("fs");
fs.readFileSync(process.argv[2]).toString().split('\n').forEach(function(line) {
    line = line.trim();
    if (line !== '') {
        var object = JSON.parse(line);
        console.log(sumId(object));
    }
});

function sumId(object) {
    var result = 0;
    var items = object.menu.items;
    for (var i = 0; i < items.length; i++) {
        var item = items[i];
        if (item && item.label && item.id) {
            result += item.id;
        }
    }

    return result;
}