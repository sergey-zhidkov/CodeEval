(function() {

    const HEIGHT = 1000;
    const WIDTH = 1000;
    const PIXELS_IN_CELL = 5;
    const BOARD_HEIGHT = HEIGHT * PIXELS_IN_CELL;
    const BOARD_WIDTH = WIDTH * PIXELS_IN_CELL;
    const MAX_ABS_VALUE = 19;

    var canvas = document.getElementById('canvas');
    var ctx = canvas.getContext("2d");
    canvas.style.height = PIXELS_IN_CELL * HEIGHT + "px";
    canvas.style.width = PIXELS_IN_CELL * WIDTH + "px";
    ctx.canvas.width = PIXELS_IN_CELL * WIDTH;
    ctx.canvas.height = PIXELS_IN_CELL * HEIGHT;


    createCells();

    function createCells() {
        const START_FROM_X = -WIDTH / 2;
        const START_FROM_Y = -HEIGHT / 2;
        for (var i = START_FROM_Y; i < HEIGHT / 2; i++) {
            for (var j = START_FROM_X; j < WIDTH / 2; j++) {
                createCell(j, i);
            }
        }
    }

    function createCell(x, y) {
        var bX = x * PIXELS_IN_CELL + (WIDTH * PIXELS_IN_CELL / 2);
        var bY = y * PIXELS_IN_CELL + (HEIGHT * PIXELS_IN_CELL / 2);
//        console.log(bX, bY);

//        var p1 = b.create('point', [bX, bY], {fixed: true, visible: false}); // left-down
//        var p2 = b.create('point', [bX + PIXELS_IN_CELL, bY], {fixed: true, visible: false}); // right-down
//        var p3 = b.create('point', [bX + PIXELS_IN_CELL, bY + PIXELS_IN_CELL], {fixed: true, visible: false}); // right-top
//        var p4 = b.create('point', [bX, bY + PIXELS_IN_CELL], {fixed: true, visible: false}); // left-top

        var fillColor = getFillColor(x, y);
        ctx.fillStyle = fillColor;
        ctx.fillRect(bX, bY, PIXELS_IN_CELL, PIXELS_IN_CELL);

//        ctx.fillStyle = '#000000';
//        ctx.font = "bold 12px Arial";
//        ctx.fillText(x + ',' + y, bX, bY + PIXELS_IN_CELL / 2);
//        var cell = b.createElement('polygon', [p1, p2, p3, p4], {fillColor: fillColor});
//        var text = b.create('text',[bX + 2 , bY + (PIXELS_IN_CELL / 2), '(' + x + ',' + y + ')'], {fontSize: 10});
    }

    function getFillColor(x, y) {
        var abs = Math.abs(x).toString() + Math.abs(y).toString();
        var numArray = abs.split('');

        var result = 0;
        for (var i = 0; i < numArray.length; i++) {
            result += parseInt(numArray[i], 10);
        }

        var fillColor = '#00bb00';
//        console.log(result, x, y);
        if (result > MAX_ABS_VALUE) {
            return '#bb0000';
        }
        return fillColor;
    }

})();