package br.uem.din.datastructures.bitset

import br.uem.din.datastructures.asReadOnly
import java.util.BitSet as JavaBitSet
import kotlin.random.Random
import kotlin.test.*

class BitSetJvmInteropTest {

    @Test
    fun testJvmBitSetParityWithJavaBitSetRandomized() {
        val random = Random(5001)
        val ours = bitSetOf(64)
        val java = JavaBitSet(64)

        repeat(2_000) { step ->
            when (random.nextInt(100)) {
                in 0..49 -> {
                    val idx = random.nextInt(0, 500)
                    ours.set(idx)
                    java.set(idx)
                }
                in 50..69 -> {
                    val idx = random.nextInt(0, 500)
                    ours.clear(idx)
                    java.clear(idx)
                }
                in 70..84 -> {
                    val idx = random.nextInt(0, 500)
                    assertEquals(java.get(idx), ours[idx], "get mismatch at step=$step idx=$idx")
                }
                in 85..94 -> {
                    val from = random.nextInt(0, 500)
                    assertEquals(java.nextSetBit(from), ours.nextSetBit(from), "nextSetBit mismatch at step=$step from=$from")
                }
                else -> {
                    assertEquals(java.cardinality(), ours.cardinality(), "cardinality mismatch at step=$step")
                    assertEquals(java.length(), ours.length(), "length mismatch at step=$step")
                    assertEquals(java.isEmpty, ours.isEmpty(), "isEmpty mismatch at step=$step")
                }
            }
        }
    }

    @Test
    fun testJvmBitSetBitwiseOpsParityWithJava() {
        val random = Random(5002)
        val oursA = bitSetOf(128)
        val oursB = bitSetOf(128)
        val javaA = JavaBitSet(128)
        val javaB = JavaBitSet(128)

        repeat(50) {
            val idx = random.nextInt(0, 200)
            oursA.set(idx); javaA.set(idx)
        }
        repeat(50) {
            val idx = random.nextInt(0, 200)
            oursB.set(idx); javaB.set(idx)
        }

        val oursAnd = bitSetOf(128).also { it.or(oursA); it.and(oursB) }
        val javaAnd = javaA.clone() as JavaBitSet; javaAnd.and(javaB)
        assertEquals(javaAnd.cardinality(), oursAnd.cardinality())
        for (i in 0 until 200) {
            assertEquals(javaAnd.get(i), oursAnd[i], "AND mismatch at bit $i")
        }

        val oursOr = bitSetOf(128).also { it.or(oursA); it.or(oursB) }
        val javaOr = javaA.clone() as JavaBitSet; javaOr.or(javaB)
        for (i in 0 until 200) {
            assertEquals(javaOr.get(i), oursOr[i], "OR mismatch at bit $i")
        }

        val oursXor = bitSetOf(128).also { it.or(oursA); it.xor(oursB) }
        val javaXor = javaA.clone() as JavaBitSet; javaXor.xor(javaB)
        for (i in 0 until 200) {
            assertEquals(javaXor.get(i), oursXor[i], "XOR mismatch at bit $i")
        }

        val oursAndNot = bitSetOf(128).also { it.or(oursA); it.andNot(oursB) }
        val javaAndNot = javaA.clone() as JavaBitSet; javaAndNot.andNot(javaB)
        for (i in 0 until 200) {
            assertEquals(javaAndNot.get(i), oursAndNot[i], "ANDNOT mismatch at bit $i")
        }
    }

    @Test
    fun testJvmWordBoundary64Bit() {
        val bs = bitSetOf(128)
        bs.set(63)
        bs.set(64)
        assertTrue(bs[63])
        assertTrue(bs[64])
        assertFalse(bs[62])
        assertFalse(bs[65])
        assertEquals(2, bs.cardinality())
        assertEquals(65, bs.length())
    }

    @Test
    fun testJvmToStringFormat() {
        val bs = bitSetOf()
        bs.set(1); bs.set(5); bs.set(10)
        val str = bs.toString()
        assertTrue(str.contains("1"), "toString should contain bit 1")
        assertTrue(str.contains("5"), "toString should contain bit 5")
        assertTrue(str.contains("10"), "toString should contain bit 10")
    }

    @Test
    fun testJvmImmutableBitSetView() {
        val mutable = bitSetOf()
        mutable.set(3); mutable.set(7); mutable.set(100)
        val readOnly: ImmutableBitSet = mutable.asReadOnly()
        assertTrue(readOnly[3])
        assertTrue(readOnly[7])
        assertTrue(readOnly[100])
        assertFalse(readOnly[4])
        assertEquals(3, readOnly.cardinality())
        assertEquals(101, readOnly.length())

        mutable.set(50)
        assertTrue(readOnly[50])
        assertEquals(4, readOnly.cardinality())
    }
}
