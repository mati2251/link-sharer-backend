package io.mateusz.linksharer.linkscontainer

import io.mateusz.linksharer.link.Link
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import kotlin.jvm.Throws

@SpringBootTest
class LinksContainerTests {

    @Test
    @Throws(Exception::class)
    fun classTest() {
        val linksContainer = LinksContainer("test", "test")
        assertThat(linksContainer).isNotNull
        assertThat(linksContainer.title).isEqualTo("test")
        assertThat(linksContainer.description).isEqualTo("test")
    }

    @Test
    @Throws(Exception::class)
    fun setLinksTest() {
        val link = Link("test", "test")
        val linksContainer = LinksContainer("test", "test")
        assertThat(linksContainer.getLinks()).isNotNull
        linksContainer.setLinks(setOf(link))
        assertThat(linksContainer.getLinks()?.get(0)?.content).isEqualTo(link)
    }

}