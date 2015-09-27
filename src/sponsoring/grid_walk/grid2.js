(function() {

    const HEIGHT = 600;
    const WIDTH = 600;
    const PIXELS_IN_CELL = 4;
    const BOARD_HEIGHT = HEIGHT * PIXELS_IN_CELL;
    const BOARD_WIDTH = WIDTH * PIXELS_IN_CELL;
    const MAX_ABS_VALUE = 19;

    const FILLED = 2;
    const UNREACHABLE = 0;
    const REACHABLE = 1;

    const FILL_COLOR_UNREACHABLE = '#bb0000';
    const FILL_COLOR_REACHABLE = '#00bb00';
    const FILL_COLOR_FILLED = '#0000bb';

    var canvas = document.getElementById('canvas');
    var ctx = canvas.getContext("2d");
    canvas.style.height = PIXELS_IN_CELL * HEIGHT + "px";
    canvas.style.width = PIXELS_IN_CELL * WIDTH + "px";
    ctx.canvas.width = PIXELS_IN_CELL * WIDTH;
    ctx.canvas.height = PIXELS_IN_CELL * HEIGHT;
    ctx.strokeStyle = '#333333';
    ctx.lineWidth = 1;

    createCells();
//    fillCells();
    fillCells2();

    function fillCells2() {
        var surface = createSurface();
        var stack = [[0, 0]];
        while (stack.length > 0) {
            var nextCoord = stack.pop();
            fillRow(nextCoord, surface, stack);
        }
        drawOnGrid(surface);
        calcAndShowResult(surface);
    }

    function fillRow(coord, surface, stack) {
        var x = coord[0];
        var y = coord[1];

        if (isFilled(surface[x][y])) {
            addCellToCheck(x, y + 1, surface, stack);
            addCellToCheck(x, y - 1, surface, stack);
            return;
        }

        // fill left
        var i;
        for (i = x; i >= 0; i--) {
            if (!isReachable(surface[i][y])) {
                break;
            }
            fillCell(i, y, surface);
            addCellToCheck(i, y + 1, surface, stack);
            addCellToCheck(i, y - 1, surface, stack);
        }

        // fill right
        for (i = x + 1; i < surface.length; i++) {
            if (!isReachable(surface[i][y])) {
                break;
            }
            fillCell(i, y, surface);
            addCellToCheck(i, y + 1, surface, stack);
            addCellToCheck(i, y - 1, surface, stack);
        }
    }

    function addCellToCheck(x, y, surface, stack) {
        if (isReachable(surface[x][y])) {
            stack.push([x, y]);
        }
    }

    function isFilled(value) {
        return value === FILLED;
    }

    function isReachable(value) {
        return value === REACHABLE;
    }

    function fillCell(x, y, surface) {
        surface[x][y] = FILLED;
    }

    function createSurface() {
        var surface = new Array(WIDTH / 2);
        var absValue;
        for (var x = 0; x < surface.length; x++) {
            surface[x] = new Array(HEIGHT / 2);
            for (var y = 0; y < HEIGHT / 2; y++) {
                absValue = getAbsValue(x, y);
                if (absValue > MAX_ABS_VALUE) { // unreachable
                    surface[x][y] = UNREACHABLE;
                } else {
                    surface[x][y] = REACHABLE; // reachable, 2 - filled
                }
            }
        }
        return surface;
    }

    function calcAndShowResult(surface) {
        var result = 0;

        for (var x = 0; x < surface.length; x++) {
            for (var y = 0; y < HEIGHT / 2; y++) {
                if (isFilled(surface[x][y])) {
                    result++;
                }
            }
        }

        var maxLen = 298; // without 0
        result = result - maxLen - maxLen - 1;
        result = result * 4;
        result = result + 4 * maxLen + 1;
        console.log(result); // 102485 for 19
    }

    function fillCells() {
        var surface = new Array(WIDTH / 2);
        var absValue;
        for (var x = 0; x < surface.length; x++) {
            surface[x] = new Array(HEIGHT / 2);
            for (var y = 0; y < HEIGHT / 2; y++) {
                absValue = getAbsValue(x, y);
                if (absValue > MAX_ABS_VALUE) { // unreachable
                    surface[x][y] = 0;
                } else {
                    surface[x][y] = 1; // reachable, 2 - filled
                }
            }
        }
        floodFill(0, 0, surface);
        drawOnGrid(surface);
    }

    function floodFill(x, y, surface) {
        if (x < 0 || y < 0 || x >= WIDTH / 2 || y >= HEIGHT / 2) return;
        var currColor = surface[x][y];

//        if (x === 29 && y === 1) {
//            debugger;
//        }
        if (currColor === 1) {
            surface[x][y] = 2;
            floodFill(x - 1, y, surface);
            floodFill(x + 1, y, surface);
            floodFill(x, y - 1, surface);
            floodFill(x, y + 1, surface);
        }
    }

    function drawOnGrid(surface) {
        ctx.fillStyle = FILL_COLOR_FILLED;
        for (var x = 0; x < WIDTH / 2; x++) {
            for (var y = 0; y < HEIGHT / 2; y++) {
                var currColor = surface[x][y];
                if (currColor != 2) continue;
                var bX = x * PIXELS_IN_CELL + (WIDTH * PIXELS_IN_CELL / 2);
                var bY = y * PIXELS_IN_CELL + (HEIGHT * PIXELS_IN_CELL / 2);
                ctx.fillRect(bX, bY, PIXELS_IN_CELL, PIXELS_IN_CELL);
            }
        }
    }


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

        ctx.fillStyle = getFillColor(x, y);
        ctx.fillRect(bX, bY, PIXELS_IN_CELL, PIXELS_IN_CELL);
        ctx.strokeRect(bX, bY, PIXELS_IN_CELL, PIXELS_IN_CELL);

//        ctx.fillStyle = '#000000';
//        ctx.font = "bold 12px Arial";
//        ctx.fillText(x + ',' + y, bX + 2, bY + PIXELS_IN_CELL / 2);
//        var cell = b.createElement('polygon', [p1, p2, p3, p4], {fillColor: fillColor});
//        var text = b.create('text',[bX + 2 , bY + (PIXELS_IN_CELL / 2), '(' + x + ',' + y + ')'], {fontSize: 10});
    }

    function getFillColor(x, y) {
        var absValue = getAbsValue(x, y);
        if (absValue > MAX_ABS_VALUE) {
            return FILL_COLOR_UNREACHABLE;
        }
        return FILL_COLOR_REACHABLE;
    }

    function getAbsValue(x, y) {
        var abs = Math.abs(x).toString() + Math.abs(y).toString();
        var numArray = abs.split('');

        var result = 0;
        for (var i = 0; i < numArray.length; i++) {
            result += parseInt(numArray[i], 10);
        }
        return result;
    }

})();