package br.uem.din.datastructures.bitset

import br.uem.din.datastructures.asReadOnly
import kotlin.random.Random
import kotlin.test.*

class BitSetNativeInteropTest {

    @Test
    fun testNativeBitSetRandomizedParityWithModel() {
        val random = Random(7001)
        val bs = bitSetOf(64)
        val model = mutableSetOf<Int>()

        repeat(1_500) { step ->
            when (random.nextInt(100)) {
                in 0..49 -> {
                    val idx = random.nextInt(0, 300)
                    bs.set(idx)
                    model.add(idx)
                }
                in 50..69 -> {
                    val idx = random.nextInt(0, 300)
                    bs.clear(idx)
                    model.remove(idx)
                }
                in 70..84 -> {
                    val idx = random.nextInt(0, 300)
                    assertEquals(model.contains(idx), bs[idx], "get mismatch at step=$step idx=$idx")
                }
                else -> {
                    assertEquals(model.size, bs.cardinality(), "cardinality mismatch at step=$step")
                }
            }
        }

        for (idx in model) {
            assertTrue(bs[idx], "model element $idx missing from bitset")
        }
        assertEquals(model.size, bs.cardinality())
    }

    @Test
    fun testNativeWordBoundary64Bit() {
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
    fun testNativeBitwiseOpsWithModel() {
        val a = bitSetOf()
        val b = bitSetOf()
        val setA = setOf(1, 5, 10, 50, 100)
        val setB = setOf(5, 10, 20, 100, 150)
        setA.forEach { a.set(it) }
        setB.forEach { b.set(it) }

        val andCopy = bitSetOf()
        setA.forEach { andCopy.set(it) }
        andCopy.and(b)
        val expectedAnd = setA.intersect(setB)
        assertEquals(expectedAnd.size, andCopy.cardinality())
        expectedAnd.forEach { assertTrue(andCopy[it]) }

        val orCopy = bitSetOf()
        setA.forEach { orCopy.set(it) }
        orCopy.or(b)
        val expectedOr = setA.union(setB)
        assertEquals(expectedOr.size, orCopy.cardinality())
        expectedOr.forEach { assertTrue(orCopy[it]) }

        val xorCopy = bitSetOf()
        setA.forEach { xorCopy.set(it) }
        xorCopy.xor(b)
        val expectedXor = (setA - setB) + (setB - setA)
        assertEquals(expectedXor.size, xorCopy.cardinality())
        expectedXor.forEach { assertTrue(xorCopy[it]) }

        val andNotCopy = bitSetOf()
        setA.forEach { andNotCopy.set(it) }
        andNotCopy.andNot(b)
        val expectedAndNot = setA - setB
        assertEquals(expectedAndNot.size, andNotCopy.cardinality())
        expectedAndNot.forEach { assertTrue(andNotCopy[it]) }
    }

    @Test
    fun testNativeLargeIndexGrow() {
        val bs = bitSetOf(64)
        bs.set(5000)
        assertTrue(bs[5000])
        assertFalse(bs[4999])
        assertEquals(1, bs.cardinality())
        assertTrue(bs.size() >= 5001)
    }

    @Test
    fun testNativeImmutableBitSetView() {
        val mutable = bitSetOf()
        mutable.set(10); mutable.set(63); mutable.set(64)
        val readOnly: ImmutableBitSet = mutable.asReadOnly()
        assertTrue(readOnly[10])
        assertTrue(readOnly[63])
        assertTrue(readOnly[64])
        assertFalse(readOnly[11])
        assertEquals(3, readOnly.cardinality())

        mutable.set(128)
        assertTrue(readOnly[128])
        assertEquals(4, readOnly.cardinality())
    }

    @Test
    fun testNativeEqualsAndHashCode() {
        val a = bitSetOf()
        val b = bitSetOf(256)
        a.set(5); a.set(100); a.set(200)
        b.set(5); b.set(100); b.set(200)
        assertEquals(a, b)
        assertEquals(a.hashCode(), b.hashCode())

        b.clear(200)
        assertNotEquals(a, b)
    }
}
