package com.baeldung.splitlist

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class SplitListIntoPartsUnitTest {
    private val evenList = listOf(0, "a", 1, "b", 2, "c");

    private val unevenList = listOf(0, "a", 1, "b", 2, "c", 3);

    private fun verifyList(resultList: List<List<Any>>) {
        assertEquals("[[0, a], [1, b], [2, c]]", resultList.toString())
    }

    private fun verifyPartialList(resultList: List<List<Any>>) {
        assertEquals("[[0, a], [1, b], [2, c], [3]]", resultList.toString())
    }

    @Test
    fun whenChunked_thenListIsSplit() {
        val resultList = evenList.chunked(2)
        verifyList(resultList)
    }

    @Test
    fun whenUnevenChunked_thenListIsSplit() {
        val resultList = unevenList.chunked(2)
        verifyPartialList(resultList)
    }

    @Test
    fun whenWindowed_thenListIsSplit() {
        val resultList = evenList.windowed(2, 2)
        verifyList(resultList)
    }

    @Test
    fun whenUnevenPartialWindowed_thenListIsSplit() {
        val resultList = unevenList.windowed(2, 2, partialWindows = true)
        verifyPartialList(resultList)
    }

    @Test
    fun whenUnevenWindowed_thenListIsSplit() {
        val resultList = unevenList.windowed(2, 2, partialWindows = false)
        verifyList(resultList)
    }

    @Test
    fun whenGroupByWithAscendingNumbers_thenListIsSplit() {
        val numberList = listOf(1, 2, 3, 4, 5, 6);
        val resultList = numberList.groupBy { (it + 1) / 2 }
        assertEquals("[[1, 2], [3, 4], [5, 6]]", resultList.values.toString())
        assertEquals("[1, 2, 3]", resultList.keys.toString())
    }

    @Test
    fun whenGroupByWithAscendingNumbersUneven_thenListIsSplit() {
        val numberList = listOf(1, 2, 3, 4, 5, 6, 7);
        val resultList = numberList.groupBy { (it + 1) / 2 }.values
        assertEquals("[[1, 2], [3, 4], [5, 6], [7]]", resultList.toString())
    }

    @Test
    fun whenGroupByWithRandomNumbers_thenListIsSplitInWrongWay() {
        val numberList = listOf(1, 3, 8, 20, 23, 30);
        val resultList = numberList.groupBy { (it + 1) / 2 }
        assertEquals("[[1], [3], [8], [20], [23], [30]]", resultList.values.toString())
        assertEquals("[1, 2, 4, 10, 12, 15]", resultList.keys.toString())
    }

    @Test
    fun whenWithIndexGroupBy_thenListIsSplit() {
        val resultList = evenList.withIndex()
          .groupBy { it.index / 2 }
          .map { it.value.map { it.value } }
        verifyList(resultList)
    }

    @Test
    fun whenWithIndexGroupByUneven_thenListIsSplit() {
        val resultList = unevenList.withIndex()
          .groupBy { it.index / 2 }
          .map { it.value.map { it.value } }
        verifyPartialList(resultList)
    }

    @Test
    fun whenFoldIndexed_thenListIsSplit() {
        val resultList = evenList.foldIndexed(ArrayList<ArrayList<Any>>(evenList.size / 2)) { index, acc, item ->
            if (index % 2 == 0) {
                acc.add(ArrayList(2))
            }
            acc.last().add(item)
            acc
        }
        verifyList(resultList)
    }

    @Test
    fun `when using partition() on a list then a pair of two lists`() {
        val numbers = listOf(42, 1984, 1, 0, -4, 23, 100, 6, 8)
        val aPairOfList = numbers.partition { it < 42 }
        with(aPairOfList) {
            assertEquals(listOf(1, 0, -4, 23, 6, 8), first)
            assertEquals(listOf(42, 1984, 100), second)
        }

        val (lessThan42, greaterThanOrEq42) = numbers.partition { it < 42 }

        assertEquals(listOf(1, 0, -4, 23, 6, 8), lessThan42)
        assertEquals(listOf(42, 1984, 100), greaterThanOrEq42)
    }
}