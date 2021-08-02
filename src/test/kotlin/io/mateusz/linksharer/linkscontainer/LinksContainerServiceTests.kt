package io.mateusz.linksharer.linkscontainer

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.lang.Exception

@SpringBootTest
class LinksContainerServiceTests {

    @Autowired
    private lateinit var linksContainerService: LinksContainerService

    @Test
    @Throws(Exception::class)
    fun linksContainersServiceIsNotNull(){
        assertThat(linksContainerService).isNotNull
    }

    @Test
    @Throws(Exception::class)
    fun createContainer(){
        val link = linksContainerService.createLinksContainer(LinksContainer("test", "test"))
        assertThat(link.title).contains("test")
        assertThat(link.description).contains("test")
        linksContainerService.removeLinksContainer(link)
    }

    @Test
    @Throws(Exception::class)
    fun getLinksContainers(){
        linksContainerService.getLinksContainers()
    }

    @Test
    @Throws(Exception::class)
    fun getLinkContainers(){
        val link = linksContainerService.createLinksContainer(LinksContainer("test", "test"))
        val actuallyLink = linksContainerService.getLinksContainer(link.id)
        assertThat(actuallyLink.title).contains(link.title)
        assertThat(actuallyLink.description).contains(link.description)
        linksContainerService.removeLinksContainer(link)
    }
}