var fs = require('fs')

// Could be done async for better performance.
var input = fs.readFileSync('./input.txt', { encoding: 'utf8' })

var inputArray = input.split('\r\n')

let largestNumber = 0
let aggrNumber = 0
let calorieTotalList = [];

inputArray.forEach( (value, index) => {
    if(value !== "") {
        aggrNumber += Number(value)
    }
    else {
        if(aggrNumber > largestNumber) {
            largestNumber = aggrNumber
        }
        calorieTotalList.push(aggrNumber)
        aggrNumber = 0
    }
});
console.log('Largest number: ', largestNumber)

calorieTotalList.sort((x, y) => y - x);
console.log("Sum of the three largest numbers: ", calorieTotalList[0] + calorieTotalList[1] + calorieTotalList[2])

