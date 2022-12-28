String input = new File('./input.txt').getText('UTF-8')
String[] inputArray = input.split('\r\n')

Map<Integer, String[]> grid = new HashMap<>()

inputArray.eachWithIndex { String line, Integer index ->
    grid.put(index, line.split(''))
}

int visibleTrees = 0
ArrayList<Integer> scenicScoreList = new ArrayList()
grid.eachWithIndex { Map.Entry<Integer, String[]> row, Integer rowIndex ->
    if(row.key == 0 || row.key == grid.size() - 1) {
        visibleTrees += row.value.length
    }else {
        row.value.eachWithIndex { String tree, Integer treeIndex ->
            if(treeIndex == 0 || treeIndex == row.value.length -1){
                visibleTrees++
            }else {
                int treeValue = Integer.parseInt(tree)
                boolean leftSide = true
                boolean rightSide = true
                boolean upSide = true
                boolean bottomSide = true

                // Part two
                int viewLeftSide = 0
                int viewRightSide = 0
                int viewUpSide = 0
                int viewBottomSide = 0
                boolean leftSidePart2 = true
                boolean rightSidePart2 = true
                boolean upSidePart2 = true
                boolean bottomSidePart2 = true

                // Row
                row.value.eachWithIndex { String otherTree, Integer otherTreeRowIndex ->
                    int otherTreeValue = Integer.parseInt(otherTree)

                    // Left side
                    if(otherTreeRowIndex < treeIndex && leftSide){
                        leftSide = treeValue > otherTreeValue
                    }
                    // Right side
                    if(otherTreeRowIndex > treeIndex && rightSide){
                        rightSide = treeValue > otherTreeValue
                    }

                    // Part two
                    // Left side
                    if(otherTreeRowIndex < treeIndex && leftSidePart2){
                        viewLeftSide++
                        int otherTreePart2Value = Integer.parseInt(row.value[treeIndex - viewLeftSide])
                        leftSidePart2 = treeValue > otherTreePart2Value
                    }
                    // Right side
                    if(otherTreeRowIndex > treeIndex && rightSidePart2 && (treeIndex + viewBottomSide) < (row.value.length - 1)){
                        viewRightSide++
                        int otherTreePart2Value = Integer.parseInt(row.value[treeIndex + viewRightSide])
                        rightSidePart2 = treeValue > otherTreePart2Value
                    }
                }

                // Column
                grid.eachWithIndex { Map.Entry<Integer, String[]> innerRow, Integer innerRowIndex ->
                    int otherTreeValue = Integer.parseInt(innerRow.value[treeIndex])

                    // Up side
                    if(innerRowIndex < rowIndex && upSide){
                        upSide = treeValue > otherTreeValue
                    }
                    // Bottom side
                    if(innerRowIndex > rowIndex && bottomSide){
                        bottomSide = treeValue > otherTreeValue
                    }

                    // Part two
                    // Up side
                    if(innerRowIndex < rowIndex && upSidePart2){
                        viewUpSide++
                        int otherTreePart2Value = Integer.parseInt(grid.get(rowIndex - viewUpSide)[treeIndex])
                        upSidePart2 = treeValue > otherTreePart2Value
                    }
                    // Bottom side
                    if(innerRowIndex > rowIndex && bottomSidePart2 && (rowIndex + viewBottomSide) < (grid.size() - 1)){
                        viewBottomSide++
                        int otherTreePart2Value = Integer.parseInt(grid.get(rowIndex + viewBottomSide)[treeIndex])
                        bottomSidePart2 = treeValue > otherTreePart2Value
                    }
                }

                if(leftSide || rightSide || upSide || bottomSide) {
                    visibleTrees++
                }

                scenicScoreList.add(viewLeftSide * viewRightSide * viewUpSide * viewBottomSide)
            }
        }
    }
}

println("Task one - Visible trees from outside the grid: " + visibleTrees)
println("Task two - Best scenic score is: " + scenicScoreList.sort().last())