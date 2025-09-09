package com.example.design_system

import com.example.design_system.utils.getLowQualityArtwork
import junit.framework.TestCase.assertEquals
import org.junit.Test

class UtilsTest {

    @Test
    fun `getLowQualityArtwork returns right artwork`() {
        assertEquals(
            "https://i1.sndcdn.com/artworks-FaQYEgmWr1XgOz5l-h6fzeQ-t67x67.jpg",
            getLowQualityArtwork("https://i1.sndcdn.com/artworks-FaQYEgmWr1XgOz5l-h6fzeQ-large.jpg")
        )

        assertEquals(null, getLowQualityArtwork(null))
    }
}