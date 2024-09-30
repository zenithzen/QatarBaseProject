package com.mbt.utils

object FlagUtils {
    fun containsFlag(input: Int, flag: Int) = input and flag == flag

    fun addFlag(input: Int, flag: Int) = input or flag

    fun removeFlag(input: Int, flag: Int) = input and flag.inv()
}