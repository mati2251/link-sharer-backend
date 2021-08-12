package io.mateusz.linksharer.link

import io.mateusz.linksharer.linkscontainer.LinksContainer
import io.mateusz.linksharer.linkscontainer.LinksContainerService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.jvm.Throws

@SpringBootTest
class LinkServiceTests {

    @Autowired
    private lateinit var linkService: LinkService

    @Autowired
    lateinit var linksContainerService: LinksContainerService

    @Test
    @Throws(Exception::class)
    fun isNotNull() {
        assertThat(linkService).isNotNull
    }

    @Test
    @Throws(Exception::class)
    fun getLinks() {
        linkService.getLinks()
    }

    @Test
    @Throws(Exception::class)
    fun createLinkWithContainer() {
        val container = linksContainerService.createLinksContainer(LinksContainer("test", "test"))
        val link = linkService.createLink(Link("test", "test", container))
        assertThat(link.title).contains("test")
        assertThat(link.url).contains("test")
        linkService.deleteLink(link)
        linksContainerService.deleteLinksContainer(container)
    }

    @Test
    @Throws(Exception::class)
    fun createLinkWithId() {
        val container = linksContainerService.createLinksContainer(LinksContainer("test", "test"))
        val link = linkService.createLink(Link("test", "test"), container.id)
        assertThat(link.title).contains("test")
        assertThat(link.url).contains("test")
        linkService.deleteLink(link)
        linksContainerService.deleteLinksContainer(container)
    }

    @Test
    @Throws(Exception::class)
    fun getLink() {
        val container = linksContainerService.createLinksContainer(LinksContainer("test", "test"))
        val link = linkService.createLink(Link("test", "test", container))
        val actuallyLink = linkService.getLinkById(link.id)
        assertThat(actuallyLink.url).contains(link.url)
        assertThat(actuallyLink.title).contains(link.title)
        linkService.deleteLink(link)
        linksContainerService.deleteLinksContainer(container)
    }

    @Test
    @Throws(Exception::class)
    fun getLinkByContainerId() {
        val container = linksContainerService.createLinksContainer(LinksContainer("test", "test"))
        val link = linkService.createLink(Link("test", "test"), container.id)
        val actuallyLink = linkService.getLinksByContainerId(container.id)[0]
        assertThat(actuallyLink.url).contains(link.url)
        assertThat(actuallyLink.title).contains(link.title)
        linkService.deleteLink(link)
        linksContainerService.deleteLinksContainer(container)
    }
}