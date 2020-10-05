package org.arhor.diploma.core

import java.util.*

class IntArray2D private constructor(@JvmField val rows: Int, @JvmField val cols: Int) {

    private val buffer: IntArray = IntArray(rows * cols)

    companion object {
        @JvmStatic
        fun create(rows: Int, cols: Int): IntArray2D {
            return IntArray2D(rows, cols)
        }

        @JvmStatic
        inline fun create(rows: Int, cols: Int, init: (Int, Int) -> Int): IntArray2D {
            return create(rows, cols).also {
                for (i in 0 until rows) {
                    for (j in 0 until cols) {
                        it[i, j] = init(i, j)
                    }
                }
            }
        }
    }

    operator fun get(row: Int, col: Int): Int {
        val index = index(row, col)
        return buffer[index]
    }

    operator fun set(row: Int, col: Int, value: Int) {
        val index = index(row, col)
        buffer[index] = value
    }

    private fun index(row: Int, col: Int): Int {
        ensureSafeAccess(row, col)
        return (cols * row) + col
    }

    private fun ensureSafeAccess(row: Int, col: Int) {
        val rowIsOut = row < 0 || row >= rows
        val colIsOut = col < 0 || col >= cols
        if (rowIsOut || colIsOut) {
            throw IndexOutOfBoundsException()
        }
    }

    override fun toString(): String {
        val result = StringJoiner("\n")
        for (i in 0 until rows) {
            val row = StringJoiner(" ")
            for (j in 0 until cols) {
                row.add(this[i, j].toString())
            }
            result.add(row.toString())
        }
        return result.toString()
    }
}