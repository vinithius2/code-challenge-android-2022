package com.example.codechallengedrop

import com.example.codechallengedrop.extension.capitalize
import com.example.codechallengedrop.extension.percentToValue
import com.example.codechallengedrop.extension.valueToPercente
import org.junit.Assert
import org.junit.Test

class ExtensionTest {

    @Test
    fun `convert Float to Int in percent`() {
        Assert.assertEquals(10f.percentToValue(), 100)
    }

    @Test
    fun `convert Int to Float in percent`() {
        Assert.assertEquals(10.valueToPercente(), 1f)
    }

    @Test
    fun `uppercase the first letter`() {
        Assert.assertEquals("Challenge", "challenge".capitalize())
    }

}
