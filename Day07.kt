package org.example

import java.nio.file.Files
import kotlin.io.path.Path
import kotlin.math.pow
import kotlin.random.Random
import kotlin.random.nextUInt

fun main() {
    val input: List<String> = Files.readAllLines(Path("src\\main\\resources\\input7.txt"))
    var count = 0L
    var count2 = 0L
    for (line in input){
        val splitted = line.split(": ")
        val result = splitted[0].toLong()
        val nums = splitted[1].split(" ").map { it.toLong() }

        if(checkResultPossiblePart1(result, nums)){
            count += result
        }

        if(checkResultPossiblePart2(result, nums)){
            count2 += result
        }

    }

    println(count)
    println(count2)
}

private fun checkResultPossiblePart1(result: Long, nums: List<Long>): Boolean{
    var tmpResult: Long = nums[0]
    val possibleConfigurationsSize = nums.size - 1
    val possibleConfigurations: Long = 2.0.pow(possibleConfigurationsSize.toDouble()).toLong()

    var possibleConfigurationsSet: HashSet<String > = hashSetOf()
    val p: List<Char> = listOf('+','*')
    //Create all possible configurations
    while (possibleConfigurationsSet.size.toLong() != possibleConfigurations) {
        val conf: CharArray = CharArray(possibleConfigurationsSize)
        for (j in conf.indices) {
            if(Random.nextBoolean()){
                conf[j] = '+'
            }else{
                conf[j] = '*'
            }

        }
        possibleConfigurationsSet.add(conf.concatToString())
    }

    var isPossible = false
    for (j in possibleConfigurationsSet){
        //println("$j ${possibleConfigurations}")
        tmpResult = nums[0]
        for((i, c) in j.withIndex()){
            if(c == '+') {
                tmpResult += nums[i + 1]
            }
            if(c == '*'){
                tmpResult *= nums[i + 1]
            }
        }

        if(tmpResult == result){
            isPossible = true
            //println("$tmpResult = ${tmpResult == result}")
            break
        }
    }

    return isPossible
}

private fun checkResultPossiblePart2(result: Long, nums: List<Long>): Boolean{
    var tmpResult: Long = nums[0]
    val possibleConfigurationsSize = nums.size - 1
    val possibleConfigurations: Long = 3.0.pow(possibleConfigurationsSize.toDouble()).toLong()

    var possibleConfigurationsSet: HashSet<String > = hashSetOf()
    val p: List<Char> = listOf('+','*', '|')
    //Create all possible configurations
    while (possibleConfigurationsSet.size.toLong() != possibleConfigurations) {
        val conf: CharArray = CharArray(possibleConfigurationsSize)
        for (j in conf.indices) {
            val rnum = Random.nextUInt() % 3U
            if(rnum == 0U){
                conf[j] = '+'
            }else if(rnum == 1U){
                conf[j] = '*'
            }else{
                conf[j] = '|'
            }

        }
        possibleConfigurationsSet.add(conf.concatToString())
    }

    var isPossible = false
    for (j in possibleConfigurationsSet){
        //println("$j ${possibleConfigurations}")
        tmpResult = nums[0]
        for((i, c) in j.withIndex()){
            if(c == '+') {
                tmpResult += nums[i + 1]
            }
            if(c == '*'){
                tmpResult *= nums[i + 1]
            }
            if(c == '|'){
                tmpResult = (tmpResult.toString() + nums[i + 1].toString()).toLong()
            }
        }

        if(tmpResult == result){
            isPossible = true
            //println("$tmpResult = ${tmpResult == result}")
            break
        }
    }

    return isPossible
}