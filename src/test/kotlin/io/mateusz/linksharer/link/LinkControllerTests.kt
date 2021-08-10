package io.mateusz.linksharer.link

import com.fasterxml.jackson.databind.ObjectMapper
import io.mateusz.linksharer.Endpoints
import io.mateusz.linksharer.linkscontainer.LinksContainer
import io.mateusz.linksharer.linkscontainer.LinksContainerService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.*
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.IanaLinkRelations
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import java.util.stream.Collectors


class EntityModelLink : EntityModel<Link>()
class CollectionModelLinks : CollectionModel<EntityModel<Link>>()

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LinkControllerTests {

    @LocalServerPort
    private val port = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Autowired
    private lateinit var linksContainerService: LinksContainerService

    @Autowired
    private lateinit var linksService: LinkService

    @Test
    @Throws(Exception::class)
    fun getLinks() {
        val response = restTemplate.getForObject(this.getUrl(), CollectionModel::class.java)
        assertThat(response).isNotNull
        assertThat(response.content).isNotNull
        assertThat(response.links.getLink(IanaLinkRelations.SELF)).isNotNull
    }

    @Test
    @Throws(Exception::class)
    fun getLinksByContainerId() {
        val container = linksContainerService.createLinksContainer(LinksContainer("test", "test"))
        val linkOne = linksService.createLink(Link("test", "test", container))
        val linkTwo = linksService.createLink(Link("test", "test", container))
        val response: CollectionModelLinks =
            restTemplate.getForObject("${this.getUrl()}/container/${container.id}", CollectionModelLinks::class.java)
        val links = response.content.stream().collect(Collectors.toList())
        assertThat(links[0].content?.id).isEqualTo(linkOne.id)
        assertThat(links[1].content?.id).isEqualTo(linkTwo.id)
        linksService.deleteLink(linkOne)
        linksService.deleteLink(linkTwo)
        linksContainerService.deleteLinksContainer(container)
    }

    @Test
    @Throws(Exception::class)
    fun getLinksById() {
        val container = linksContainerService.createLinksContainer(LinksContainer("test", "test"))
        val link = Link("test", "description", container)
        linksService.createLink(link)
        val response: EntityModel<Link> =
            restTemplate.getForObject("${this.getUrl()}/${link.id}", EntityModelLink::class.java)
        this.assertLinks(response, link)
        val id: Long = link.id
        linksService.deleteLink(id)
        linksContainerService.deleteLinksContainer(container)
    }

    @Test
    @Throws(Exception::class)
    fun postLinks() {
        val container = linksContainerService.createLinksContainer(LinksContainer("test", "test"))
        val link = Link("test", "url")
        val json = ObjectMapper().writeValueAsString(link).toString()
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val request = HttpEntity(json, headers)
        val response: EntityModel<Link> = restTemplate.postForObject(
            "${this.getUrl()}/container/${container.id}",
            request,
            EntityModelLink::class.java
        )
        assertLinks(response, link)
        val id: Long = response.content?.id!!
        linksService.deleteLink(id)
        linksContainerService.deleteLinksContainer(container)
    }

    @Test
    @Throws(Exception::class)
    fun postLinksByContainerId(){
        val container = linksContainerService.createLinksContainer(LinksContainer("test", "test"))
        val link = Link("test", "url")
        val json = ObjectMapper().writeValueAsString(link).toString()
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val request = HttpEntity(json, headers)
        val response: EntityModel<Link> = restTemplate.postForObject(
            "${this.getUrl()}/container/${container.id}",
            request,
            EntityModelLink::class.java
        )
        assertThat(response.content?.getContainerId()).isEqualTo(container.id)
        linksService.deleteLink(response.content!!.id)
        linksContainerService.deleteLinksContainer(container)
    }

    @Test
    @Throws(Exception::class)
    fun changeContainerById() {
        val containerOne = linksContainerService.createLinksContainer(LinksContainer("test", "test"))
        val containerTwo = linksContainerService.createLinksContainer(LinksContainer("test", "test"))
        var link = linksService.createLink(Link("test", "description", containerOne))
        restTemplate.put("${this.getUrl()}${link.id}?id=${containerTwo.id}", "")
        link = linksService.getLinkById(link.id)
        assertThat(link.getContainerId()).isEqualTo(containerTwo.id)
        linksService.deleteLink(link)
        linksContainerService.deleteLinksContainer(containerOne)
        linksContainerService.deleteLinksContainer(containerTwo)
    }

    fun assertLinks(entityModel: EntityModel<Link>, link: Link) {
        assertThat(entityModel).isNotNull
        assertThat(entityModel.content?.url).contains(link.url)
        assertThat(entityModel.content?.title).contains(link.title)
        assertThat(entityModel.links.getLink(IanaLinkRelations.SELF))
        assertThat(entityModel.links.getLink("all"))
        assertThat(entityModel.links.getLink("container"))
    }

    private fun getUrl(): String {
        return "${Endpoints.BASE_URL}:$port/${Endpoints.LINK_CONTROLLER_PATH}"
    }
}