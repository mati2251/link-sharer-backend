package io.mateusz.linksharer.link

import io.mateusz.linksharer.linkscontainer.LinksContainer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import kotlin.jvm.Throws

@SpringBootTest
class LinkTests {


    @Test
    @Throws(Exception::class)
    fun classTest(){
        val link = Link("test", "test")
        assertThat(link).isNotNull
        assertThat(link.title).isEqualTo("test")
        assertThat(link.url).isEqualTo("test")
    }

    @Test
    @Throws(Exception::class)
    fun setContainerTest(){
        val link = Link("test", "test")
        val linksContainer = LinksContainer("test", "test")
        link.setLinksContainer(linksContainer)
        assertThat(link.getLinksContainer().content).isEqualTo(linksContainer)
    }
}