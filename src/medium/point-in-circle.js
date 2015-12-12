var fs = require("fs");
fs.readFileSync(process.argv[2]).toString().split('\n').forEach(function(line) {
  if (line !== "") {
    line = line.trim();
    var parts = line.split(';');
    var centerPoint = new Point2D(parts[0].trim());
    var radius = parseFloat(parts[1].trim().split(' ')[1]);
    var point = new Point2D(parts[2].trim());
    console.log(isPointInCircle(point, centerPoint, radius));
  }
});

function isPointInCircle(point, cPoint, radius) {
  return point.squareDistTo(cPoint) <= (radius * radius);
}

function Point2D(line) {
  var parts = line.split(' ');
  this.x = parseFloat(parts[1].substring(1, parts[1].length - 1));
  this.y = parseFloat(parts[2].substring(0, parts[1].length - 1));

  this.squareDistTo = function(point) {
    return (point.x - this.x)*(point.x - this.x) + (point.y - this.y)*(point.y - this.y);
  }
}