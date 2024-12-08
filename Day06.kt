package org.example

import java.awt.Point
import java.nio.file.Files
import kotlin.io.path.Path

enum class Direction {
    UP, DOWN, LEFT, RIGHT
}

fun main() {
    val input: List<String> = Files.readAllLines(Path("src\\main\\resources\\input6.txt"))
    val map: Array<CharArray> = Array(input.size) { CharArray(input.size) }
    val positionSet: HashSet<Point> = hashSetOf()
    var startPos: Point = Point()
    var direction = Direction.UP
    for ((y, line) in input.withIndex()){
        for ((x, c) in line.withIndex()){
            map[y][x] = c
            if(c == '^'){
                startPos = Point(x, y)
            }
        }
    }

    var currentPos: Point = Point(startPos.x, startPos.y)
    positionSet.add(currentPos)
    try {
        while (true) {
            when (direction) {
                Direction.UP -> {
                    if (map[currentPos.y - 1][currentPos.x] != '#') {
                        currentPos = Point(currentPos.x, currentPos.y-1)
                    } else {
                        direction = Direction.RIGHT
                    }
                }

                Direction.DOWN -> {
                    if (map[currentPos.y + 1][currentPos.x] != '#') {
                        currentPos = Point(currentPos.x, currentPos.y+1)
                    } else {
                        direction = Direction.LEFT
                    }
                }

                Direction.LEFT -> {
                    if (map[currentPos.y][currentPos.x - 1] != '#') {
                        currentPos = Point(currentPos.x-1, currentPos.y)
                    } else {
                        direction = Direction.UP
                    }
                }

                Direction.RIGHT -> {
                    if (map[currentPos.y][currentPos.x + 1] != '#') {
                        currentPos = Point(currentPos.x+1, currentPos.y)
                    } else {
                        direction = Direction.DOWN
                    }
                }
            }

            positionSet.add(currentPos)
        }
    }catch (e: Exception){ }

    println(positionSet.size)

    //Part 2
    var counter = 0
    for (row in map.indices){
        for (col in map[0].indices){
            val map2 = copyMap(input)
            if(map2[row][col] == '#') continue
            map2[row][col] = '#'
            val positionSet2: HashSet<Point> = hashSetOf()
            val directionMap: HashMap<Point, MutableList<Direction>> = hashMapOf()
            direction = Direction.UP
            currentPos = Point(startPos.x, startPos.y)
            try {
                while (true) {
                    when (direction) {
                        Direction.UP -> {
                            if (map2[currentPos.y - 1][currentPos.x] != '#') {
                                currentPos = Point(currentPos.x, currentPos.y-1)
                            } else {
                                direction = Direction.RIGHT
                            }
                        }

                        Direction.DOWN -> {
                            if (map2[currentPos.y + 1][currentPos.x] != '#') {
                                currentPos = Point(currentPos.x, currentPos.y+1)
                            } else {
                                direction = Direction.LEFT
                            }
                        }

                        Direction.LEFT -> {
                            if (map2[currentPos.y][currentPos.x - 1] != '#') {
                                currentPos = Point(currentPos.x-1, currentPos.y)
                            } else {
                                direction = Direction.UP
                            }
                        }

                        Direction.RIGHT -> {
                            if (map2[currentPos.y][currentPos.x + 1] != '#') {
                                currentPos = Point(currentPos.x+1, currentPos.y)
                            } else {
                                direction = Direction.DOWN
                            }
                        }
                    }
                    if(directionMap[currentPos] == null){
                        directionMap[currentPos] = mutableListOf()
                    }
                    if(positionSet2.contains(currentPos) && directionMap[currentPos]!!.contains(direction)){
                        counter++
                        //println("$row $col")
                        break
                    }
                    positionSet2.add(currentPos)

                    directionMap[currentPos]!!.add(direction)
                }
            }catch (e: ArrayIndexOutOfBoundsException){ }
        }
    }

    println(counter)

}

private fun copyMap(input: List<String>): Array<CharArray>{
    val newMap: Array<CharArray> = Array(input.size){ CharArray(input.size) }

    for ((y, line) in input.withIndex()){
        for ((x, c) in line.withIndex()){
            newMap[y][x] = c
        }
    }

    return newMap
}