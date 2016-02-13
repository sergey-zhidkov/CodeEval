var fs = require("fs");
fs.readFileSync(process.argv[2]).toString().split('\n').forEach(function(line) {
  if (line !== "") {
    line = line.trim();
    console.log(printAge(parseInt(line, 10)));
  }
});

//If they're from 0 to 2 the child should be with parents print : 'Still in Mama's arms'
//If they're 3 or 4 and should be in preschool print : 'Preschool Maniac'
//If they're from 5 to 11 and should be in elementary school print : 'Elementary school'
//From 12 to 14: 'Middle school'
//From 15 to 18: 'High school'
//From 19 to 22: 'College'
//From 23 to 65: 'Working for the man'
//From 66 to 100: 'The Golden Years'
//If the age of the person less than 0 or more than 100 - it might be an alien - print: "This program is for humans"

function printAge(age) {
  if (age >= 0 && age <= 2) { return 'Still in Mama\'s arms'; }
  if (age >= 3 && age <= 4) { return 'Preschool Maniac'; }
  if (age >= 5 && age <= 11) { return 'Elementary school'; }
  if (age >= 12 && age <= 14) { return 'Middle school'; }
  if (age >= 15 && age <= 18) { return 'High school'; }
  if (age >= 19 && age <= 22) { return 'College'; }
  if (age >= 23 && age <= 65) { return 'Working for the man'; }
  if (age >= 66 && age <= 100) { return 'The Golden Years'; }

  /*if (age < 0 || age > 100) {*/ return 'This program is for humans'; /*}*/
}
